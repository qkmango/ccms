package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.ExceptionInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 异常信息dao
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-19 20:44
 */
@Mapper
public interface ExceptionInfoDao extends BaseDao<ExceptionInfo, Integer> {
    @Override
    int insert(ExceptionInfo info);
}
