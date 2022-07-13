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
    //分页数据
    pageNum: 1,
    pageSize: 11,
    pages: 0,
    showloading: false,
    nomore: false,
    total: 0,

    filter1: 'actived',
    filter2: "a",
    option1: [{
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
    bcUserList: [],
    //搜索框
    value: '',


  },
  //初始化参数
  init: function () {
    this.setData({
      pageNum: 1,
      pageSize: 11,
      pages: 0,
      showloading: false,
      nomore: false,
      total: 0,
      bcUserList: [],
      value:''
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    // this.getBcUserList().then(res => {
    //   console.log(res)
    //   that.setData({
    //     total: res.data.bcUserList.total,
    //     bcUserList: [...that.data.bcUserList, ...res.data.bcUserList.list],
    //     pageNum: res.data.bcUserList.list.length < that.data.pageSize ? that.data.pageNum : that.data.pageNum + 1,
    //     showloading: false,
    //     nomore: res.data.bcUserList.list.length < that.data.pageSize ? true : false,
    //   })
    // }).catch(() => {
    //   util.showErrorModal("数据请求出错");
    // });

    that.searchBcUserList();
  },

  onChange: function (value) {
    this.setData({
      filter1:value.detail
    })
    this.init();

    // this.getBcUserList().then(res => {
    //   this.setData({
    //     bcUserList: res.data.bcUserList,
    //     pages: res.data.bcUserList.pages,
    //   })
    // }).catch(() => {
    //   util.showErrorModal("数据请求出错");
    // });
    this.searchBcUserList();
  },

  //关键字搜索
  onSearchChange: function (e) {
    let that = this;
    //每次变化初始化数据
    that.setData({
      pageNum: 1,
      bcUserList: [],
      total: 0,
      value: e.detail
    })
    if (that.data.value != '') {
      that.searchBcUserListByKeyword(that.data.value);
    }
    if (that.data.value == '') {
      console.log("关键字为空")
      //切换到正常搜索
      that.searchBcUserList();
    }
  },
  searchBcUserListByKeyword: function (keyword) {
    let that = this;
    var data = {
      type: that.data.filter1,
      keyword: keyword, //获取关键词
      pageNum: that.data.pageNum,
      pageSize: that.data.pageSize,
    }
    util.request(api.GetBcUserListByKeyword, data).then((res) => {
      if (res.errno == 0) {
        that.setData({
          total: res.data.bcUserList.total,
          bcUserList: [...that.data.bcUserList, ...res.data.bcUserList.list],
          pageNum: res.data.bcUserList.list.length < that.data.pageSize ? that.data.pageNum : that.data.pageNum + 1,
          showloading: false,
          nomore: res.data.bcUserList.list.length < that.data.pageSize ? true : false,
        })
        console.log(res);
      } else {
        wx.showToast({
          title: '出错了~',
        })
      }
    })
  },

  searchBcUserList: function () {
    let that = this;
    var data = {
      type: that.data.filter1,
      keyword: '', //获取关键词
      pageNum: that.data.pageNum,
      pageSize: that.data.pageSize,
    }
    util.request(api.GetBcUserListByKeyword, data).then((res) => {
      if (res.errno == 0) {
        that.setData({
          total: res.data.bcUserList.total,
          bcUserList: [...that.data.bcUserList, ...res.data.bcUserList.list],
          pageNum: res.data.bcUserList.list.length < that.data.pageSize ? that.data.pageNum : that.data.pageNum + 1,
          showloading: false,
          nomore: res.data.bcUserList.list.length < that.data.pageSize ? true : false,
        })
        console.log(res);
      } else {
        wx.showToast({
          title: '出错了~',
        })
      }
    })
  },

  //无关键字搜索


  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    //有关键字搜索
    if (that.data.nomore) {
      return;
    } else {
      if (that.data.value == "") {
        that.searchBcUserList();
      } else {
        that.searchBcUserListByKeyword(that.data.value)
      }
      // this.getBcUserList().then(res => {
      //   // res.data.bcUserList.list.forEach(item => {
      //   //   that.data.bcUserList.list.push(item);
      //   // })
      //   that.setData({
      //     bcUserList: [...that.data.bcUserList, ...res.data.bcUserList.list],
      //     pageNum: res.data.bcUserList.list.length < that.data.pageSize ? that.data.pageNum : that.data.pageNum + 1,
      //     showloading: false,
      //     nomore: res.data.bcUserList.list.length < that.data.pageSize ? true : false,
      //   })

      // }).catch(() => {
      //   util.showErrorModal("数据请求出错");
      //   this.data.pageNum--;
      // })
    }


    //无关键字搜索
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {},

  /**
   * 废弃api
   */
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
  pass: function (e) {
    var that = this;
    wx.showLoading({
      title: '加载中',
    });
    //获取在数据库中的id
    var data = {
      "id": e.currentTarget.dataset.id,
      "status": 1
    }
    util.request(api.UpdateBcUser, data, "POST").then((res) => {
      if (res.errno == 0) {
        // that.setData({
        //   pageNum: 1,
        //   pageSize: 11,
        //   pages: 0,
        // });
        // that.getBcUserList().then(res => {
        //   that.setData({
        //     bcUserList: res.data.bcUserList,
        //     pages: res.data.bcUserList.pages,
        //   })
        // }).catch(() => {
        //   util.showErrorModal("数据请求出错");
        // });
        that.init();
        that.searchBcUserList();
      } else {}
      wx.hideLoading({
        success: (res) => {},
      })
    })
  },
  unpass: function (e) {
    var that = this;
    wx.showLoading({
      title: '加载中',
    });
    //获取在数据库中的id
    var data = {
      "id": e.currentTarget.dataset.id,
      "status": 0
    }
    util.request(api.UpdateBcUser, data, "POST").then((res) => {
      if (res.errno == 0) {
        // that.setData({
        //   pageNum: 1,
        //   pageSize: 11,
        //   pages: 0,
        // });
        // that.getBcUserList().then(res => {
        //   that.setData({
        //     bcUserList: res.data.bcUserList,
        //     pages: res.data.bcUserList.pages,
        //   })
        // }).catch(() => {
        //   util.showErrorModal("数据请求出错");
        // });
        that.init();
        that.searchBcUserList();
      } else {}
      wx.hideLoading({
        success: (res) => {},
      })
    })
  }

})