var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();
// pages/ucenter/admin/admin.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    bcUserInfo: null,
    userInfo: {
      nickName: '点击登录',
      avatarUrl: '/static/images/my.png'
    },
    isShowAuthorizon: false,
    order: {
      unpaid: 0,
      unship: 0,
      unrecv: 0,
      uncomment: 0
    },
    hasLogin: false
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
    //获取用户的登录信息
    if (app.globalData.hasLogin) {
      let userInfo = wx.getStorageSync('userInfo');
      this.setData({
        userInfo: userInfo,
        hasLogin: true
      });
    }
    //判断用户是否为管理员
    util.request(api.GetSingleBcUserByUserId).then((res) => {
      if (res.errno == 0) {
        this.setData({
          bcUserInfo: res.data.bcUserInfo,
        })
      }
    }).catch(() => {

    })
  },
  goSetTime: function () {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/setTime/setTime"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goSystemConfig:function(){
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/systemConfig/systemConfig"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goUserManagement: function () {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/userManagement/userManagement"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goDishes: function () {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/dishes/dishes"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goDailyMenu: function () {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/DailyMenu/DailyMenu"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goStatistics: function () {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/statistics/statistics"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
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