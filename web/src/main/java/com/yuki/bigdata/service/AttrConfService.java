package com.yuki.bigdata.service;

import com.google.common.base.Preconditions;
import com.yuki.bigdata.dto.AttrConfQuery;
import com.yuki.bigdata.mapper.AttrConMapper;
import common.PageList;
import common.XPage;
import model.AttrConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: janke
 * @description:
 **/
@Service
public class AttrConfService {

    @Autowired
    private AttrConMapper attrConMapper;

    public boolean create(AttrConf attrConf) {
        Preconditions.checkNotNull(attrConf.getAid(), "aid is required");
        int ret = attrConMapper.insert(attrConf);
        return ret == 1;
    }

    public XPage<AttrConf> page(AttrConfQuery attrConfQuery) {
        PageList pageList = attrConMapper.page(attrConfQuery);
        return XPage.wrap(pageList);
    }

}
