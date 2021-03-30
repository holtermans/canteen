package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallTimingMapper;
import org.linlinjava.litemall.db.domain.LitemallTiming;
import org.linlinjava.litemall.db.domain.LitemallTimingExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LitemallTimingService {

    @Autowired
    private LitemallTimingMapper timingMapper;

    public  List<LitemallTiming> queryAll(){
        LitemallTimingExample example = new LitemallTimingExample();
        example.or();
        return timingMapper.selectByExample(example);

    }
    public int updateById(LitemallTiming litemallTiming){
        return  timingMapper.updateByPrimaryKeySelective(litemallTiming);
    }
}
