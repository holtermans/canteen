package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.CustomDailyMenuMapper;
import org.linlinjava.litemall.db.dao.LitemallDailyMenuMapper;
import org.linlinjava.litemall.db.domain.LitemallAddressExample;
import org.linlinjava.litemall.db.domain.LitemallDailyMenu;
import org.linlinjava.litemall.db.domain.LitemallDailyMenuExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class LitemallDailyMenuService {
    @Autowired
    private LitemallDailyMenuMapper dailyMenuMapper;
    @Autowired
    private CustomDailyMenuMapper customDailyMenuMapper;

    public Object queryByDate(LocalDate date){
        LitemallDailyMenuExample example = new LitemallDailyMenuExample();
        example.or().andDateEqualTo(date);
        List<LitemallDailyMenu> litemallDailyMenus = dailyMenuMapper.selectByExampleSelective(example);
        return litemallDailyMenus;
    }

    public void add(List<LitemallDailyMenu> records){
        customDailyMenuMapper.insertList(records);
    }

    public void deleteById(int id){
        dailyMenuMapper.deleteByPrimaryKey(id);
    }
}
