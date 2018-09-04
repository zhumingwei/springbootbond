package com.zhumingwei.bond.entity.base;

import java.util.List;

/**
 * @author zhumingwei
 * @date 2018/9/4 上午9:25
 */
public class ListModel<T> {
    int nextCursor;
    long totalCount;
    List<T> items;

    public int getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(int nextCursor) {
        this.nextCursor = nextCursor;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
