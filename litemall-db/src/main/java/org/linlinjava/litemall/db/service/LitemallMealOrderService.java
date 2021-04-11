package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.linlinjava.litemall.db.dao.CustomMealOrderMapper;
import org.linlinjava.litemall.db.dao.LitemallMealOrderMapper;
import org.linlinjava.litemall.db.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallMealOrderService {
    public static final short WAITING = 103;
    public static final short CHECKED = 107;

    @Autowired
    private LitemallMealOrderMapper mealOrderMapper;

    @Autowired
    private CustomMealOrderMapper customMealOrderMapper;
    @Autowired
    private CanteenOrderService canteenOrderService;
    @Autowired
    private LitemallDishesService dishesService;

    public void add(Integer userId, List<LitemallMealOrder> mealOrder) {
        //计算商品价格
        BigDecimal price = new BigDecimal(0) ;
        //添加商品的信息
        for (LitemallMealOrder item : mealOrder) {

            LitemallDishes litemallDishes = dishesService.queryById(item.getDishesId());
            price = price.add(new BigDecimal(litemallDishes.getPrice()).multiply( new BigDecimal(item.getQuantity())));
            item.setDishesPrice(litemallDishes.getPrice());
            item.setAddTime(LocalDateTime.now());
            item.setUserId(userId);
            item.setDishesBrief(litemallDishes.getBrief());
            item.setDishesPicUrl(litemallDishes.getPicUrl());
        }
        //添加订单信息
        CanteenOrder order = new CanteenOrder();
        order.setUserId(userId);
        order.setOrderSn(canteenOrderService.generateOrderSn());
        order.setOrderStatus(WAITING);
        order.setOrderPrice(price);
        order.setAddTime(LocalDateTime.now());
        order.setTimingId(mealOrder.get(0).getTimingId());
        order.setDate(mealOrder.get(0).getDate());
        order.setTimingName(mealOrder.get(0).getTimingName());
        canteenOrderService.add(order);

        //获取插入后返回的id
        Integer orderId = order.getId();
        for (LitemallMealOrder orderDish : mealOrder) {
            orderDish.setOrderId(orderId);
        }
        customMealOrderMapper.insertOrders(mealOrder);
    }

    public List<LitemallMealOrder> findByUidAndDate(Integer userId, String date) {
        LitemallMealOrderExample example = new LitemallMealOrderExample();
        example.or().andUserIdEqualTo(userId).andDateEqualTo(LocalDate.parse(date)).andDeletedEqualTo(false);
        List<LitemallMealOrder> litemallMealOrders = mealOrderMapper.selectByExample(example);
        return litemallMealOrders;
    }

    public void deleteByUidAndDateAndTid(Integer userId, String date, Integer timingId) {
        LitemallMealOrderExample example = new LitemallMealOrderExample();
        example.or().andUserIdEqualTo(userId).andDateEqualTo(LocalDate.parse(date)).andTimingIdEqualTo(timingId);

        mealOrderMapper.logicalDeleteByExample(example);
    }

    public PageInfo<LitemallMealOrder> queryByUid(Integer userId) {
        LitemallMealOrderExample example = new LitemallMealOrderExample();

        example.setOrderByClause("id desc");
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        PageHelper.startPage(1, 10);//在查询之前设置分页参数
        List<LitemallMealOrder> mealOrders = mealOrderMapper.selectByExample(example);
        PageInfo<LitemallMealOrder> result = new PageInfo<>(mealOrders);
        return result;

    }

    public List<LitemallMealOrder> queryByDate(String date) {

        LitemallMealOrderExample example = new LitemallMealOrderExample();
        example.or().andDateEqualTo(LocalDate.parse(date)).andDeletedEqualTo(false);
        List<LitemallMealOrder> mealOrders = mealOrderMapper.selectByExample(example);
        return mealOrders;
    }

    public List<LitemallMealOrder> findByOrderId(Integer userId, Integer orderId) {
        LitemallMealOrderExample example = new LitemallMealOrderExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        List<LitemallMealOrder> mealOrders = mealOrderMapper.selectByExampleSelective(example);
        return mealOrders;
    }

    public int check(Integer userId, Integer orderId) {
        LitemallMealOrderExample example = new LitemallMealOrderExample();

        example.or().andUserIdEqualTo(userId).andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        LitemallMealOrder litemallMealOrder = new LitemallMealOrder();
        litemallMealOrder.setCancelled(true);
        return mealOrderMapper.updateByExampleSelective(litemallMealOrder,example);
    }
}
