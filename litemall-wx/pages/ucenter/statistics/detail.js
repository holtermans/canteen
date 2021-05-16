// pages/ucenter/statistics/detail.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderId: null,
    orderSn: null,
    orderDetail: null,
    canteenOrder: null,
    name: null,
    mobile: null,
    avatar: null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options);
    this.setData({
      name: options.name,
      mobile: options.mobile,
      avatar: options.avatar,
    })
    this.getOrderDetail(options.orderId); //获取订单菜品详情
    this.getCanteenOrder(options.orderSn);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  getCanteenOrder(orderSn) {
    let that = this;
    var data = {
      orderSn: orderSn
    }
    util.request(api.CanteenOrderByOrderSn, data).then(res => { //查询报餐记录
      if (res.errno == 0) {
        that.setData({
          canteenOrder: res.data.canteenOrder,
        });
      } else {
        wx.showToast({
          title: res.errmsg,
        })
      }
    })
  },
  getOrderDetail(orderId) {
    let that = this;
    var data = {
      orderId: orderId
    }
    util.request(api.MealOrderByOrderId, data).then(res => { //查询报餐记录
      if (res.errno == 0) {
        that.setData({
          orderDetail: res.data.mealOrders,
        });

      }
    })
  },




})