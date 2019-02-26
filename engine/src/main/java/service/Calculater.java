package service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;
import com.googlecode.aviator.AviatorEvaluator;

import helper.AviatorExpHelper;
import helper.ComplexAttrMapCacheHelper;
import model.AttrConf;
import model.AttrType;
import model.CalType;
import service.CalculatorService.AttrValue;

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
			handleComplexAttr(complexAttr, attrValue.getKey());
		}
		return restVal;
	}
	
	private Object handleComplexAttr(List<AttrConf> complexAttr, String key) {
		return null;
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
}
