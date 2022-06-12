package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.linlinjava.litemall.db.dao.CanteenOrderMapper;
import org.linlinjava.litemall.db.domain.CanteenOrder;
import org.linlinjava.litemall.db.domain.CanteenOrderExample;
import org.linlinjava.litemall.db.util.CanteenOrderConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CanteenOrderService {
    public static final short WAITING = 103;
    public static final short CHECKED = 107;

    @Autowired
    private CanteenOrderMapper canteenOrderMapper;

    public void add(CanteenOrder canteenOrder) {
        canteenOrderMapper.insertSelective(canteenOrder);
    }

    /**
     * 查询个人某天的订单
     *
     * @param userId
     * @param
     * @return
     */
    public PageInfo<CanteenOrder> queryByUid(Integer userId, Short status) {

        CanteenOrderExample example = new CanteenOrderExample();
        example.setOrderByClause("id desc");
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false).andOrderStatusEqualTo(status);
        List<CanteenOrder> canteenOrders = canteenOrderMapper.selectByExample(example);

        PageHelper.startPage(1, 10);
        PageInfo<CanteenOrder> result = new PageInfo<>(canteenOrders);
        return result;
    }

    /**
     * 查询个人前10条的订单
     *
     * @param userId
     * @param
     * @return
     */
    public PageInfo<CanteenOrder> queryByUid(Integer userId) {
        CanteenOrderExample example = new CanteenOrderExample();
        example.setOrderByClause("id desc");
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<CanteenOrder> canteenOrders = canteenOrderMapper.selectByExample(example);
        PageHelper.startPage(1, 10);
        PageInfo<CanteenOrder> result = new PageInfo<>(canteenOrders);
        return result;
    }


    public void deleteByUidAndDateAndTid(Integer userId, String date, Integer timingId) {
        CanteenOrderExample example = new CanteenOrderExample();
        example.or().andUserIdEqualTo(userId).andDateEqualTo(LocalDate.parse(date)).andTimingIdEqualTo(timingId);
        canteenOrderMapper.logicalDeleteByExample(example);
    }

    /**
     * 查询个人某个时间段的报餐情况
     *
     * @param userId
     * @param date
     * @return
     */
    public List<CanteenOrder> queryByUidAndDateRange(Integer userId, String date) {

        LocalDate before5Day = LocalDate.parse(date).minusDays(5);
        LocalDate after5Day = LocalDate.parse(date).plusDays(5);
        CanteenOrderExample example = new CanteenOrderExample();
        example.or().andUserIdEqualTo(userId).andDateBetween(before5Day, after5Day).andDeletedEqualTo(false);
        List<CanteenOrder> canteenOrders = canteenOrderMapper.selectByExample(example);
        return canteenOrders;

    }


    public String generateOrderSn() {
        //格式化当前时间
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strDate = sfDate.format(new Date());
        //得到17位时间如：20170411094039080
        System.out.println("时间17位：" + strDate);
        //为了防止高并发重复,再获取3个随机数
        String random = getRandom620(3);

        //最后得到20位订单编号。
        return strDate + random;
    }

    public static String getRandom620(Integer length) {
        String result = "";
        Random rand = new Random();
        int n = 20;
        if (null != length && length > 0) {
            n = length;
        }
        int randInt = 0;
        for (int i = 0; i < n; i++) {
            randInt = rand.nextInt(10);
            result += randInt;
        }
        return result;
    }

    /**
     * 核销订单
     *
     * @param userId
     * @param orderId
     * @return
     */
    public int check(Integer userId, Integer orderId) {
        CanteenOrderExample example = new CanteenOrderExample();
        example.or().andUserIdEqualTo(userId).andIdEqualTo(orderId).andDeletedEqualTo(false);
        CanteenOrder order = new CanteenOrder();
        order.setOrderStatus(CanteenOrderConstant.CHECKED);
        order.setUpdateTime(LocalDateTime.now());
        return canteenOrderMapper.updateByExampleSelective(order, example);
    }

    /**
     * 根据日期查询
     *
     * @param date
     * @return
     */
    public List<CanteenOrder> queryByDate(String date) {
        CanteenOrderExample example = new CanteenOrderExample();
        example.setOrderByClause("id desc");
        example.or().andDateEqualTo(LocalDate.parse(date)).andDeletedEqualTo(false);
        List<CanteenOrder> canteenOrders = canteenOrderMapper.selectByExample(example);
        return canteenOrders;
    }

    /**
     * 根据用户id，日期查询
     *
     * @param userid 用户id
     * @param date
     * @return
     */
    public List<CanteenOrder> queryByUidAndDate(Integer userid, String date) {
        CanteenOrderExample example = new CanteenOrderExample();
        example.setOrderByClause("id desc");
        example.or().andUserIdEqualTo(userid).andDateEqualTo(LocalDate.parse(date)).andDeletedEqualTo(false);
        List<CanteenOrder> canteenOrders = canteenOrderMapper.selectByExample(example);
        return canteenOrders;
    }

    public CanteenOrder queryByKey(Integer orderId) {
        CanteenOrder order = canteenOrderMapper.selectByPrimaryKey(orderId);
        return order;
    }

    public CanteenOrder queryByOrderSn(String orderSn) {
        CanteenOrderExample example = new CanteenOrderExample();
        example.or().andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        CanteenOrder order = canteenOrderMapper.selectOneByExampleSelective(example);
        return order;
    }

    public void delByPKey(Integer orderId) {
        CanteenOrder order = new CanteenOrder();
        order.setId(orderId);
        order.setUpdateTime(LocalDateTime.now());
        canteenOrderMapper.updateByPrimaryKeySelective(order);
        canteenOrderMapper.logicalDeleteByPrimaryKey(orderId);
    }

    public List<CanteenOrder> queryByFilter(CanteenOrder order) {
        CanteenOrderExample example = new CanteenOrderExample();

        CanteenOrderExample.Criteria or = example.or();
        or.andDateEqualTo(order.getDate());
        if (order.getTimingId() != null) {
            or.andTimingIdEqualTo(order.getTimingId());
        }
        if (order.getOrderStatus() != null) {
            or.andOrderStatusEqualTo(order.getOrderStatus());
        }
        or.andDeletedEqualTo(false);
        List<CanteenOrder> canteenOrders = canteenOrderMapper.selectByExample(example);
        return canteenOrders;
    }

    public List<CanteenOrder> queryByUidAndPage(Integer userId, Integer pageNum, Integer pageSize,Short status) {
        CanteenOrderExample example = new CanteenOrderExample();
        CanteenOrderExample.Criteria criteria = example.createCriteria();

        if(status != null){
            criteria.andOrderStatusEqualTo(status);
        }
        criteria.andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause("id desc");
        PageHelper.startPage(pageNum,pageSize);
        return canteenOrderMapper.selectByExample(example);

    }

    public List<CanteenOrder> findByUidInAMonth(Integer userId) {
        LocalDate now = LocalDate.now();
        LocalDate firstday = LocalDate.of(now.getYear(), now.getMonth(), 1);//当月首日
        CanteenOrderExample example = new CanteenOrderExample();
        example.or().andUserIdEqualTo(userId).andDateGreaterThanOrEqualTo(firstday).andDeletedEqualTo(false);
        List<CanteenOrder> canteenOrders = canteenOrderMapper.selectByExampleSelective(example);
        return canteenOrders;
    }
}
