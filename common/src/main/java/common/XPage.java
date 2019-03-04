package common;

import java.util.List;

public class XPage<T> {

    private PageInfo pageInfo;
    private List<T> result;

    public static XPage wrap(PageList pageList) {
        return newInstance(pageList, pageList.getPageInfo());
    }

    public static XPage newInstance(List result, PageInfo pageInfo) {
        return new XPage(result, pageInfo);
    }

    public XPage(List result, PageInfo pageInfo) {
        this.result = result;
        this.pageInfo = pageInfo;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
