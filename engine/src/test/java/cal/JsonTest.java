package cal;

import com.alibaba.fastjson.JSONObject;
import model.CUDModel;

/**
 * @author janke
 * @date 2018/12/11.
 */
public class JsonTest {

    public static String update = "{\n" +
            "\t\"database\":\"portal\",\n" +
            "\t\"table\":\"table_label\",\n" +
            "\t\"type\":\"update\",\n" +
            "\t\"ts\":1544441144,\n" +
            "\t\"xid\":12639,\n" +
            "\t\"commit\":true,\n" +
            "\t\"data\":\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\":77,\n" +
            "\t\t\t\t\"tid\":216217,\n" +
            "\t\t\t\t\"label\":\"CDN日志1\",\n" +
            "\t\t\t\t\"creator\":\"dw_zengzhihua\",\n" +
            "\t\t\t\t\"createTime\":\"2018-11-13 10:16:00\",\n" +
            "\t\t\t\t\"remark\":\"暂无描述\"\n" +
            "\t\t\t},\n" +
            "\t\"old\":\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"label\":\"CDN日志\"\n" +
            "\t\t\t}\n" +
            "}";

    public static String insert = "{\n" +
            "\t\"database\":\"portal\",\n" +
            "\t\"table\":\"table_label\",\n" +
            "\t\"type\":\"insert\",\n" +
            "\t\"ts\":1544442871,\n" +
            "\t\"xid\":13367,\n" +
            "\t\"commit\":true,\n" +
            "\t\"data\":\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\":78,\n" +
            "\t\t\t\t\"tid\":122333,\n" +
            "\t\t\t\t\"label\":\"测试日志\",\n" +
            "\t\t\t\t\"creator\":\"dw_wupeijian\",\n" +
            "\t\t\t\t\"createTime\":null,\n" +
            "\t\t\t\t\"remark\":\"无\"\n" +
            "\t\t\t}\n" +
            "}";

    public static String delete = "{\n" +
            "\t\"database\":\"portal\",\n" +
            "\t\"table\":\"table_label\",\n" +
            "\t\"type\":\"delete\",\n" +
            "\t\"ts\":1544443038,\n" +
            "\t\"xid\":13443,\n" +
            "\t\"commit\":true,\n" +
            "\t\"data\":\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\":78,\n" +
            "\t\t\t\t\"tid\":122333,\n" +
            "\t\t\t\t\"label\":\"测试日志\",\n" +
            "\t\t\t\t\"creator\":\"dw_wupeijian\",\n" +
            "\t\t\t\t\"createTime\":null,\n" +
            "\t\t\t\t\"remark\":\"无\"\n" +
            "\t\t\t}\n" +
            "}";

    public static void main(String[] args) {
        CUDModel cudModel = JSONObject.parseObject(update, CUDModel.class);
//        System.out.println(cudModel.getType().());
    }
}
