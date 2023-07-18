package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.database.DeleteException;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Area;
import cn.qkmango.ccms.domain.pagination.Pagination;

import java.util.List;
import java.util.Locale;

/**
 * 描述
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-29 18:09
 */
public interface AreaService {
    /**
     * 添加区域
     *
     * @param area   区域信息
     * @param locale 语言环境
     * @throws InsertException 添加异常
     */
    void insert(Area area, Locale locale) throws InsertException;

    /**
     * 删除区域
     *
     * @param id     区域id
     * @param locale 语言环境
     * @throws DeleteException 删除异常
     */
    void delete(String id, Locale locale) throws DeleteException;

    /**
     * 获取区域列表
     *
     * @param pagination 分页查询条件
     * @return 区域列表
     */
    R<List<Area>> list(Pagination<Area> pagination);

    /**
     * 修改区域
     *
     * @param area   区域
     * @param locale 语言环境
     * @throws UpdateException 修改异常
     */
    void update(Area area, Locale locale) throws UpdateException;

    /**
     * 获取区域详细信息
     *
     * @param id 区域id
     * @return 区域详细信息
     */
    Area detail(String id);

}
