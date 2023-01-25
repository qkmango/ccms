package cn.qkmango.ccms.domain.entity;

import java.io.Serializable;

/**
 * 基本实体类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-18 18:56
 */
public interface BaseEntity extends Serializable {
    public String redisKey();
}
