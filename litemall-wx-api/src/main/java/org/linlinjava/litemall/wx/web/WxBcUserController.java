package org.linlinjava.litemall.wx.web;

import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.litemallBcUserService;
import org.linlinjava.litemall.db.util.BcUserConstant;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     *
     * @param operatorUserId 操作者UserId，需要进行记录
     * @param userId
     */
    public void updateStatus(@LoginUser Integer operatorUserId, Integer userId){
    }

}
