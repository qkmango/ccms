package cn.qkmango.ccms.domain.dto;

import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 可校验的List集合
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-10 20:59
 */
public class DataTransferObjectValidList<E> implements Serializable {
    @Valid
    private List<E> list;

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "DataTransferObjectList{" +
                "list=" + list +
                '}';
    }
}
