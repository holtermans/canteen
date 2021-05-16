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
    active: 1,
    statusCode: ['', 103, 107],
    checkMode: undefined,

    pageNum: 1,
    pageSize: 3,
    nomore: false,
    showLoading: false,
  },

  onReachBottom: function () {
    console.log("bottom")
    if (this.data.nomore) return;
    this.setData({
      showloading: true,
    });
    setTimeout(() => {
      this.getCanteenOrder();
    }, 500);

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    this.getTimingList(); //获取时段列表
    this.getDishesList(); //获取菜品列表
    this.getMyOrderList();
    // this.getCanteenOrder();
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },
  getMyOrderList() {
    let that = this;
    util.request(api.MealOrderList).then(res => { //查询报餐记录
      if (res.errno == 0) {
        var dateList = [];

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
   * 获取时段列表信息
   */
  getTimingList: function () {
    let that = this;
    util.request(api.TimingList).then(function (res) {
      if (res.errno === 0) {
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
        startTime = timing.startTime.replace(/-/g, '/');
        endTime = timing.endTime.replace(/-/g, '/');
      }
    })
    var startDateTime = new Date(date + " " + startTime);
    var endDateTime = new Date(date + " " + endTime);

    util.getServerTime().then(now => {
      now = now.replace(/-/g, '/')
      if (startDateTime.getTime() < new Date(now).getTime()) {
        wx.scanCode({
          onlyFromCamera: true,
          success(res) {
            var code = res.result;
            util.request(api.OrderCheck, {
              orderId: id,
              code: code
            }).then(res => {
              if (res.errno == 0) {
                wx.showToast({
                  title: '核销成功',
                })
                that.getCanteenOrder();
              } else {
                wx.showToast({
                  icon: "error",
                  title: res.errmsg
                })
              }
            })
          },
          fail(res) {

          }
        })
      } else {
        wx.showToast({
          icon: 'error',
          title: '未到时间取餐',
        })
      }
    })
    // 只允许从相机扫码

  },
  cancelMeal(e) {
    var that = this;
    let id = e.currentTarget.dataset.id;
    let type = e.currentTarget.dataset.type;
    let date = e.currentTarget.dataset.date;
    var startTime = null;
    var endTime = null;
    var stopTime = null; //报餐截止时间
    this.data.timingList.forEach(timing => {
      if (timing.id == type) {
        startTime = timing.startTime.replace(/-/g, '/');
        endTime = timing.endTime.replace(/-/g, '/');
        stopTime = timing.stopTime.replace(/-/g, '/');
      }
    })
    date = date.replace(/-/g, '/');
    var startDateTime = new Date(date + " " + startTime);
    var endDateTime = new Date(date + " " + endTime);
    var stopDateTime = new Date(date + " " + stopTime);

    //获取服务器时间
    util.getServerTime().then(nowTime => {
      nowTime = nowTime.replace(/-/g, '/');
      if (stopDateTime.getTime() < new Date(nowTime).getTime()) {
        wx.showToast({
          title: '超出时间',
          icon: 'error',
          duration: 1500
        })
        return;
      }
      wx.showModal({
        title: '提示',
        content: '取消报餐',
        success(res) {
          if (res.confirm) {
            var data = {
              orderId: id,
            }
            util.request(api.OrderCancel, data).then(res => {
              if (res.errno == 0) {
                wx.showToast({
                  title: '已取消',
                });
                that.setData({
                  pageNum: 1,
                  canteenOrderList: [],
                  nomore: false,
                  showLoading: false,
                })
                that.getCanteenOrder();
              } else {
                wx.showToast({
                  icon: "error",
                  title: '取消时出错',
                })
              }
            })
          } else if (res.cancel) {}
        }
      });
    });

  },
  onChange(e) {
    console.log("change事件")
    wx.showLoading({
      title: '加载中',
    })
    this.setData({
      active: e.detail.index,
      pageNum: 1,
      canteenOrderList: [],
      nomore: false,
      showLoading: false,
    })
    var promise = new Promise((resolve, reject) => {
      this.getCanteenOrder();
      resolve();
    });
    promise.then(() => {
      wx.hideLoading({
        success: (res) => {},
      })
    });

  },

  getCanteenOrder() {
    let that = this;
    var data = {
      pageNum: this.data.pageNum,
      pageSize: this.data.pageSize,
      status: this.data.statusCode[this.data.active]
    }
    util.request(api.CanteenOrderPage, data).then(res => { //查询报餐记录
      console.log(res);
      if (res.errno == 0) {
        that.setData({
          pageNum: res.data.length < that.data.pageSize ? that.data.pageNum : that.data.pageNum + 1,
          canteenOrderList: [...that.data.canteenOrderList, ...res.data],
          showloading: false,
          nomore: res.data.length < that.data.pageSize ? true : false,
        });
        wx.setStorageSync('canteenOrderList', that.data.canteenOrderList);
      }
    })
  },
  createQrcode(e) {
    wx.navigateTo({
      url: '/pages/ucenter/QRCode/QRCode?orderSn=' + e.currentTarget.dataset.orderSn + '&orderId=' + e.currentTarget.dataset.orderId,
    })
  },
  onPullDownRefresh: function () {  
    this.setData({
      pageNum: 1,
      canteenOrderList: [],
      nomore: false,
      showLoading: false,
    })
    var promise = new Promise((resolve, reject) => {
      this.getCanteenOrder();
      resolve();
    });
    promise.then(() => {
      wx.stopPullDownRefresh({
        success: (res) => {},
      });
      wx.hideLoading({
        success: (res) => {},
      })
    });
   
  },

})