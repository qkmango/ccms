package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Update;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Area;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.entity.Store;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.PosParam;
import cn.qkmango.ccms.domain.vo.PosVO;
import cn.qkmango.ccms.domain.vo.StoreAndAreaVO;
import cn.qkmango.ccms.mvc.service.AreaService;
import cn.qkmango.ccms.mvc.service.PosService;
import cn.qkmango.ccms.mvc.service.StoreService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * 商家部门管理
 * <p>区域管理、包含商户管理、刷卡机管理</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-13 16:11
 */
@Permission(Role.admin)
@RestController
@RequestMapping("merchant")
public class MerchantController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private AreaService areaService;

    @Resource
    private StoreService storeService;

    @Resource
    private PosService posService;

    @Value("${ccms.pos.default.password}")
    private String POS_DEFAULT_PASSWORD;


    // ================================ 以下为区域管理 ================================
    /**
     * 获取区域列表
     *
     * @param pagination 分页查询条件
     * @return 区域列表
     */
    @Permission
    @PostMapping("area/pagination/list.do")
    public R<List<Area>> listArea(@RequestBody Pagination<Area> pagination) {
        return areaService.list(pagination);
    }

    /**
     * 添加区域
     *
     * @param area   区域信息
     * @param locale 语言环境
     * @return 添加结果
     * @throws InsertException 添加异常
     */
    @PostMapping("area/one/insert.do")
    public R<Object> insertArea(@Validated(Insert.class) Area area, Locale locale) throws InsertException {
        areaService.insert(area, locale);
        return R.success(messageSource.getMessage("db.area.insert.success", null, locale));
    }

    /**
     * 删除区域
     *
     * @param id     区域id
     * @param locale 语言环境
     * @return 删除结果
     * @throws DeleteException 删除异常
     */
    @PostMapping("area/one/delete.do")
    public R<Object> deleteArea(@RequestParam String id, Locale locale) throws DeleteException {
        areaService.delete(id, locale);
        return R.success(messageSource.getMessage("db.area.delete.success", null, locale));
    }

    /**
     * 修改区域
     *
     * @param area   区域
     * @param locale 语言环境
     * @return 修改结果
     * @throws UpdateException 修改异常
     */
    @PostMapping("area/one/update.do")
    public R<Object> updateArea(@Validated(Update.class) Area area, Locale locale) throws UpdateException {
        areaService.update(area, locale);
        return R.success(messageSource.getMessage("db.area.update.success", null, locale));
    }

    /**
     * 获取区域详细信息
     *
     * @param id 区域id
     * @return 区域详细信息
     */
    @Permission
    @GetMapping("area/one/detail.do")
    public R<Area> detailArea(@RequestParam String id) {
        Area detail = areaService.detail(id);
        return R.success(detail);
    }

    // ================================ 以下为商户管理 ================================

    /**
     * 获取商户列表
     *
     * @param pagination 分页查询条件
     * @return 商户列表
     */
    @PostMapping("store/pagination/list.do")
    public R<List<StoreAndAreaVO>> listStore(@RequestBody Pagination<Store> pagination) {
        return storeService.list(pagination);
    }

    /**
     * 添加商户
     *
     * @param store  商户
     * @param locale 语言环境
     * @return 添加结果
     * @throws InsertException 添加异常
     */
    @PostMapping("store/one/insert.do")
    public R<Object> insertStore(@Validated(Insert.class) Store store, Locale locale) throws InsertException {
        storeService.insert(store, locale);
        return R.success(messageSource.getMessage("db.store.insert.success", null, locale));
    }

    /**
     * 删除商户
     *
     * @param id     商户id
     * @param locale 语言环境
     * @return 删除结果
     * @throws DeleteException 删除异常
     */
    @PostMapping("store/one/delete.do")
    public R<Object> deleteStore(@RequestParam String id, Locale locale) throws DeleteException {
        storeService.delete(id, locale);
        return R.success(messageSource.getMessage("db.store.delete.success", null, locale));
    }

    /**
     * 获取商户详细信息
     *
     * @param id 商户id
     * @return 商户详细信息
     */
    @GetMapping("store/one/detail.do")
    public R<StoreAndAreaVO> detailStore(@RequestParam String id) {
        StoreAndAreaVO detail = storeService.detail(id);
        return R.success(detail);
    }

    /**
     * 修改商户信息
     *
     * @param store  新的商户信息
     * @param locale 语言环境
     * @return 修改结果
     * @throws UpdateException 修改失败
     */
    @PostMapping("store/one/update.do")
    public R<Object> updateStore(@Validated(Update.class) Store store, Locale locale) throws UpdateException {
        storeService.update(store, locale);
        return R.success(messageSource.getMessage("db.store.update.success", null, locale));
    }

    // ------------------------------ 以下为刷卡机管理 ------------------------------

    /**
     * 添加刷卡机
     *
     * @param pos    Pos刷卡机信息
     * @param locale 语言环境
     * @return 添加结果
     * @throws InsertException 添加异常
     */
    @PostMapping("pos/one/insert.do")
    public R insertPos(@Validated(Insert.class) Pos pos, Locale locale) throws InsertException {
        String id = posService.add(pos, locale);
        String[] args = {id, POS_DEFAULT_PASSWORD};
        return R.success(id,messageSource.getMessage("db.pos.add.success", args, locale));
    }

    /**
     * 删除刷卡机
     *
     * @param id     刷卡机id
     * @param locale 语言环境
     * @return 删除结果
     * @throws DeleteException 删除异常
     */
    @PostMapping("pos/one/delete.do")
    public R deletePos(@RequestParam String id, Locale locale) throws DeleteException {
        posService.delete(id, locale);
        return R.success(messageSource.getMessage("db.pos.delete.success", null, locale));
    }

    /**
     * 修改刷卡机信息
     *
     * @return 修改结果
     */
    @PostMapping("pos/one/update.do")
    public R updatePos(@Validated Pos pos, Locale locale) throws UpdateException {
        posService.update(pos,locale);
        return R.success(messageSource.getMessage("db.pos.update.success", null, locale));
    }

    /**
     * 刷卡机列表
     *
     * @param pagination 分页查询条件
     * @return 刷卡机列表
     */
    @PostMapping("pos/pagination/list.do")
    public R<List<PosVO>> listPos(@RequestBody Pagination<PosParam> pagination) {
        return posService.list(pagination);
    }

    /**
     * 获取刷卡机详细信息
     *
     * @param id 刷卡机id
     * @return 刷卡机详细信息
     */
    @Permission({Role.admin, Role.pos})
    @GetMapping("pos/one/detail.do")
    public R<PosVO> detailPos(@RequestParam String id) {
        PosVO detail = posService.detail(id);
        return R.success(detail);
    }



}
