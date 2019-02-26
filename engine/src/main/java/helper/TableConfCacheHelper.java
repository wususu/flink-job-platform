package helper;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import conf.MapperHelper;
import javafx.scene.control.Tab;
import mapper.TableConfMapper;
import model.TableConf;

public class TableConfCacheHelper extends CacheHelper<String, TableConf>{

	private TableConfMapper tableConfMapper;
	
	public static final TableConfCacheHelper singleton = new TableConfCacheHelper();
	
	public static TableConfCacheHelper instance() {
		return singleton;
	}
			
	
	private TableConfCacheHelper() {
	}
	
	@Override
	public void prepare() {
		this.tableConfMapper = MapperHelper.getMapper(TableConfMapper.class);
	}

	@Override
	public ConcurrentHashMap<String, TableConf> getNew() {
		List<TableConf> tableConfs = tableConfMapper.getAll();
		ConcurrentHashMap<String, TableConf> map = new ConcurrentHashMap<String, TableConf>();
		for (TableConf tableConf : tableConfs) {
			map.put(tableConf.getName(), tableConf);
		}
		return map;
	}

	
}
