package com.tscloud.common.framework.rest.view;

import java.io.Serializable;
import java.util.List;

/**
 * {'total':64,'pageNum':1,'maxSize':10,'pageSize':10,'condition':''};
 *
 * @author daowan.hu
 */
public class Page implements Serializable {
    /**
     * 每页数据条数
     */
    private int pageSize = 10;
    /**
     * 页码
     */
    private int pageNum = 1;
    /**
     * 总的数据数
     */
    private int total = 0;
    /**
     * 总页数
     */
    private int totalPageNum = 0;
    private int startRowNum = 0;
    private int endRowNum = 0;
    private Object condition;

    @SuppressWarnings("rawtypes")
    private List rows;

    public Page() {
    }

    public Page(int pageSize, int total) {
        this.pageSize = pageSize;
        this.total = total;
        int mod = total % pageSize;
        totalPageNum = mod == 0 ? (total / pageSize) : (total / pageSize) + 1;
        if (startRowNum <= 0) {
            startRowNum = 0;
            endRowNum = pageSize;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNumber) {
        this.pageNum = pageNumber;
        int startTemp = pageSize * (pageNumber - 1);
        if (startTemp > total) {
            this.startRowNum = total - pageSize;
        } else {
            this.startRowNum = startTemp;
        }
        int temp = pageSize * pageNum;
        if (temp > total) {
            this.endRowNum = total;
        } else {
            this.endRowNum = temp;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        int mod = total % pageSize;
        totalPageNum = mod == 0 ? (total / pageSize) : (total / pageSize) + 1;
        if (startRowNum <= 0) {
            startRowNum = 0;
            endRowNum = pageSize;
        }
        this.startRowNum = (pageSize * (pageNum - 1));
        int temp = pageSize * pageNum;
        if (temp > total) {
            this.endRowNum = total;
        } else {
            this.endRowNum = temp;
        }
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getStartRowNum() {
        return startRowNum;
    }

    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    public int getEndRowNum() {
        int temp = pageSize * pageNum;
        if (temp > total) {
            this.endRowNum = total;
        } else {
            this.endRowNum = (pageSize * pageNum);
        }
        return endRowNum;
    }

    public void setEndRowNum(int endRowNum) {
        this.endRowNum = endRowNum;
    }

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }

    @SuppressWarnings("rawtypes")
    public void setRows(List rows) {
        this.rows = rows;
    }

    @SuppressWarnings("rawtypes")
    public List getRows() {
        return rows;
    }
}
