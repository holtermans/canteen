package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.ConfigMapper;
import org.linlinjava.litemall.db.domain.Config;
import org.linlinjava.litemall.db.domain.ConfigExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    @Autowired
    private ConfigMapper configMapper;


    public Config queryByName(String name){
        ConfigExample example = new ConfigExample();
        example.or().andNameEqualTo(name).andDeletedEqualTo(false);
        return  configMapper.selectOneByExample(example);
    }
}
