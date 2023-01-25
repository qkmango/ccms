package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Area;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.dao.AreaDao;
import cn.qkmango.ccms.mvc.service.AreaService;
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
public class AreaServiceImpl implements AreaService {
    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private AreaDao dao;

    /**
     * 添加区域
     *
     * @param area   区域
     * @param locale 语言环境
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(Area area, Locale locale) throws InsertException {
        int affectedRows = dao.insert(area);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.area.insert.failure", null, locale));
        }
    }

    /**
     * 删除区域
     *
     * @param id     区域id
     * @param locale 语言环境
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(String id, Locale locale) throws DeleteException {
        int affectedRows = dao.delete(id);
        if (affectedRows != 1) {
            throw new DeleteException(messageSource.getMessage("db.area.delete.failure", null, locale));
        }
    }

    /**
     * 获取区域详细信息
     *
     * @param id 区域id
     * @return 区域详细信息
     */
    @Override
    public Area detail(String id) {
        return dao.detail(id);
    }

    /**
     * 更新区域信息
     *
     * @param area   区域信息
     * @param locale 语言环境
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Area area, Locale locale) throws UpdateException {
        int affectedRows = dao.update(area);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.area.update.failure", null, locale));
        }
    }

    /**
     * 获取区域列表
     *
     * @param pagination 分页查询条件
     * @return 查询结果
     */
    @Override
    public R<List<Area>> list(Pagination<Area> pagination) {
        List<Area> list = dao.list(pagination);
        return R.success(list).setCount(dao.count());
    }
}
