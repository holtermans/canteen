var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
// pages/ucenter/addDish/addDish.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    chooseImageA: {
      sourceObj: {},
      isUploaded: false,
      uploadPath: null,

    },
    chooseImageB: {
      sourceObj: {},
      isUploaded: false
    },
    chooseImageC: {
      sourceObj: {},
      isUploaded: false
    },
    chooseImageD: {
      sourceObj: {},
      isUploaded: false
    },
    statementText: '注：注意事项。'
  },

  /** 
   * 选择图片
   */
  chooseImage: function (e) {
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'Screenshots'],
      success: res => {
        switch (e.currentTarget.dataset.chooseImage) {
          case 'A':
            this.setData({
              chooseImageA: {
                sourceObj: res.tempFiles[0],
                isUploaded: true
              }
            })
            break
          case 'B':
            this.setData({
              chooseImageB: {
                sourceObj: res.tempFiles[0],
                isUploaded: true
              }
            })
            break
          case 'C':
            this.setData({
              chooseImageC: {
                sourceObj: res.tempFiles[0],
                isUploaded: true
              }
            })
            break
          case 'D':
            this.setData({
              chooseImageD: {
                sourceObj: res.tempFiles[0],
                isUploaded: true
              }
            })
            break
        }
      }
    })
  },
  formSubmit(e) {
    wx.showLoading({
      title: '加载中',
    })
    // tempFilePath可以作为img标签的src属性显示图片
    const tempFilePaths = this.data.chooseImageA.sourceObj;
    if (tempFilePaths.filePath == undefined) {
      var data = {
        name: e.detail.value.name,
        price: e.detail.value.price,
        brief: e.detail.value.brief,
      }
      //再发请求去存储
      util.request(api.DishesAdd, data, "POST").then((res) => {
        if (res.errno == 0) {
          wx.navigateBack({
            delta: 1,
          })
        }
      })
      return;
    }
    var that = this;
    wx.uploadFile({
      url: api.StorageUpload, //仅为示例，非真实的接口地址
      filePath: tempFilePaths.path,
      name: 'file',
      formData: {
        'user': 'admin'
      },
      header: {
        'Content-Type': 'application/json',
      },
      success(res) {
        const result = JSON.parse(res.data);
        var data = {
          name: e.detail.value.name,
          price: e.detail.value.price,
          brief: e.detail.value.brief,
          picUrl: result.data.storage.url
        }
        that.setData({
          "chooseImageA.uploadPath": result.data.storage.url,
        })
        //再发请求去存储
        util.request(api.DishesAdd, data, "POST").then((res) => {
          if (res.errno == 0) {
            wx.navigateBack({
              delta: 1,
            })
          }
        })
        //do something
      }
    })
    wx.hideLoading({
      success: (res) => {},
    })
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