package com.ssh.pojo;

import java.util.List;

public class PageInfo<T> {
    //每页显示数据条数
    private int pageSize;
    //当前页码数
    private int pageNum;
    //数据库中总记录数
    private int rowCount;
    //总页数
    private int pageCount;
    //从多少条记录开始查询
    private int rowStart;
    //是否有上一页
    private boolean hasPrevious = false;
    //上一页
    private int previousPage;
    //首页
    private int firstPage;
    //是否有下一页
    private boolean hasNext = false;
    //下一页
    private int nextPage;
    //末尾页
    private int lastPage;
    //每页显示的页码数
    private int everyPageCount = 10;
    //每页开始的页码数
    private int everyPageStart;
    //每页结束的页码数
    private int everyPageEnd;
    //数据
    private List<T> list;
    //总条数
    private long totalCount;

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

    public PageInfo() {
    }

    public PageInfo(List<T> list) {
        this.list = list;
    }

    public PageInfo(int pageNum, int pageSize, int rowCount, List<T> list) {
        this.pageNum = pageNum == 0 ? 1 : pageNum;
        this.pageSize = pageSize == 0 ? 5 : pageSize;
        this.rowCount = rowCount;
        this.totalCount = rowCount;
        this.list = list;

        //总页数
        this.pageCount = (int) Math.ceil(this.rowCount * 1.0 / this.pageSize);
        //当删除最后一页数据时，会造成pageNum>pageCount,所以赋值。
        if (this.pageNum > this.pageCount) {
            this.pageNum = this.pageCount;
        }

        //每次从第几条记录开始查  select * from onesong limit rowStart ,pageSize
        this.rowCount = (this.pageNum - 1) * this.pageSize;

        //当页码数大于1则存在上一页，和首页
        if (this.pageNum > 1) {
            this.hasPrevious = true;
            this.previousPage = this.pageNum - 1;
            this.firstPage = 1;
        }

        //当页码数小于1则存在下一页，和尾页
        if (this.pageNum < this.pageCount) {
            this.hasNext = true;
            this.nextPage = this.pageNum + 1;
            this.lastPage = this.pageCount;
        }

        //每页显示的页码数的开始和结束
        this.everyPageStart = (this.pageNum - this.pageSize / 2) <= 0 ? 1 : (this.pageNum - this.pageSize / 2);
        this.everyPageEnd = (this.everyPageStart + this.everyPageCount - 1) >= this.pageCount ? pageCount : (this.everyPageStart + this.everyPageCount - 1);
    }
}
