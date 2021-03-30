// pages/ucenter/record/record.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    mealOrders: [],
    timingList: [],
    hasOrderDateList: [],
    dishesList: [],
    canteenOrderList: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
    let that = this;
    this.getTimingList(); //获取时段列表
    this.getDishesList(); //获取菜品列表
    this.getMyOrderList();
    this.getCanteenOrder();
  },
  getMyOrderList() {
    let that = this;
    util.request(api.MealOrderList).then(res => { //查询报餐记录
      if (res.errno == 0) {
        var dateList = [];
        console.log(res);
        res.data.list.forEach(item => {
          dateList.push(item.date);
        })
        dateList.sort((a, b) => { //排序
          return Date.parse(b) - Date.parse(a);
        })
        that.setData({
          mealOrders: res.data.list,
          hasOrderDateList: util.unique(dateList)
        });


      }
    })

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
  /**
   * 获取时段里诶博爱
   */
  getTimingList: function () {
    let that = this;
    util.request(api.TimingList).then(function (res) {
      if (res.errno === 0) {
        console.log(res);
        that.setData({
          timingList: res.data.timingList,
        })
      }
    });
  },
  //获取菜品列表
  getDishesList: function () {
    let that = this;
    util.request(api.dishesList).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          dishesList: res.data.dishesList,
        })
      }
    });
  },
  checkMeal(e) {
var that = this;
    let id = e.currentTarget.dataset.id;
    let type = e.currentTarget.dataset.type;
    let date = e.currentTarget.dataset.date;

    var startTime = null;
    var endTime = null;
    this.data.timingList.forEach(timing => {
      if (timing.id == type) {
        startTime = timing.startTime;
        endTime = timing.endTime;
      }
    })
    var startDateTime = new Date(date + " " + startTime);
    var endDateTime = new Date(date + " " + endTime);

    util.getServerTime().then(now => {
      console.log(now);
      if (startDateTime.getTime() < new Date(now).getTime()) {
        wx.scanCode({
          onlyFromCamera: true,
          success(res) {
            var code = res.result;
            util.request(api.OrderCheck, {
              orderId: id,
              code: code
            }).then(res => {
              if(res.errno == 0){
                wx.showToast({
                  title: '核销成功',
                })
                that.getCanteenOrder();
              }else{
                wx.showToast({
                  title: '核销二维码不正确',
                })
              }
            })
          }
        })
      } else {
        wx.showToast({
          title: '未到时间取餐',
        })
      }
    })
    // 只允许从相机扫码

  },

  getCanteenOrder() {
    let that = this;
    util.request(api.CanteenOrderList).then(res => { //查询报餐记录
      if (res.errno == 0) {
        console.log(res);
        that.setData({
            canteenOrderList: res.data.list,
          }),
          console.log(that.data.canteenOrderList)
      }
    })
  }
})