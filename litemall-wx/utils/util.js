var api = require('../config/api.js');
var app = getApp();

function getYMDHMS (timestamp) {
  let time = new Date(timestamp)
  let year = time.getFullYear()
  let month = time.getMonth() + 1
  let date = time.getDate()
  let hours = time.getHours()
  let minute = time.getMinutes()
  let second = time.getSeconds()

  if (month < 10) { month = '0' + month }
  if (date < 10) { date = '0' + date }
  if (hours < 10) { hours = '0' + hours }
  if (minute < 10) { minute = '0' + minute }
  if (second < 10) { second = '0' + second }
  return year + '-' + month + '-' + date + ' ' + hours + ':' + minute + ':' + second
}
function getYMD(timestamp){
  let time = new Date(timestamp)
  let year = time.getFullYear()
  let month = time.getMonth() + 1
  let date = time.getDate()
 

  if (month < 10) { month = '0' + month }
  if (date < 10) { date = '0' + date }

  return year + '-' + month + '-' + date
}
function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}
function formatTimeNextYear(date) {
  var year = date.getFullYear()+1
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()


  return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}
function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

/**
 * 封封微信的的request
 */
function request(url, data = {}, method = "GET") {
  return new Promise(function(resolve, reject) {
    wx.request({
      url: url,
      data: data,
      method: method,
      header: {
        'Content-Type': 'application/json',
        'X-Litemall-Token': wx.getStorageSync('token')
      },
      success: function(res) {
        if (res.statusCode == 200) {
          if (res.data.errno == 501) {
            // 清除登录相关内容
            try {
              wx.removeStorageSync('userInfo');
              wx.removeStorageSync('token');
            } catch (e) {
              // Do something when catch error
            }
            // 切换到登录页面
            wx.redirectTo({
              url: '/pages/auth/login/login'
            });
            
          } else if(res.data.errno == 507){ // 507是未授权代码                            
              console.log("未授权");
              wx.showToast({
                icon:'error',
                title: '账号未激活',
              })
          } else{
            resolve(res.data);
          }
        } else {
          reject(res.errMsg);
        }

      },
      fail: function(err) {
        reject(err)
      }
    })
  });
}


function redirect(url) {

  //判断页面是否需要登录
  if (false) {
    wx.redirectTo({
      url: '/pages/auth/login/login'
    });
    return false;
  } else {
    wx.redirectTo({
      url: url
    });
  }
}

function showErrorToast(msg) {
  wx.showToast({
    title: msg,
    image: '/static/images/icon_error.png',
   
  })
}

function showErrorModal(msg) {
  wx.showModal({
    showCancel:false,
    content: msg,
    success (res) {
      if (res.confirm) {
      } 
    }
  })
}
function unique(arr) {
  const res = new Map();
  return arr.filter((a) => !res.has(a) && res.set(a, 1))
}
//判断数组中某元素出现次数
function arrCheck(arr){
  var newArr = [];
  for(var i=0;i<arr.length;i++){
    var temp=arr[i];
    var count=0;
    for(var j=0;j<arr.length;j++){
      if(arr[j]==temp){
        count++;
        arr[j]=-1;
      }
    }
    if(temp != -1){
      newArr.push({el:temp,count:count})
    }
  }
  return newArr;
}
//判断是否在数组中
function isInArr(value,array) {
  return array.indexOf(value); //不存在返回-1
}
//获取服务器时间
function getServerTime() {
  return new Promise((resolve, reject) => {
    request(api.GetServerTime).then(res => {
      if (res.errno == 0) {
        resolve(res.data.nowTime);
      } else {
        wx.showToast({
          title: '时间同步出错',
        })
        reject(res);

      }
    })
  }).catch((err) => {
    console.log(err);
  })
}
  /**
   * 定义根据id删除数组的方法
   * @param {*} array 
   * @param {*} val 
   */
  function removeByValue(array, val) {
    for (var i = 0; i < array.length; i++) {
      if (array[i].id == val) {
        array.splice(i, 1);
        break;
      }
    }
  }

module.exports = {
  getYMD,
  getYMDHMS,
  formatTime,
  formatTimeNextYear,
  request,
  redirect,
  showErrorToast,
  showErrorModal,
  unique,
  arrCheck,
  isInArr,
  getServerTime,
  removeByValue
}