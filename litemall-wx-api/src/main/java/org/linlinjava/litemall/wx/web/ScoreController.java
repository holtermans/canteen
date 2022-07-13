package org.linlinjava.litemall.wx.web;


import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.CanteenOrder;
import org.linlinjava.litemall.db.domain.Score;
import org.linlinjava.litemall.db.service.CanteenOrderService;
import org.linlinjava.litemall.db.service.ScoreService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wx/score")
@Validated
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CanteenOrderService canteenOrderService;

    /**
     * 添加用户评分
     * @param userId
     * @param score
     * @return
     */
    @RequestMapping("add")
    public Object add(@LoginUser Integer userId, Integer score) {
        List<CanteenOrder> canteenOrders = canteenOrderService.findByUidInAMonth(userId);
        Integer orderNum = canteenOrders.size();  //如果有订餐
        if (orderNum > 0) { //有订餐
            List<Score> scoresList = scoreService.findByUidAndInMonth(userId);
            Integer scoreNum = scoresList.size();
            //继续判断是否已经评价
            if (scoreNum > 0) {
                //已经评价就不要再来凑热闹了
                return ResponseUtil.fail(233, "本月已评价");
            } else {
                Integer integer = scoreService.addScore(userId, score);
                return ResponseUtil.ok("评价成功！");
            }

        } else {
            return ResponseUtil.fail(233, "未订餐无法评价");
        }
    }

    /**
     * 按时间顺序 通过userId 查询个人前10次的评分记录
     * @param userId
     * @return
     */
    @RequestMapping("findByUserId")
    public Object findByUserId(@LoginUser Integer userId) {
        List<Score> scoresList = scoreService.findByUserId(userId);  //查询评价记录
        return ResponseUtil.ok(scoresList);

    }

    /**
     * 通过月份 查询所有评分记录
     * @return
     */
    @RequestMapping("findByMonth")
    public Object findByMonth(int year ,int month) {
        List<Score> scoresList = scoreService.findByMonth(year,month);  //查询评价记录
        return ResponseUtil.ok(scoresList);
    }

}
