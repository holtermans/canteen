// pages/ucenter/dishes/dished.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    dishesList: [],
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
    this.getDishesList();
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

  },
  //获取菜品列表
  getDishesList: function () {
    let that = this;
    util.request(api.dishesList).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          dishesList: res.data.dishesList,
        })
      }
    });
  },

  addDish: function () {
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success(res) {
        // tempFilePath可以作为img标签的src属性显示图片
        const tempFilePaths = res.tempFilePaths
        wx.uploadFile({
          url: api.StorageUpload, //图片上传接口
          filePath: tempFilePaths[0],
          name: 'file',
          formData: {
            'user': 'admin'
          },
          success(res) {
            const data = res.data
            console.log(res);
            wx.showToast({
              title: '上传成功',
            })
            //do something
          },
          fail(res){
            const data = res.data
            console.log(res);
            wx.showToast({
              title: '上传图片出错',
            })
          }
        })
      }
    })
  },
  navigateToPublish: function () {
    wx.navigateTo({
      url: '/pages/ucenter/addDish/addDish'
    })
  },
  delete: function (e) {
    var that = this;
    console.log(e);
    wx.showModal({
      title: '提示',
      content: '确定要删除？',
      success(res) {
        if (res.confirm) {
          util.request(api.DishesDel, {id:e.currentTarget.dataset.id}).then(res => {
            if (res.errno == 0) {
              wx.showToast({
                title: '已删除',
              });
              that.getDishesList();
            }
          })
        } else if (res.cancel) {}
      }
    })


  }

})