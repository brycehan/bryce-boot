package com.brycehan.boot.mp.entity.po;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 微信公众号粉丝entity
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Data
@TableName("brc_mp_user")
public class MpUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * openid
     */
    @TableId
    private String openId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headImgUrl;

    /**
     * 性别（0未知，1男，2女）
     */
    private Integer sex;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 标签ID列表
     */
    private Object tagIds;

    /**
     * 是否关注
     */
    private Boolean subscribe;

    /**
     * 关注时间
     */
    private LocalDateTime subscribeTime;

    /**
     * 关注场景
     */
    private String subscribeScene;

    /**
     * 扫码场景值
     */
    private String qrSceneStr;

    /**
     * unionid
     */
    private String unionId;

    /**
     * 语言
     */
    private String language;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    public static MpUser create(WxMpUser wxMpUser) {
        MpUser mpUser = new MpUser();
        mpUser.setOpenId(wxMpUser.getOpenId());
        mpUser.setUnionId(wxMpUser.getUnionId());
        mpUser.setSubscribe(wxMpUser.getSubscribe());
        if (wxMpUser.getSubscribe()) {
            mpUser.setSubscribeTime(LocalDateTime.now());
            mpUser.setRemark(wxMpUser.getRemark());
            mpUser.setTagIds(JSONUtil.toJsonStr(wxMpUser.getTagIds()));
            mpUser.setSubscribeScene(wxMpUser.getSubscribeScene());
            String qrScene = wxMpUser.getQrScene();
            String qrSceneStr = StringUtils.isEmpty(qrScene) ? wxMpUser.getQrSceneStr() : qrScene;
            mpUser.setQrSceneStr(qrSceneStr);
        }
        return mpUser;
    }
}
