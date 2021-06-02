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
    mealOrders: [],
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
  onShow: function () {
    this.setData({
      focus: true
    })
  },
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

        const innerAudioContext = wx.createInnerAudioContext();
        innerAudioContext.onError(function (res) {
          wx.showToast({
            title: '语音播放失败',
            icon: 'none',
          })
        })
        innerAudioContext.autoplay = true
        innerAudioContext.src = that.data.src
        innerAudioContext.onPlay(() => {})

      },
      fail: function (res) {}
    })
  },
  check(orderSn) {
    var that = this;
    wx.showLoading({
      title: '加载中',
    })
    var data = {
      orderSn: orderSn,
    }
    //先查询订单状态
    util.request(api.CanteenOrderByOrderSn, data).then(res => {
      if (res.errno == 0) {
        var canteenOrder = res.data.canteenOrder; // 临时用
        var status = canteenOrder.orderStatus; //取出订单状态
        if (status == 107) { // 规定107是已经核销的订单，直接结束
          var param = [canteenOrder.userId];

          util.request(api.GetBcUserHashMapByUserId, param, "POST").then((res) => {
            that.setData({
              bcUserVo: res.data.bcUserVoHashMap[canteenOrder.userId],
            })
          })
          var param2 = {
            orderId: canteenOrder.id,
          };
          util.request(api.MealOrderByOrderId, param2).then(res => {
            that.setData({
              mealOrders: res.data.mealOrders,
            })
          })
          that.setData({
            canteenOrder: res.data.canteenOrder,
            show: true,
          })
          if (that.data.checked) {

            that.speak("该订单已核销");
            wx.showToast({
              title: '该订单已核销',
              icon:"error"
            })
          }
          wx.hideLoading({
            success: (res) => {},
          });
          return;
        } else { //未核销的订单，去核销
          util.request(api.OrderCheckByOrderSn, data).then(res => {
            if (res.errno == 0) {
              that.setData({
                canteenOrder: res.data.canteenOrder,
                mealOrders: res.data.mealOrders,
                bcUserVo: res.data.bcUserVo,
                show: true,
              })
              if (that.data.checked) {

                var content = "";
                if (that.data.nameChecked) {
                  content += that.data.bcUserVo.name;
                }
                if (that.data.typeChecked) {
                  content += that.data.canteenOrder.timingName;
                }
                if (that.data.priceChecked) {
                  content += that.data.canteenOrder.orderPrice + "元，";
                }
                content += "祝您用餐愉快";
                that.speak(content);
              }
              wx.hideLoading({
                success: (res) => {},
              })
            } else {
              that.speak("网络出现了一点问题~");
              util.showErrorModal(res.errmsg);
            }
          })
          this.setData({
            value: '',
            focus: true,
          });
        }
      } else {
        wx.hideLoading({
          success: (res) => {},
        });
        if (that.data.checked) {
          wx.showToast({
            title: '二维码有误',
            icon:"error"
          })
          that.speak("二维码有误");
        }
        return;

      }
    })
  },
  inputConfirm: function (e) {
    var that = this;
    // wx.showToast({
    //   title: e.detail,
    // });
    this.check(e.detail);
    this.setData({
      focus: true,
      value: '',
    })
  },
  goStatistics: function () {
    wx.navigateTo({
      url: "/pages/ucenter/statistics/statistics"
    });

  },
  getFocus: function () {
    this.setData({
      value: '',
      focus: true,
    });
  },
  onChange(e) {
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
    // 需要手动对 checked 状态进行更新
    this.setData({
      nameChecked: e.detail.value
    });
  },
  onChangePrice(e) {
    // 需要手动对 checked 状态进行更新
    this.setData({
      priceChecked: e.detail.value
    });
  },
  onChangeType(e) {

    // 需要手动对 checked 状态进行更新
    this.setData({
      typeChecked: e.detail.value
    });
  },
  collapse(event) {
    this.setData({
      collapseAvtive: event.detail,
    });
  },
  scanForCheck() {
    var that = this;
    wx.scanCode({
      onlyFromCamera: true,
      success(res) {
        var code = res.result;
        that.check(code);
      },
      fail(res) {

      }
    })
  }
})