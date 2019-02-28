package helper;

import conf.MapperHelper;
import mapper.AttrConfMapper;
import model.AttrConf;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: janke
 * @description:
 **/
public class ComplexAttrCacheHelper extends CacheHelper<String, List<String>> {

    private AttrConfMapper attrConfMapper;

    public static final ComplexAttrCacheHelper singleton = new ComplexAttrCacheHelper();

    public static ComplexAttrCacheHelper instance() {
        return singleton;
    }

    protected void prepare() {
        this.attrConfMapper = MapperHelper.getMapper(AttrConfMapper.class);
    }

    protected ConcurrentHashMap<String, List<String>> getNew() {
        ConcurrentHashMap<String, List<String>> cache = new ConcurrentHashMap<String, List<String>>();
        List<AttrConf> attrConfs = attrConfMapper.getAll();
        for (AttrConf attrConf: attrConfs) {
            String preAid = attrConf.getAid();
            List<String> complexIds = attrConfMapper.getComplexAttrByRealAttrId(preAid);
            if (!CollectionUtils.isEmpty(complexIds)) {
                cache.put(preAid, complexIds);
            }
        }
        return cache;
    }
}
