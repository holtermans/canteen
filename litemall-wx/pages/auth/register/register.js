import WxValidate from '../../../utils/WxValidate.js';
var api = require('../../../config/api.js');
var check = require('../../../utils/check.js');
var util = require('../../../utils/util.js');

var app = getApp();
Page({
  data: {
    username: '',
    mobile: '',
    code: '',
    imgUrl: "https://bkimg.cdn.bcebos.com/pic/203fb80e7bec54e736d1578e56728c504fc2d5623c42?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UyNzI=,g_7,xp_5,yp_5/format,f_auto"
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    // 页面渲染完成
    this.initValidate(); //验证规则函数
  },
  onReady: function () {

  },
  onShow: function () {
    // 页面显示

  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  },
  sendCode: function () {
    let that = this;

    if (this.data.mobile.length == 0) {
      wx.showModal({
        title: '错误信息',
        content: '手机号不能为空',
        showCancel: false
      });
      return false;
    }

    wx.request({
      url: api.AuthRegisterCaptcha,
      data: {
        mobile: that.data.mobile
      },
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data.errno == 0) {
          wx.showModal({
            title: '发送成功',
            content: '验证码已发送',
            showCancel: false
          });
        } else {
          wx.showModal({
            title: '错误信息',
            content: res.data.errmsg,
            showCancel: false
          });
        }
      }
    });
  },
  //登录
  requestRegister: function (wxCode) {
    let that = this;
    wx.request({
      url: api.AuthLoginByWeixinSec,
      data: {
        username: that.data.username,
        mobile: that.data.mobile,
        authCode: that.data.code,
        wxCode: wxCode
      },
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data.errno == 0) {
          app.globalData.hasLogin = true;
          wx.setStorageSync('userInfo', res.data.data.userInfo);
          wx.setStorage({
            key: "token",
            data: res.data.data.token,
            success: function () {
              wx.showToast({
                title: '注册完成',
                icon: 'success',
                duration: 1500,
                success:function(){
                  setTimeout(() => {
                    wx.switchTab({
                      url: '/pages/ucenter/index/index'
                    });
                  }, 1500);
                  
                }
              })
              
            }
          });
        } else {
          wx.showModal({
            title: '错误信息',
            content: res.data.errmsg,
            showCancel: false
          });
        }
      }
    });
  },
  initValidate() {
    const rules = {
      username: {
        required: true
      },
      mobile: {
        required: true,
        tel: true
      },
      code: {
        required: true
      }
    }
    const messages = {
      username: {
        required: '请填写姓名'
      },
      mobile: {
        required: '请填写手机号',
        tel: '请填写正确的手机号'
      },
      code: {
        required: '请填写授权码'
      }
    }
    this.WxValidate = new WxValidate(rules, messages)
  },
  startRegister: function (e) {

    var that = this
    const params = e.detail.value

    //校验表单
    // console.log(e);
    if (!that.WxValidate.checkForm(params)) {
      const error = that.WxValidate.errorList[0]
      util.showErrorModal(error.msg)
      return false;
    }
    this.setData({
      username:params.username,
      mobile:params.mobile,
      code:params.code
    })

    // if (this.data.username.length == 0) {
    //   wx.showModal({
    //     title: '错误信息',
    //     content: '名字不能为空',
    //     showCancel: false
    //   });
    //   return false;
    // }

    // if (this.data.mobile.length == 0 ) {
    //   wx.showModal({
    //     title: '错误信息',
    //     content: '手机号',
    //     showCancel: false
    //   });
    //   return false;
    // }

    wx.login({
      success: function (res) {
        if (!res.code) {
          wx.showModal({
            title: '错误信息',
            content: '注册失败',
            showCancel: false
          });
        }
        that.requestRegister(res.code);
      }
    });
  },
  bindUsernameInput: function (e) {
    this.setData({
      username: e.detail.value
    });
  },
  bindPasswordInput: function (e) {

    this.setData({
      password: e.detail.value
    });
  },
  bindConfirmPasswordInput: function (e) {

    this.setData({
      confirmPassword: e.detail.value
    });
  },
  bindMobileInput: function (e) {

    this.setData({
      mobile: e.detail.value
    });
  },
  bindCodeInput: function (e) {

    this.setData({
      code: e.detail.value
    });
  },
  clearInput: function (e) {
    switch (e.currentTarget.id) {
      case 'clear-username':
        this.setData({
          username: ''
        });
        break;
      case 'clear-password':
        this.setData({
          password: ''
        });
        break;
      case 'clear-confirm-password':
        this.setData({
          confirmPassword: ''
        });
        break;
      case 'clear-mobile':
        this.setData({
          mobile: ''
        });
        break;
      case 'clear-code':
        this.setData({
          code: ''
        });
        break;
    }
  }
})