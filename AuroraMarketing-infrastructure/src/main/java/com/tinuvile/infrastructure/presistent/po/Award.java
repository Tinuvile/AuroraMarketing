package com.tinuvile.infrastructure.presistent.po;


import lombok.Data;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 奖品表
 * @since 2025/11/23
 */
@Data
public class Award {

    /** 自增ID */
    private Long id;

    /** 抽奖奖品ID - 内部流转使用 */
    private Integer awardId;

    /** 奖品对接标识 - 对应发奖策略 */
    private String awardKey;

    /** 奖品配置信息 */
    private String awardConfig;

    /** 奖品内容描述 */
    private String awardDesc;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
