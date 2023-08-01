package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.dto.MessageDto;
import cn.qkmango.ccms.domain.entity.Message;
import cn.qkmango.ccms.domain.pagination.Flow;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.MessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 留言
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-09 15:22
 */
@Mapper
public interface MessageDao extends BaseDao<Message, Integer> {
    int count();

    int insert(Message message);

    int delete(Integer id, Integer author);

    List<MessageVO> list(Pagination<MessageDto> pagination);

    List<Message> flow(Flow<MessageDto> flow);
}
