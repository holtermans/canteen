package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.linlinjava.litemall.db.domain.LitemallTiming;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallTimingService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.LitemallBcUserService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/wx/timing")
@Validated
public class WxTimingController {
    @Autowired
    private LitemallTimingService timingService;
    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallBcUserService bcUserService;

    @RequestMapping("list")
    public Object index() {


        List<LitemallTiming> timing = timingService.queryAll();
        HashMap<Object, Object> timingTotal = new HashMap<>();
        timingTotal.put("timingList", timing);

        return ResponseUtil.ok(timingTotal);
    }


    @GetMapping("update")
    public Object update(@RequestParam int id,
                         @RequestParam String name,
                         @RequestParam byte status,
                         @RequestParam String startTime,
                         @RequestParam String endTime,
                         @RequestParam String stopTime,
                         @LoginUser Integer userId) {

        if (userId != null) { //token过期，提示一下
            //根据userId查询用户状态是否验证
            LitemallUser user = userService.findById(userId);
            if (user != null) {
                int bcUserId = user.getBcUserId();
                LitemallBcUser bcUser = bcUserService.findById(bcUserId);
                if (bcUser.getStatus() == 0) { //未激活
                    return ResponseUtil.notActive();
                }
            }
        } else {
            return ResponseUtil.unlogin();
        }

        LitemallTiming timing = new LitemallTiming();
        timing.setId(id);
        timing.setStartTime(LocalTime.parse(startTime));
        timing.setEndTime(LocalTime.parse(endTime));
        timing.setStatus(status);
        timing.setStopTime(LocalTime.parse(stopTime));

        int i = timingService.updateById(timing);
        return ResponseUtil.ok(id);
    }

}
