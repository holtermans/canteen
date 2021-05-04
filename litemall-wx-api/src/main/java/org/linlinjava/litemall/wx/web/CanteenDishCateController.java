package org.linlinjava.litemall.wx.web;

import org.checkerframework.checker.units.qual.A;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.CanteenDishCategory;
import org.linlinjava.litemall.db.service.CanteenCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/wx/canteenDishCate")
@Validated
public class CanteenDishCateController {

    @Autowired
    private CanteenCategoryService canteenCategoryService;
    @RequestMapping("getAllCate")
    public Object getAllCate(){
        List<CanteenDishCategory> categoryList = canteenCategoryService.getAllCategory();
        HashMap<Object, Object> resMap = new HashMap<>();
        resMap.put("categoryList",categoryList);
        return ResponseUtil.ok(resMap);
    }
}
