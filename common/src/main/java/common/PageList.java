package common;

import java.util.ArrayList;
import java.util.Collection;

public class PageList<T> extends ArrayList<T> {

    private PageInfo pageInfo;


    public PageList(Page page, Collection<? extends T> e) {
        super(e);
        this.pageInfo = new PageInfo(page);
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }


//    public class PageInfo {
//
//        private Long total;
//        private Integer pageNo;
//        // 页大小
//        private Integer pageSize;
//        private Integer totalPage;
//        private boolean hasNextPage;
//        private int offset;
//        private String orderType;
//        private List<String> orderColumn;
//        public PageInfo() {
//        }
//
//        public PageInfo(Page page) {
//            this.pageNo = page.getPageNo();
//            this.pageSize = page.getPageSize();
//            this.total = page.getTotal();
//            this.offset = page.getOffset();
//  this.orderType = page.getOrderType();
//            this.orderColumn = page.getOrderColumn();
//            this.totalPage= Long.valueOf(Math.floorDiv(total, pageSize)).intValue();
//            this.hasNextPage = pageNo < totalPage;
//        }
//
//        public Long getTotal() {
//            return total;
//        }
//
//        public void setTotal(Long total) {
//            this.total = total;
//        }
//
//        public Integer getPageNo() {
//            return pageNo;
//        }
//
//        public void setPageNo(Integer pageNo) {
//            this.pageNo = pageNo;
//        }
//
//        public Integer getPageSize() {
//            return pageSize;
//        }
//
//        public void setPageSize(Integer pageSize) {
//            this.pageSize = pageSize;
//        }
//
//        public Integer getTotalPage() {
//            return totalPage;
//        }
//
//        public void setTotalPage(Integer totalPage) {
//            this.totalPage = totalPage;
//        }
//
//        public boolean isHasNextPage() {
//            return hasNextPage;
//        }
//
//        public void setHasNextPage(boolean hasNextPage) {
//            this.hasNextPage = hasNextPage;
//        }
//
//        public int getOffset() {
//            return offset;
//        }
//
//        public void setOffset(int offset) {
//            this.offset = offset;
//        }
//
//        public String getOrderType() {
//            return orderType;
//        }
//
//        public void setOrderType(String orderType) {
//            this.orderType = orderType;
//        }
//
//        public List<String> getOrderColumn() {
//            return orderColumn;
//        }
//
//        public void setOrderColumn(List<String> orderColumn) {
//            this.orderColumn = orderColumn;
//        }
//
//        @Override
//        public String toString() {
//            return "XPage{" +
//                    "total=" + total +
//                    ", pageNo=" + pageNo +
//                    ", pageSize=" + pageSize +
//                    ", totalPage=" + totalPage +
//                    ", hasNextPage=" + hasNextPage +
//                    ", offset=" + offset +
//                    '}';
//        }
//    }


    @Override
    public String toString() {
        return "PageList{" +
                "pageInfo=" + pageInfo +
                '}';
    }
}
