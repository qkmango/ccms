package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Clazz;
import cn.qkmango.ccms.domain.entity.Faculty;
import cn.qkmango.ccms.domain.entity.Specialty;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.ClazzParam;
import cn.qkmango.ccms.domain.vo.ClazzVO;
import cn.qkmango.ccms.domain.vo.SpecialtyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 教学部门 Dao
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-11 11:18
 */
@Mapper
public interface TeachDao {
    int count();

    /**
     * 添加学院
     *
     * @param faculty 学院
     * @return 受影响的行数
     */
    int insertFaculty(Faculty faculty);

    /**
     * 获取学院分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    List<Faculty> listFaculty(Pagination<Faculty> pagination);

    /**
     * 删除学院
     *
     * @param id 学院id
     * @return 受影响的行数
     */
    int deleteFaculty(String id);

    /**
     * 修改学院信息
     *
     * @param faculty 学院
     * @return 受影响的行数
     */
    int updateFaculty(Faculty faculty);

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
     * @return 分页列表
     */
    List<SpecialtyVO> listSpecialty(Pagination<Specialty> pagination);

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
     * @return 受影响的行数
     */
    int updateSpecialty(Specialty specialty);

    /**
     * 添加专业
     *
     * @param specialty 专业
     * @return 受影响的行数
     */
    int insertSpecialty(Specialty specialty);

    /**
     * 删除专业
     *
     * @param id 专业id
     * @return 受影响的行数
     */
    int deleteSpecialty(String id);

    /**
     * 添加班级
     *
     * @param clazz 班级
     * @return 受影响的行数
     */
    int insertClazz(Clazz clazz);

    /**
     * 删除班级
     *
     * @param id 班级 ID
     * @return 受影响的行数
     */
    int deleteClazz(String id);

    /**
     * 获取班级分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    List<ClazzVO> listClazz(Pagination<ClazzParam> pagination);

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
     * @param clazz 班级
     * @return 受影响的行数
     */
    int updateClazz(Clazz clazz);
}
