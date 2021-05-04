// pages/ucenter/systemConfig.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();
const plugin = requirePlugin("WechatSI")

Page({

  /**
   * 页面的初始数据
   */
  data: {
    radio: 1,
    value: undefined,
    focus: true,
    canteenOrder: undefined,
    mealOrders: undefined,
    bcUserVo: undefined,
    show: false,
    src: '',
    innerAudioContext: undefined,
    checked: true,
    nameChecked: true,
    priceChecked: true,
    typeChecked: true,
    collapseAvtive: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var checkMode = app.globalData.checkMode;
    this.setData({
      radio: checkMode,
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},

  speak(content) {
    var that = this;
    plugin.textToSpeech({
      lang: "zh_CN",
      tts: true,
      content: content,
      success: function (res) {
        that.setData({
          src: res.filename
        })
        console.log("succ tts", res.filename)

        const innerAudioContext = wx.createInnerAudioContext();
        innerAudioContext.onError(function (res) {
          console.log(res);
          wx.showToast({
            title: '语音播放失败',
            icon: 'none',
          })
        })
        console.log(that.data.src);
        innerAudioContext.autoplay = true
        innerAudioContext.src = that.data.src
        innerAudioContext.onPlay(() => {
          console.log('开始播放')
        })

      },
      fail: function (res) {
        console.log("fail tts", res)
      }
    })
  },
  inputConfirm: function (e) {
    // wx.showToast({
    //   title: e.detail,
    // });
    wx.showLoading({
      title: '加载中',
    })
    var data = {
      orderSn: e.detail,
    }
    util.request(api.OrderCheckByOrderSn, data).then(res => {
      wx.hideLoading({
        success: (res) => {},
      })
      if (res.errno == 0) {
        // wx.showToast({
        //   title: '核销成功',
        // });
        this.setData({
          canteenOrder: res.data.canteenOrder,
          mealOrders: res.data.mealOrders,
          bcUserVo: res.data.bcUserVo,
          show: true,
        })
        if (this.data.checked) {
          var content = "";
          if(this.data.nameChecked){
            content += this.data.bcUserVo.name;
          }
          if(this.data.typeChecked){
            content += this.data.canteenOrder.timingName;
          }
          if(this.data.priceChecked){
            content += this.data.canteenOrder.orderPrice + "元，";
          }
          content += "用餐愉快";
          this.speak(content);
        }

      } else {
        util.showErrorModal(res.errmsg);
      }
    })
    this.setData({
      value: '',
      focus: true,
    });
  },
  getFocus: function () {
    this.setData({
      value: '',
      focus: true,
    });
  },
  onChange(e) {
    console.log(e);
    // 需要手动对 checked 状态进行更新
    this.setData({
      checked: e.detail.value
    });
    if (e.detail.value == false) {
      this.setData({
        nameChecked: e.detail.value,
        priceChecked: e.detail.value,
        typeChecked: e.detail.value,

      });
    }
  },
  onChangeName(e) {
    console.log(e);
    // 需要手动对 checked 状态进行更新
    this.setData({
      nameChecked: e.detail.value
    });
  },
  onChangePrice(e) {
    console.log(e);
    // 需要手动对 checked 状态进行更新
    this.setData({
      priceChecked: e.detail.value
    });
  },
  onChangeType(e) {
    console.log(e);
    // 需要手动对 checked 状态进行更新
    this.setData({
      typeChecked: e.detail.value
    });
  },
  collapse(event){
    this.setData({
      collapseAvtive: event.detail,
    });
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

  }
})