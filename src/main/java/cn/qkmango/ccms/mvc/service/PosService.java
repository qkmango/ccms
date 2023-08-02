package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.database.DeleteException;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.domain.dto.PosDto;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.PosVO;

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
    PageData<PosVO> list(Pagination<PosDto> pagination);

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
