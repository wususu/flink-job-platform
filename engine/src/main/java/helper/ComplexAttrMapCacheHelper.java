package helper;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import conf.MapperHelper;
import mapper.AttrConfMapper;
import model.AttrConf;

public class ComplexAttrMapCacheHelper extends CacheHelper<String, List<AttrConf>> {
	private AttrConfMapper attrConfMapper;
	
	public static ComplexAttrMapCacheHelper singleton;
	
	public static ComplexAttrMapCacheHelper instance() {
		if (singleton == null) {
			synchronized (singleton) {
				if (singleton == null) {
					singleton = new ComplexAttrMapCacheHelper();
				}
			}
		}
		return singleton;
	}
	@Override
	protected void prepare() {
		attrConfMapper = MapperHelper.getMapper(AttrConfMapper.class);
	}

	@Override
	protected ConcurrentHashMap<String, List<AttrConf>> getNew() {
		ConcurrentHashMap<String, List<AttrConf>> cache = new ConcurrentHashMap<>();
		List<AttrConf> attrConfs = attrConfMapper.getAll();
		for (AttrConf attrConf : attrConfs) {
			String attrId = attrConf.getAid();
			List<AttrConf> complexAttrs = attrConfMapper.selectComplexAttrConfs(attrId);
			if (!CollectionUtils.isEmpty(complexAttrs)) {
				cache.put(attrId, complexAttrs);
			}
		}
		return cache;
	}
	
	

}
