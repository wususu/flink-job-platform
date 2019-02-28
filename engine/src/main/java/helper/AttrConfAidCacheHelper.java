package helper;

import conf.MapperHelper;
import mapper.AttrConfMapper;
import model.AttrConf;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: janke
 * @description:
 **/
public class AttrConfAidCacheHelper extends CacheHelper<String, AttrConf> {

    private AttrConfMapper attrConfMapper;

    public static final AttrConfAidCacheHelper singleton = new AttrConfAidCacheHelper();

    public static AttrConfAidCacheHelper instance() {
        return singleton;
    }

    @Override
    protected void prepare() {
        attrConfMapper = MapperHelper.getMapper(AttrConfMapper.class);
    }

    @Override
    protected ConcurrentHashMap<String, AttrConf> getNew() {
        ConcurrentHashMap<String, AttrConf> cache = new ConcurrentHashMap<String, AttrConf>();
        List<AttrConf> attrConfs =  attrConfMapper.getAll();
        for (AttrConf attrConf: attrConfs) {
            cache.put(attrConf.getAid(), attrConf);
        }
        return cache;
    }
}
