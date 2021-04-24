package org.linlinjava.litemall.wx.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.utils.Jackson;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.CanteenOrder;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.linlinjava.litemall.db.domain.LitemallMealOrder;
import org.linlinjava.litemall.db.service.CanteenOrderService;
import org.linlinjava.litemall.db.service.LitemallMealOrderService;
import org.linlinjava.litemall.wx.service.WebSocketServer;
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
    private static String QUEUEKEY  = "canteen:queue";
    @Resource
    private WebSocketServer webSocket;

    @RequestMapping("/test")
    public Object test(@RequestBody LitemallBcUser bcUser) {

        Set<String> keys = redis.keys(QUEUEKEY);
        if (keys.size() != 0) {//
            //获取列表数量
            long len = redis.llen(QUEUEKEY);
            //大于排队长度
            if(len >= RedisConfig.QUEUE_LEN){
                return ResponseUtil.fail(555,"请等待排队");
            }else{

                redis.rpush(QUEUEKEY,JSON.toJSONString(bcUser));
            }
            return ResponseUtil.ok(redis.lrange(QUEUEKEY,0, redis.llen(QUEUEKEY)) );
        }else{//初始化
            redis.lpush(QUEUEKEY,JSON.toJSONString(bcUser));
            return ResponseUtil.ok(redis.lrange(QUEUEKEY,0, 0));
        }


//        strRedis.opsForValue().set("json:user",JacksonUtil.toJson(user));

    }
    @RequestMapping("add")
    public Object add(@RequestParam("orderId") Integer orderId){
        CanteenOrder order = canteenOrderService.queryByKey(orderId);

        Set<String> keys = redis.keys(QUEUEKEY);
        if (keys.size() != 0) {//有数据
            //获取列表数量
            long len = redis.llen(QUEUEKEY);

            if(len >= RedisConfig.QUEUE_LEN){  //大于排队长度
                return ResponseUtil.fail(555,"请等待排队");
            }else{
                redis.rpush(QUEUEKEY,JSON.toJSONString(order)); //加入排队
                HashMap<Object, Object> resMap = new HashMap<>();

                if(order == null){
                    return ResponseUtil.fail(555,"订单异常，排队失败");
                }else{
//                    List<LitemallMealOrder> byOrderId = mealOrderService.findByOrderId(11, order.getId());
                    List<String> lrange = redis.lrange(QUEUEKEY, 0, RedisConfig.QUEUE_LEN);
                    webSocket.sendMessage(JSON.toJSONString(lrange));
                    resMap.put("orderinfo",lrange);
                    return ResponseUtil.ok(resMap);
                }
            }

        }else{//初始化
            redis.lpush(QUEUEKEY,JSON.toJSONString(order));

            HashMap<Object, Object> resMap = new HashMap<>();

            if(order == null){
                return ResponseUtil.fail(555,"订单异常，排队失败");
            }else{
//                    List<LitemallMealOrder> byOrderId = mealOrderService.findByOrderId(11, order.getId());
                List<String> lrange = redis.lrange(QUEUEKEY, 0, RedisConfig.QUEUE_LEN);
                webSocket.sendMessage(JSON.toJSONString(lrange));
                resMap.put("orderinfo",lrange);
                return ResponseUtil.ok(resMap);
            }

        }



    }
    /**
     * 弹出列表里的第一个元素
     * @return
     */
    @RequestMapping("/del")
    public Object testDel() {
        Set<String> keys = redis.keys(QUEUEKEY);
        if(keys.size() == 0){
            return ResponseUtil.ok("没有可以删除的值");
        }else{
            String lpop = redis.lpop(QUEUEKEY);
            List<String> lrange = redis.lrange(QUEUEKEY, 0, RedisConfig.QUEUE_LEN);
            webSocket.sendMessage(JSON.toJSONString(lrange));
            return ResponseUtil.ok(lpop);
        }
    }

    @RequestMapping("/all")
    public Object queryAll() {
        Set<String> keys = redis.keys(QUEUEKEY);
        if(keys.size() == 0){
            return ResponseUtil.ok("没有可以查询的值");
        }else{
            List<String> lrange = redis.lrange(QUEUEKEY, 0, redis.llen(QUEUEKEY));
            Iterator<String> iterator = lrange.iterator();
            ArrayList<Object> objects = new ArrayList<>();
            for(String s: lrange){
                JSONObject jsonObject = JSON.parseObject(s);
                objects.add(jsonObject);
            }
            HashMap<Object, Object> resultMap = new HashMap<>();
            resultMap.put("orderInfo",objects);
            return ResponseUtil.ok(resultMap);
        }
    }
}
