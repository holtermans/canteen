package org.linlinjava.litemall.wx.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
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
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    private LitemallBcUserService bcUserService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private RedisController redisController;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private BcUserVoService bcUserVoService;

    /**
     * 根据用户id，日期范围查询
     *
     * @param userId
     * @param date
     * @return
     */
    @RequestMapping("listByIdAndDate")
    public Object getByIdAndDateRange(@RequestParam Integer userId, @RequestParam String date) {

        List<CanteenOrder> canteenOrders = canteenOrderService.queryByUidAndDateRange(userId, date);
        return ResponseUtil.ok(canteenOrders);
    }

    /**
     * 根据用户id，日期查询
     *
     * @param userId
     * @param date
     * @return
     */
    @RequestMapping("queryByIdAndDate")
    public Object getByIdAndDate(@LoginUser Integer userId, @RequestParam String date) {
        Object o = userInfoService.checkUserId(userId);
        if (o != null) {
            return o;
        } else {
            List<CanteenOrder> canteenOrders = canteenOrderService.queryByUidAndDate(userId, date);
            HashMap<Object, Object> resMap = new HashMap<>();
            resMap.put("canteenOrderList", canteenOrders);
            return ResponseUtil.ok(resMap);
        }
    }

    @RequestMapping("findByOrderSn")
    public Object getByOrderSn(@LoginUser Integer userId, @RequestParam String orderSn) {
        Object o = userInfoService.checkUserId(userId);
        if (o != null) {
            return o;
        } else {
            CanteenOrder order = canteenOrderService.queryByOrderSn(orderSn);
            if (order == null) {
                return ResponseUtil.fail(601, "根据序列号未找到订单");
            }
            HashMap<Object, Object> resMap = new HashMap<>();
            resMap.put("canteenOrder", order);
            return ResponseUtil.ok(resMap);
        }
    }

    /**
     * 获取前十条订餐记录
     *
     * @return
     */
    @RequestMapping("list")
    public Object get10Record(@LoginUser Integer userId, @RequestParam Short status) {
        Object o = userInfoService.checkUserId(userId);
        if (o != null) {
            return o;
        } else {
            PageInfo<CanteenOrder> canteenOrders;
            if (status != null) {
                canteenOrders = canteenOrderService.queryByUid(userId, status);
            } else {
                canteenOrders = canteenOrderService.queryByUid(userId);
            }

            return ResponseUtil.ok(canteenOrders);
        }

    }

    /**
     * 订单核销
     *
     * @return
     */
    @RequestMapping("check")
    public Object orderCheck(@LoginUser Integer userId, @RequestParam Integer orderId, @RequestParam String code) {
        Object o = userInfoService.checkUserId(userId);
        if (o != null) {
            return o;
        } else { //o为null 代表所有信息都没问题
            //获取二维码信息
            Config config = configService.queryByName("checkCode");
            String checkCode = config.getValue();
            //判断二维码是否正确
            if (!checkCode.equals(code)) {
                return ResponseUtil.fail(555, "二维码不正确");
            }
            //这里要增加排队功能，还要判断前面是否有人在排队。在此之前已经做了时间和二维码的验证，所以只需要进行队列的验证
            //先做加入队列的功能
            long hadd = redisController.hadd(orderId);
            if (hadd == 1003) {
                int num1 = canteenOrderService.check(userId, orderId);
                int num2 = mealOrderService.check(userId, orderId);
                return ResponseUtil.ok();

            } else {
                return ResponseUtil.fail(555, "等待排队");
            }
            //todo 之前设计的不合理，所以导致这里需要两边的订单都要做核销，待改进
        }


    }

    /**
     * 订单核销
     *
     * @return
     */
    @RequestMapping("checkByOrderSn")
    public Object orderCheck(@LoginUser Integer operatorId, @RequestParam String orderSn) {
        Object o = userInfoService.checkUserId(operatorId);
        if (o != null) {
            return o;
        } else { //o为null 代表所有信息都没问题
            CanteenOrder canteenOrder = canteenOrderService.queryByOrderSn(orderSn);
            if (canteenOrder == null) {
                return ResponseUtil.fail(555, "根据序列号未找到订单");
            } else {
                //先做订单的校验

                Integer orderId = canteenOrder.getId();
                Integer userId = canteenOrder.getUserId();
                BcUserVo bcUserVo = bcUserVoService.getBcUserVoByUserId(userId);
                List<LitemallMealOrder> mealOrders = mealOrderService.findByOrderId(userId, orderId);
                //这里要增加排队功能，还要判断前面是否有人在排队。在此之前已经做了时间和二维码的验证，所以只需要进行队列的验证
                //先做加入队列的功能
//                long hadd = redisController.hadd(orderId);
//                if (hadd == 1003) {
                int num1 = canteenOrderService.check(userId, orderId);
                int num2 = mealOrderService.check(userId, orderId);
                HashMap<Object, Object> resMap = new HashMap<>();
                resMap.put("canteenOrder", canteenOrder);
                resMap.put("mealOrders", mealOrders);
                resMap.put("bcUserVo", bcUserVo);
                return ResponseUtil.ok(resMap);
//                } else {
//                    return ResponseUtil.fail(555, "等待排队");
//                }
                //todo 之前设计的不合理，所以导致这里需要两边的订单都要做核销，待改进
            }

        }
    }

    /**
     * 取消订单
     * @param operatorId
     * @param orderId
     * @return
     */
    @RequestMapping("cancel")
    public Object orderCancel(@LoginUser Integer operatorId, @RequestParam Integer orderId) {
        Object o = userInfoService.checkUserId(operatorId);
        if (o != null) {
            return o;
        } else {
            try {
                canteenOrderService.delByPKey(orderId);
                mealOrderService.deleteByOrderId(orderId);
                return ResponseUtil.ok();
            } catch (Exception e) {
                return ResponseUtil.fail();
            }
        }
    }

    /**
     * 某一天所有订单查询
     *
     * @return
     */
    @RequestMapping("dailyList")
    public Object dailyList(@RequestParam String date) {
        List<CanteenOrder> canteenOrders = canteenOrderService.queryByDate(date);
        return ResponseUtil.ok(canteenOrders);
    }

    /**
     * 按条件查询
     *
     * @return
     */
    @RequestMapping("queryByFilter")
    public Object queryByFilter(@LoginUser Integer userId,@RequestBody CanteenOrder order) {

        List<CanteenOrder> canteenOrders = canteenOrderService.queryByFilter(order);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("canteenOrderList",canteenOrders);

        return ResponseUtil.ok(hashMap);
    }

    /**
     * 按条件查询
     *
     * @return
     */
    @RequestMapping("queryByFilterThenGroup")
    public Object queryByFilterThenGroup(@RequestBody CanteenOrder order) {
        List<CanteenOrder> canteenOrders = canteenOrderService.queryByFilter(order);
        Map<Short, List<CanteenOrder>> collect = canteenOrders.stream().collect(Collectors.groupingBy(t -> t.getOrderStatus()));
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("canteenOrderList",canteenOrders);
        hashMap.put("canteenOrderListGroup",collect);
        return ResponseUtil.ok(hashMap);
    }

    /**
     * 按条件查询
     *
     * @return
     */
    @RequestMapping("queryByUidAndPage")
    public Object queryByUidAndPage(@LoginUser Integer userId,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") Short status
                                    ) {
        List<CanteenOrder> canteenOrders = canteenOrderService.queryByUidAndPage(userId, pageNum, pageSize,status);

        return ResponseUtil.ok(canteenOrders);

    }

}
