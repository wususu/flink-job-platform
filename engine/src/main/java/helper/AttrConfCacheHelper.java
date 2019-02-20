package helper;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;

import conf.MapperHelper;
import mapper.AttrConfMapper;
import model.AttrConf;
import model.XTable;


public class AttrConfCacheHelper extends CacheHelper<XTable, List<AttrConf>>{

	private AttrConfMapper attrConfMapper;
	
	public static final AttrConfCacheHelper singleton = new AttrConfCacheHelper();
	
	public static AttrConfCacheHelper instance() {
		return singleton;
	}
	
	private AttrConfCacheHelper() {
	}
	
	@Override
	protected ConcurrentHashMap<XTable, List<AttrConf>> getNew() {
		List<AttrConf> attrConfs =  attrConfMapper.getAll();
		ConcurrentHashMap<XTable,  List<AttrConf>> map = new ConcurrentHashMap();
		if (attrConfs != null && !attrConfs.isEmpty()) {
			for (AttrConf attrConf : attrConfs) {
				XTable xTable = new XTable(attrConf.getDbName(), attrConf.getTblName());
				attrConfs = map.get(xTable);
				if (attrConfs == null) {
					attrConfs = Lists.newArrayList();
					attrConfs.add(attrConf);
					map.put(xTable, attrConfs);
					continue;
				}
				attrConfs.add(attrConf);
			}
		}
		return map;
	}

	@Override
	protected void prepare() {
		attrConfMapper = MapperHelper.getMapper(AttrConfMapper.class);
	}

}
