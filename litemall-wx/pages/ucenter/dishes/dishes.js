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
    columns: ['杭州', '宁波', '温州', '嘉兴', '湖州'],
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
  onReady: function () {


  },
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
      cateId: this.data.dishesCategory[this.data.activeKey].id
    }
    util.request(api.DishGetBydCateId, data).then(res => {
      console.log(res);
      if (res.errno == 0) {
        that.setData({
          dishesList: res.data.dishesList,
        })
      }
    })
  },

  cateChange(event) {
    var that = this;
    var index = event.detail;
    var cateId = this.data.dishesCategory[index].id //得到菜品分类ID
    var data = {
      cateId: cateId
    }
    // var dishesList = wx.getStorageSync('dishCate' + cateId);
    // if (dishesList == '') {
      util.request(api.DishGetBydCateId, data).then(res => {
        console.log(res);
        if (res.errno == 0) {
          that.setData({
            dishesList: res.data.dishesList,
          })
          wx.setStorageSync('dishCate' + cateId, res.data.dishesList);
        }
      })
    // } else {
    //   that.setData({
    //     dishesList: dishesList,
    //   });
    // }
    //设置定期清理缓存
    // setTimeout(() => {
    //   wx.removeStorageSync('dishCate' + cateId)
    // }, 30000);
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
          fail(res) {
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
    wx.redirectTo({
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
          util.request(api.DishesDel, {
            id: e.currentTarget.dataset.id
          }).then(res => {
            if (res.errno == 0) {
              wx.showToast({
                title: '已删除',
              });
              that.getDishesList();
              that.getDishCate().then(()=>{})
            }
          })
        } else if (res.cancel) {}
      }
    })


  }

})