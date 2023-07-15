package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.mvc.service.DepartmentService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * @param id 父节点id
     * @return
     */
    @GetMapping("/child-list.do")
    public R<List<Department>> childList(Integer id) {
        return R.success(service.childList(id));
    }

}
