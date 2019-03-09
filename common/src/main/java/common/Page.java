package common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: janke
 * @description: 分页，使用方式： <T extend Page> 作为参数传入mapper， 将会自动识别并将该sql进行分页，无需更改mapper的sql
 **/
public class Page {

    enum OrderType {
        DESC("DESC"),
        AES("ASC");

        public String val;

        OrderType(String val) {
            this.val = val;
        }
    }

    private static final int DEFAULT_LIMIT = 10;
    // 请求页号
    private Integer pageNo;
    // 页大小
    private Integer pageSize;
    // page*pagesize
    private Integer offset;
    // 排序的域
    private List<String> orderColumn;
    private String orderType = OrderType.DESC.val;
    private Map<String, Object> params = new HashMap<String, Object>();//其他的参数
    // 总记录
    private Long total;

    public Integer getPageNo() {
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize <= 0) {
            pageSize = DEFAULT_LIMIT;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_LIMIT;
        }
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        if (pageNo == null || pageNo <= 0) {
            return 0;
        }
        offset = (pageNo - 1) * getPageSize();
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<String> getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(List<String> orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        for (OrderType type : OrderType.values()) {
            if (type.val.equalsIgnoreCase(orderType)) {
                this.orderType = type.val;
                return;
            }
        }
        this.orderType = OrderType.DESC.val;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", orderColumn=" + orderColumn +
                ", orderType='" + orderType + '\'' +
                ", params=" + params +
                ", total=" + total +
                '}';
    }
}
