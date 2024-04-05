package com.brycehan.boot.mp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.io.Serial;

/**
 * 微信公众号带参二维码entity
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@TableName("brc_mp_qr_code")
public class MpQrCode implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 是否为临时二维码
     */
    private Boolean isTemporary;

    /**
     * 场景值ID
     */
    private String sceneStr;

    /**
     * 二维码ticket
     */
    private String ticket;

    /**
     * 二维码图片解析后的地址
     */
    private String url;

    /**
     * 该二维码失效时间
     */
    private LocalDateTime expireTime;

    /**
     * 该二维码创建时间
     */
    private LocalDateTime createTime;

}
