package com.brycehan.boot.ma.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.api.ma.vo.MaUserApiVo;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.constant.JwtConstants;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.common.base.context.LoginUserContext;
import com.brycehan.boot.ma.MaConstants;
import com.brycehan.boot.ma.convert.MaUserConvert;
import com.brycehan.boot.ma.dto.MaLoginDto;
import com.brycehan.boot.ma.dto.MaUserDto;
import com.brycehan.boot.ma.dto.MaUserPageDto;
import com.brycehan.boot.ma.entity.MaUser;
import com.brycehan.boot.ma.mapper.MaUserMapper;
import com.brycehan.boot.ma.service.MaUserService;
import com.brycehan.boot.ma.vo.MaUserLoginVo;
import com.brycehan.boot.ma.vo.MaUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 微信小程序用户服务实现
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MaUserServiceImpl extends BaseServiceImpl<MaUserMapper, MaUser> implements MaUserService {

    private final WxMaService wxMaService;
    private final StringRedisTemplate stringRedisTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 更新微信小程序用户
     *
     * @param maUserDto 微信小程序用户Dto
     */
    public MaUserVo update(MaUserDto maUserDto) {

        MaUser maUser = MaUserConvert.INSTANCE.convert(maUserDto);
        String openId = LoginUserContext.currentOpenId();
        maUser.setOpenId(openId);
        MaUser user = getByOpenId(openId);
        maUser.setId(user.getId());
        this.baseMapper.updateById(maUser);
        return MaUserConvert.INSTANCE.convert(maUser);
    }

    @Override
    public PageResult<MaUserVo> page(MaUserPageDto maUserPageDto) {
        IPage<MaUser> page = this.baseMapper.selectPage(getPage(maUserPageDto), getWrapper(maUserPageDto));
        return new PageResult<>(page.getTotal(), MaUserConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param maUserPageDto 微信小程序用户分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<MaUser> getWrapper(MaUserPageDto maUserPageDto){
        LambdaQueryWrapper<MaUser> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    @Override
    public void export(MaUserPageDto maUserPageDto) {
        List<MaUser> maUserList = this.baseMapper.selectList(getWrapper(maUserPageDto));
        List<MaUserVo> maUserVoList = MaUserConvert.INSTANCE.convert(maUserList);
        ExcelUtils.export(MaUserVo.class, "微信小程序用户_".concat(DateTimeUtils.today()), "微信小程序用户", maUserVoList);
    }

    @Override
    public MaUser getByOpenId(String openId) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<MaUser>().eq(MaUser::getOpenId, openId));
    }

    @Override
    public MaUserLoginVo login(MaLoginDto maLoginDto) throws WxErrorException {
        WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(maLoginDto.getCode());
        MaUser maUser = this.getByOpenId(sessionInfo.getOpenid());

        // 获取手机号
        WxMaPhoneNumberInfo phoneNoInfo = null;
        if (StringUtils.isNoneEmpty(maLoginDto.getEncryptedData()) && StringUtils.isNoneEmpty(maLoginDto.getIv())) {
            phoneNoInfo = this.wxMaService.getUserService().getPhoneNoInfo(
                    sessionInfo.getSessionKey(),
                    maLoginDto.getEncryptedData(),
                    maLoginDto.getIv());
        }

        log.info("openid：{}，手机号：{}", sessionInfo.getOpenid(), phoneNoInfo.getPhoneNumber());
        if (maUser == null) {
            // 新增小程序用户
            maUser = new MaUser();
            maUser.setId(IdGenerator.nextId());
            maUser.setOpenId(sessionInfo.getOpenid());
            maUser.setUnionId(sessionInfo.getUnionid());
            maUser.setAccount(RandomStringUtils.randomAlphanumeric(10));
            maUser.setSessionKey(sessionInfo.getSessionKey());
            maUser.setSessionCreatedTime(LocalDateTime.now());
            if (phoneNoInfo != null){
                maUser.setPhone(phoneNoInfo.getPhoneNumber());
            }
            try {
                this.save(maUser);
            } catch (DuplicateKeyException e) {
                if (e.getMessage().contains("uk_appid_openid")) {
                    maUser = this.getByOpenId(sessionInfo.getOpenid());
                }
            }
        } else {
            // 更新小程序用户
            maUser.setSessionKey(sessionInfo.getSessionKey());
            maUser.setSessionCreatedTime(LocalDateTime.now());
            maUser.setUnionId(sessionInfo.getUnionid());
            if (phoneNoInfo != null){
                maUser.setPhone(phoneNoInfo.getPhoneNumber());
            }

            this.updateById(maUser);
        }

        // 保存session会话信息，有效期3天
        String sessionKey = MaConstants.MA_SESSION_KEY.concat(sessionInfo.getOpenid());
        stringRedisTemplate.opsForValue().set(sessionKey, sessionInfo.getSessionKey(), 71, TimeUnit.HOURS);

        // 生成 jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstants.LOGIN_OPEN_ID, maUser.getOpenId());
        String token = this.jwtTokenProvider.generateToken(claims, JwtConstants.APP_EXPIRE_MINUTE);

        MaUserLoginVo loginVo = new MaUserLoginVo();
        BeanUtils.copyProperties(maUser, loginVo);
        loginVo.setToken(JwtConstants.TOKEN_PREFIX.concat(token));

        return loginVo;
    }

    @Override
    public MaUserApiVo loadMaUserByOpenid(String openid) {
        MaUser maUser = getByOpenId(openid);
        if (maUser != null) {
            MaUserApiVo maUserApiVo = new MaUserApiVo();
            BeanUtils.copyProperties(maUser, maUserApiVo);
            return maUserApiVo;
        }
        return null;
    }

}
