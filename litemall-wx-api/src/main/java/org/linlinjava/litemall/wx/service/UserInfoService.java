package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallBcUserService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {
    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallBcUserService bcUserService;

    public UserInfo getInfo(Integer userId) {
        LitemallUser user = userService.findById(userId);
        Assert.state(user != null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }

    public Object checkUserId(Integer userId) {

        if (userId != null) { //token过期，提示一下
            //根据userId查询用户状态是否验证
            LitemallUser user = userService.findById(userId);
            if (user != null) {
                Integer bcUserId = user.getBcUserId();
                LitemallBcUser bcUser = bcUserService.findById(bcUserId);
                if (bcUserId == null || bcUser == null) {
                    return ResponseUtil.unlogin();
                }
                if (bcUser.getStatus() == 0) { //未激活
                    return ResponseUtil.notActive();
                }
            }else{
                return ResponseUtil.unlogin();
            }
        } else {
            return ResponseUtil.unlogin();
        }
        return null;
    }
}
