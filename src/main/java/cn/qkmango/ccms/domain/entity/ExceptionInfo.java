package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.domain.bind.ExceptionInfoState;

/**
 * 异常信息
 * <p>异常信息为系统出现一些错误，并且必须手动解决的</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-19 20:45
 */
public class ExceptionInfo {
    private Integer id;
    // 异常详情描述
    private String description;
    // 关联的ID
    private Long relationId;
    // 解决方案
    private String solution;
    // 异常处理状态
    private ExceptionInfoState state;
    private Integer version;

    public Integer getId() {
        return id;
    }

    public ExceptionInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExceptionInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getRelationId() {
        return relationId;
    }

    public ExceptionInfo setRelationId(Long relationId) {
        this.relationId = relationId;
        return this;
    }

    public String getSolution() {
        return solution;
    }

    public ExceptionInfo setSolution(String solution) {
        this.solution = solution;
        return this;
    }

    public ExceptionInfoState getState() {
        return state;
    }

    public ExceptionInfo setState(ExceptionInfoState state) {
        this.state = state;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public ExceptionInfo setVersion(Integer version) {
        this.version = version;
        return this;
    }
}
