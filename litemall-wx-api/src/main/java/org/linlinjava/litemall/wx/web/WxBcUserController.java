package org.linlinjava.litemall.wx.web;

import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.LitemallBcUserService;
import org.linlinjava.litemall.db.util.BcUserConstant;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
    private LitemallBcUserService bcUserService;

    /**
     * 查询用户列表没有通过审核的名单
     *
     * @param
     * @return
     */
    @RequestMapping("queryAll")
    public Object queryAll(@RequestParam("type") String type,
                           @RequestParam(value = "pageNum", required = false) Integer pageNum,
                           @RequestParam(value = "pageSize", required = false) Integer pageSize
                           ) {
        //设置默认的页码
        pageNum =  pageNum == null ? 1 : pageNum;
        pageSize =  pageSize == null ? 10 : pageSize;

        LitemallBcUser bcUser = new LitemallBcUser();
        switch (type){
            case "actived":
                bcUser.setStatus(BcUserConstant.ACTIVE);
                break;
            case "notActived":
                bcUser.setStatus(BcUserConstant.NOT_ACTIVE);
                break;
            default:
                break;
        }

        //
        PageInfo<LitemallBcUser> bcUsersList = bcUserService.queryByStatus(bcUser,pageNum,pageSize);

        HashMap<Object, Object> result = new HashMap<>();
        result.put("bcUserList", bcUsersList);
        return ResponseUtil.ok(result);
    }


    @RequestMapping("updateBcUser")
    public Object updateUser(@LoginUser Integer operatorUserId, @RequestBody LitemallBcUser bcUserInfo){
        if (operatorUserId != null) { //token过期，提示一下
            //根据userId查询用户状态是否验证
            LitemallUser user = userService.findById(operatorUserId);
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

        if(bcUserInfo.getId() == null){
            return ResponseUtil.badArgument();
        }
        int feedback = bcUserService.update(operatorUserId,bcUserInfo);
        if(feedback == 0){
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }
    /**
     * 查询用户列表没有通过审核的名单
     *
     * @param
     * @return
     */
    @RequestMapping("getBcUserListByKeyword")
    public Object getBcUserListByKeyword(@RequestParam("type") String type,
                                         @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                         @RequestParam(value = "keyword", required = false) String keyword
                                         ) {
        //设置默认的页码
        pageNum =  pageNum == null ? 1 : pageNum;
        pageSize =  pageSize == null ? 10 : pageSize;
        LitemallBcUser bcUser = new LitemallBcUser();
        bcUser.setName(keyword);

        System.out.println(type);
        System.out.println(pageNum);
        System.out.println(pageSize);
        System.out.println(keyword);
        switch (type){
            case "actived":
                bcUser.setStatus(BcUserConstant.ACTIVE);
                break;
            case "notActived":
                bcUser.setStatus(BcUserConstant.NOT_ACTIVE);
                break;
            default:
                bcUser.setStatus(BcUserConstant.ACTIVE);
                break;
        }
        PageInfo<LitemallBcUser> bcUsersList = bcUserService.queryByKeywordAndStatus(bcUser,pageNum,pageSize);
        HashMap<Object, Object> result = new HashMap<>();
        result.put("bcUserList", bcUsersList);
        return ResponseUtil.ok(result);
    }

}
