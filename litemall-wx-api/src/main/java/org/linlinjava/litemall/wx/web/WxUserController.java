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
import org.linlinjava.litemall.wx.service.UserInfoService;
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
    @Autowired
    private UserInfoService userInfoService;

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
     *
     * @param userIds
     * @return
     */
    @RequestMapping("getBcUserInfoByUserId")
    public Object getBcUserInfoByUserId(@RequestBody Integer[] userIds) {

        List<Integer> userIdList = Arrays.asList(userIds);
        List<Integer> bcUserIdList = new ArrayList<>();
        List<LitemallUser> userList = userService.findByIdList(userIdList);
        for (LitemallUser user : userList) {
            bcUserIdList.add(user.getBcUserId());
        }
        //获取报餐用户信息
        List<LitemallBcUser> bcUserList = bcUserService.findByIdList(bcUserIdList);

        List<BcUserVo> bcUserVos = new ArrayList<>();
        HashMap<Integer, BcUserVo> bcUserVoHashMap = new HashMap<>();

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
     * 根据微信用户表中的id找到自定义的报餐用户信息
     * 查找一组用户信息
     *
     * @param userIds
     * @return
     */
    @RequestMapping("getBcUserHashMapByUserId")
    public Object getBcUserHashMapByUserId(@RequestBody Integer[] userIds) {

        List<Integer> userIdList = Arrays.asList(userIds);
        List<Integer> bcUserIdList = new ArrayList<>();
        List<LitemallUser> userList = userService.findByIdList(userIdList);
        for (LitemallUser user : userList) {
            bcUserIdList.add(user.getBcUserId());
        }
        //获取报餐用户信息
        List<LitemallBcUser> bcUserList = bcUserService.findByIdList(bcUserIdList);

        List<BcUserVo> bcUserVos = new ArrayList<>();
        HashMap<Integer, BcUserVo> bcUserVoHashMap = new HashMap<>();

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
                    bcUserVoHashMap.put(userId, bcUserVo);
                }
            }
        }
        HashMap<Object, Object> resHashMap = new HashMap<>();
        resHashMap.put("bcUserVoHashMap", bcUserVoHashMap);
        return ResponseUtil.ok(resHashMap);
    }

    /**
     * 取得当前用户的身份信息
     *
     * @param userId
     * @return result.put(" bcUserInfo ", bcUserVo);
     */

    @RequestMapping("getSingleBcUserByUserId")
    public Object getBcUserByUserId(@LoginUser Integer userId) {

        Map response = (Map) userInfoService.checkUserId(userId);
        if (response != null && (Integer) response.get("errno") != 507) {
            return response;
        } else {
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

    /**
     * 更新指定用户的身份信息
     */

    @RequestMapping("updateSingleBcUserByUserId")
    public Object updateBcUserByUserId(@LoginUser Integer userId ,@RequestBody LitemallUser litemallUser) {
        Map response = (Map) userInfoService.checkUserId(userId);
        if (response != null && (Integer) response.get("errno") != 507) {
            return response;
        } else {
            litemallUser.setId(userId);
            System.out.println(litemallUser);
            int i = userService.updateById(litemallUser);
            if (i != 0) {
                return ResponseUtil.ok();
            }else{
                return ResponseUtil.fail();
            }
        }

    }

}