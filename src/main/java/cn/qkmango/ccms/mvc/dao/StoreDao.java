package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Store;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.StoreAndAreaVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-29 18:10
 */
@Mapper
public interface StoreDao {
    int count();

    int insert(Store store);

    int delete(String id);

    List<StoreAndAreaVO> list(Pagination<Store> pagination);

    StoreAndAreaVO detail(String id);

    int update(Store store);
}
