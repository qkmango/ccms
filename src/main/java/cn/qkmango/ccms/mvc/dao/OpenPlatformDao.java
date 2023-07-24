package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.OpenPlatform;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-24 15:22
 */
@Mapper
public interface OpenPlatformDao extends BaseDao<OpenPlatformDao, Integer> {

    @Override
    OpenPlatformDao getRecordById(Integer id);

    OpenPlatform getRecordByUid(String uid);
}
