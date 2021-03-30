const {
  TimingList
} = require("../../../config/api");
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
const {
  showErrorToast,
  showErrorModal
} = require("../../../utils/util.js");
// pages/ucenter/setTime/setTime.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    checked: true,
    timingList: [],
    time: "09:00",
    startTime: "09:00",
    endTime: "11:00",
    modTiming: {  //编辑数据保存
      id: null,
      name: '',
      status: 1,
      startTime: '',
      endTime: '',
      reminder: 0,
      stopTime: '',
    },
    show: {
      basic: false,
    }
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
    this.getTimingList();
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
    this.getTimingList();
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

  onChange(e) {
    console.log(e.detail.value);
    this.setData({
      checked: e.detail.value
    });
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
  //弹出编辑界面
  showBasic: function (e) {
    console.log(e.target.dataset.itemId);
    var list = this.data.timingList;
    list.forEach(item => {
      if (item.id == e.target.dataset.itemId) {
        this.setData({
          'modTiming.id': item.id,
          'modTiming.name': item.name,
          'modTiming.status': item.status,
          'modTiming.startTime': item.startTime.substring(0, 5),
          'modTiming.endTime': item.endTime.substring(0, 5),
          'modTiming.reminder': item.reminder,
          'modTiming.stopTime': item.stopTime.substring(0, 5),
        })
      }
    })

    this.setData({
      "show.basic": true,
    })
  },
//关闭编辑界面
  hideBasic: function () {
    this.setData({
      "show.basic": false,
    })
  },
  //时间选择器数据改变更新
  bindTimeChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.target.dataset.timeType)
    var field = "modTiming." + e.target.dataset.timeType;
    console.log(field);
    var fieldin = {}
    fieldin[field] = e.detail.value;
    console.log(fieldin);
    this.setData(fieldin);
  },
//编辑页面保存
  onSubmit: function () {
    var that = this;
    let modTiming = this.data.modTiming;
    if (!this.compareTime(modTiming.startTime, modTiming.endTime)) {
      showErrorModal("开始时间不能大于结束时间")
      return;
    } else {
      util.request(api.TimingUpdate, modTiming).then(function (res) {
        if (res.errno === 0) {
          showErrorModal("已保存")
          that.getTimingList();
        }
      });
    }
  },
  //编辑界面可用状态按钮响应事件
  changeStatus: function (e) {
    console.log(e.detail.value)
    var status = e.detail.value ? 1 : 0;
    console.log(status);
    this.setData({
      "modTiming.status": status,
    })
  },
  //工具 比较时间大小time1比time2小返回false
  compareTime: function (time1, time2) {
    let time1Arr = time1.split(":");
    let time2Arr = time2.split(":");
    let time1h = time1Arr[0]; //时
    let time2h = time2Arr[0]; //时
    let time1m = time1Arr[1]; //分
    let time2m = time2Arr[1]; //分

    if (time1h < time2h) {
      return true;
    } else if (time1h == time2h && time1m <= time2m) {
      return true;
    } else {
      return false;
    }
  },
})