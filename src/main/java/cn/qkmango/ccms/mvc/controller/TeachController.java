package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Update;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Clazz;
import cn.qkmango.ccms.domain.entity.Faculty;
import cn.qkmango.ccms.domain.entity.Specialty;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.ClazzParam;
import cn.qkmango.ccms.domain.vo.ClazzVO;
import cn.qkmango.ccms.domain.vo.SpecialtyVO;
import cn.qkmango.ccms.mvc.service.TeachService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * 教学部门管理
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-11 11:06
 */
@RestController
@Permission(Role.admin)
@RequestMapping("teach")
public class TeachController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private TeachService service;

    /**
     * 添加学院
     *
     * @param faculty 学院
     * @param locale  语言环境
     * @return 添加结果
     */
    @PostMapping("/faculty/one/insert.do")
    public R insertFaculty(@Validated Faculty faculty, Locale locale) throws InsertException {
        service.insertFaculty(faculty, locale);
        return R.success(messageSource.getMessage("db.faculty.insert.success", null, locale));
    }

    /**
     * 获取学院分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    @PostMapping("/faculty/pagination/list.do")
    public R<List<Faculty>> listFaculty(@RequestBody Pagination<Faculty> pagination) {
        return service.listFaculty(pagination);
    }

    /**
     * 学院详细信息
     *
     * @param id 学院 id
     * @return 详细信息
     */
    @GetMapping("/faculty/one/detail.do")
    public R<Faculty> detailFaculty(@RequestParam String id) {
        Faculty detail = service.detailFaculty(id);
        return R.success(detail);
    }

    /**
     * 删除学院
     *
     * @param id     学院id
     * @param locale 语言环境
     * @return 删除结果
     * @throws DeleteException 删除失败
     */
    @PostMapping("/faculty/one/delete.do")
    public R deleteFaculty(@RequestParam String id, Locale locale) throws DeleteException {
        service.deleteFaculty(id, locale);
        return R.success(messageSource.getMessage("db.faculty.delete.success", null, locale));
    }

    /**
     * 修改学院信息
     *
     * @param faculty 学院
     * @param locale  语言环境
     * @return 修改结果
     * @throws UpdateException 修改失败
     */
    @PostMapping("/faculty/one/update.do")
    public R updateFaculty(@Validated(Update.class) Faculty faculty, Locale locale) throws UpdateException {
        service.updateFaculty(faculty, locale);
        return R.success(messageSource.getMessage("db.faculty.update.success", null, locale));
    }

    /**
     * 获取专业分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    @PostMapping("/specialty/pagination/list.do")
    public R<List<SpecialtyVO>> listSpecialty(@RequestBody Pagination<Specialty> pagination) {
        return service.listSpecialty(pagination);
    }

    /**
     * 添加专业
     *
     * @param specialty 专业
     * @param locale    语言环境
     * @return 添加结果
     * @throws InsertException 添加失败
     */
    @PostMapping("/specialty/one/insert.do")
    public R insertSpecialty(@Validated(Insert.class) Specialty specialty, Locale locale) throws InsertException {
        service.insertSpecialty(specialty, locale);
        return R.success(messageSource.getMessage("db.specialty.insert.success", null, locale));
    }


    /**
     * 专业详细信息
     *
     * @param id 专业 id
     * @return 详细信息
     */
    @GetMapping("/specialty/one/detail.do")
    public R<SpecialtyVO> detailSpecialty(@RequestParam String id) {
        SpecialtyVO detail = service.detailSpecialty(id);
        return R.success(detail);
    }

    /**
     * 修改专业信息
     *
     * @param specialty 专业
     * @param locale    语言环境
     * @return 修改结果
     * @throws UpdateException 修改失败
     */
    @PostMapping("/specialty/one/update.do")
    public R updateSpecialty(@Validated(Update.class) Specialty specialty, Locale locale) throws UpdateException {
        service.updateSpecialty(specialty, locale);
        return R.success(messageSource.getMessage("db.specialty.update.success", null, locale));
    }


    /**
     * 删除专业
     *
     * @param id     专业id
     * @param locale 语言环境
     * @return 删除结果
     * @throws DeleteException 删除失败
     */
    @PostMapping("/specialty/one/delete.do")
    public R deleteSpecialty(@RequestParam String id, Locale locale) throws DeleteException {
        service.deleteSpecialty(id, locale);
        return R.success(messageSource.getMessage("db.specialty.delete.success", null, locale));
    }

    /**
     * 添加班级
     *
     * @param clazz  班级
     * @param locale 语言环境
     * @return 添加结果
     * @throws InsertException 添加失败
     */
    @PostMapping("/clazz/one/insert.do")
    public R insertClazz(@Validated(Insert.class) Clazz clazz, Locale locale) throws InsertException {
        service.insertClazz(clazz, locale);
        return R.success(messageSource.getMessage("db.clazz.insert.success", null, locale));
    }

    /**
     * 删除班级
     *
     * @param id     班级 ID
     * @param locale 语言环境
     * @return 删除结果
     * @throws DeleteException 删除失败
     */
    @PostMapping("/clazz/one/delete.do")
    public R deleteClazz(@RequestParam String id, Locale locale) throws DeleteException {
        service.deleteClazz(id, locale);
        return R.success(messageSource.getMessage("db.clazz.delete.success", null, locale));
    }

    /**
     * 获取班级分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    @PostMapping("/clazz/pagination/list.do")
    public R<List<ClazzVO>> listClazz(@RequestBody Pagination<ClazzParam> pagination) {
        return service.listClazz(pagination);
    }

    /**
     * 班级详细信息
     *
     * @param id 班级 id
     * @return 详细信息
     */
    @GetMapping("/clazz/one/detail.do")
    public R<ClazzVO> detailClazz(@RequestParam String id) {
        ClazzVO detail = service.detailClazz(id);
        return R.success(detail);
    }

    /**
     * 修改班级信息
     *
     * @param clazz  班级
     * @param locale 语言环境
     * @return 修改结果
     * @throws UpdateException 修改失败
     */
    @PostMapping("/clazz/one/update.do")
    public R updateClazz(@Validated(Update.class) Clazz clazz, Locale locale) throws UpdateException {
        service.updateClazz(clazz, locale);
        return R.success(messageSource.getMessage("db.clazz.update.success", null, locale));
    }

}

