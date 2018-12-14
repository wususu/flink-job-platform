package model;

import java.util.Map;

/**
 * @author janke
 * @date 2018/12/11.
 */
public class CUDModel {

    private String database;
    private String table;
    private BinLogType type;
    private long ts;
    private long xid;
    private boolean commit;
    private Map<String, String> data;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public BinLogType getType() {
        return type;
    }

    public void setType(BinLogType type) {
        this.type = type;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getXid() {
        return xid;
    }

    public void setXid(long xid) {
        this.xid = xid;
    }

    public boolean isCommit() {
        return commit;
    }

    public void setCommit(boolean commit) {
        this.commit = commit;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public boolean isCudOp(){
        if (type != null || type.contains())
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "CUDModel{" +
                "database='" + database + '\'' +
                ", table='" + table + '\'' +
                ", type=" + type +
                ", ts=" + ts +
                ", xid=" + xid +
                ", commit=" + commit +
                ", data=" + data +
                '}';
    }
}
