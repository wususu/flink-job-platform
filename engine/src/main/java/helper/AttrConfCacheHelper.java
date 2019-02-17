package helper;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Maps;

import conf.MapperHelper;
import mapper.AttrConfMapper;
import model.AttrConf;


public class AttrConfCacheHelper extends CacheHelper<String, AttrConf>{

	private AttrConfMapper attrConfMapper;
	
	@Override
	public ConcurrentHashMap<String, AttrConf> getNew() {
		// TODO Auto-generated method stub
		List<AttrConf> attrConfs =  attrConfMapper.getAll();
		ConcurrentHashMap<String, AttrConf> map = new ConcurrentHashMap();
		if (attrConfs != null && !attrConfs.isEmpty()) {
			for (AttrConf attrConf : attrConfs) {
				map.put(attrConf.getAid(), attrConf);
			}
		}
		return map;
	}

	@Override
	public void prepare() {
		attrConfMapper = MapperHelper.getMapper(AttrConfMapper.class);
	}

}