package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Pay;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-24 20:35
 */
@Mapper
public interface PayDao {
    int insert(Pay pay);

    int update(Pay pay);

    int delete(String id);
}
