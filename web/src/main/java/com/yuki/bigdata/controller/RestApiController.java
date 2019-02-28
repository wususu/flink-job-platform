package com.yuki.bigdata.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class RestApiController {

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
}
