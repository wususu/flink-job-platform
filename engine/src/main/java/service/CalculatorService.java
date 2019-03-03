package service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class CalculatorService {

		protected static final Logger LOG = LoggerFactory.getLogger(CalculatorService.class);
	
		@Autowired
		private JedisService jedisService;
		
		
		public List<AttrValue> calculate(CUDModel model, List<AttrConf> attrConfs) {
			List<AttrValue> attrValues = Lists.newArrayList();
				for (AttrConf attrConf : attrConfs) {
					try{
						AttrValue attrValue = null;
					String attrId = attrConf.getAid();
					String diimensionKey =  attrConf.getDimensionKey();
					String dimensionType = attrConf.getDimensionType();
					String diimension = model.getData().get(diimensionKey);
					Preconditions.checkNotNull(diimension, "can not found dimension");
					String key = dimensionType + ":" + diimension;
					CalType calType = CalType.get(attrConf.getCalType());
					Class<?> clazz = getClass(attrConf);
					if (CalType.AVIEXP.equals(calType)) {
						Map<String, Object> data = Maps.newHashMap();
						if (!BinLogType.DELETE.equals(model.getType())) {
							if (!CollectionUtils.isEmpty(model.getOld())) {
								data.putAll(model.getOld());
							}
							if (!CollectionUtils.isEmpty(model.getData())) {
								data.putAll(model.getData());
							}
						}
						attrValue = AttrValue.newAviatorValue(attrConf.getCalExpression(), data, attrId, key, calType, clazz);
						attrValues.add(attrValue);
						continue;
					}

					Object newVal = execNewValue(model, attrConf, clazz);
					attrValue = new AttrValue(newVal, attrId, key, calType, clazz);
					attrValues.add(attrValue);
					}catch (Exception e) {
						LOG.error(e.getMessage(), e);
					}
				}
				return attrValues;
		}
		
		private static Class<?> getClass(AttrConf attrConf) {
			String fieldType = attrConf.getFieldType();
			FieldType type = FieldType.get(fieldType);
			return type.clazz;
		}
		
		private static<T> T execNewValue(CUDModel model, AttrConf attrConf, Class<T> clazz) {
			String field = attrConf.getField();
			String rawVal = model.getData().get(field);
			return clazz.cast(rawVal);
		}
		
		public static class AttrValue{
			private AttrType attrType;
			private Object newValue;
			private String exp;
			private Map<String, Object> data;
			private String aid;
			private String key;
			private CalType calType;
			private Class<?> clazz;

			public AttrValue() {

			}
			public static AttrValue  newAviatorValue(String exp, Map<String, Object> data, String aid, String key, CalType calType, Class<?> clazz) {
				AttrValue attrValue = new AttrValue();
				attrValue.setExp(exp);
				attrValue.setData(data);
				attrValue.setAid(aid);
				attrValue.setKey(key);
				attrValue.setCalType(calType);
				attrValue.setClazz(clazz);
				return attrValue;
			}
			
			public AttrValue(Object newValue, String aid, String key, CalType calType, Class<?> clazz) {
				super();
				this.newValue = newValue;
				this.aid = aid;
				this.key = key;
				this.calType = calType;
				this.clazz = clazz;
			}

			public Object getNewValue() {
				return newValue;
			}

			public void setNewValue(Object newValue) {
				this.newValue = newValue;
			}

			public String getAid() {
				return aid;
			}
			public void setAid(String aid) {
				this.aid = aid;
			}
			public CalType getCalType() {
				return calType;
			}
			public void setCalType(CalType calType) {
				this.calType = calType;
			}
			public String getKey() {
				return key;
			}
			public void setKey(String key) {
				this.key = key;
			}

			public Class<?> getValClazz() {
				return clazz;
			}

			public void setValClazz(Class<?> clazz) {
				this.clazz = clazz;
			}

			public String getExp() {
				return exp;
			}

			public void setExp(String exp) {
				this.exp = exp;
			}

			public Map<String, Object> getData() {
				return data;
			}

			public void setData(Map<String, Object> data) {
				this.data = data;
			}

			public Class<?> getClazz() {
				return clazz;
			}

			public void setClazz(Class<?> clazz) {
				this.clazz = clazz;
			}

			public AttrType getAttrType() {
				return attrType;
			}

			public void setAttrType(AttrType attrType) {
				this.attrType = attrType;
			}
		}
}
