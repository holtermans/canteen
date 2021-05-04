package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallDishesMapper;
import org.linlinjava.litemall.db.dao.LitemallTimingMapper;
import org.linlinjava.litemall.db.domain.LitemallDishes;
import org.linlinjava.litemall.db.domain.LitemallDishesExample;
import org.linlinjava.litemall.db.domain.LitemallTiming;
import org.linlinjava.litemall.db.domain.LitemallTimingExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallDishesService {

    public static final Integer DEFAULT_LIMIT = 1;
    @Autowired
    private LitemallDishesMapper dishesMapper;

    public  List<LitemallDishes> queryAll(){
        LitemallDishesExample example = new LitemallDishesExample();
        example.or().andLogicalDeleted(false);
        return dishesMapper.selectByExample(example);

    }
    public int updateById(LitemallDishes litemallDishes){
        return  dishesMapper.updateByPrimaryKeySelective(litemallDishes);
    }

    public void add(LitemallDishes dish) {
        dish.setAddTime(LocalDateTime.now());
        dish.setLimit(DEFAULT_LIMIT);
        dishesMapper.insertSelective(dish);
    }

    public void deleteByid(Integer id) {
        dishesMapper.logicalDeleteByPrimaryKey(id);

    }

    public List<LitemallDishes> findByKeyword(String keyword) {
        LitemallDishesExample example = new LitemallDishesExample();
        example.or().andNameLike("%"+keyword+"%").andLogicalDeleted(false);
        List<LitemallDishes> litemallDishes = dishesMapper.selectByExample(example);
        return litemallDishes;
    }

    public LitemallDishes queryById(Integer dishesId) {
        LitemallDishesExample example = new LitemallDishesExample();
        example.or().andIdEqualTo(dishesId).andLogicalDeleted(false);
        LitemallDishes litemallDishes = dishesMapper.selectOneByExample(example);
        return litemallDishes;
    }

    public List<LitemallDishes> queryByCateId(Integer cateId) {
        LitemallDishesExample example = new LitemallDishesExample();
        example.or().andCategoryIdEqualTo(cateId);
        List<LitemallDishes> litemallDishes = dishesMapper.selectByExample(example);
        return litemallDishes;
    }
}
