package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Clazz;
import cn.qkmango.ccms.domain.entity.Faculty;
import cn.qkmango.ccms.domain.entity.Specialty;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.ClazzParam;
import cn.qkmango.ccms.domain.vo.ClazzVO;
import cn.qkmango.ccms.domain.vo.SpecialtyVO;

import java.util.List;
import java.util.Locale;

/**
 * 教学部门
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-11 11:17
 */
public interface TeachService {
    /**
     * 添加学院
     *
     * @param faculty 学院
     * @param locale  语言环境
     * @throws InsertException 添加失败
     */
    void insertFaculty(Faculty faculty, Locale locale) throws InsertException;

    /**
     * 获取学院分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    R<List<Faculty>> listFaculty(Pagination<Faculty> pagination);

    /**
     * 删除学院
     *
     * @param id     学院id
     * @param locale 语言环境
     * @throws DeleteException 删除失败
     */
    void deleteFaculty(String id, Locale locale) throws DeleteException;

    /**
     * 修改学院信息
     *
     * @param faculty 学院
     * @param locale  语言环境
     * @throws UpdateException 修改失败
     */
    void updateFaculty(Faculty faculty, Locale locale) throws UpdateException;

    /**
     * 学院详细信息
     *
     * @param id 学院 id
     * @return 详细信息
     */
    Faculty detailFaculty(String id);

    /**
     * 获取学院分页列表
     *
     * @param pagination 分页查询条件
     * @return 学院分页列表
     */
    R<List<SpecialtyVO>> listSpecialty(Pagination<Specialty> pagination);

    /**
     * 专业详细信息
     *
     * @param id 专业 id
     * @return 详细信息
     */
    SpecialtyVO detailSpecialty(String id);

    /**
     * 修改专业信息
     *
     * @param specialty 专业
     * @param locale    语言环境
     * @throws UpdateException 修改失败
     */
    void updateSpecialty(Specialty specialty, Locale locale) throws UpdateException;

    /**
     * 添加专业
     *
     * @param specialty 专业
     * @param locale    语言环境
     * @throws InsertException 添加失败
     */
    void insertSpecialty(Specialty specialty, Locale locale) throws InsertException;

    /**
     * 删除专业
     *
     * @param id     专业id
     * @param locale 语言环境
     * @throws DeleteException 删除失败
     */
    void deleteSpecialty(String id, Locale locale) throws DeleteException;

    /**
     * 添加班级
     *
     * @param clazz  班级
     * @param locale 语言环境
     * @throws InsertException 添加失败
     */
    void insertClazz(Clazz clazz, Locale locale) throws InsertException;

    /**
     * 删除班级
     *
     * @param id     班级 ID
     * @param locale 语言环境
     * @throws DeleteException 删除失败
     */
    void deleteClazz(String id, Locale locale) throws DeleteException;

    /**
     * 获取班级分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    R<List<ClazzVO>> listClazz(Pagination<ClazzParam> pagination);

    /**
     * 班级详细信息
     *
     * @param id 班级 id
     * @return 详细信息
     */
    ClazzVO detailClazz(String id);

    /**
     * 修改班级信息
     *
     * @param clazz  班级
     * @param locale 语言环境
     * @throws UpdateException 修改失败
     */
    void updateClazz(Clazz clazz, Locale locale) throws UpdateException;
}
