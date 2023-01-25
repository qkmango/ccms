package cn.qkmango.ccms.mvc.service.impl;

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
import cn.qkmango.ccms.mvc.dao.TeachDao;
import cn.qkmango.ccms.mvc.service.TeachService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * 教学部门 Service
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-11 11:18
 */
@Service
public class TeachServiceImpl implements TeachService {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private TeachDao dao;

    /**
     * 添加学院
     *
     * @param faculty 学院
     * @param locale  语言环境
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertFaculty(Faculty faculty, Locale locale) throws InsertException {
        int affectedRows = dao.insertFaculty(faculty);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.faculty.insert.failure", null, locale));
        }
    }

    /**
     * 获取学院分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    @Override
    public R<List<Faculty>> listFaculty(Pagination<Faculty> pagination) {
        List<Faculty> list = dao.listFaculty(pagination);
        int count = dao.count();
        return R.success(list, count);
    }

    /**
     * 删除学院
     *
     * @param id     学院id
     * @param locale 语言环境
     * @throws DeleteException 删除失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteFaculty(String id, Locale locale) throws DeleteException {
        int affectedRows = dao.deleteFaculty(id);
        if (affectedRows != 1) {
            throw new DeleteException(messageSource.getMessage("db.faculty.delete.failure", null, locale));
        }
    }

    /**
     * 修改学院信息
     *
     * @param faculty 学院
     * @param locale  语言环境
     * @throws UpdateException 修改失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateFaculty(Faculty faculty, Locale locale) throws UpdateException {
        int affectedRows = dao.updateFaculty(faculty);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.faculty.update.failure", null, locale));
        }
    }

    /**
     * 学院详细信息
     *
     * @param id 学院 id
     * @return 详细信息
     */
    @Override
    public Faculty detailFaculty(String id) {
        return dao.detailFaculty(id);
    }

    /**
     * 获取学院分页列表
     *
     * @param pagination 分页查询条件
     * @return 学院分页列表
     */
    @Override
    public R<List<SpecialtyVO>> listSpecialty(Pagination<Specialty> pagination) {
        List<SpecialtyVO> list = dao.listSpecialty(pagination);
        int count = dao.count();
        return R.success(list, count);
    }

    /**
     * 专业详细信息
     *
     * @param id 专业 id
     * @return 详细信息
     */
    @Override
    public SpecialtyVO detailSpecialty(String id) {
        return dao.detailSpecialty(id);
    }

    /**
     * 修改专业信息
     *
     * @param specialty 专业
     * @param locale    语言环境
     * @throws UpdateException 修改失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateSpecialty(Specialty specialty, Locale locale) throws UpdateException {
        int affectedRows = dao.updateSpecialty(specialty);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.specialty.update.failure", null, locale));
        }
    }

    /**
     * 添加专业
     *
     * @param specialty 专业
     * @param locale    语言环境
     * @throws InsertException 添加失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertSpecialty(Specialty specialty, Locale locale) throws InsertException {
        int affectedRows = dao.insertSpecialty(specialty);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.specialty.insert.failure", null, locale));
        }
    }

    /**
     * 删除专业
     *
     * @param id     专业id
     * @param locale 语言环境
     * @throws DeleteException 删除失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteSpecialty(String id, Locale locale) throws DeleteException {
        int affectedRows = dao.deleteSpecialty(id);
        if (affectedRows != 1) {
            throw new DeleteException(messageSource.getMessage("db.specialty.delete.failure", null, locale));
        }
    }

    /**
     * 添加班级
     *
     * @param clazz  班级
     * @param locale 语言环境
     * @throws InsertException 添加失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertClazz(Clazz clazz, Locale locale) throws InsertException {
        int affectedRows = dao.insertClazz(clazz);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.clazz.insert.failure", null, locale));
        }
    }

    /**
     * 删除班级
     *
     * @param id     班级 ID
     * @param locale 语言环境
     * @throws DeleteException 删除失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteClazz(String id, Locale locale) throws DeleteException {
        int affectedRows = dao.deleteClazz(id);
        if (affectedRows != 1) {
            throw new DeleteException(messageSource.getMessage("db.clazz.delete.failure", null, locale));
        }
    }

    /**
     * 获取班级分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    @Override
    public R<List<ClazzVO>> listClazz(Pagination<ClazzParam> pagination) {
        List<ClazzVO> list = dao.listClazz(pagination);
        int count = dao.count();
        return R.success(list, count);
    }

    /**
     * 班级详细信息
     *
     * @param id 班级 id
     * @return 详细信息
     */
    @Override
    public ClazzVO detailClazz(String id) {
        return dao.detailClazz(id);
    }

    /**
     * 修改班级信息
     *
     * @param clazz  班级
     * @param locale 语言环境
     * @throws UpdateException 修改失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateClazz(Clazz clazz, Locale locale) throws UpdateException {
        int affectedRows = dao.updateClazz(clazz);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.clazz.update.failure", null, locale));
        }
    }
}
