package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.PosParam;
import cn.qkmango.ccms.domain.vo.PosVO;
import cn.qkmango.ccms.mvc.dao.PosDao;
import cn.qkmango.ccms.mvc.service.PosService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * 描述
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-28 16:36
 */
@Service
public class PosServiceImpl implements PosService {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private PosDao dao;

    /**
     * 添加刷卡机
     * TODO 修改表结构，密码字段待处理
     * @param pos    刷卡机
     * @param locale 语言环境
     * @return 新添加的刷卡机ID
     * @throws InsertException 插入异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String add(Pos pos, Locale locale) throws InsertException {
        //设置默认密码
//        pos.setPassword(POS_DEFAULT_PASSWORD);
        int affectedRows = dao.add(pos);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.pos.add.failure", null, locale));
        }
        return dao.lastInsertId();
    }

    /**
     * 删除刷卡机
     *
     * @param id     刷卡机id
     * @param locale 语言环境
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(String id, Locale locale) throws DeleteException {
        int affectedRows = dao.delete(id);
        if (affectedRows != 1) {
            throw new DeleteException(messageSource.getMessage("db.pos.delete.failure", null, locale));
        }
    }

    /**
     * 分页查询刷卡机
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    @Override
    public R<List<PosVO>> list(Pagination<PosParam> pagination) {
        List<PosVO> list = dao.list(pagination);
        return new R<List<PosVO>>().setSuccess(true).setData(list).setCount(dao.count());
    }

    /**
     * 获取刷卡机详细信息
     *
     * @param id 刷卡机id
     * @return 刷卡机详细信息
     */
    @Override
    public PosVO detail(String id) {
        return dao.detail(id);
    }

    /**
     * 修改刷卡机信息
     *
     * @param pos    刷卡机信息
     * @param locale 语言环境
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Pos pos, Locale locale) throws UpdateException {
        int affectedRows = dao.update(pos);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.pos.update.failure", null, locale));
        }
    }
}

