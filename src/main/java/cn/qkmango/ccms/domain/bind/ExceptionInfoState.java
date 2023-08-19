package cn.qkmango.ccms.domain.bind;

/**
 * 异常信息状态
 * 异常信息为系统出现一些错误，并且必须手动解决的
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-19 20:46
 */
public enum ExceptionInfoState {
    /**
     * 待处理
     */
    pending,
    /**
     * 处理中
     */
    processing,
    /**
     * 处理完成
     */
    finish
}
