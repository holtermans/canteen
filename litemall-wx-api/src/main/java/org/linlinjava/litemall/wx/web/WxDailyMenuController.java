package org.linlinjava.litemall.wx.web;


import io.swagger.models.auth.In;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallDailyMenu;
import org.linlinjava.litemall.db.service.LitemallDailyMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/dailyMenu")
@Validated
public class WxDailyMenuController {
    @Autowired
    private LitemallDailyMenuService dailyMenuService;

    @RequestMapping("queryByDate")
    public Object queryByDate(@RequestParam String date){
        Object o = dailyMenuService.queryByDate(LocalDate.parse(date));
        HashMap<Object, Object> map = new HashMap<>();
        map.put("dailyMenuList",o);
        return ResponseUtil.ok(map);
    }

    @RequestMapping("queryByDateAndTimingId")
    public Object queryByDateAndTimingId(@RequestParam String date, @RequestParam Integer timingId){

        List<LitemallDailyMenu> litemallDailyMenus = dailyMenuService.queryByDateAndTimingId(LocalDate.parse(date), timingId);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("dailyMenuList",litemallDailyMenus);
        return ResponseUtil.ok(map);
    }
    @RequestMapping("add")
    public Object add(@RequestBody List<LitemallDailyMenu> dailyMenu){
        System.out.println(dailyMenu);
        dailyMenuService.add(dailyMenu);
        return ResponseUtil.ok();
    }

    @RequestMapping("deleteById")
    public Object deleteById(@RequestParam Integer id){

        dailyMenuService.deleteById(id);
        return ResponseUtil.ok();
    }


}
