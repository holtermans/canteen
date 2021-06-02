var util = require('../../../../utils/util.js');
var api = require('../../../../config/api.js');
const {
  dishesList
} = require('../../../../config/api.js');
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
    input: {
      name: '',
      price: '',
      brief: '',
      categoryId: '',
      picUrl: ''
    },
    fileList: [],
    array: [],
    index: 0,
    hasMod: false,
    upload: [], //上传图片
  },

  bindPickerChange: function (e) {
    this.setData({
      index: e.detail.value
    })
  },
  formSubmit(e) {
    var that = this;
    wx.showLoading({
      title: '加载中',
    })
    //校验表单
    if (e.detail.value.name == null || e.detail.value.name == '') {
      wx.showToast({
        title: '名称不能为空',
      })
      return;
    }
    if (e.detail.value.price == null || e.detail.value.price == '') {
      wx.showToast({
        title: '价格不能为空',
      })
      return;
    }
    // tempFilePath可以作为img标签的src属性显示图片
    var query = {};

    try {
      query = {
        id: that.data.input.id,
        name: e.detail.value.name,
        price: e.detail.value.price,
        brief: e.detail.value.brief,
        categoryId: that.data.array[that.data.index].id,
        picUrl: that.data.input.picUrl == that.data.fileList[0].url ? null : that.data.fileList[0].url,
      }
    } catch (err) {
      query = {
        id: that.data.input.id,
        name: e.detail.value.name,
        price: e.detail.value.price,
        brief: e.detail.value.brief,
        categoryId: that.data.array[that.data.index].id,
        picUrl: ''
      };
    };
    console.log(that.data.input.picUrl);
    // console.log(that.data.fileList[0].url);
    console.log(query);
    if (query.picUrl != '' && query.picUrl != null) { //不为空上传图片
      this.storeImg(query.picUrl).then((url) => {
        query.picUrl = url;
        this.update(query);
      }).catch(() => {

      });
    } else { //为空直接保存数据
      this.update(query);
    }
    return;
    if (tempFilePath == undefined) {
      var data = {
        id: that.data.input.id,
        name: e.detail.value.name,
        price: e.detail.value.price,
        brief: e.detail.value.brief,
        categoryId: that.data.array[that.data.index].id,
        picUrl: "/static/images/default-dish.png"
      }
      console.log(data);
      return;
      //再发请求去存储
      util.request(api.DishUpdate, data, "POST").then((res) => {
        if (res.errno == 0) {
          wx.redirectTo({
            url: '/pages/ucenter/dishes/dishes',
          })
        } else {
          wx.hideLoading({
            success: (res) => {},
          })
          wx.showToast({
            title: res.errmsg,
          })
        }
      })
      return;
    } else {
      if (that.data.hasMod) {
        wx.uploadFile({
          url: api.StorageUpload,
          filePath: tempFilePath,
          name: 'file',
          formData: {
            'user': 'admin'
          },
          header: {
            'Content-Type': 'application/json',
          },
          success(res) {
            console.log(res.data)
            const result = JSON.parse(res.data);
            var data = {
              id: that.data.input.id,
              name: e.detail.value.name,
              price: e.detail.value.price,
              brief: e.detail.value.brief,
              categoryId: that.data.array[that.data.index].id,
              picUrl: result.data.storage.url
            }
            console.log(data);
            return;

            //再发请求去存储
            util.request(api.DishUpdate, data, "POST").then((res) => {
              if (res.errno == 0) {
                wx.redirectTo({
                  url: '/pages/ucenter/dishes/dishes',
                })
              }
            })
          },
          fail(res) {
            console.log(res);
          }

        })
      } else {
        var data = {
          id: that.data.input.id,
          name: e.detail.value.name,
          price: e.detail.value.price,
          brief: e.detail.value.brief,
          categoryId: that.data.array[that.data.index].id,
        }
        console.log(data);
        return;
        //再发请求去存储
        util.request(api.DishUpdate, data, "POST").then((res) => {
          if (res.errno == 0) {
            wx.redirectTo({
              url: '/pages/ucenter/dishes/dishes',
            })
          }
        })
      }

      wx.hideLoading({
        success: (res) => {},
      })
    }
  },
  storeImg: function (url) {
    return new Promise((resolve, reject) => {
      wx.uploadFile({
        url: api.StorageUpload,
        filePath: url,
        name: 'file',
        formData: {
          'user': 'admin'
        },
        header: {
          'Content-Type': 'application/json',
        },
        success(res) {
          if (res.statusCode == 413) {
            wx.showToast({
              icon: "error",
              title: '图片超过1M',
            });
            reject();
            return;
          } else {
            var resdata = JSON.parse(res.data);
            // console.log(resdata.data.storage.url);
            resolve(resdata.data.storage.url);
          }
        },
        fail(res) {
          console.log(res);
          reject();
        }
      })
    })

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var dishesCategory = wx.getStorageSync('dishesCategory');
    var dishDetail = JSON.parse(options.queryBean);
    var categoryId = dishDetail.categoryId;
    var index = 0;
    for (var i = 0; i < dishesCategory.length; i++) {
      if (dishesCategory[i].id == categoryId) {
        index = i;
        break;
      }
    }

    var temp = [{
      name: dishDetail.name,
      url: dishDetail.picUrl
    }]

    this.setData({
      array: dishesCategory,
      arrayIndex: util.toIndex(dishesCategory),
      input: JSON.parse(options.queryBean),
      fileList: temp,
      index: index
    });
  },

  previewImg: function (e) {
    wx.previewImage({
      urls: [e.currentTarget.dataset.imageUrl],
      current: e.currentTarget.dataset.imageUrl,
      showmenu: true,
      success: (res) => {},
      fail: (res) => {},
      complete: (res) => {},
    })
  },
  delImg: function (e) {
    console.log(e.detail.index);
    this.data.fileList.splice(e.detail.index, 1);
    this.setData({
      fileList: this.data.fileList
    });

  },
  afterRead(event) {
    const {
      file,
      name
    } = event.detail;
    console.log(JSON.stringify(file, null, 2));
    const fileList = this.data[`fileList`];
    this.setData({
      [`upload`]: fileList.concat(file),
      [`fileList`]: fileList.concat(file),
    });

  },
  update(query) {
    //再发请求去存储  
    util.request(api.DishUpdate, query, "POST").then((res) => {
      if (res.errno == 0) {
        // wx.redirectTo({
        //   url: '/pages/ucenter/dishes/dishes',
        // })
        // let pages = getCurrentPages();
        // let beforePage=pages[pages.length-2];
        // beforePage.onLoad();
        wx.navigateBack({
          delta: 1,
        })
      }
    })
  }
})