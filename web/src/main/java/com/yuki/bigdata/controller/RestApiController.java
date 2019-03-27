package com.yuki.bigdata.controller;

import com.yuki.bigdata.dto.AttrConfQuery;
import com.yuki.bigdata.service.AttrConfService;
import com.yuki.bigdata.service.JedisService;

import model.AttrConf;
import model.AttrDto;
import model.AttrType;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
import com.yuki.bigdata.dto.ActionRest;
import com.yuki.bigdata.dto.TableConfQuery;
import com.yuki.bigdata.service.TableConfService;

import common.XPage;
import model.TableConf;
import sun.net.www.content.text.plain;
import sun.tools.jar.resources.jar;

@RestController
@RequestMapping(value="/api")
public class RestApiController {

	@Autowired
	TableConfService tableConfService;

	@Autowired
	AttrConfService attrConfService;
	
	@Autowired
	JedisService jedisService;

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping("/table/page.do")
	public ActionRest page(@RequestBody(required = false) TableConfQuery tableConfQuery) {
		try {
			if (tableConfQuery == null) {
				tableConfQuery = new TableConfQuery();
			}
			return ActionRest.sucess(tableConfService.page(tableConfQuery));
		}catch (Exception e) {
			return ActionRest.error(e);
		}
	}
	
	@RequestMapping(value = "/table/create.do", method = RequestMethod.POST)
	public ActionRest create(@RequestBody TableConf tableConf) {
		try {
			boolean rest = tableConfService.create(tableConf);
			if (rest) {
				return ActionRest.sucess();
			}
			return ActionRest.error("create fail");
		}catch (Exception e) {
			return ActionRest.error(e);
		}
	}
	
	@RequestMapping("/attr/page.do")
	public ActionRest pageAttr(@RequestBody(required = false) AttrConfQuery attrConfQuery) {
		try {
			if (attrConfQuery == null) {
				attrConfQuery = new AttrConfQuery();
			}
			return ActionRest.sucess(attrConfService.page(attrConfQuery));
		}catch (Exception e) {
			return ActionRest.error(e);
		}
	}

	public static final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\D*?(fr\\d+)\\D*?");
	
	@Transactional
	@RequestMapping(value = "/attr/create.do", method = RequestMethod.POST)
	public ActionRest createAttr(@RequestBody AttrDto attrDto) {
		try {
			if (!attrDto.isValid()) {
				return ActionRest.error("参数错误");
			}
			int source = attrDto.getSource();
			TableConf tableConf = tableConfService.get(source);
			if (tableConf == null) {
				return ActionRest.error("数据源表不存在");
			}
			AttrConf attrConf = new AttrConf();
			attrConf.setDbName(tableConf.getName().split("\\.")[0]);
			attrConf.setTblName(tableConf.getName().split("\\.")[1]);
			attrConf.setTableId(tableConf.getId());
			attrConf.setAid(attrDto.getAttrId());
			attrConf.setAttrType(attrDto.getAttrType());
			attrConf.setCalType(attrDto.getCalType());
			attrConf.setCalExpression(attrDto.getAviExp());
			attrConf.setDimensionKey(attrDto.getDimensionKey());
			attrConf.setDimensionType(attrDto.getDimensionType());
			attrConf.setField(attrDto.getField());
			attrConf.setIsOnline(1);
			attrConf.setCreateTime(new Date());
			List<String> realAid = Lists.newArrayList();
			if (attrConf.getAttrType() == 2) {
				Matcher matcher = pattern.matcher(attrConf.getCalExpression());
				while(matcher.find()) {
					realAid.add(matcher.group(1));
				}
			}
			boolean rest = attrConfService.create(attrConf);
			if (rest) {
				if (!realAid.isEmpty()) {
					attrConfService.insertComplexRealMap(attrConf.getAid(), realAid);
				}
				return ActionRest.sucess();
			}
			return ActionRest.error("create fail");
		}catch (Exception e) {
			return ActionRest.error(e);
		}
	}
	
	@RequestMapping(value="/real/result.do")
	public ActionRest searchResult(@RequestBody SearchModel searchModel) {
		String attrId = searchModel.getAttrId();
		String dimensionId = searchModel.getDimensionId();
		String value = jedisService.hget(attrId, dimensionId);
		Map<String, String> data = Maps.newHashMap();
		data.put("attrId", attrId);
		data.put("dimensionId", dimensionId);
		data.put("result", value);
		return ActionRest.sucess(data);
	}
	
	static class SearchModel {
		private String attrId;
		private String dimensionId;
		public String getAttrId() {
			return attrId;
		}
		public void setAttrId(String attrId) {
			this.attrId = attrId;
		}
		public String getDimensionId() {
			return dimensionId;
		}
		public void setDimensionId(String dimensionId) {
			this.dimensionId = dimensionId;
		}
	}
	
	public static void main(String[] args) {
		String aString = "test_maxwell.user_info";
		System.out.println(Arrays.toString(aString.split("\\.")));
		
		String test1 = "fr0901 + 'a' + fr1102 ";
		Matcher matcher = pattern.matcher(test1);
		while(matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}
}
