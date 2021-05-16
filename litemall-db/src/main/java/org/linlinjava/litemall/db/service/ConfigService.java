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

    public int updateByName(String name,String value){
        Config config = new Config();
        config.setName(name);
        config.setValue(value);
        ConfigExample configExample = new ConfigExample();
        configExample.or().andNameEqualTo(name);
        int i = configMapper.updateByExampleSelective(config, configExample);
        return i;
    }
}
