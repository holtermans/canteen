// pages/ucenter/DailyMenu/DailyMenu.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    result: [],
    keyword: '',
    timingList: [],
    currentTiming: [],
    currentTimingId: null,
    dailyMenuList: [],
    dishesList: [], //菜品列表
    hasAdd: [],
    date: {
      selectSingle: null, //目前只用了这个
    },
    type: 'single',
    round: true,
    color: undefined,
    minDate: Date.now(),
    maxDate: new Date(
      new Date().getFullYear(),
      new Date().getMonth() + 6,
      new Date().getDate()
    ).getTime(),
    maxRange: undefined,
    position: undefined,
    formatter: undefined,
    showConfirm: false,
    showCalendar: false,
    tiledMinDate: new Date(2012, 0, 10).getTime(),
    tiledMaxDate: new Date(2012, 2, 20).getTime(),
    confirmText: undefined,
    confirmDisabledText: undefined,
    show: {
      addDish: false,
    }
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
    this.getTimingList();
    var date = new Date();
    var promise = new Promise((resolve, reject) => {
      this.setData({
        'date.selectSingle': date.getTime()
      });
      resolve();
    })
    promise.then(() => {
      console.log(":)")
      this.getDailyMenu();
    })
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
    this.onShow();
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
  //确认日期
  onConfirm(event) {
    console.log(event);
    this.setData({
      showCalendar: false
    });
    var promise = new Promise((resolve, reject) => {
      this.setData({
        [`date.${this.data.id}`]: Array.isArray(event.detail) ? event.detail.map(date => date.valueOf()) : event.detail.valueOf()
      });
      resolve();
    });
    promise.then(this.getDailyMenu());

  },

  onSelect(event) {
    console.log(event);
  },

  onUnselect(event) {
    console.log(event);
  },

  onClose() {
    console.log(this.data.date);
    this.setData({
      showCalendar: false
    });
  },

  onOpen() {
    console.log('open');
  },

  onOpened() {
    console.log('opened');
  },

  onClosed() {
    console.log('closed');
  },
  //重置日期
  resetSettings() {
    this.setData({
      round: true,
      color: null,
      minDate: Date.now(),
      maxDate: new Date(
        new Date().getFullYear(),
        new Date().getMonth() + 6,
        new Date().getDate()
      ).getTime(),
      maxRange: null,
      position: 'bottom',
      formatter: null,
      showConfirm: true,
      confirmText: '确定',
      confirmDisabledText: null
    });
  },
  //选择日期
  selectDate(event) {
    this.resetSettings();
    const {
      type,
      id
    } = event.currentTarget.dataset;
    console.log(event.currentTarget.dataset)
    const data = {
      id,
      type,
      showCalendar: true
    };
    this.setData(data);
  },
  //获取用餐时段列表
  getTimingList: function () {
    let that = this;
    util.request(api.TimingList).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          timingList: res.data.timingList,
          currentTiming: res.data.timingList[0],
          currentTimingId: res.data.timingList[0].id
        })
      }
    });
  },
  getDailyMenu: function () {
    let that = this;
    //将日期
    var date = util.getYMD(this.data.date.selectSingle);
    console.log(date);
    let data = {
      date: date,
    }
    util.request(api.DailyMenuList, data).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          dailyMenuList: res.data.dailyMenuList,
        })
      }
      console.log(that.data.dailyMenuList);
    });
  },
  //标签点击事件
  onTabClick(event) {
    this.setData({
      currentTimingId: this.data.timingList[event.detail.index].id,
      currentTiming: this.data.timingList[event.detail.index]
    })

  },
  //切换弹出层显示状态
  toggle(type, show) {
    this.setData({
      [`show.${type}`]: show
    });
  },
  // 点击“添加菜品”弹出
  showAddDish() {
    this.getDishesList();
    this.toggle('addDish', true);
  },
  search(e){
    console.log(e);
  },
  onSearch(e){
    var that = this;
    console.log("搜索")
    util.request(api.DishesSearch,{keyword:e.detail}).then(res => {
      if(res.errno == 0){
        that.setData({
          dishesList:res.data.dishesList
        })
      }
    })

  },
  // 关闭“添加菜品”弹出层
  hideAddDish() {
    this.toggle('addDish', false);
  },
  //清空复选框
  clearCheckBoxResult() {
    this.setData({
      result: [],
    })
  },
  onPopClose() {
    //清空复选框
    this.clearCheckBoxResult();
    this.hideAddDish();
    this.setData({
      keyword:'',
    })
  },
  //复选框
  onChange(event) {
    
    const {
      key
    } = event.currentTarget.dataset;
    this.setData({
      [key]: event.detail
    });
  },
  //获取菜品
  getDishesList: function () {
    let that = this;
    util.request(api.dishesList).then(function (res) {
      if (res.errno === 0) {
        let dishesList = res.data.dishesList;
        var len1 = dishesList.length;
        var len2 = that.data.dailyMenuList.length;
        var listDML = that.data.dailyMenuList;
        var currentTimingId = that.data.currentTimingId;
        console.log(dishesList);
        console.log(listDML);
        var hasAdd = false;
        var dishesListY = [];
        var listDMLY = [];
        listDML.forEach(item => { //筛选当前时段的菜品
          if (item.timingId == currentTimingId) {
            listDMLY.push(item);
          }
        })
        //判断菜品表中的菜品是否在 目前展示出来的表中
        for (var i = 0; i < dishesList.length; i++) {
          var flag = false;
          for (var j = 0; j < listDMLY.length; j++) {
            if (listDMLY[j].dishesId == dishesList[i].id) {
              flag = true;
              break;
            }
          }
          dishesList[i].hasAdd = flag;
        }
        console.log(dishesList);
        that.setData({
          dishesList: dishesList,
        });

      }
    });
  },
  checkBoxToggle(event) {
    const {
      index
    } = event.currentTarget.dataset;
    const checkbox = this.selectComponent(`.checkboxes-${index}`);
    checkbox.toggle();
  },

  noop() {},
  //确认添加菜谱
  saveConfirm() {
    var that = this;
    wx.showModal({
      title: '提示',
      content: '更新菜谱', //this.data.result中存的是check-box中的name
      success(res) {
        if (res.confirm) {
          console.log('用户点击确定')
          var data = [];
          //组装数据
          that.data.result.forEach((r) => {

            var dataTemp = {
              "date": util.getYMD(that.data.date.selectSingle),
              "timingId": that.data.currentTimingId,
              "timingName": that.data.currentTiming.name,
              "dishesId": r,
              "dishesName": that.getUtil(r, "name"),
              "dishesBrief": that.getUtil(r, "brief")
            }
            data.push(dataTemp);
          })
          console.log(data);
          util.request(api.DailyMenuAdd, data, "POST").then((res) => {
            if (res.errno == 0) {
              console.log("插入成功")
            }
            that.getDailyMenu();
          });
          that.clearCheckBoxResult();
          that.hideAddDish();
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  getUtil(id, param) {
    var dishesList = this.data.dishesList;
    for (var i = 0; i < dishesList.length; i++) {
      if (dishesList[i].id == parseInt(id)) {
        if (param == "name") {
          return dishesList[i].name;
        }
        if (param == "brief") {
          return dishesList[i].brief;
        }
      }
    }
  },

  DeleteDish(e) {

    let that = this;
    var data = {
      "id": e.currentTarget.dataset.id
    }
    wx.showModal({
      title: '提示',
      content: '确定删除？',
      success(res) {
        if (res.confirm) {
          util.request(api.DailyMenuDel, data, "GET").then(res => {
            if (res.errno == 0) {
              wx.showToast({
                title: '删除成功',
              })
            }
            that.getDailyMenu();
          })
        } else if (res.cancel) {
          return;
        }
      }
    })

  }
})