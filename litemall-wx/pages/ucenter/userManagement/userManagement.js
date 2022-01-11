// pages/ucenter/userManagement/userManagement.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pageNum: 1,
    pageSize: 20,
    pages: 0,
    filter1: 'actived',
    filter2: "a",
    option1: [
      {
        text: '已激活',
        value: 'actived'
      },
      {
        text: '待激活',
        value: 'notActived'
      }
    ],
    option2: [{
      text: '默认排序',
      value: "a"
    }, ],
    bcUserList: {},
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getBcUserList().then(res => {
      this.setData({
        bcUserList: res.data.bcUserList,
        pages: res.data.bcUserList.pages,
      })
    }).catch(() => {
      util.showErrorModal("数据请求出错");
    });
  },

  onChange: function (value) {
    this.setData({
      filter1: value.detail,
      pageNum: 1,
      pageSize: 20,
      pages: 0,
    });
    this.getBcUserList().then(res => {
      this.setData({
        bcUserList: res.data.bcUserList,
        pages: res.data.bcUserList.pages,
      })
    }).catch(() => {
      util.showErrorModal("数据请求出错");
    });
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
    var that = this;
    if (this.data.pageNum < this.data.pages) {
      var pageNum = this.data.pageNum + 1;
      that.setData({
        pageNum: pageNum
      })
      this.getBcUserList().then(res => {
        res.data.bcUserList.list.forEach(item => {
          that.data.bcUserList.list.push(item);
        })
        that.setData({
          bcUserList: that.data.bcUserList
        })

      }).catch(() => {
        util.showErrorModal("数据请求出错");
        this.data.pageNum--;
      })
    }
  },
 

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  getBcUserList: function () {
    var that = this;

    wx.showLoading({
      title: '加载中',
    });
    var data = {
      type: that.data.filter1,
      pageNum: that.data.pageNum,
      pageSize: that.data.pageSize,
    }
    return new Promise((resolve, reject) => {
      util.request(api.GetBcUserList, data).then((res) => {
        if (res.errno == 0) {
          resolve(res)
        } else {
          reject();
        }
        wx.hideLoading({
          success: (res) => {},
        })
      })
    })
  },
  pass: function(e){
    var that = this;
    wx.showLoading({
      title: '加载中',
    });
    //获取在数据库中的id
    var data = {
      "id": e.currentTarget.dataset.id,
      "status": 1
    }
    util.request(api.UpdateBcUser, data,"POST").then((res) => {
      if (res.errno == 0) {
        that.setData({
          pageNum: 1,
          pageSize: 10,
          pages: 0,
        });
        that.getBcUserList().then(res => {
          that.setData({
            bcUserList: res.data.bcUserList,
            pages: res.data.bcUserList.pages,
          })
        }).catch(() => {
          util.showErrorModal("数据请求出错");
        });
      } else {}
      wx.hideLoading({
        success: (res) => {},
      })
    })
  },
  unpass: function(e){
    var that = this;
    wx.showLoading({
      title: '加载中',
    });
    //获取在数据库中的id
    var data = {
      "id": e.currentTarget.dataset.id,
      "status": 0
    }
    util.request(api.UpdateBcUser, data,"POST").then((res) => {
      if (res.errno == 0) {
        that.setData({
          pageNum: 1,
          pageSize: 10,
          pages: 0,
        });
        that.getBcUserList().then(res => {
          that.setData({
            bcUserList: res.data.bcUserList,
            pages: res.data.bcUserList.pages,
          })
        }).catch(() => {
          util.showErrorModal("数据请求出错");
        });
      } else {  
      }
      wx.hideLoading({
        success: (res) => {},
      })
    })
  }
})