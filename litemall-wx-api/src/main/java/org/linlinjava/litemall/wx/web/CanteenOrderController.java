package org.linlinjava.litemall.wx.web;

import com.github.pagehelper.PageInfo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.CanteenOrder;
import org.linlinjava.litemall.db.domain.Config;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wx/canteenOrder")
@Validated
public class CanteenOrderController {
    @Autowired
    private CanteenOrderService canteenOrderService;
    @Autowired
    private LitemallMealOrderService mealOrderService;
    @Autowired
    private LitemallUserService userService;
    @Autowired
    private litemallBcUserService bcUserService;
    @Autowired
    private ConfigService configService;

    @RequestMapping("listByIdAndDate")
    public Object getByIdAndDateRange(@RequestParam Integer userId, @RequestParam String date) {
        System.out.println(userId);
        System.out.println(date);
        List<CanteenOrder> canteenOrders = canteenOrderService.queryByUidAndDateRange(userId, date);
        return ResponseUtil.ok(canteenOrders);
    }

    /**
     * 获取前十条订餐记录
     *
     * @return
     */
    @RequestMapping("list")
    public Object get10Record(@LoginUser Integer userId) {
        if (userId != null) { //token过期，提示一下
            //根据userId查询用户状态是否验证
            LitemallUser user = userService.findById(userId);
            if (user != null) {
                Integer bcUserId = user.getBcUserId();
                if (bcUserId == null) {
                    return ResponseUtil.unlogin();
                }
                LitemallBcUser bcUser = bcUserService.findById(bcUserId);
                if (bcUser.getStatus() == 0) { //未激活
                    return ResponseUtil.notActive();
                }
            }
        } else {
            return ResponseUtil.unlogin();
        }

        PageInfo<CanteenOrder> canteenOrders = canteenOrderService.queryByUidAndDate(userId);
        return ResponseUtil.ok(canteenOrders);
    }

    /**
     * 订单核销
     *
     * @return
     */
    @RequestMapping("check")
    public Object orderCheck(@LoginUser Integer userId, @RequestParam Integer orderId, @RequestParam String code) {
        if (userId != null) { //token过期，提示一下
            //根据userId查询用户状态是否验证
            LitemallUser user = userService.findById(userId);
            if (user != null) {
                Integer bcUserId = user.getBcUserId();
                if (bcUserId == null) {
                    return ResponseUtil.unlogin();
                }
                LitemallBcUser bcUser = bcUserService.findById(bcUserId);
                if (bcUser.getStatus() == 0) { //未激活
                    return ResponseUtil.notActive();
                }
            }
        } else {
            return ResponseUtil.unlogin();
        }
        Config config = configService.queryByName("checkCode");
        String checkCode = config.getValue();
        if(!checkCode.equals(code)){
            return ResponseUtil.badArgument();
        }
        int num1 = canteenOrderService.check(userId, orderId);
        int num2 = mealOrderService.check(userId, orderId);
        return ResponseUtil.ok();
    }
    /**
     * 订单核销
     *
     * @return
     */
    @RequestMapping("dailyList")
    public Object dailyList( @RequestParam String date) {
//        if (userId != null) { //token过期，提示一下
//            //根据userId查询用户状态是否验证
//            LitemallUser user = userService.findById(userId);
//            if (user != null) {
//                Integer bcUserId = user.getBcUserId();
//                if (bcUserId == null) {
//                    return ResponseUtil.unlogin();
//                }
//                LitemallBcUser bcUser = bcUserService.findById(bcUserId);
//                if (bcUser.getStatus() == 0) { //未激活
//                    return ResponseUtil.notActive();
//                }
//            }
//        } else {
//            return ResponseUtil.unlogin();
//        }

        List<CanteenOrder> canteenOrders =canteenOrderService.queryByDate(date);

        return ResponseUtil.ok(canteenOrders);
    }

}
