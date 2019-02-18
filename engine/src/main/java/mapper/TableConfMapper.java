package mapper;

import model.TableConf;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

@Repository
public interface TableConfMapper {

	@SelectProvider(type=TableConfMapperProvider.class, method="getAll")
	@ResultMap("mapper.TableConfMapper.BaseResultMap")
    List<TableConf> getAll();

//    TableConf getOne(Integer tid);

    @InsertProvider(type=TableConfMapperProvider.class, method="insert")
    @Options(useGeneratedKeys=true, keyColumn="id", keyProperty="id")
    int insert(TableConf user);

//    void update(TableConf user);


    class TableConfMapperProvider {
    	
    	public static String TABLE = "table_conf";
    	
    	public static String COLUMNS = "id, type, name, topic, create_time,offline_time,creater,is_online"; 
    	
    	public String insert() {
    		return new SQL() {
    			{
    				INSERT_INTO(TABLE);
    				INTO_COLUMNS(COLUMNS);
    				INTO_VALUES("#{id},#{type},#{name},#{topic},#{createTime},#{offlineTime},#{creater},#{isOnline}");
    			}
    		}.toString();
    	}
    	
    	public String getAll() {
    		return new SQL() {
    			{
    				SELECT(COLUMNS);
    				FROM(TABLE);
    			}
    		}.toString();
    	}
    }
}
