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
    orderId: undefined,
    orderSn: undefined,
    canteenOrderList: undefined,
    orderDetail: undefined,
    canteenOrder: undefined,
    brightness: undefined
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;

    wx.getScreenBrightness({
      success: (option) => {
        that.setData({
          brightness: option.value,
        })
      },
    })
    wx.setScreenBrightness({
      value: 0.7,
    });
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
    console.log(canteenOrderListTemp);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    createQrcode(this.data.orderSn);
    this.getOrderDetail(this.data.orderId);
    this.getCanteenOrder(this.data.orderSn);
    interval = setInterval(() => {
      this.getCanteenOrder(this.data.orderSn);
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
    var brightness = this.data.brightness;
    wx.setScreenBrightness({
      value: brightness,
    });
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
        console.log(res);
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
        console.log(res);
        that.setData({
          orderDetail: res.data.mealOrders,
        });

      }
    })
  },
})