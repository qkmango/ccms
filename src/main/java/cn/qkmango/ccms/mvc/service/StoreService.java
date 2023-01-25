package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Store;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.StoreAndAreaVO;

import java.util.List;
import java.util.Locale;

/**
 * 描述
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-29 18:09
 */
public interface StoreService {
    /**
     * 添加商户
     *
     * @param store  商户
     * @param locale 语言环境
     * @throws InsertException 添加异常
     */
    void insert(Store store, Locale locale) throws InsertException;

    /**
     * 删除商户
     *
     * @param id     商户id
     * @param locale 语言环境
     * @throws DeleteException 删除异常
     */
    void delete(String id, Locale locale) throws DeleteException;

    /**
     * 获取商户列表
     *
     * @param pagination 分页查询条件
     * @return 商户列表
     */
    R<List<StoreAndAreaVO>> list(Pagination<Store> pagination);

    /**
     * 获取商户详细信息
     *
     * @param id 商户id
     * @return 商户详细信息
     */
    StoreAndAreaVO detail(String id);

    /**
     * 修改商户信息
     *
     * @param store  新的商户信息
     * @param locale 语言环境
     * @throws UpdateException 修改失败
     */
    void update(Store store, Locale locale) throws UpdateException;
}
