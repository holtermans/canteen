var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();
// pages/ucenter/statistics/statistics.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabs:[
      {id:"checked",name:"已核销"},
      {id:"not_checked",name:"未核销"}
    ],
    result: null,
    checkTimingId: null,
    timingList: [],
    dishesList: [],
    userList: {}, //储存
    checkUserList:{},  //储存核销与为核销用户id列表
    orderDishesList: {},
    dishesCount: {},
    checkedRecord: {},  //统计核销和未核销用户个数
    //弹窗显示
    show: {
      closeIcon: false,
    },
    checkTimingId: null, //统计详情页的timingId
    currentBcUserList: null, //当前时段下报餐用户的真实信息列表
    //日历数据
    date: {
      selectSingle: null, //目前只用了这个
    },
    type: 'single',
    round: true,
    color: undefined,
    minDate: Date.now(),
    maxDate: new Date(
      new Date().getFullYear(),
      new Date().getMonth() + 1,
      new Date().getDate()
    ).getTime(),
    maxRange: undefined,
    position: undefined,
    formatter: undefined,
    showConfirm: false,
    showCalendar: false,
    tiledMinDate: new Date(2012, 0, 10).getTime(),
    tiledMaxDate: new Date(2012, 2, 20).getTime(),
    confirmText: undefined,
    confirmDisabledText: undefined,
  },

  toggle(type, show) {
    this.setData({
      [`show.${type}`]: show
    });
  },
  goDetail(e) {
    console.log(e);
    this.setData({
      checkTimingId: e.currentTarget.dataset.id,
    })
    this.getBcUserInfoByUserId(this.data.userList[e.currentTarget.dataset.id]);
    this.toggle('closeIcon', true);
  },
  hideCloseIcon() {
    this.toggle('closeIcon', false);
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
    var that = this;

    this.getTimingList();
    this.getDishesList();
    var date = new Date();
    var promise = new Promise((resolve, reject) => {
      this.setData({
        'date.selectSingle': date.getTime()
      });
      resolve();
    });
    promise.then(() => {
      this.getStatistics();
      this.getCheckedList();
    })

  },
  getStatistics() {
    var that = this;
    var judge = [];
    var userList = {}; //根据时段列出用户id
    var orderDishesList = {}; //每一餐的菜品预定总集合
    var dishesCount = {};

    //查询一日的报餐情况
    wx.showLoading({
      title: '加载中',
    });
    util.request(api.AllOrderByDate, {
      date: util.getYMD(that.data.date.selectSingle)
    }).then((res) => {
      wx.hideLoading({
        success: (res) => {},
      })
      if (res.errno == 0) {
        that.data.timingList.forEach(timing => {
          userList[timing.id] = [];
          orderDishesList[timing.id] = [];
          dishesCount[timing.id] = {};
        })
        console.log(res);
        res.data.mealOrders.forEach(item => { //遍历回传的信息,寻找有用信息
          judge.push(item.userId + "/" + item.date + "/" + item.timingId); //组装唯一字符串标识
          orderDishesList[item.timingId].push(item.dishesId);
        });

        that.data.timingList.forEach(item => {
          var result = util.arrCheck(orderDishesList[item.id]);
          dishesCount[item.id] = result;
        })
        console.log(dishesCount);

        judge = util.unique(judge); //当日全部报餐名单，包括各个时段
        console.log(judge);
        judge.forEach(record => { //单独把记录拿出来
          that.data.timingList.forEach(timing => { //j记录进行时段匹配
            if (timing.id == record.split("/")[2]) {
              userList[timing.id].push(record.split("/")[0]);
            }
          })
        })
        that.setData({
          userList: userList,
          orderDishesList: orderDishesList,
          dishesCount: dishesCount,
          result: res.data.mealOrders
        })
        console.log(userList);
      }
    })
  },
  getCheckedList() {
    var that = this;
    var orderGroup = {};
    //查询一日的报餐情况
    wx.showLoading({
      title: '加载中',
    });
    util.request(api.DailyCanteenOrderList, {
      date: util.getYMD(that.data.date.selectSingle)
    }).then((res) => {
      wx.hideLoading({
        success: (res) => {},
      })
      if (res.errno == 0) {
        that.data.timingList.forEach(timing => { //分时段统计
          orderGroup[timing.id] = [];
        })
        console.log(res);
        res.data.forEach(item => { //遍历回传的信息,寻找有用信息
          orderGroup[item.timingId].push(item);
        });
        var check = {};
        var checkUserList = {}; 
        for (let key in orderGroup) {  //统计取餐人数和未取餐人数
          if (orderGroup.hasOwnProperty(key)) {
            checkUserList[key] = {};
            checkUserList[key]["checked"] = [];
            checkUserList[key]["not_checked"] = [];
            check[key] = {};
            check[key]["checked"] = 0;
            check[key]["not_checked"] = 0;
            orderGroup[key].forEach(item => {
              if (item.orderStatus == 103) {  //未核销
                check[key]["not_checked"]++;
                checkUserList[key]["not_checked"].push(item);
              } else { //已核销
                check[key]["checked"]++;
                checkUserList[key]["checked"].push(item);
              }
            })
          }
        }
      that.setData({
        checkedRecord:check,
        checkUserList: checkUserList,
      })
      console.log(checkUserList);
      }
    });

  },
  /**
   * 所有菜品信息
   */
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

  getBcUserInfoByUserId: function (arr) {
    var data = arr;
    var that = this;
    util.request(api.GetBcUserInfoByUserId, data, "POST").then(res => {
      if (res.errno == 0) {
        console.log(res)
        that.setData({
          currentBcUserList: res.data,
        })
      }
    })
  },
  //确认日期
  onConfirm(event) {
    let that = this;
    console.log(event);
    this.setData({
      showCalendar: false
    });
    var promise = new Promise((resolve, reject) => {
      this.setData({
        [`date.${this.data.id}`]: Array.isArray(event.detail) ? event.detail.map(date => date.valueOf()) : event.detail.valueOf()
      });
      resolve();
    });
    promise.then(() => {
      that.getStatistics();
      that.getCheckedList();
    });

  },

  onSelect(event) {
    console.log(event);
  },

  onUnselect(event) {
    console.log(event);
  },

  onClose() {
    console.log(this.data.date);
    this.setData({
      showCalendar: false
    });
  },

  onOpen() {
    console.log('open');
  },

  onOpened() {
    console.log('opened');
  },

  onClosed() {
    console.log('closed');
  },
  //获取用餐时段列表
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
  //重置日期
  resetSettings() {
    this.setData({
      round: true,
      color: null,
      minDate: new Date(
        new Date().getFullYear(),
        new Date().getMonth(),
        new Date().getDate()
      ).getTime(),
      maxDate: new Date(
        new Date().getFullYear(),
        new Date().getMonth() + 1,
        new Date().getDate()
      ).getTime(),
      maxRange: null,
      position: 'top',
      formatter: null,
      showConfirm: true,
      confirmText: '确定',
      confirmDisabledText: null
    });
  },
  //选择日期
  selectDate(event) {
    this.resetSettings();
    const {
      type,
      id,
    } = event.currentTarget.dataset;
    console.log(event.currentTarget.dataset)
    const data = {
      type,
      id,
      showCalendar: true
    };
    this.setData(data);
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
    this.getStatistics();
    this.getCheckedList();
    wx.stopPullDownRefresh();
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

  }
})