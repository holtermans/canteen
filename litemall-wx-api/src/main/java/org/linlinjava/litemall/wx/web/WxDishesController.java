package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallDishes;
import org.linlinjava.litemall.db.domain.LitemallTiming;
import org.linlinjava.litemall.db.service.LitemallDishesService;
import org.linlinjava.litemall.db.service.LitemallTimingService;
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


    @RequestMapping("update")
    public Object update(@RequestParam int id,
                         @RequestParam String name,
                         @RequestParam byte status,
                         @RequestParam String startTime,
                         @RequestParam String endTime,
                         @RequestParam String stopTime) {
        LitemallTiming timing = new LitemallTiming();
        timing.setId(id);
        timing.setStartTime(LocalTime.parse(startTime));
        timing.setEndTime(LocalTime.parse(endTime));
        timing.setStatus(status);
        timing.setStopTime(LocalTime.parse(stopTime));
        return ResponseUtil.ok(id);
    }

    @RequestMapping("add")
    public Object add(@RequestBody LitemallDishes dish) {
        dishesService.add(dish);
        return ResponseUtil.ok();
    }

    @RequestMapping("delete")
    public Object delete(@RequestParam Integer id) {
        dishesService.deleteByid(id);
        return ResponseUtil.ok();
    }
}
