// pages/score/score.js
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    value: 0,
    score: 0,
    tips: "对食堂满意度进行打分",
    activeNames: ['1'],
    scoreList: [],
    datetime: '',
  },

  valueChange(event) {
    this.setData({
      value: event.detail,
      score: event.detail * 20
    });
  },
  submit() {
    var that = this;
    console.log(that.data.score)
    //发送添加到数据库请求
    util.request(api.AddScore, {
      score: that.data.score
    }).then((res) => {
      console.log(res);
      if (res.errno == 0) {
        wx.showToast({
          title: '评价成功',
          icon: 'success',
          duration: 1000
        })
        that.queryMyScore();
      } else {
        wx.showToast({
          title: res.errmsg,
          icon: 'error',
          duration: 1000
        })
      }
    })


  },

  onChange(event) {
    this.setData({
      activeNames: event.detail,
    });
  },
  queryMyScore() {
    var that = this;
    util.request(api.FindByUserId).then((res) => {
      console.log(res);
      if (res.errno == 0) {
        that.setData({
          scoreList: res.data
        })
      } else {
        console.log(res);
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var that = this;
    util.getServerTime().then(nowTime => {
      nowTime = nowTime.replace(/-/g, '/');
      that.setData({
        datetime: new Date(nowTime).getMonth()+1
      })
    })
    this.queryMyScore();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})