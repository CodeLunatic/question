package entity;

import java.util.List;

/**
 * 封装分页结果的实体类
 * 【状态：已测试2018年11月29日22:45:22】
 * By CY
 * Date 2018/11/28 19:40
 */
public class PageResult {

    private long total;

    private List rows;

    public PageResult(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
