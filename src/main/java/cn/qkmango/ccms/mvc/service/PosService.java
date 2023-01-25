package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.PosParam;
import cn.qkmango.ccms.domain.vo.PosVO;

import java.util.List;
import java.util.Locale;

/**
 * 刷卡机
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-28 16:36
 */
public interface PosService {
    /**
     * 添加刷卡机
     *
     * @param pos    刷卡机
     * @param locale 语言环境
     * @return 新添加的刷卡机ID
     * @throws InsertException 插入异常
     */
    String add(Pos pos, Locale locale) throws InsertException;

    /**
     * 删除刷卡机
     *
     * @param id     刷卡机id
     * @param locale 语言环境
     */
    void delete(String id, Locale locale) throws DeleteException;

    /**
     * 分页查询刷卡机
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    R<List<PosVO>> list(Pagination<PosParam> pagination);

    /**
     * 获取刷卡机详细信息
     *
     * @param id 刷卡机id
     * @return 刷卡机详细信息
     */
    PosVO detail(String id);

    /**
     * 修改刷卡机信息
     *
     * @param pos    刷卡机信息
     * @param locale 语言环境
     */
    void update(Pos pos, Locale locale) throws UpdateException;
}
