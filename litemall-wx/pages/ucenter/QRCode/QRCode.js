// pages/ucenter/QRCode/QRCode.js
import drawQrcode from '../../../lib/qrcode/weapp.qrcode.esm.js'
import createQrcode from "../../../utils/qrcode.js"
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var interval = undefined;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderId: null,
    orderSn: '',
    canteenOrderList: [],
    orderDetail: null,
    canteenOrder: [],
    brightness: null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    var canteenOrderListTemp = wx.getStorageSync('canteenOrderList');
    canteenOrderListTemp.forEach(item => {
      if (item.orderSn == options.orderSn) {
        that.setData({
          canteenOrder: item
        });
      }
    })
    this.setData({
      orderId: options.orderId,
      orderSn: options.orderSn,
      canteenOrderList: canteenOrderListTemp
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    createQrcode(this.data.orderSn);//创建二维码
    this.getOrderDetail(this.data.orderId); //获取订单菜品详情
    this.getCanteenOrder(this.data.orderSn);  //获取订单信息
    interval = setInterval(() => {
      this.getOrderDetail(this.data.orderId); //获取订单菜品详情
      this.getCanteenOrder(this.data.orderSn);
    }, 1000);
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    clearInterval(interval);
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

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