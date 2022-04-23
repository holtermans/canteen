// pages/ucenter/dishes/dished.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
const {
  CouponSelectList
} = require('../../../config/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    dishesList: [],
    dishesCategory: [],
    cateCount: [],
    activeKey: 0, //侧边栏激活标签,
    columns: [],
    //分页数据
    pageNum: 1,
    pageSize: 10,
    nomore: false,
    showLoading: false,
    triggered: false,
    //搜索框
    value: '',
    // 分类添加弹出
    showAddCate: false,
    input: {
      cateName: '',
    },
    //滚动
    scrollTop: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    this.getDishCate().then(() => {
      that.getDishesList();
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},
  getDishCate() {
    var that = this;
    return new Promise((resovle, reject) => {
      //请求数据
      util.request(api.CanteenDishCateList).then(res => {
        if (res.errno == 0) {
          that.setData({
            dishesCategory: res.data.categoryList,
            cateCount: res.data.cateCount, //跟随分类而来的菜品统计数目
          });
          wx.setStorageSync('dishesCategory', res.data.categoryList); //缓存
          wx.setStorageSync('cateCount', res.data.cateCount); //缓存
          resovle();
        } else {
          wx.showToast({
            title: errmsg,
          })
          reject();
        }
      })
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    // this.refresh();
  },


  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  //获取菜品列表
  getDishesList: function () {
    let that = this;
    var data = {
      cateId: that.data.dishesCategory[that.data.activeKey].id,
      pageNum: that.data.pageNum,
      pageSize: that.data.pageSize,
    }
    util.request(api.QueryByCateIdAndPage, data).then(res => {
      if (res.errno == 0) {
        that.setData({
          dishesList: [...that.data.dishesList, ...res.data],
          pageNum: res.data.length < that.data.pageSize ? that.data.pageNum : that.data.pageNum + 1,
          showloading: false,
          nomore: res.data.length < that.data.pageSize ? true : false,
        })
      }

    })
  },

  cateChange(event) {
    var that = this;
    var index = event.detail;
    this.setData({
      scrollTop: 0
    })
    var cateId = this.data.dishesCategory[index].id //得到菜品分类ID
    this.setData({
      activeKey: index
    });
    this.setData({
      pageNum: 1,
      dishesList: [],
      nomore: false,
      showLoading: false,
    })
    this.getDishesList();
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
            wx.showToast({
              title: '上传成功',
            })
            //do something
          },
          fail(res) {
            const data = res.data
            wx.showToast({
              title: '上传图片出错',
            })
          }
        })
      }
    })
  },
  showAddWindow: function () {
    this.setData({
      show: true,
    })
  },
  closeWindow: function () {
    this.setData({
      show: false,
    })
  },
  navigateToPublish: function () {
    wx.navigateTo({
      url: '/pages/ucenter/addDish/addDish'
    })
  },
  delete: function (e) {
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确定要删除？',
      success(res) {
        if (res.confirm) {
          util.request(api.DishesDel, {
            id: e.currentTarget.dataset.id
          }).then(res => {
            if (res.errno == 0) {
              wx.showToast({
                title: '已删除',
              });
              that.refresh();
            }
          })
        } else if (res.cancel) {}
      }
    })
  },
  goDetail: function (e) {
    var id = e.currentTarget.dataset.id;
    this.data.dishesList.filter(v => {
      if (v.id == id) {
        wx.navigateTo({
          url: '/pages/ucenter/dishes/updateDish/updateDish?queryBean=' + JSON.stringify(v),
        })
      }
    })
  },
  scrollToBottom() {
    var that = this;
    if (that.data.value == '') { //无关键词搜索
      if (this.data.nomore) return;
      this.setData({
        showloading: true,
      });
      this.getDishesList();

    } else {
      if (this.data.nomore) return;
      var query = {
        cateId: that.data.dishesCategory[that.data.activeKey].id,
        keyword: that.data.value,
        pageNum: that.data.pageNum,
        pageSize: that.data.pageSize,
      }
      util.request(api.SearchByCateAndKeyword, query).then((res) => {
        that.setData({
          dishesList: [...that.data.dishesList, ...res.data.dishesList],
          pageNum: res.data.dishesList.length < that.data.pageSize ? that.data.pageNum : that.data.pageNum + 1,
          showloading: false,
          nomore: res.data.dishesList.length < that.data.pageSize ? true : false,
        });
      })

    }
  },

  onChange(e) {
    var that = this;
    this.setData({
      pageNum: 1,
      pageSize: 10,
      dishesList: [],
      value: e.detail
    })
    var query = {
      cateId: that.data.dishesCategory[that.data.activeKey].id,
      keyword: e.detail
    }
    util.request(api.SearchByCateAndKeyword, query).then((res) => {
      that.setData({
        dishesList: [...that.data.dishesList, ...res.data.dishesList],
        pageNum: res.data.dishesList.length < that.data.pageSize ? that.data.pageNum : that.data.pageNum + 1,
        showloading: false,
        nomore: res.data.dishesList.length < that.data.pageSize ? true : false,
      });
    })
  },
  addCate() {
    this.setData({
      showAddCate: true
    })
  },
  closeAddCate() {
    this.setData({
      showAddCate: false
    })
  },
  saveCate(e) {
    var that = this;
    this.setData({
      showAddCate: false,
      'input.cateName': ''
    });

    if (e.detail.value.cateName == '') {
      return
    }
    var query = {
      name: e.detail.value.cateName
    }
    util.request(api.CanteenDishCateAdd, query, "POST").then((res) => {
      console.log(res.data);
      that.onLoad();
    })
  },
  longtap(e) {
    var that = this;
    wx.showModal({
      title: '删除',
      content: e.currentTarget.dataset.name,
      success(res) {
        if (res.confirm) {
          var query = {
            id: e.currentTarget.dataset.id
          }
          util.request(api.CanteenDishCateDel, query).then((res) => {
            res.errno == 0 ? that.onLoad() : wx.showToast({
              title: '不能删除',
            })
          })
        } else if (res.cancel) {

        }
      }
    })
  },
  refresh() {
    var that = this;
    this.setData({
      pageNum: 1,
      dishesList: []
    })
    that.onLoad();
  }

})