package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.linlinjava.canteen.redis.utils.RedisOperator;
@RestController
@RequestMapping("/wx/myredis")
@Validated
public class RedisController {
    @Autowired
    private StringRedisTemplate strRedis;
    @Autowired
    private RedisOperator redis;
    @RequestMapping("/test")
    public Object test() {
        LitemallBcUser user = new LitemallBcUser();
        user.setId(11);
        user.setStatus(1);

//        strRedis.opsForValue().set("json:user",JacksonUtil.toJson(user));
        redis.set("json:user",JacksonUtil.toJson(user));
        String userListJson = redis.get("json:user");
        return ResponseUtil.ok(userListJson);
    }
}
