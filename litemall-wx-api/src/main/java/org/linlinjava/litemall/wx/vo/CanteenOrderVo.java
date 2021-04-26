package org.linlinjava.litemall.wx.vo;

import org.linlinjava.litemall.db.domain.BcUserVo;
import org.linlinjava.litemall.db.domain.CanteenOrder;
import org.linlinjava.litemall.db.domain.LitemallMealOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CanteenOrderVo {
    private Integer id;
    private Integer userId;
    private LocalDate date;
    private String orderSn;
    private Short orderStatus;
    private BigDecimal orderPrice;
    private Integer timingId;
    private String timingName;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
    private List<LitemallMealOrder> dishes;
    private BcUserVo bcUserVo;

    public BcUserVo getBcUserVo() {
        return bcUserVo;
    }

    public void setBcUserVo(BcUserVo bcUserVo) {
        this.bcUserVo = bcUserVo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Short getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getTimingId() {
        return timingId;
    }

    public void setTimingId(Integer timingId) {
        this.timingId = timingId;
    }

    public String getTimingName() {
        return timingName;
    }

    public void setTimingName(String timingName) {
        this.timingName = timingName;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public List<LitemallMealOrder> getDishes() {
        return dishes;
    }

    public void setDishes(List<LitemallMealOrder> dishes) {
        this.dishes = dishes;
    }
}
