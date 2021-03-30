package org.linlinjava.litemall.wx.web;

import io.swagger.models.auth.In;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.litemallBcUserService;
import org.linlinjava.litemall.db.util.BcUserConstant;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/wx/bcuser")
@Validated
public class WxBcUserController {
    private final Log logger = LogFactory.getLog(WxUserController.class);

    @Autowired
    private LitemallOrderService orderService;

    @Autowired
    private LitemallUserService userService;

    @Autowired
    private litemallBcUserService bcUserService;

    /**
     * 查询用户列表没有通过审核的名单
     *
     * @param
     * @return
     */
    @RequestMapping("queryAll")
    public Object queryAll() {
        LitemallBcUser bcUser = new LitemallBcUser();
        //查找没有通过审核的名单
        bcUser.setStatus(BcUserConstant.NOT_ACTIVE);
        List<LitemallBcUser> BcUsersNotActived = bcUserService.queryByStatus(bcUser);
        //查找通过审核的名单
        bcUser.setStatus(BcUserConstant.ACTIVE);
        List<LitemallBcUser> BcUsersActived = bcUserService.queryByStatus(bcUser);
        HashMap<Object, Object> result = new HashMap<>();

        //分离出哪些没通过审核，哪些通过审核
        result.put("BcUsersNotActived", BcUsersNotActived);
        result.put("BcUsersActived", BcUsersActived);
        return result;
    }


}
