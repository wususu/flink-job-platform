package com.yuki.bigdata.controller;

import com.yuki.bigdata.dto.AttrConfQuery;
import com.yuki.bigdata.service.AttrConfService;
import com.yuki.bigdata.service.JedisService;

import model.AttrConf;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.yuki.bigdata.dto.ActionRest;
import com.yuki.bigdata.dto.TableConfQuery;
import com.yuki.bigdata.service.TableConfService;

import common.XPage;
import model.TableConf;
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

	@RequestMapping(value = "/attr/create.do", method = RequestMethod.POST)
	public ActionRest createAttr(@RequestBody AttrConf attrConf) {
		try {
			boolean rest = attrConfService.create(attrConf);
			if (rest) {
				return ActionRest.sucess();
			}
			return ActionRest.error("create fail");
		}catch (Exception e) {
			return ActionRest.error(e);
		}
	}
	
	@RequestMapping(value="/real/result/{attrId}/{dimensionKey}", method=RequestMethod.GET)
	public ActionRest searchResult(@PathVariable("attrId") String attrId, @PathVariable("dimensionKey")String dimensionKey) {
		String value = jedisService.hget(attrId, dimensionKey);
		Map<String, String> data = Maps.newHashMap();
		data.put("attrId", attrId);
		data.put("dimensionKey", dimensionKey);
		data.put("result", value);
		return ActionRest.sucess(data);
	}
}
