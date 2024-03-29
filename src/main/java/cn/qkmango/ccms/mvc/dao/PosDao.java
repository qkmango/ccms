package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.dto.PosDto;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.PosVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-28 16:36
 */
@Mapper
public interface PosDao {

    int count();

    List<PosVO> list(Pagination<PosDto> pagination);

    PosVO detail(String id);

    int update(Pos pos);
}
