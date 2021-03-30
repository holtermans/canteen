var api = require('../../../config/api.js');
var util = require('../../../utils/util.js');
var user = require('../../../utils/user.js');

var app = getApp();
Page({
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    // 页面渲染完成

  },
  onReady: function () {

  },
  onShow: function () {
    // 页面显示
    console.log(app.globalData.userInfo);

  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  },
  wxLogin: function (e) {
    // console.log(e);
    console.log(e);
    if (e.detail.userInfo == undefined) {
      app.globalData.hasLogin = false;
      util.showErrorToast('微信登录失败');
      return;
    }
    console.log("授权成功")

    app.globalData.userInfo = e.userInfo; //保存为全局变量
    user.checkLogin().catch(() => { //如果没登录，就登录
      user.loginByWeixin(e.detail.userInfo).then(res => {
        if (res.data.token == null) {
          wx.redirectTo({
            url: "/pages/auth/register/register"
          });
        }else{
          app.globalData.hasLogin = true;
          wx.navigateBack({
            delta: 1
          })
        }
       
      }).catch((err) => {
        app.globalData.hasLogin = false;
        util.showErrorToast('微信登录失败');
      });

    });
  },
  accountLogin: function () {
    wx.navigateTo({
      url: "/pages/auth/accountLogin/accountLogin"
    });
  }
})