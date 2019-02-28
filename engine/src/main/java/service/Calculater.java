package service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import helper.AviatorExpHelper;
import helper.ComplexAttrMapCacheHelper;
import model.AttrConf;
import model.AttrType;
import model.CalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CalculatorService.AttrValue;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author janke
 * @date 2018/12/11.
 */
public class Calculater {

	public static final Logger LOG = LoggerFactory.getLogger(Calculater.class);
	
	private JedisService jedisService;
	private Map<String, List<AttrConf>> complexMapCache;
	
	public Calculater(JedisService jedisService) {
		this.jedisService = jedisService;
	}
	
	public String cal(AttrValue attrValue) throws Exception{
		complexMapCache = ComplexAttrMapCacheHelper.instance().getAll();
		CalType calType = attrValue.getCalType();
		AttrType attrType = attrValue.getAttrType();
		String restVal = null;

		switch (calType) {
		case APPEND:
				restVal = calAppend(attrValue);
			break;
		case ADD:
				restVal = calAdd(attrValue);
			break;
		case MAX:
				restVal = calMax(attrValue);
			break;
		case MIN:
				restVal = calMin(attrValue);
			break;
		case NEW:
				restVal = calNew(attrValue);
			break;
		case AVIEXP:
				restVal = calAviExp(attrValue);
			break;
		default:
			LOG.error("can not solve type:[{}]", calType);
			throw new IllegalArgumentException(String.format("can not solve type:[%s]", calType));
		}
		if (AttrType.REAL.equals(attrType)) {
			List<AttrConf> complexAttr = complexMapCache.get(attrValue.getAid());

			Map<String,String> calRest = handleComplexAttr(complexAttr, attrValue.getKey());
			for(Map.Entry<String, String> kv: calRest.entrySet()) {
				String aid = kv.getKey();
				String value = kv.getValue();
				jedisService.hset(aid, attrValue.getKey(), value);
			}
		}
		return restVal;
	}
	
	private Map handleComplexAttr(List<AttrConf> complexAttrs, String key) {
		Map<String,String> attrRestVal = Maps.newHashMap();
		for (AttrConf complexAttr: complexAttrs) {
			String exp = complexAttr.getCalExpression();
			Pattern p= Pattern.compile("fr\\d+");
			Matcher m=p.matcher(exp);
			List<String> subAttrIds = Lists.newArrayList();
			while (m.find()) {
				subAttrIds.add(m.group().trim());
			}
			Map<String, Object> env = Maps.newHashMap();
			for (String subAttrId: subAttrIds) {
				String value = jedisService.hget(subAttrId, key);
				env.put(subAttrId, value);
			}
			AttrValue attrValue = new AttrValue();
			attrValue.setExp(exp);
			attrValue.setData(env);
			String restVal = calAviExp(attrValue);
			attrRestVal.put(complexAttr.getAid(), restVal);
		}
		return attrRestVal;
	}
	
	public String calAppend(AttrValue attrValue) {
		if (!String.class.equals(attrValue.getValClazz())) {
			LOG.error("calType is APPEND, but field type isn't String,fieldType:[{}] ", attrValue.getCalType().val);
			throw new IllegalArgumentException(String.format("can not cal APPEND of type: [%s]", attrValue.getValClazz().getName()));
		}
		Object currentVal = attrValue.getCurrentValue();
		Object newVal = attrValue.getNewValue();
		if (newVal == null) {
			newVal = "nil";
		}
		if (currentVal == null) {
			currentVal = "";
		}
		String restVal = String.valueOf(currentVal) + "," + String.valueOf(newVal);
		return String.valueOf(restVal);
	}
	
	public String calMax(AttrValue attrValue) {
		if (Integer.class.equals(attrValue.getValClazz())) {
			Object currentObj = attrValue.getCurrentValue();
			Object newObj = attrValue.getNewValue();
			if (currentObj == null) {
				currentObj = 0;
			}
			if (newObj == null) {
				newObj = 0;
			}
			Integer restVal = Integer.class.cast(currentObj) <= Integer.class.cast(newObj) ?Integer.class.cast(newObj) : Integer.class.cast(currentObj);
			return String.valueOf(restVal);
		}
		else if( Double.class.equals(attrValue.getValClazz())) {
			Object currentObj = attrValue.getCurrentValue();
			Object newObj = attrValue.getNewValue();
			if (currentObj == null) {
				currentObj = 0;
			}
			if (newObj == null) {
				newObj = 0;
			}
			Double restVal = Double.class.cast(currentObj) <= Double.class.cast(newObj) ?Double.class.cast(newObj) : Double.class.cast(currentObj);
			return String.valueOf(restVal);
		}else {
			LOG.error("can not cal Max of type: [{}]", attrValue.getValClazz().getName());
			throw new IllegalArgumentException(String.format("can not cal Max of type: [%s]", attrValue.getValClazz().getName()));
		}
	}
	
	public String calMin(AttrValue attrValue) {
		if (Integer.class.equals(attrValue.getValClazz())) {
			Object currentObj = attrValue.getCurrentValue();
			Object newObj = attrValue.getNewValue();
			if (currentObj == null) {
				currentObj = 0;
			}
			if (newObj == null) {
				newObj = 0;
			}
			Integer restVal = Integer.class.cast(currentObj) >= Integer.class.cast(newObj) ?Integer.class.cast(newObj) : Integer.class.cast(currentObj);
			return String.valueOf(restVal);
		}
		else if( Double.class.equals(attrValue.getValClazz())) {
			Object currentObj = attrValue.getCurrentValue();
			Object newObj = attrValue.getNewValue();
			if (currentObj == null) {
				currentObj = 0;
			}
			if (newObj == null) {
				newObj = 0;
			}
			Double restVal = Double.class.cast(currentObj) >= Double.class.cast(newObj) ?Double.class.cast(newObj) : Double.class.cast(currentObj);
			return String.valueOf(restVal);
		}else {
			LOG.error("can not cal Min of type: [{}]", attrValue.getValClazz().getName());
			throw new IllegalArgumentException(String.format("can not cal Min of type: [%s]", attrValue.getValClazz().getName()));
		}
	}
	
	public String calAdd(AttrValue attrValue) {
		if (Integer.class.equals(attrValue.getValClazz())) {
			Object currentObj = attrValue.getCurrentValue();
			Object newObj = attrValue.getNewValue();
			if (currentObj == null) {
				currentObj = 0;
			}
			if (newObj == null) {
				newObj = 0;
			}
			Integer restVal = Integer.class.cast(currentObj) + Integer.class.cast(newObj);
			return String.valueOf(restVal);
		}
		else if (Double.class.equals(attrValue.getValClazz())) {
			Object currentObj = attrValue.getCurrentValue();
			Object newObj = attrValue.getNewValue();
			if (currentObj == null) {
				currentObj = 0;
			}
			if (newObj == null) {
				newObj = 0;
			}
			Double restVal = Double.class.cast(currentObj) + Double.class.cast(newObj);
			return String.valueOf(restVal);
		}
		else {
			LOG.error("can not cal ADD of type: [{}]", attrValue.getValClazz().getName());
			throw new IllegalArgumentException(String.format("can not cal ADD of type: [%s]", attrValue.getValClazz().getName()));
		}
	}

	public String calNew(AttrValue attrValue) {
		Object currentObj = attrValue.getCurrentValue();
		Object newObj = attrValue.getNewValue();
		return String.valueOf(newObj);
	}
	
	public String calAviExp(AttrValue attrValue) {	
		String exp = attrValue.getExp();
		Map<String, Object> env = attrValue.getData();
		return AviatorExpHelper.excute(exp, env);
	}

	public static void main(String[] args) {

	}
}
