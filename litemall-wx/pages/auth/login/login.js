var api = require('../../../config/api.js');
var util = require('../../../utils/util.js');
var user = require('../../../utils/user.js');

var app = getApp();
Page({
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    // 页面渲染完成

  },
  getUserProfile(e) {
    wx.showLoading({
      title: '登录中',
    })
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
    // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    wx.getUserProfile({
      desc: '用于完善用户资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        app.globalData.userInfo = res.userInfo;
        user.checkLogin().catch(() => { //如果没登录，就登录
          user.loginByWeixin(res.userInfo).then(Rres => {
            if (Rres.data.token == null) {
              wx.showModal({
                title: '提示',
                content: '您还未注册个人真实信息，现在填写？',
                success (res) {
                  if (res.confirm) {
                    wx.redirectTo({
                      url: "/pages/auth/register/register"
                    });
                  } else if (res.cancel) {
                    
                  }
                }
              })
              
            } else {
              app.globalData.hasLogin = true;
              wx.switchTab({
                url: '/pages/ucenter/index/index'
              });

            }

          }).catch((err) => {
            app.globalData.hasLogin = false;
            util.showErrorToast('微信登录失败');
          });

        });
        wx.hideLoading({
          success: (res) => {},
        })
      },
      fail: (res) => {
        wx.hideLoading({
          success: (res) => {},
        })
        app.globalData.hasLogin = false;
        util.showErrorToast('微信登录失败');
        return;
      }
    })
  },
  wxLogin: function (e) {

    if (e.detail.userInfo == undefined) {
      app.globalData.hasLogin = false;
      util.showErrorToast('微信登录失败');
      return;
    }
    app.globalData.userInfo = e.userInfo; //保存为全局变量
    user.checkLogin().catch(() => { //如果没登录，就登录
      user.loginByWeixin(e.detail.userInfo).then(res => {
        if (res.data.token == null) {
          wx.redirectTo({
            url: "/pages/auth/register/register"
          });
        } else {
          app.globalData.hasLogin = true;
          wx.switchTab({
            url: '/pages/ucenter/index/index'
          });
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