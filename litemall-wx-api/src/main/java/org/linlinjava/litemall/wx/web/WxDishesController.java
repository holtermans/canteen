package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.CanteenOrder;
import org.linlinjava.litemall.db.domain.LitemallDishes;
import org.linlinjava.litemall.db.domain.LitemallTiming;
import org.linlinjava.litemall.db.service.LitemallDishesService;
import org.linlinjava.litemall.db.service.LitemallTimingService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/wx/dishes")
@Validated
public class WxDishesController {

    @Autowired
    private LitemallDishesService dishesService;
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("list")
    public Object index() {
        List<LitemallDishes> dishes = dishesService.queryAll();
        HashMap<Object, Object> dishesTotal = new HashMap<>();
        dishesTotal.put("dishesList", dishes);
        return ResponseUtil.ok(dishesTotal);
    }

    @RequestMapping("search")
    public Object searchByKeyword(@RequestParam String keyword) {
        System.out.println(keyword);
        List<LitemallDishes> dishes = dishesService.findByKeyword(keyword);
        HashMap<Object, Object> dishesFilter = new HashMap<>();
        dishesFilter.put("dishesList", dishes);
        return ResponseUtil.ok(dishesFilter);
    }
    @RequestMapping("searchByCateAndKeyword")
    public Object searchByCateAndKeyword(@RequestParam String keyword,
                                         @RequestParam Integer cateId,
                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize
                                         ) {

        List<LitemallDishes> dishes = dishesService.findByCateAndKeyword(keyword,cateId,pageNum,pageSize);
        HashMap<Object, Object> dishesFilter = new HashMap<>();
        dishesFilter.put("dishesList", dishes);
        return ResponseUtil.ok(dishesFilter);
    }

    @RequestMapping("update")
    public Object update(@RequestBody LitemallDishes dish) {
        dishesService.updateById(dish);
        return ResponseUtil.ok();
    }

    @RequestMapping("add")
    public Object add(@RequestBody LitemallDishes dish) {
        dishesService.add(dish);
        return ResponseUtil.ok();
    }

    @RequestMapping("delete")
    public Object delete(@RequestParam Integer id) {
        try {
            dishesService.deleteByid(id);
            return ResponseUtil.ok();
        }catch (Exception e){
            return ResponseUtil.fail();
        }
    }

    @RequestMapping("getBydCateId")
    public Object getByCateId(@LoginUser Integer operatorId, @RequestParam Integer cateId) {
        Object o = userInfoService.checkUserId(operatorId); //校验userId，这里是操作者id
        if (o != null) {
            return o;
        } else {
            List<LitemallDishes> dishes = dishesService.queryByCateId(cateId);
            HashMap<Object, Object> resMap = new HashMap<>();
            resMap.put("dishesList", dishes);
            return ResponseUtil.ok(resMap);
        }
    }

    /**
     * 按条件查询
     *
     * @return
     */
    @RequestMapping("queryByCateIdAndPage")
    public Object queryByUidAndPage(
                                    @RequestParam Integer cateId,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        List<LitemallDishes> litemallDishes = dishesService.queryByCateIdAndPage(cateId, pageNum, pageSize);
        return ResponseUtil.ok(litemallDishes);

    }
}
