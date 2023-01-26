package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Notice;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.ConsumeDao;
import cn.qkmango.ccms.mvc.dao.NoticeDao;
import cn.qkmango.ccms.mvc.dao.UserDao;
import cn.qkmango.ccms.mvc.service.AggregationService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聚合接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-07 20:26
 */
@Service
public class AggregationServiceImpl implements AggregationService {

    @Resource
    private CardDao cardDao;
    @Resource
    private UserDao userDao;
    @Resource
    private ConsumeDao consumeDao;
    @Resource
    private NoticeDao noticeDao;

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * 用户欢迎界面聚合数据
     *
     * @param id 学生id
     * @return 当前月消费信息聚合数据
     */
    @Override
    public R<Map<String, Object>> userWelcome(String id) {
        //获取当前月消费次数
        Map<String, Integer> currMonthConsumeInfo = consumeDao.currMonthConsumeInfo(id);

        //查询最新的5条公告
        Pagination<Notice> pagination = new Pagination<>();
        pagination.setPage(1);
        pagination.setLimit(5);
        pagination.setPreview(false);
        List<Notice> noticeList = noticeDao.getNoticeList(pagination);

        //每类消费金额饼图
        List<Map<String, Integer>> currMonthConsumeTypePricePie = consumeDao.currMonthConsumeTypePricePie(id);

        //将查询到的数据封装到map中
        Map<String, Object> data = new HashMap<>(3);
        data.put("currMonthConsumeInfo", currMonthConsumeInfo);
        data.put("noticeList", noticeList);
        data.put("currMonthConsumeTypePricePie", currMonthConsumeTypePricePie);

        return R.success(data);
    }
}