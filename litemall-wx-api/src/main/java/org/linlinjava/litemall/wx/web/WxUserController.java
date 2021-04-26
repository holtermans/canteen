package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.BcUserVo;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.LitemallBcUserService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 用户服务
 */
@RestController
@RequestMapping("/wx/user")
@Validated
public class WxUserController {
    private final Log logger = LogFactory.getLog(WxUserController.class);

    @Autowired
    private LitemallOrderService orderService;

    @Autowired
    private LitemallUserService userService;

    @Autowired
    private LitemallBcUserService bcUserService;

    /**
     * 用户个人页面数据
     * <p>
     * 目前是用户订单统计信息
     *
     * @param userId 用户ID
     * @return 用户个人页面数据
     */
    @GetMapping("index")
    public Object list(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("order", orderService.orderInfo(userId));
        return ResponseUtil.ok(data);
    }

    /**
     * 根据微信用户表中的id找到自定义的报餐用户信息
     * 查找一组用户信息
     * @param userIds
     * @return
     */
    @RequestMapping("getBcUserInfoByUserId")
    public Object getBcUserInfoByUserId(@RequestBody Integer[] userIds) {
        List<Integer> userIdList = Arrays.asList(userIds);
        List<Integer> bcUserIdList = new ArrayList<>();
        List<LitemallUser> userList = userService.findByIdList(userIdList);
        System.out.println(userList);
        for (LitemallUser user : userList) {
            bcUserIdList.add(user.getBcUserId());
        }
        //获取报餐用户信息
        List<LitemallBcUser> bcUserList = bcUserService.findByIdList(bcUserIdList);

        List<BcUserVo> bcUserVos = new ArrayList<>();
        for (LitemallUser user : userList) {
            int userId = user.getId();
            int bcUserId = user.getBcUserId();
            String avatar = user.getAvatar();
            String nickName = user.getNickname();
            for (LitemallBcUser bcUser : bcUserList) {
                if (bcUser.getId() == bcUserId) {
                    BcUserVo bcUserVo = new BcUserVo();
                    bcUserVo.setId(userId);
                    bcUserVo.setBcId(bcUserId);
                    bcUserVo.setAvatar(avatar);
                    bcUserVo.setNickname(nickName);
                    bcUserVo.setName(bcUser.getName());
                    bcUserVo.setMobile(bcUser.getMobile());
                    bcUserVos.add(bcUserVo);
                }
            }
        }

        return ResponseUtil.ok(bcUserVos);
    }

    /**
     * 取得当前用户的身份信息
     * @param userId
     * @return result.put(" bcUserInfo ", bcUserVo);
     */

    @RequestMapping("getSingleBcUserByUserId")
    public Object getBcUserByUserId(@LoginUser Integer userId) {
        if (userId != null) { //token过期，提示一下
            //根据userId查询用户状态是否验证
            LitemallUser user = userService.findById(userId);
            if (user != null) {
                int bcUserId = user.getBcUserId();
                LitemallBcUser bcUser = bcUserService.findById(bcUserId);
                if (bcUser.getStatus() == 0) { //未激活
//                    return ResponseUtil.notActive();
                }
            }
        } else {
            return ResponseUtil.unlogin();
        }


        LitemallUser user = userService.findById(userId);
        Integer bcUserId = user.getBcUserId();
        String avatar = user.getAvatar();
        String nickName = user.getNickname();

        //获取报餐用户信息
        LitemallBcUser bcUser = bcUserService.findById(bcUserId);

        BcUserVo bcUserVo = new BcUserVo();
        bcUserVo.setId(userId);
        bcUserVo.setBcId(bcUserId);
        bcUserVo.setAvatar(avatar);
        bcUserVo.setNickname(nickName);
        bcUserVo.setName(bcUser.getName());
        bcUserVo.setMobile(bcUser.getMobile());
        bcUserVo.setAdmin(bcUser.getIsAdmin());
        bcUserVo.setStatus(bcUser.getStatus());
        HashMap<Object, Object> result = new HashMap<>();
        result.put("bcUserInfo", bcUserVo);
        return ResponseUtil.ok(result);
    }

}