package com.ssh.pojo;

import java.util.List;

public class MyPageInfo<T> {
    private long totalCount;
    private List<T> list;

    public MyPageInfo() {

    }

    public MyPageInfo(long totalCount, List<T> list) {
        this.totalCount = totalCount;
        this.list = list;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
