package org.linlinjava.litemall.wx.web;

import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.CanteenOrderService;
import org.linlinjava.litemall.db.service.LitemallMealOrderService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.LitemallBcUserService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.UserInfoService;
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
    private final Log logger = LogFactory.getLog(WxMealOrderController.class);


    @Autowired
    private LitemallMealOrderService mealOrderService;
    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallBcUserService bcUserService;
    @Autowired
    private CanteenOrderService canteenOrderService;
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("add")
    public Object save(@LoginUser Integer userId, @RequestBody List<LitemallMealOrder> mealOrder) {
        logger.info(userId);
        logger.info(mealOrder);
        try {
            Thread.sleep(1*100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object response = userInfoService.checkUserId(userId);
        if (response != null) {
            return response;
        } else {
            try {
                mealOrderService.add(userId, mealOrder);
            }catch (Exception e){
                return ResponseUtil.fail();
            }finally {

            }
            return ResponseUtil.ok();
        }

    }

    /**
     * 根据userId 和 日期查询订单
     * @param userId
     * @param date
     * @return
     */
    //查找用户某天的订餐记录
    @RequestMapping("findByUid")
    public Object findByUidAndDate(@LoginUser Integer userId, @RequestParam("date") String date) {
        Object response = userInfoService.checkUserId(userId);
        if (response != null) {
            return response;
        } else {
            List<LitemallMealOrder> mealOrders = mealOrderService.findByUidAndDate(userId, date);
            HashMap<Object, Object> result = new HashMap<>();
            result.put("mealOrders", mealOrders);
            return ResponseUtil.ok(result);
        }

    }

    @RequestMapping("cancel")
    public Object cancelMealOrder(@LoginUser Integer userId, @RequestParam("date") String date, @RequestParam("timingId") Integer timingId) {

        Object response = userInfoService.checkUserId(userId);
        if (response != null) {
            return response;
        } else {
            canteenOrderService.deleteByUidAndDateAndTid(userId, date, timingId);
            mealOrderService.deleteByUidAndDateAndTid(userId, date, timingId);
            return ResponseUtil.ok();
        }

    }

    /**
     * 根据订单查询订单信息
     *
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping("findByOrderId")
    public Object findByOrderId(@LoginUser Integer userId, @RequestParam("orderId") Integer orderId) {
        Object response = userInfoService.checkUserId(userId);
        if (response != null) {
            return response;
        } else {
            List<LitemallMealOrder> mealOrders = mealOrderService.findByOrderId(userId, orderId);
            HashMap<Object, Object> result = new HashMap<>();
            result.put("mealOrders", mealOrders);
            return ResponseUtil.ok(result);
        }
    }

    /**
     * 查询用户个人报餐记录
     *
     * @param userId
     * @return
     */
    @RequestMapping("list")
    public Object listMealOrder(@LoginUser Integer userId) {
        Object response = userInfoService.checkUserId(userId);
        if (response != null) {
            return response;
        } else {
            PageInfo<LitemallMealOrder> litemallMealOrderPageInfo = mealOrderService.queryByUid(userId);
            return ResponseUtil.ok(litemallMealOrderPageInfo);
        }
    }

    /**
     * 根据日期查找所有的订单
     *
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

    /**
     * 根据日期和时段分类查询订单，做null判断，有一个为空都不查询
     * @param date
     * @param timingId
     * @return
     */
    @RequestMapping("listByOrderAndTiming")
    public Object queryByDate(@RequestParam("date") String date,@RequestParam("timingId") Integer timingId) {

        List<LitemallMealOrder> mealOrders = mealOrderService.queryByDateAndTimingId(date,timingId);
        HashMap<Object, Object> result = new HashMap<>();
        result.put("mealOrders", mealOrders);
        return ResponseUtil.ok(result);
    }
}
