package org.linlinjava.litemall.wx.web;

import org.checkerframework.checker.units.qual.A;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.CanteenDishCategory;
import org.linlinjava.litemall.db.service.CanteenCategoryService;
import org.linlinjava.litemall.db.service.LitemallDishesService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/wx/canteenDishCate")
@Validated
public class CanteenDishCateController {

    @Autowired
    private CanteenCategoryService canteenCategoryService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private LitemallDishesService dishesService;

    @RequestMapping("getAllCate")
    public Object getAllCate() {
        List<CanteenDishCategory> categoryList = canteenCategoryService.getAllCategory();
        ArrayList<Object> cateCount = new ArrayList<>();
        for (CanteenDishCategory cdc : categoryList) {
            Integer cateId = cdc.getId();
            long l = dishesService.countByCateId(cateId);
            cateCount.add(l);
        }
        HashMap<Object, Object> resMap = new HashMap<>();
        resMap.put("categoryList", categoryList);
        resMap.put("cateCount", cateCount);
        return ResponseUtil.ok(resMap);
    }

//    @RequestMapping("add")
//    public Object add(@LoginUser Integer operatorId, @RequestBody CanteenDishCategory dishCategory) {
//        Object o = userInfoService.checkUserId(operatorId); //校验userId，这里是操作者id
//        if (o != null) {
//            return o;
//        } else {
//            int add = canteenCategoryService.add(operatorId, dishCategory);
//            if (add >= 1) {
//                return ResponseUtil.ok();
//            } else {
//                return ResponseUtil.fail();
//            }
//        }
//    }

    @RequestMapping("add")
    public Object add(@RequestBody CanteenDishCategory dishCategory) {
           return canteenCategoryService.add(dishCategory) > 0 ? ResponseUtil.ok() : ResponseUtil.fail();
    }

    @RequestMapping("delete")
    public Object add(@RequestParam Integer id) {
        return canteenCategoryService.delete(id) > 0 ? ResponseUtil.ok() : ResponseUtil.fail();
    }
}

