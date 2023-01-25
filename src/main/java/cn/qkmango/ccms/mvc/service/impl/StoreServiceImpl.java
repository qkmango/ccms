package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Store;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.StoreAndAreaVO;
import cn.qkmango.ccms.mvc.dao.AreaDao;
import cn.qkmango.ccms.mvc.dao.StoreDao;
import cn.qkmango.ccms.mvc.service.StoreService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * 描述
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-29 18:09
 */
@Service
public class StoreServiceImpl implements StoreService {
    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private StoreDao dao;

    @Resource
    private AreaDao areaDao;

    /**
     * 添加商户
     *
     * @param store  商户
     * @param locale 语言环境
     * @throws InsertException 添加异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(Store store, Locale locale) throws InsertException {
        int affectedRows = dao.insert(store);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.store.insert.failure", null, locale));
        }
    }

    /**
     * 删除商户
     *
     * @param id     商户id
     * @param locale 语言环境
     * @throws DeleteException 删除异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(String id, Locale locale) throws DeleteException {
        int affectedRows = dao.delete(id);
        if (affectedRows != 1) {
            throw new DeleteException(messageSource.getMessage("db.store.delete.failure", null, locale));
        }
    }

    /**
     * 获取商户列表
     *
     * @param pagination 分页查询条件
     * @return 商户列表
     */
    @Override
    public R<List<StoreAndAreaVO>> list(Pagination<Store> pagination) {
        List<StoreAndAreaVO> list = dao.list(pagination);
        return new R<List<StoreAndAreaVO>>()
                .setSuccess(true)
                .setData(list)
                .setCount(dao.count());
    }

    /**
     * 获取商户详细信息
     *
     * @param id 商户id
     * @return 商户详细信息
     */
    @Override
    public StoreAndAreaVO detail(String id) {
        return dao.detail(id);
    }

    /**
     * 修改商户信息
     *
     * @param store  新的商户信息
     * @param locale 语言环境
     * @throws UpdateException 修改失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Store store, Locale locale) throws UpdateException {
        int affectedRows = dao.update(store);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.store.update.failure", null, locale));
        }
    }
}
