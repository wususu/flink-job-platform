package com.yuki.bigdata.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuki.bigdata.dto.TableConfQuery;
import com.yuki.bigdata.mapper.TableConfMapper;

import common.PageList;
import common.XPage;
import model.TableConf;

@Service
public class TableConfService {

	@Autowired
	private TableConfMapper tableConfMapper;
	
	
	public TableConf get(Integer id) {
		return tableConfMapper.get(id);
	}
	
	public boolean create(TableConf tableConf) {
		tableConf.setId(null);
		tableConf.setCreateTime(new Date());
		tableConf.setCreater("admin");
		tableConf.setOfflineTime(null);
		tableConf.setIsOnline(0);
		int rest = tableConfMapper.insert(tableConf);
		return rest == 1;
	}
	
	public XPage<TableConf> page(TableConfQuery tableConfQuery) {
		PageList<TableConf> pageList = tableConfMapper.queryByPage(tableConfQuery);
		return XPage.wrap(pageList);
	}
}
