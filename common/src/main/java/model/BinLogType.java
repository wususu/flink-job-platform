package model;

/**
 * @author janke
 * @date 2018/12/11.
 */
public enum BinLogType {
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    ;

    private String val;

    BinLogType(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public boolean contains() {
        for (BinLogType binType : values()) {
            if (binType.equals(this))
                return true;
        }
        return false;
    }
}
