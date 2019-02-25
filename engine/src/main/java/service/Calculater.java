package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.CalType;
import service.CalculatorService.AttrValue;

/**
 * @author janke
 * @date 2018/12/11.
 */
@Component
public class Calculater {

	public static final Logger LOG = LoggerFactory.getLogger(Calculater.class);
	
	@Autowired
	JedisService jedisService;
	
	public String cal(AttrValue attrValue) throws Exception{
		CalType calType = attrValue.getCalType();
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
		return restVal;
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
		return null;
	}
}
