package org.linlinjava.litemall.wx.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.utils.Jackson;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.CanteenOrderConstant;
import org.linlinjava.litemall.wx.service.WebSocketServer;
import org.linlinjava.litemall.wx.vo.CanteenOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yllt.canteen.redis.config.RedisConfig;
import org.yllt.canteen.redis.utils.RedisOperator;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/wx/myredis")
@Validated
public class RedisController {
    @Autowired
    private StringRedisTemplate strRedis;
    @Autowired
    private RedisOperator redis;
    @Autowired
    private CanteenOrderService canteenOrderService;
    @Autowired
    private LitemallMealOrderService mealOrderService;
    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallBcUserService bcUserService;
    @Autowired
    private BcUserVoService bcUserVoService;


    private static String QUEUEKEY = "canteen:queue";
    private static String HQUEUEKEY = "hash:canteen:queue";

    @Resource
    private WebSocketServer webSocket;

    @RequestMapping("/test")
    public Object test(@RequestBody LitemallBcUser bcUser) {

        redis.hset(HQUEUEKEY, "id", "hello");
        redis.hset(HQUEUEKEY, "i1", "hello");
        Set<String> keys = redis.keys(HQUEUEKEY);
        System.out.println(keys);
        Map<Object, Object> hgetall = redis.hgetall(HQUEUEKEY);
        redis.hdel(HQUEUEKEY, "id");
        redis.hdel(HQUEUEKEY, "i1");
        Set<String> key1 = redis.keys(HQUEUEKEY);
        System.out.println(key1);
        long hgetsize = redis.hgetsize(HQUEUEKEY);
        return ResponseUtil.ok(hgetall);
//        Set<String> keys = redis.keys(QUEUEKEY);
//        if (keys.size() != 0) {//
//            //获取列表数量
//            long len = redis.llen(QUEUEKEY);
//            //大于排队长度
//            if (len >= RedisConfig.QUEUE_LEN) {
//                return ResponseUtil.fail(555, "请等待排队");
//            } else {
//
//                redis.rpush(QUEUEKEY, JSON.toJSONString(bcUser));
//            }
//            return ResponseUtil.ok(redis.lrange(QUEUEKEY, 0, redis.llen(QUEUEKEY)));
//        } else {//初始化
//            redis.lpush(QUEUEKEY, JSON.toJSONString(bcUser));
//            return ResponseUtil.ok(redis.lrange(QUEUEKEY, 0, 0));
//        }


//        strRedis.opsForValue().set("json:user",JacksonUtil.toJson(user));

    }

    @RequestMapping("hadd")
    public long hadd(@RequestParam("orderId") Integer orderId) {
        CanteenOrder order = canteenOrderService.queryByKey(orderId);
        Set<String> keys = redis.keys(HQUEUEKEY);
        long len;

        if (keys.size() != 0) {//有数据
            //获取列表数量
            len = redis.hgetsize(HQUEUEKEY);
        } else {//初始化
            len = 0;
        }

        if (len >= RedisConfig.QUEUE_LEN) {  //大于排队长度
//            return ResponseUtil.fail(555, "请等待排队");
            return 1001;
        } else {

            HashMap<Object, Object> resMap = new HashMap<>();
            CanteenOrderVo orderVo = new CanteenOrderVo();
            orderVo.setId(order.getId());
            orderVo.setUserId(order.getUserId());
            orderVo.setDate(order.getDate());
            orderVo.setOrderSn(order.getOrderSn());
            orderVo.setOrderStatus(order.getOrderStatus());
            orderVo.setOrderPrice(order.getOrderPrice());
            orderVo.setTimingId(order.getTimingId());
            orderVo.setTimingName(order.getTimingName());
            orderVo.setAddTime(order.getAddTime());
            orderVo.setUpdateTime(order.getUpdateTime());

            if (order == null) {
//                return ResponseUtil.fail(555, "订单异常，排队失败");
                return 1002;
            } else {
                if (order.getOrderStatus() == CanteenOrderConstant.WAITING) { //订单处于未核销状态
                    //todo 这里userId在查询中是不用的
                    List<LitemallMealOrder> orderDishes = mealOrderService.findByOrderId(11, order.getId());
                    BcUserVo bcUserVo = bcUserVoService.getBcUserVoByUserId(order.getUserId());
                    orderVo.setDishes(orderDishes);
                    orderVo.setBcUserVo(bcUserVo);
                    redis.hset(HQUEUEKEY, orderId + "", JSON.toJSONString(orderVo)); //加入排队,排队信息还需要在查询
//                    List<LitemallMealOrder> byOrderId = mealOrderService.findByOrderId(11, order.getId());

                    Map<Object, Object> hgetall = redis.hgetall(HQUEUEKEY);
                    Set<Map.Entry<Object, Object>> entries = hgetall.entrySet();
                    for (Map.Entry<Object, Object> entry : entries){
                        String  key = (String)entry.getKey();
                        String value = (String) entry.getValue();
                        hgetall.put(key,JSON.parseObject(value));
                    }
                    webSocket.sendMessage(JSON.toJSONString(hgetall));
                    resMap.put("orderinfo", hgetall);
//                    return ResponseUtil.ok(resMap);
                    return 1003;
                } else {
//                    return ResponseUtil.fail(555, "订单已经核销，排队失败");
                    return 1004;

                }
            }
        }


    }

    @RequestMapping("add")
    public Object add(@RequestParam("orderId") Integer orderId) {
        CanteenOrder order = canteenOrderService.queryByKey(orderId);
        Set<String> keys = redis.keys(QUEUEKEY);
        long len;

        if (keys.size() != 0) {//有数据
            //获取列表数量
            len = redis.llen(QUEUEKEY);
        } else {//初始化
            len = 0;
        }

        if (len >= RedisConfig.QUEUE_LEN) {  //大于排队长度
            return ResponseUtil.fail(555, "请等待排队");
        } else {

            HashMap<Object, Object> resMap = new HashMap<>();
            CanteenOrderVo orderVo = new CanteenOrderVo();
            orderVo.setId(order.getId());
            orderVo.setUserId(order.getUserId());
            orderVo.setDate(order.getDate());
            orderVo.setOrderSn(order.getOrderSn());
            orderVo.setOrderStatus(order.getOrderStatus());
            orderVo.setOrderPrice(order.getOrderPrice());
            orderVo.setTimingId(order.getTimingId());
            orderVo.setTimingName(order.getTimingName());
            orderVo.setAddTime(order.getAddTime());
            orderVo.setUpdateTime(order.getUpdateTime());

            if (order == null) {
                return ResponseUtil.fail(555, "订单异常，排队失败");
            } else {
                if (order.getOrderStatus() == CanteenOrderConstant.WAITING) { //订单处于未核销状态
                    //todo 这里userId在查询中是不用的
                    List<LitemallMealOrder> orderDishes = mealOrderService.findByOrderId(11, order.getId());
                    BcUserVo bcUserVo = bcUserVoService.getBcUserVoByUserId(order.getUserId());
                    orderVo.setDishes(orderDishes);
                    orderVo.setBcUserVo(bcUserVo);
                    redis.rpush(QUEUEKEY, JSON.toJSONString(orderVo)); //加入排队,排队信息还需要在查询
//                    List<LitemallMealOrder> byOrderId = mealOrderService.findByOrderId(11, order.getId());
                    List<String> lrange = redis.lrange(QUEUEKEY, 0, RedisConfig.QUEUE_LEN);
                    webSocket.sendMessage(JSON.toJSONString(lrange));
                    resMap.put("orderinfo", lrange);
                    return ResponseUtil.ok(resMap);
                } else {
                    return ResponseUtil.fail(555, "订单已经核销，排队失败");

                }
            }
        }


    }

    /**
     * 弹出列表里的第一个元素
     *
     * @return
     */
    @RequestMapping("/del")
    public Object testDel() {
        Set<String> keys = redis.keys(QUEUEKEY);
        if (keys.size() == 0) {
            return ResponseUtil.ok("没有可以删除的值");
        } else {
            String lpop = redis.lpop(QUEUEKEY);
            List<String> lrange = redis.lrange(QUEUEKEY, 0, RedisConfig.QUEUE_LEN);
            webSocket.sendMessage(JSON.toJSONString(lrange));
            return ResponseUtil.ok(lpop);
        }
    }

    @RequestMapping("/hDel")
    public Object hDel(@RequestParam("orderId") Integer orderId) {
        Set<String> keys = redis.keys(HQUEUEKEY);
        if (keys.size() == 0) {
            return ResponseUtil.ok("没有可以删除的值");
        } else {
            redis.hdel(HQUEUEKEY,orderId+"");
            Map<Object, Object> hgetall = redis.hgetall(HQUEUEKEY);
            Set<Map.Entry<Object, Object>> entries = hgetall.entrySet();
            for (Map.Entry<Object, Object> entry : entries){
                String  key = (String)entry.getKey();
                String value = (String) entry.getValue();
                hgetall.put(key,JSON.parseObject(value));
            }
            webSocket.sendMessage(JSON.toJSONString(hgetall));
            return ResponseUtil.ok(hgetall);
        }
    }

    @RequestMapping("/all")
    public Object queryAll() {
        Set<String> keys = redis.keys(QUEUEKEY);
        if (keys.size() == 0) {
            return ResponseUtil.ok("没有可以查询的值");
        } else {
            List<String> lrange = redis.lrange(QUEUEKEY, 0, redis.llen(QUEUEKEY));
            Iterator<String> iterator = lrange.iterator();
            ArrayList<Object> objects = new ArrayList<>();
            for (String s : lrange) {
                JSONObject jsonObject = JSON.parseObject(s);
                objects.add(jsonObject);
            }
            HashMap<Object, Object> resultMap = new HashMap<>();
            resultMap.put("orderInfo", objects);
            return ResponseUtil.ok(resultMap);
        }
    }

    @RequestMapping("/hAll")
    public Object hQueryAll() {
        Set<String> keys = redis.keys(HQUEUEKEY);
        if (keys.size() == 0) {
            return ResponseUtil.ok("没有可以查询的值");
        } else {
            Map<Object, Object> hgetall = redis.hgetall(HQUEUEKEY);
            Set<Map.Entry<Object, Object>> entries = hgetall.entrySet();
            for (Map.Entry<Object, Object> entry : entries){
                String  key = (String)entry.getKey();
                String value = (String) entry.getValue();
                hgetall.put(key,JSON.parseObject(value));
            }

            HashMap<Object, Object> resultMap = new HashMap<>();
            resultMap.put("orderInfo", hgetall);
            return ResponseUtil.ok(resultMap);
        }
    }
}
