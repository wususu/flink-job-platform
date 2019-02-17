package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.AttrConfMapper;
import model.AttrConf;

@Service
public class AttrConfService {

	@Autowired
	AttrConfMapper attrConfMapper;
	
	public void create(AttrConf attrConf) {
		attrConfMapper.insert(attrConf);
	}
	
	public List<AttrConf> getAll() {
		return attrConfMapper.getAll();
	}
	
}
