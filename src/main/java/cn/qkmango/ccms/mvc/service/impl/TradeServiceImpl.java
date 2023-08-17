package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.domain.dto.TradeDto;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.TradeVO;
import cn.qkmango.ccms.mvc.dao.AccountDao;
import cn.qkmango.ccms.mvc.dao.TradeDao;
import cn.qkmango.ccms.mvc.service.DepartmentService;
import cn.qkmango.ccms.mvc.service.TradeService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-22 13:44
 */
@Service
public class TradeServiceImpl implements TradeService {

    @Resource
    private TradeDao dao;

    @Resource
    private AccountDao accountDao;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private ReloadableResourceBundleMessageSource ms;


    @Override
    public PageData<Trade> list(Pagination<TradeDto> pagination) {
        List<Trade> list = dao.list(pagination);
        int count = dao.count();
        return PageData.of(list, count);
    }

    @Override
    public Trade record(Long id) {
        return dao.getRecordById(id);
    }

    @Override
    public TradeVO detail(Long id) {
        TradeVO vo = new TradeVO();

        // 交易记录
        Trade trade = dao.getRecordById(id);
        if (trade == null) {
            return null;
        }
        vo.setTrade(trade);

        // 付款方
        Account payerAccount = accountDao.getRecordById(trade.getAccount(), false);
        if (payerAccount != null) {
            LinkedList<Department> chain = departmentService.departmentChain(payerAccount.getDepartment());
            vo.setPayer(payerAccount);
            vo.setPayerDeptChain(chain);
        }

        // 收款方/创建者
        Account creatorAccount = accountDao.getRecordById(trade.getCreator(), false);
        if (creatorAccount != null) {
            LinkedList<Department> chain = departmentService.departmentChain(creatorAccount.getDepartment());
            vo.setCreator(creatorAccount);
            vo.setCreatorDeptChain(chain);
        }

        return vo;
    }
}
