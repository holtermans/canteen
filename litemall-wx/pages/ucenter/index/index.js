var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();

Page({
  data: {
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
    hasLogin: false,
    isAdmin: false,
  },
  onLoad: function (options) {
    let that = this;
    // 页面初始化 options为页面跳转所带来的参数
    //判断用户是否为管理员
    util.request(api.GetSingleBcUserByUserId).then((res) => {
      if (res.errno == 0) {
        this.setData({
          bcUserInfo: res.data.bcUserInfo,
          isAdmin: res.data.bcUserInfo.admin,
        })
      }
    }).catch(() => {})
  },
  onShow: function () {
    let that = this;
    //获取用户的登录信息
    if (app.globalData.hasLogin) {
      let userInfo = wx.getStorageSync('userInfo');
      if (userInfo == null) {
        var temp = {
          nickName: '点击登录',
          avatarUrl: '/static/images/my.png'
        };
        this.setData({
          userInfo: temp,
          hasLogin: false
        });
      } else {
        this.setData({
          userInfo: userInfo,
          hasLogin: true
        });
      }
    }
  },
  goLogin() {
    if (!this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    }
  },
  updateInfo() {
    var that = this;
    wx.showLoading({
      title: '加载中',
    })
    wx.getUserProfile({
      desc: '更新账号信息',
      success: (res) => {
        wx.setStorageSync('userInfo', res.userInfo);
        let userInfo = wx.getStorageSync('userInfo');
        this.setData({
          userInfo: userInfo,
          hasLogin: true
        });
        util.request(api.updateUser, {
          avatar: userInfo.avatarUrl
        }, 'POST').then(function (res) {
          if (res.errno === 0) {
            wx.hideLoading();
            wx.showToast({
              title: '已更新',
            })

          } else {
            wx.hideLoading();
            wx.showToast({
              icon: 'error',
              title: 'Oops!',
            })
          }
        }).catch(function (e) {
          wx.hideLoading();
          wx.showToast({
            title: '网络有点问题~',
            icon: 'error'
          })
        });
      },
      fail: (res) => {
        wx.hideLoading();
        wx.showToast({
          title: '更新时出错',
          content: res
        })
      }
    })
  },

  goOrder() {
    if (this.data.hasLogin) {
      try {
        wx.setStorageSync('tab', 0);
      } catch (e) {

      }
      wx.navigateTo({
        url: "/pages/ucenter/order/order"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    }
  },
  goOrderIndex(e) {
    if (this.data.hasLogin) {
      let tab = e.currentTarget.dataset.index
      let route = e.currentTarget.dataset.route
      try {
        wx.setStorageSync('tab', tab);
      } catch (e) {}
      wx.navigateTo({
        url: route,
        success: function (res) {},
        fail: function (res) {},
        complete: function (res) {},
      })
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  getSwitch() {
    var that = this;
    var query = {
      name: "switch1"
    }
    return new Promise((resolve, reject) => {
      util.request(api.GetConfig, query).then((res) => {
        if (res.errno == 0) {
          resolve(res.data.config.value);
        } else {
          reject();
        }
      })
    })

  },
  goComment: function (e) {
    this.getSwitch().then(res => {
      console.log(JSON.parse(res))
      if (!JSON.parse(res)) {
        wx.showToast({
          title: '未开放',
        })
        return;
      }
      if (this.data.hasLogin) {
        let tab = e.currentTarget.dataset.index
        let route = e.currentTarget.dataset.route
        try {
          wx.setStorageSync('tab', tab);
        } catch (e) {}
        wx.navigateTo({
          url: route,
          success: function (res) {},
          fail: function (res) {},
          complete: function (res) {},
        })
      } else {
        wx.navigateTo({
          url: "/pages/auth/login/login"
        });
      };
    })


  },
  goSystemConfig: function () {
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
  goAdmin: function () {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/admin/admin"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  bindPhoneNumber: function (e) {
    if (e.detail.errMsg !== "getPhoneNumber:ok") {
      // 拒绝授权
      return;
    }

    if (!this.data.hasLogin) {
      wx.showToast({
        title: '绑定失败：请先登录',
        icon: 'none',
        duration: 2000
      });
      return;
    }

    util.request(api.AuthBindPhone, {
      iv: e.detail.iv,
      encryptedData: e.detail.encryptedData
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        wx.showToast({
          title: '绑定手机号码成功',
          icon: 'success',
          duration: 2000
        });
      }
    });
  },
  goAfterSale: function () {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/aftersaleList/aftersaleList"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  aboutUs: function () {
    wx.navigateTo({
      url: '/pages/about/about'
    });
  },
  goHelp: function () {
    wx.navigateTo({
      url: '/pages/help/help'
    });
  },

  exitLogin: function () {
    wx.showModal({
      title: '',
      confirmColor: '#b4282d',
      content: '退出登录？',
      success: function (res) {
        if (!res.confirm) {
          return;
        }
        util.request(api.AuthLogout, {}, 'POST');
        app.globalData.hasLogin = false;
        wx.removeStorageSync('token');
        wx.removeStorageSync('userInfo');
        wx.reLaunch({
          url: '/pages/index/index'
        });
      }
    })
  },

})