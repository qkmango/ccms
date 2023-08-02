package cn.qkmango.ccms.domain.pagination;

import java.util.List;

/**
 * 分页数据
 * <p>用于分页查询时，返回的数据
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-02 17:56
 */
public class PageData<T> {
    private Integer count;
    private List<T> list;

    public Integer getCount() {
        return count;
    }

    public PageData<T> setCount(Integer count) {
        this.count = count;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public PageData<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public static <T> PageData<T> of(List<T> list, Integer count) {
        return new PageData<T>().setList(list).setCount(count);
    }
}
