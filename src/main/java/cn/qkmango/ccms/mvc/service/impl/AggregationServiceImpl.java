package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.util.DateTimeUtil;
import cn.qkmango.ccms.domain.entity.Notice;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.dao.ConsumeDao;
import cn.qkmango.ccms.mvc.dao.NoticeDao;
import cn.qkmango.ccms.mvc.dao.StatisticDao;
import cn.qkmango.ccms.mvc.service.AggregationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    private ConsumeDao consumeDao;
    @Resource
    private NoticeDao noticeDao;
    @Resource
    private StatisticDao statisticDao;


    /**
     * 用户欢迎界面聚合数据
     *
     * @param id 学生id
     * @return 当前月消费信息聚合数据
     */
    @Override
    public Map<String, Object> userWelcome(String id) {

        //查询最新的5条公告
        Pagination<Notice> pagination = new Pagination<>();
        pagination.setPage(1);
        pagination.setLimit(5);
        pagination.setPreview(false);
        List<Notice> noticeList = noticeDao.list(pagination);

        //查询当前月消费信息
        SimpleDateFormat format = DateTimeUtil.SIMPLE_DATE_WITH_MILLISECOND_FORMAT;
        String start = format.format(DateTimeUtil.getMonthStart().getTime());
        String end = format.format(DateTimeUtil.getMonthEnd().getTime());
        Map<String, Integer> monthConsumeInfo = statisticDao.consumeInfo(id, start, end);


        //每类消费金额饼图
        List<Map<String, Integer>> monthConsumeTypePrice = statisticDao.consumeTypePrice(id, start, end);

        //将查询到的数据封装到map中
        Map<String, Object> data = new HashMap<>(3);
        data.put("notices", noticeList);
        data.put("consumeInfo", monthConsumeInfo);
        data.put("consumeTypePrice", monthConsumeTypePrice);


        return data;
    }
}
