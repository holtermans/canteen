package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.CanteenDishCategoryMapper;
import org.linlinjava.litemall.db.domain.CanteenDishCategory;
import org.linlinjava.litemall.db.domain.CanteenDishCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanteenCategoryService {
    @Autowired
    private CanteenDishCategoryMapper dishCategoryMapper;

    /**
     * 获取所有分类
     * @return
     */
    public List<CanteenDishCategory> getAllCategory(){
        CanteenDishCategoryExample example = new CanteenDishCategoryExample();
        example.or();
        List<CanteenDishCategory> result = dishCategoryMapper.selectByExample(example);
        return result;
    }
}
