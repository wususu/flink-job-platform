package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import model.AttrConf;
import model.CUDModel;

@Service
public class CalculatorService {

		@Autowired
		private JedisService jedisService;
		
		
		public void calculate(CUDModel model, List<AttrConf> attrConfs) {
				for (AttrConf attrConf : attrConfs) {
					String attrId = attrConf.getAid();
					String diimensionKey =  attrConf.getDimensionKey();
					String dimensionType = attrConf.getDimensionType();
					String diimension = model.getData().get(diimensionKey);
					Preconditions.checkNotNull(diimension, "can not found dimension");
					String key = dimensionType + ":" + diimension;
					String old = jedisService.hget(attrId, key);
					
				}
		}
}
