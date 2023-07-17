package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.mvc.service.DepartmentService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * 部门
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-15 18:43
 */
@Validated
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private DepartmentService service;

    /**
     * 获取子节点列表
     * 默认 {@code ID == null} 时获取根节点
     *
     * @param id 父节点id
     * @return
     */
    @GetMapping("/child-list.do")
    public R<List<Department>> childList(Integer id) {
        return R.success(service.childList(id));
    }

    @PostMapping("/one/insert.do")
    public R insert(@Validated(Insert.class) @RequestBody Department department, Locale locale) throws InsertException {
        service.insert(department, locale);
        return R.success(ms.getMessage("db.insert.department.success", null, locale));
    }

}
