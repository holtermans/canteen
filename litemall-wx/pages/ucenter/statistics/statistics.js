import * as echarts from '../../../lib/ec-canvas/echarts';
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();
var i = undefined; //全局计时器
var j = undefined;
let chart = null //定义变量接收echarts实例,方便改数据
Page({

  /**
   * 页面的初始数据
   */
  data: {
    canteenOrderListGroup: null,
    orderGroup: null, //食堂一日订单都在这；
    queue: 0,
    activeNames: ['1'],
    tabs: [{
        id: "checked",
        name: "已核销"
      },
      {
        id: "not_checked",
        name: "未核销"
      }
    ],
    result: null,
    checkTimingId: null,
    checkTimingName: null,
    timingList: [],
    dishesList: [],
    userList: {}, //储存
    checkUserList: {}, //储存核销与为核销用户id列表
    orderDishesList: {},
    dishesCount: {},
    checkedRecord: {}, //统计核销和未核销用户个数
    //弹窗显示
    show: {
      closeIcon: false,
    },
    checkTimingId: null, //统计详情页的timingId
    currentBcUserList: null, //当前时段下报餐用户的真实信息列表
    //日历数据
    date: {
      selectSingle: null, //目前只用了这个
    },
    type: 'single',
    round: true,
    color: undefined,
    minDate: new Date(
      new Date().getFullYear(),
      new Date().getMonth(),
      new Date().getDate() - 7
    ).getTime(),
    maxDate: new Date(
      new Date().getFullYear(),
      new Date().getMonth(),
      new Date().getDate() + 7
    ).getTime(),
    showCalendar: false,
    connected: false,
    message: "",
    defaultDate: Date.now(), //默认选中日期
    position: 'top',
    formatter(day) {
      const month = day.date.getMonth() + 1;
      const date = day.date.getDate();
      if (month == new Date().getMonth() + 1 && date == new Date().getDate()) {
        day.text = "今天"
      }
      return day;
    },
    showConfirm: false,
    confirmText: '确定',
    defaultDate: Date.now(),
    option1: [],
    option2: [{
        text: '全部',
        value: ''
      },
      {
        text: '已取餐',
        value: 107
      },
      {
        text: '未取餐',
        value: 103
      },
    ],
    value1: '',
    value2: '',
    canteenOrderList: [],

    menuTop: '',
    ec: {
      lazyLoad: true
    },
    tabs: ['早餐', '中餐', '晚餐'],
    tabsValue: [1004, 1005, 1006],
    curIndex: 0,
    totalNum: '0',
    bcRecordList: [],
    deptList: [],
    currentPage: 1,
    pageSize: 10,
    emptyText: '该日暂无员工报餐',
    loadding: false,
    nomore: false,

    dailyMenuMap: null,
    chart: {
      data: null,
    },
    showUnchecked: false,
    showChecked: false,
    main: true,
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var date = new Date();
    this.setData({
      'date.selectSingle': date.getTime()
    });
    this.ecComponent = this.selectComponent('#mychart-dom-pie');
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;

    this.initChart();
    this.search();
    this.getDishesList();
    wx.showLoading({
      title: '加载中',
    });
    wx.hideLoading({
      success: (res) => {},
    })
  },

  tabClick(e) {
    const that = this;
    that.setData({
      curIndex: e.currentTarget.id,
      bcRecordList: [],
      currentPage: 1,
      deptId: '',
      selectIndex: null,
      emptyText: '该日暂无员工报餐',
      loadding: false,
      nomore: false
    });
    // chart.clear(); //清空实例,防止数字闪烁
    that.initChart();
    that.search();

  },
  // e-chart 环形图
  initChart: function () {
    const that = this;
    // 获取组件的 canvas、width、height 后的回调函数
    Promise.all([that.getTimingList(), that.getDailyMenu(), that.getMealOrder()]).then(result => {
      var timingList = result[0];
      var dailyMenu = result[1];
      var mealOrders = result[2];
      var data = {};
      var list = [];
      for (var i = 0; i < dailyMenu.length; i++) {
        var num = 0;
        for (var j = 0; j < mealOrders.length; j++) {
          if (dailyMenu[i].dishesId == mealOrders[j].dishesId) {
            num += mealOrders[j].quantity;
          }
        }
        data = {
          name: dailyMenu[i].dishesName,
          value: num
        };
        list.push(data);
      }
      this.setData({
        "chart.data": list,
      });
      console.log(list);
      // that.setOption(chart);
    });
    // this.ecComponent.init((canvas, width, height, dpr) => {
    //   // 在这里初始化图表
    //   chart = echarts.init(canvas, null, {
    //     width: width,
    //     height: height,
    //     devicePixelRatio: dpr
    //   });
    //   // 注意这里一定要返回 chart 实例，否则会影响事件处理等
    //   return chart;
    // });
  },
  /**
   * 获取图标数据
   */
  setOption: function (chart) {
    const that = this;
    var datas = [];

    let option = {
      color: ['#fe5d4d', '#737dde', '#3ba4ff', '#fe9e7f', '#fecc5e', '#66dfe2', '#37a1d9'],
      tooltip: {
        trigger: 'item'
      },
      toolbox: {
        show: true,
        feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
      legend: {
        top: '5%',
        left: 'center',
        z: -1,
        type: 'scroll',
      
      },
      series: [{
        z: 2,
        name: '菜品统计',
        type: 'pie',
        radius: ['30%', '50%'],
        avoidLabelOverlap: true,
        
        label: {
          show: true,
          position: 'outside',
          formatter: '{b}\n{c}份',
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '12',
          }
        },
        labelLine: {
          show: true
        },
        data: that.data.chart.data,
      }]
    };
    chart.setOption(option);
  },

  //查询数据
  search() {
    // this.getStatistics()
    var that = this;
    wx.showLoading({
      title: '查询中',
    })
    var userList = [];
    var data = {
      timingId: this.data.tabsValue[this.data.curIndex],
      orderStatus: this.data.value2, //订单状态初始化为不限
      date: util.getYMD(this.data.date.selectSingle),
    }
    util.request(api.QueryByFilterThenGroup, data, "POST").then(res => {
      if (res.errno == 0) {
        that.setData({
          canteenOrderList: res.data.canteenOrderList,
          canteenOrderListGroup: res.data.canteenOrderListGroup
        });
        that.data.canteenOrderList.forEach(item => {
          userList.push(item.userId);
        })
        that.getBcUserInfoByUserId(userList);
      }
    });

    wx.hideLoading({
      success: (res) => {},
    });
  },
  //完成事件
  finish(e) {
    var orderId = e.currentTarget.dataset.id
    var data = {
      "orderId": orderId
    }
    util.request(api.QueueDel, data).then(res => {
      if (res.errno == 0) {}
    })
  },

  /**
   * 排队
   */
  initQueue() {
    var that = this;
    util.request(api.QueueQuery).then((res) => {
      if (res.errno == 0) {
        if (res.data.orderInfo == null) {
          that.setData({
            message: []
          })
        } else {
          that.setData({
            message: res.data.orderInfo
          })
        }

      }

    })
  },
  //监听任务
  startTask() {
    this.initQueue();
  },

  //折叠菜单
  collapse(event) {
    this.setData({
      activeNames: event.detail,
    });
  },
  toggle(type, show) {
    this.setData({
      [`show.${type}`]: show
    });
  },
  goDetail(e) {
    // this.setData({
    //   checkTimingId: e.currentTarget.dataset.id,
    //   checkTimingName: e.currentTarget.dataset.timingName
    // })
    // this.getBcUserInfoByUserId(this.data.userList[e.currentTarget.dataset.id]);
    console.log(e);
    wx.navigateTo({
      url: '/pages/ucenter/statistics/detail?orderSn=' + e.currentTarget.dataset.orderSn + '&orderId=' + e.currentTarget.dataset.orderId + '&name=' + e.currentTarget.dataset.name + '&mobile=' + e.currentTarget.dataset.mobile + '&avatar=' + e.currentTarget.dataset.avatar,
    })

  },
  hideCloseIcon() {
    this.toggle('closeIcon', false);
  },
  getMealOrder() {
    var that = this;
    return new Promise((resolve, reject) => {
      util.request(api.MealOrderQueryByOrderAndTiming, {
        date: util.getYMD(that.data.date.selectSingle),
        timingId: this.data.tabsValue[this.data.curIndex]
      }).then((res) => {
        wx.hideLoading({
          success: (res) => {},
        })
        if (res.errno == 0) {}
        resolve(res.data.mealOrders);
      }).catch(res => {

        reject();
      })
    })

  },
  getStatistics() {
    var that = this;
    var judge = [];
    var userList = {}; //根据时段列出用户id
    var orderDishesList = {}; //每一餐的菜品预定总集合
    var dishesCount = {};

    //查询报餐详情情况
    // wx.showLoading({
    //   title: '加载中',
    // });
    util.request(api.AllOrderByDate, {
      date: util.getYMD(that.data.date.selectSingle)
    }).then((res) => {
      wx.hideLoading({
        success: (res) => {},
      })
      if (res.errno == 0) {
        that.data.timingList.forEach(timing => {
          userList[timing.id] = [];
          orderDishesList[timing.id] = [];
          dishesCount[timing.id] = {};
        })

        res.data.mealOrders.forEach(item => { //遍历回传的信息,寻找有用信息
          judge.push(item.userId + "/" + item.date + "/" + item.timingId); //组装唯一字符串标识
          orderDishesList[item.timingId].push(item.dishesId);
        });

        that.data.timingList.forEach(item => {
          var result = util.arrCheck(orderDishesList[item.id]);
          dishesCount[item.id] = result;
        })

        judge = util.unique(judge); //当日全部报餐名单，包括各个时段
        judge.forEach(record => { //单独把记录拿出来
          that.data.timingList.forEach(timing => { //记录进行时段匹配
            if (timing.id == record.split("/")[2]) {
              userList[timing.id].push(record.split("/")[0]);
            }
          })
        })
        that.setData({
          userList: userList,
          orderDishesList: orderDishesList,
          dishesCount: dishesCount,
          result: res.data.mealOrders
        })
      }
    })
  },
  getCheckedList() {
    var that = this;
    var orderGroup = {};
    //查询一日的报餐情况
    util.request(api.DailyCanteenOrderList, {
      date: util.getYMD(that.data.date.selectSingle)
    }).then((res) => {

      if (res.errno == 0) {
        that.data.timingList.forEach(timing => { //分时段统计
          orderGroup[timing.id] = [];
        });
        res.data.forEach(item => { //遍历回传的信息,寻找有用信息
          orderGroup[item.timingId].push(item);
        });
        that.setData({
          orderGroup: orderGroup
        });
        var check = {};
        var checkUserList = {};
        for (let key in orderGroup) { //统计取餐人数和未取餐人数
          if (orderGroup.hasOwnProperty(key)) {
            checkUserList[key] = {};
            checkUserList[key]["checked"] = [];
            checkUserList[key]["not_checked"] = [];
            check[key] = {};
            check[key]["checked"] = 0;
            check[key]["not_checked"] = 0;
            orderGroup[key].forEach(item => {
              if (item.orderStatus == 103) { //未核销
                check[key]["not_checked"]++;
                checkUserList[key]["not_checked"].push(item);
              } else { //已核销
                check[key]["checked"]++;
                checkUserList[key]["checked"].push(item);
              }
            })
          }
        }
        that.setData({
          checkedRecord: check,
          checkUserList: checkUserList,
        })
        wx.hideLoading({
          success: (res) => {},
        })
      }
    });

  },
  /**
   * 所有菜品信息
   */
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
  getBcUserInfoByUserId: function (arr) {
    var data = arr;
    if (arr.length == 0) {
      return;
    }
    var that = this;
    util.request(api.GetBcUserHashMapByUserId, data, "POST").then(res => {
      if (res.errno == 0) {
        that.setData({
          currentBcUserList: res.data.bcUserVoHashMap,
        })
      }
    })
  },
  //确认日期
  onConfirm(event) {
    let that = this;
    this.setData({
      showCalendar: false
    });
    var promise = new Promise((resolve, reject) => {
      this.setData({
        [`date.${this.data.id}`]: Array.isArray(event.detail) ? event.detail.map(date => date.valueOf()) : event.detail.valueOf()
      });
      resolve();
    });
    promise.then(() => {
      // that.getStatistics();
      // that.getCheckedList();
      // chart.clear(); //清空实例,防止数字闪烁
      that.initChart();
      that.search();
    });
  },


  CloseClender() {
    this.setData({
      showCalendar: false
    });
  },




  //获取用餐时段列表
  getTimingList: function () {
    let that = this;
    return new Promise((resolve, reject) => {
      util.request(api.TimingList).then(function (res) {
        if (res.errno === 0) {
          that.setData({
            timingList: res.data.timingList,
          })
          resolve(res.data.timingList);
        }
      }).catch((res) => {

        reject();
      });
    })

  },
  //获取当日 早餐/中餐/晚餐 的菜谱
  getDailyMenu() {
    var that = this;
    return new Promise((resolve, reject) => {
      //将日期格式化
      var date = util.getYMD(that.data.date.selectSingle);
      let data = {
        date: date,
        timingId: that.data.tabsValue[that.data.curIndex]
      }
      util.request(api.DailyMenuLisByDateAndTimingId, data).then(res => {
        if (res.errno == 0) {
          res.data.dailyMenuList.forEach(item => {
            item.quantity = 0;
          });
          that.setData({
            dailyMenuList: res.data.dailyMenuList,
          });
          // console.log(res.data.dailyMenuList);
          resolve(res.data.dailyMenuList);
        };
      }).catch((res) => {
        reject();
      });
    })
  },
  setfilter(timingList) {
    var option1temp = [];
    timingList.forEach(item => {
      var data = {
        text: item.name,
        value: item.id
      };
      option1temp.push(data);
    });
    var data = {
      text: "不限",
      value: null
    };
    option1temp.push(data);
    this.setData({
      option1: option1temp,
      value1: option1temp[0].value
    })
  },
  onSwitch1Change({
    detail
  }) {
    this.setData({
      value1: detail
    });

  },
  onSwitch2Change({
    detail
  }) {
    this.setData({
      value2: detail
    });
  },
  //重置日期
  resetSettings() {
    this.setData({
      round: true,
      minDate: new Date(
        new Date().getFullYear(),
        new Date().getMonth(),
        new Date().getDate() - 7
      ).getTime(),
      maxDate: new Date(
        new Date().getFullYear(),
        new Date().getMonth(),
        new Date().getDate() + 7
      ).getTime(),
      position: 'top',
      formatter: null,
      showConfirm: true,
      confirmText: '确定',
      defaultDate: Date.now(),
    });
  },
  //选择日期
  selectDate(event) {
    // chart.clear(); //清空实例,防止数字闪烁
    // this.resetSettings();
    const {
      type,
      id,
    } = event.currentTarget.dataset;
    const data = {
      type,
      id,
      showCalendar: true
    };
    // console.log(event);
    this.setData(data);
  },


  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

    clearInterval(j);
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.initChart();
    this.search();
    this.getDishesList();
    wx.showLoading({
      title: '加载中',
    });
    wx.hideLoading({
      success: (res) => {},
    })
    wx.stopPullDownRefresh();
  },

  showUnchecked: function () {
    this.setData({
      showUnchecked: !this.data.showUnchecked,
      showChecked: false,
    })
    this.setData({
      main: !(this.data.showUnchecked || this.data.showChecked),
    })
  },
  showChecked: function () {
    this.setData({
      showChecked: !this.data.showChecked,
      showUnchecked: false,
    })
    this.setData({
      main: !(this.data.showUnchecked || this.data.showChecked),
    })
  },
})