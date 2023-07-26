package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<OpenPlatform> state(@Param("account") Integer id);

    int unbind(@Param("account") Integer account, @Param("type") PlatformType type);

    int insert(OpenPlatform platform);

}
