package org.linlinjava.litemall.wx.web;

import com.github.pagehelper.PageInfo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.CanteenOrderService;
import org.linlinjava.litemall.db.service.LitemallMealOrderService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.LitemallBcUserService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/wx/mealOrder")
@Validated
public class WxMealOrderController {

    @Autowired
    private LitemallMealOrderService mealOrderService;
    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallBcUserService bcUserService;
    @Autowired
    private CanteenOrderService canteenOrderService;

    @RequestMapping("add")
    public Object save(@LoginUser Integer userId, @RequestBody List<LitemallMealOrder> mealOrder) {
        if (userId != null) { //token过期，提示一下
            //根据userId查询用户状态是否验证
            LitemallUser user = userService.findById(userId);
            if (user != null) {
                Integer bcUserId = user.getBcUserId();
                if(bcUserId == null){
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

        mealOrderService.add(userId,mealOrder);
        return ResponseUtil.ok();
    }

    //查找用户某天的订餐记录
    @RequestMapping("findByUid")
    public Object findByUidAndDate(@LoginUser Integer userId, @RequestParam("date") String date) {
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


        List<LitemallMealOrder> mealOrders = mealOrderService.findByUidAndDate(userId, date);
        HashMap<Object, Object> result = new HashMap<>();
        result.put("mealOrders", mealOrders);
        return ResponseUtil.ok(result);
    }

    @RequestMapping("cancel")
    public Object cancelMealOrder(@LoginUser Integer userId, @RequestParam("date") String date, @RequestParam("timingId") Integer timingId) {
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
        canteenOrderService.deleteByUidAndDateAndTid(userId,date,timingId);
        mealOrderService.deleteByUidAndDateAndTid(userId, date, timingId);
        return ResponseUtil.ok();
    }

    /**
     * 根据订单查询订单信息
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping("findByOrderId")
    public Object findByOrderId(@LoginUser Integer userId, @RequestParam("orderId") Integer orderId) {
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


        List<LitemallMealOrder> mealOrders = mealOrderService.findByOrderId(userId, orderId);
        HashMap<Object, Object> result = new HashMap<>();
        result.put("mealOrders", mealOrders);
        return ResponseUtil.ok(result);
    }

    /**
     * 查询用户个人报餐记录
     *
     * @param userId
     * @return
     */
    @RequestMapping("list")
    public Object listMealOrder(@LoginUser Integer userId) {
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

        PageInfo<LitemallMealOrder> litemallMealOrderPageInfo = mealOrderService.queryByUid(userId);
        return ResponseUtil.ok(litemallMealOrderPageInfo);
    }

    /**
     * 根据日期查找所有的订单
     * @param date
     * @return
     */
    @RequestMapping("listByDate")
    public Object queryByDate(@RequestParam("date") String date) {
        List<LitemallMealOrder> mealOrders = mealOrderService.queryByDate(date);
        HashMap<Object, Object> result = new HashMap<>();
        result.put("mealOrders", mealOrders);
        return ResponseUtil.ok(result);
    }

}
