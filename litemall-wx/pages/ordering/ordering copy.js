var util = require("../../utils/util.js");
var api = require('../../config/api.js');
var user = require('../../utils/user.js');
var app = getApp();
// pages/ordering/ordering.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    banner: [{
      url: "https://www.joyfey.xyz/upload/banner1.jpg"
    }],
    price: 0,
    sum: 0,
    cartList: [], //购物车列表
    timingList: [], //左侧时间段列表
    hasOrderTimingIdArr: [],
    dishesList: [],
    hasOrderFlag: true,
    currentTiming: { //当前时段，用于确定当前选择哪个时段
      id: null,
      name: null,
    },
    dailyMenuList: [],
    date: {
      selectSingle: null, //目前只用了这个
    },
    type: 'single',
    round: true,
    color: undefined,
    minDate: Date.now(),
    maxDate: new Date(
      new Date().getFullYear(),
      new Date().getMonth(),
      new Date().getDate() + 7
    ).getTime(),
    maxRange: undefined,
    position: undefined,
    formatter: undefined,
    showConfirm: true,
    showCalendar: false,
    tiledMinDate: new Date(2012, 0, 10).getTime(),
    tiledMaxDate: new Date(2012, 2, 20).getTime(),
    confirmText: undefined,
    confirmDisabledText: undefined
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //获取时段列表（发起请求）
    //设置当前分类
    var that = this;
    var date = new Date();
    this.setData({
      'date.selectSingle': date.getTime() //设置当前时间
    });
    Promise.all([this.getTimingList(), this.getDailyMenu()]).then(result => {
      var timingList = result[0];
      var dailyMenu = result[1];
      var data = {};
      for (var i = 0; i < timingList.length; i++) {
        data[timingList[i].id] = [];
      }
      for (var i = 0; i < dailyMenu.length; i++) {
        data[dailyMenu[i].timingId].push(dailyMenu[i]);
      }
      this.setData({
        dailyMenuMap: data
      });
      that.getCurrentTiming(timingList[0].id);
      this.getDishesList();
      this.getCanteenOrder();
    });
    //获取时段列表
  },


  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.onPullDownRefresh();
  },
  /**
   * 判断菜单是否为空状态
   */
  isEmpty() {
    var that = this;
    var currentTimingId = that.data.currentTiming.id;
    var dailyMenuList = that.data.dailyMenuList;
    var result = dailyMenuList.some(item => {
      if (item.timingId == currentTimingId) {
        return true;
      }
    })
    that.setData({
      hideEmpty: result,
    });
  },

  //获取指定日期报餐信息
  getOrderInfo() {
    var that = this;
    return new Promise((resovle, reject) => {
      // var dailyMenuList = that.data.dailyMenuList;
      util.request(api.MealOrderQuery, {
        date: util.getYMD(that.data.date.selectSingle)
      }).then((res) => {
        // let timingIdArr = [];
        if (res.errno == 0) {
          res.data.mealOrders.forEach(item => {
            timingIdArr.push(item.timingId);
            for (var index in dailyMenuList) { //恢复点菜数量

              if (dailyMenuList[index].dishesId == item.dishesId && dailyMenuList[index].timingId == item.timingId) {
                dailyMenuList[index].quantity = item.quantity;
              }
            }
          });
          that.setData({
            dailyMenuList: dailyMenuList
          })
        };
        this.setData({
          hasOrderTimingIdArr: util.unique(timingIdArr),
        });
        resovle();
      })
    })
  },
  /**
   * 获取订单 并且设置时段分类的数据
   */
  getCanteenOrder() {
    var that = this;
    return new Promise((resovle, reject) => {
      util.request(api.QueryByIdAndDate, {
        date: util.getYMD(this.data.date.selectSingle)
      }).then((res) => {
        var timinglist = that.data.timingList;

        timinglist.forEach(timing => {
          timing.orderNum = 0;
          timing.hasOrder = false;
          timing.orderStatus = '';
          res.data.canteenOrderList.forEach(order => {
            if (order.timingId == timing.id) {
              timing.orderNum++;
              timing.hasOrder = true;
              timing.orderStatus = order.orderStatus;
            }
          })
        })
        that.setData({
          timingList: timinglist,
        });
        that.getCurrentTiming(that.data.currentTiming.id);
        resovle();
      }).catch(res => {
        var timinglist = that.data.timingList;
        wx.showToast({
          title: '获取报餐出错',
        });
      });

    })

  },
  //确认日期
  onConfirm(event) {
    var that = this;
    wx.showLoading({
      title: '加载中',
    })
    this.setData({
      showCalendar: false,
      [`date.${this.data.id}`]: Array.isArray(event.detail) ? event.detail.map(date => date.valueOf()) : event.detail.valueOf()
    });

    Promise.all([this.getTimingList(), this.getDailyMenu()]).then(result => {
      var timingList = result[0];
      var dailyMenu = result[1];
      var data = {};
      for (var i = 0; i < timingList.length; i++) {
        data[timingList[i].id] = [];
      }
      for (var i = 0; i < dailyMenu.length; i++) {
        data[dailyMenu[i].timingId].push(dailyMenu[i]);
      }
      that.setData({
        dailyMenuMap: data,
      });
    });
    this.getCanteenOrder();
    this.calculateSum(this.data.currentTiming.id, this.data.dailyMenuList, this.data.dishesList);
    wx.hideLoading({
      success: (res) => {},
    })
  },
  onClose() {
    this.setData({
      showCalendar: false
    });
  },

  //重置日期
  resetSettings() {
    this.setData({
      round: true,
      color: null,
      minDate: Date.now(),
      maxDate: new Date(
        new Date().getFullYear(),
        new Date().getMonth(),
        new Date().getDate() + 7
      ).getTime(),
      maxRange: null,
      position: 'bottom',
      formatter: null,
      showConfirm: true,
      confirmText: '确定',
      confirmDisabledText: null
    });
  },
  /**
   * 选择日期
   * @param {*} event 
   */
  selectDate(event) {
    this.resetSettings();
    const {
      type,
      id
    } = event.currentTarget.dataset;

    const data = {
      id,
      type,
      showCalendar: true
    };
    this.setData(data);
  },
  //切换时段 -> 设置当前时段 -> 跟据当前时段获取当前日期的菜品
  switchCate: function (event) {
    var that = this;

    //获取点击分类的id判断
    if (this.data.currentTiming.id == event.currentTarget.dataset.id) {
      //点击当前分类不响应
      return false;
    }
    this.setData({
      hideEmpty: true,
    })
    this.getCurrentTiming(event.currentTarget.dataset.id); //设置当前时段分类
    //以下是为了刷新数据所做查询
    this.getCanteenOrder();
    // this.getOrderInfo().then(() => {
    this.calculateSum(this.data.currentTiming.id, this.data.dailyMenuList, this.data.dishesList);
    // });

  },
  /**
   * 提供时段id，把当前时段设置为id对应的时段
   * */
  getCurrentTiming: function (id) {
    let timgList = this.data.timingList;
    timgList.forEach(item => {
      if (item.id == id) {
        this.setData({
          currentTiming: {
            name: item.name,
            id: item.id,
            status: item.status,
            startTime: item.startTime,
            endTime: item.endTime,
            reminder: item.reminder,
            stopTime: item.stopTime,
            hasOrder: item.hasOrder,
            orderStatus: item.orderStatus,
          },
        });
      }
    })
  },
  /**
   * 计算当前所选时间段的点餐价格
   *  
   */
  calculateSum(currentTimingId, dailyMenuList, dishesList) {

    var sum = 0;
    dailyMenuList.forEach(item => {
      if (item.timingId == currentTimingId) {
        var quantity = item.quantity;
        var price = 0;
        for (var index in dishesList) {
          if (dishesList[index].id == item.dishesId) {
            price = dishesList[index].price;
            break;
          }
        }
        sum += quantity * price;
      }
    })
    this.setData({
      sum: sum
    })

  },
  //数量加号按钮
  addCount: function (e) {
    //判断是否报餐
    if (util.isInArr(this.data.currentTiming.id, this.data.hasOrderTimingIdArr) != -1) {
      return;
    }
    var id = e.currentTarget.dataset.id;

    for (var i in this.data.dailyMenuList) { // 遍历菜单找到被点击的菜品，数量加1
      if (this.data.dailyMenuList[i].dishesId == id && this.data.currentTiming.id == this.data.dailyMenuList[i].timingId) {
        //确定判断有没有过报餐时间
        var stopTime = null;
        this.data.timingList.forEach(item => { //获取截止时间
          if (this.data.dailyMenuList[i].timingId == item.id) {
            stopTime = item.stopTime;
          }
        })
        var stopDateTime = new Date((this.data.dailyMenuList[i].date + " " + stopTime).replace(/-/g, '/')); //这里是为了兼容苹果，要 "2021/02/02 11:12:11" 这样的时间格式

        this.getServerTime().then(nowTime => {
          nowTime = nowTime.replace(/-/g, '/');
          if (stopDateTime.getTime() < new Date(nowTime).getTime()) {
            wx.showToast({
              title: '已过报餐时间',
              icon: 'failure',
              duration: 2000
            })
            return;
          }
          this.data.dailyMenuList[i].quantity += 1;
          this.setData({
            dailyMenuList: this.data.dailyMenuList,
          })
          this.checkCartCount();
          this.calculateSum(this.data.currentTiming.id, this.data.dailyMenuList, this.data.dishesList);
        }).catch((err) => {
          wx.showToast({
            title: err,
          });
        })

        // if (this.data.currentSubCategoryList[i].limit == this.data.currentSubCategoryList[i].quantity) {
        //   wx.showToast({
        //     title: '超出限额',
        //   })
        //   return;
        // }

        break;
      }
    }

  },
  /**
   * 获取服务器时间，统一从后台得到标准时间
   */
  getServerTime() {
    return new Promise((resolve, reject) => {
      util.request(api.GetServerTime).then(res => {
        if (res.errno == 0) {
          resolve(res.data.nowTime);
        } else {
          wx.showToast({
            title: '时间同步出错',
          })
        }
      })
    }).catch((err) => {
      reject(err);
    })
  },
  // 数量减号按钮
  minusCount: function (e) {
    if (util.isInArr(this.data.currentTiming.id, this.data.hasOrderTimingIdArr) != -1) {
      return;
    }
    var id = e.currentTarget.dataset.id;

    for (var i in this.data.dailyMenuList) {
      if (this.data.dailyMenuList[i].dishesId == id && this.data.currentTiming.id == this.data.dailyMenuList[i].timingId) {

        this.data.dailyMenuList[i].quantity -= 1;
        if (this.data.dailyMenuList[i].quantity <= 0) {
          this.data.dailyMenuList[i].quantity = 0;
        }
      }
    }
    this.setData({
      dailyMenuList: this.data.dailyMenuList,
    })
    this.checkCartCount();

    this.calculateSum(this.data.currentTiming.id, this.data.dailyMenuList, this.data.dishesList);
  },


  /**
   * 定义根据id删除数组的方法
   * @param {*} array 
   * @param {*} val 
   */
  removeByValue: function (array, val) {
    for (var i = 0; i < array.length; i++) {
      if (array[i].id == val) {
        array.splice(i, 1);
        break;
      }
    }
  },

  /**
   * 计算购物车已点餐价格
   */
  checkCartCount: function () {
    let count = 0;
    this.data.cartList.forEach(function (item) {
      count += item.price;
    })
    this.setData({
      price: count,
    })
  },

  //提交报餐
  onSubmitOrder: function () {
    let that = this;
    let order = [];
    if (!app.globalData.hasLogin) {
      wx.navigateTo({
        url: '/pages/auth/login/login',
      })
      return;
    }
    //获取点菜信息
    this.data.dailyMenuList.forEach(item => {
      if (item.quantity != 0 && (that.data.currentTiming.id == item.timingId)) {
        order.push(item);
      }
    })
    if (order.length == 0) {
      wx.showToast({
        title: '请选择菜品~',
        icon: 'error',
        duration: 500
      });
      return;
    } else {
      wx.showModal({
        title: '提示',
        content: '报' + this.data.currentTiming.name,
        success(res) {
          if (res.confirm) {
            var data = []
            order.forEach((item => {
              var dataTemp = {
                "date": item.date,
                "timingId": item.timingId,
                "timingName": item.timingName,
                "dishesId": item.dishesId,
                "dishesName": item.dishesName,
                "quantity": item.quantity,
              }
              data.push(dataTemp);
            }));
            util.request(api.MealOrder, data, "POST").then(function (res) {
              if (res.errno === 0) {
                that.getDailyMenu();
                that.getCanteenOrder();
                // that.getOrderInfo();
              }
            });
          } else if (res.cancel) {

          }
        }
      })
    }


  },
  //获取菜品列表
  getDishesList: function () {
    return new Promise((resolve, reject) => {
      let that = this;
      util.request(api.dishesList).then(function (res) {
        if (res.errno === 0) {
          that.setData({
            dishesList: res.data.dishesList,
          })
        }
        resolve();
      });
    })

  },
  //取消报餐
  cancelOrder: function () {
    let that = this;
    //确定判断有没有过报餐时间
    var stopTime = this.data.currentTiming.stopTime;
    var stopDateTime = new Date(util.getYMD(this.data.date.selectSingle) + " " + stopTime);
    if (stopDateTime.getTime() < new Date().getTime()) {

      wx.showToast({
        title: '不可取消',
        icon: 'failure',
        duration: 2000
      })
      return;
    }

    wx.showModal({
      title: '提示',
      content: '取消' + this.data.currentTiming.name,
      success(res) {
        if (res.confirm) {
          var data = {
            date: util.getYMD(that.data.date.selectSingle),
            timingId: that.data.currentTiming.id
          }
          util.request(api.MealOrderCancel, data).then(res => {
            if (res.errno == 0) {
              wx.showToast({
                title: '已取消',
              })
              that.getDailyMenu().then(() => {
                that.getCanteenOrder();
                // that.getOrderInfo().then(() => {
                that.calculateSum(that.data.currentTiming.id, that.data.dailyMenuList, that.data.dishesList);
                // })

              });
            }
          })
        } else if (res.cancel) {

        }
      }
    })

  },
  //获取用餐时段列表
  getTimingList: function () {
    let that = this;
    return new Promise((resovle, reject) => {
      util.request(api.TimingList).then(function (res) {
        if (res.errno === 0) {
          that.setData({
            timingList: res.data.timingList,
          });
          resovle(res.data.timingList);
        }
      });
    })
  },
  //获取每日菜谱
  getDailyMenu: function () {
    var that = this;
    return new Promise((resolve, reject) => {
      //将日期格式化
      var date = util.getYMD(this.data.date.selectSingle);
      let data = {
        date: date,
      }
      util.request(api.DailyMenuList, data).then(res => {
        if (res.errno == 0) {
          res.data.dailyMenuList.forEach(item => {
            item.quantity = 0;
          });
          that.setData({
            dailyMenuList: res.data.dailyMenuList,
          });
          resolve(res.data.dailyMenuList);
        };
      }).catch(() => {
        reject();
      });
    })
  },


  onPullDownRefresh: function () {
    var that = this;

    this.getDailyMenu();
    this.getCanteenOrder();
    this.getDishesList() //获取菜的信息
    this.calculateSum(this.data.currentTiming.id, this.data.dailyMenuList, this.data.dishesList);
    wx.stopPullDownRefresh({
      success: (res) => {},
    })
  },


})