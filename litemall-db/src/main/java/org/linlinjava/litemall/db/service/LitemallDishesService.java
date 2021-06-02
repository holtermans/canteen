package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallDishesMapper;
import org.linlinjava.litemall.db.dao.LitemallTimingMapper;
import org.linlinjava.litemall.db.domain.*;
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
        example.or().andDeletedEqualTo(false);
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
        dishesMapper.deleteByPrimaryKey(id);
    }

    public List<LitemallDishes> findByKeyword(String keyword) {
        LitemallDishesExample example = new LitemallDishesExample();
        example.or().andNameLike("%"+keyword+"%").andDeletedEqualTo(false);
        List<LitemallDishes> litemallDishes = dishesMapper.selectByExample(example);
        return litemallDishes;
    }

    public LitemallDishes queryById(Integer dishesId) {
        LitemallDishesExample example = new LitemallDishesExample();
        example.or().andIdEqualTo(dishesId).andDeletedEqualTo(false);
        LitemallDishes litemallDishes = dishesMapper.selectOneByExample(example);
        return litemallDishes;
    }

    public long countByCateId(Integer cateId) {
        LitemallDishesExample example = new LitemallDishesExample();
        example.or().andCategoryIdEqualTo(cateId).andDeletedEqualTo(false);
        long l = dishesMapper.countByExample(example);
        return l;
    }
    public List<LitemallDishes> queryByCateId(Integer cateId) {
        LitemallDishesExample example = new LitemallDishesExample();
        example.or().andCategoryIdEqualTo(cateId).andDeletedEqualTo(false);
        List<LitemallDishes> litemallDishes = dishesMapper.selectByExample(example);
        return litemallDishes;
    }

    public List<LitemallDishes> queryByCateIdAndPage(Integer cateId, Integer pageNum, Integer pageSize) {
        LitemallDishesExample example = new LitemallDishesExample();

        LitemallDishesExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cateId);
        criteria.andDeletedEqualTo(false);
        example.setOrderByClause("name desc");
        PageHelper.startPage(pageNum,pageSize);
        return dishesMapper.selectByExample(example);
    }

    public List<LitemallDishes> findByCateAndKeyword(String keyword, Integer cateId, Integer pageNum, Integer pageSize) {

        LitemallDishesExample example = new LitemallDishesExample();

        LitemallDishesExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cateId);
        criteria.andNameLike("%"+keyword+"%");
        criteria.andDeletedEqualTo(false);
        example.setOrderByClause("id desc");
        PageHelper.startPage(pageNum,pageSize);
        return dishesMapper.selectByExample(example);
    }
}
