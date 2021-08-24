// 以下是业务服务器API地址
// 本机开发时使用
// var WxApiRoot = 'http://localhost:8080/wx/';
var UploadRoot = 'https://www.joyfey.xyz:4423/';
//局域网
// var WxApiRoot = 'http://192.168.1.2:8080/wx/';

// 云平台上线时使用
var WxApiRoot = 'https://www.joyfey.xyz:4423/wx/'; 
// var UploadRoot = 'https://www.joyfey.xyz:4423/';
//公司内部服务器
// var WxApiRoot = 'https://www.yllt.icu:4413/wx/';





module.exports = {
  UploadRoot:UploadRoot,
  IndexUrl: WxApiRoot + 'home/index', //首页数据接口
  AboutUrl: WxApiRoot + 'home/about', //介绍信息

  GetServerTime:  WxApiRoot + 'dateTime/getNow', //介绍信息

  CatalogList: WxApiRoot + 'catalog/index', //分类目录全部分类数据接口
  CatalogCurrent: WxApiRoot + 'catalog/current', //分类目录当前分类数据接口

  AuthLoginByWeixin: WxApiRoot + 'auth/login_by_weixin', //微信登录
  AuthLoginByWeixinFs: WxApiRoot + 'auth/login_fs', //微信初步登录
  AuthLoginByWeixinSec: WxApiRoot + 'auth/login_sec', // 认证登录

  AuthLoginByAccount: WxApiRoot + 'auth/login', //账号登录
  AuthLogout: WxApiRoot + 'auth/logout', //账号登出
  AuthRegister: WxApiRoot + 'auth/register', //账号注册
  AuthReset: WxApiRoot + 'auth/reset', //账号密码重置
  AuthRegisterCaptcha: WxApiRoot + 'auth/regCaptcha', //验证码
  AuthBindPhone: WxApiRoot + 'auth/bindPhone', //绑定微信手机号

  CommentList: WxApiRoot + 'comment/list', //评论列表
  CommentCount: WxApiRoot + 'comment/count', //评论总数
  CommentPost: WxApiRoot + 'comment/post', //发表评论

  TopicList: WxApiRoot + 'topic/list', //专题列表
  TopicDetail: WxApiRoot + 'topic/detail', //专题详情
  TopicRelated: WxApiRoot + 'topic/related', //相关专题


  OrderSubmit: WxApiRoot + 'order/submit', // 提交订单
  OrderPrepay: WxApiRoot + 'order/prepay', // 订单的预支付会话
  OrderList: WxApiRoot + 'order/list', //订单列表
  OrderDetail: WxApiRoot + 'order/detail', //订单详情
  OrderRefund: WxApiRoot + 'order/refund', //退款取消订单
  OrderDelete: WxApiRoot + 'order/delete', //删除订单
  OrderConfirm: WxApiRoot + 'order/confirm', //确认收货
  OrderGoods: WxApiRoot + 'order/goods', // 代评价商品信息
  OrderComment: WxApiRoot + 'order/comment', // 评价订单商品信息

  TimingList: WxApiRoot + 'timing/list', //用餐时段列表
  TimingUpdate: WxApiRoot + 'timing/update', //用餐时段更新
  MealOrder: WxApiRoot + 'mealOrder/add', //报餐
  MealOrderQuery: WxApiRoot + 'mealOrder/findByUid', //指定日期报餐查询
  MealOrderCancel: WxApiRoot + 'mealOrder/cancel', //取消报餐
  MealOrderList: WxApiRoot + 'mealOrder/list', //查询个人报餐列表
  MealOrderByOrderId: WxApiRoot + 'mealOrder/findByOrderId', //查询个人报餐列表
  MealOrderQueryByOrderAndTiming : WxApiRoot + 'mealOrder/listByOrderAndTiming', 
  CanteenOrderList: WxApiRoot + 'canteenOrder/list', //查询近期个人十条报餐记录
  CanteenOrderPage: WxApiRoot + 'canteenOrder/queryByUidAndPage', //根据分页和状态查询
  CanteenOrderByOrderSn: WxApiRoot + 'canteenOrder/findByOrderSn', //通过条码查询
  GetConfig: WxApiRoot + 'config/getConfig',

  DailyCanteenOrderList: WxApiRoot + 'canteenOrder/dailyList', //查询当日订单信息
  AllOrderByDate: WxApiRoot + 'mealOrder/listByDate', //查询指定日期报餐列表
  QueryByIdAndDate: WxApiRoot + 'canteenOrder/queryByIdAndDate', //查询指定日期报餐列表
  
  dishesList: WxApiRoot + 'dishes/list', //菜品列表
  QueryByCateIdAndPage: WxApiRoot + 'dishes/queryByCateIdAndPage', //菜品列表
  SearchByCateAndKeyword: WxApiRoot + 'dishes/searchByCateAndKeyword', //查找菜品

  DishesAdd: WxApiRoot + 'dishes/add', //用餐添加
  DishesDel: WxApiRoot + 'dishes/delete', //菜品信息删除
  DishUpdate: WxApiRoot + 'dishes/update',//菜品信息更新
  CanteenDishCateList: WxApiRoot + 'canteenDishCate/getAllCate', //获取全部菜品分类
  CanteenDishCateAdd: WxApiRoot + 'canteenDishCate/add', //添加菜品分类
  CanteenDishCateDel: WxApiRoot + 'canteenDishCate/delete', //删除菜品分类
  DishGetBydCateId: WxApiRoot + 'dishes/getBydCateId', //获取全部菜品分类
  
  DishesSearch: WxApiRoot + 'dishes/search', //用餐时段更新
  DailyMenuList: WxApiRoot + 'dailyMenu/queryByDate', //获取列表
  DailyMenuAdd: WxApiRoot + 'dailyMenu/add', //添加菜品
  DailyMenuDel: WxApiRoot + 'dailyMenu/deleteById', //删除菜品
  DailyMenuLisByDateAndTimingId: WxApiRoot + 'dailyMenu/queryByDateAndTimingId', //获取列表
  StorageUpload: WxApiRoot + 'storage/upload', //图片上传,

  OrderCheck: WxApiRoot + 'canteenOrder/check', //订单核销,
  OrderCancel: WxApiRoot + 'canteenOrder/cancel', //订单核销,
  QueryByFilter: WxApiRoot + 'canteenOrder/queryByFilter',
  OrderCheckByOrderSn: WxApiRoot + 'canteenOrder/checkByOrderSn', //订单核销,
  QueryByFilterThenGroup: WxApiRoot + 'canteenOrder/queryByFilterThenGroup', //查询并分组
  

  GetBcUserInfoByUserId: WxApiRoot + 'user/getBcUserInfoByUserId', //传入ID数组，返回用户,
  GetSingleBcUserByUserId: WxApiRoot + 'user/getSingleBcUserByUserId', //查询当前用户的bc用户信息
  GetBcUserHashMapByUserId: WxApiRoot + 'user/getBcUserHashMapByUserId', //传入ID数组，返回用户,
  GetBcUserList: WxApiRoot + 'bcuser/queryAll', //查询当前用户的bc用户信息
  UpdateBcUser: WxApiRoot + 'bcuser/updateBcUser', //更新用户信息
  Socket: "wss://www.joyfey.xyz/" + 'websocket/12',
  QueueQuery: WxApiRoot + 'myredis/hAll',
  QueueDel: WxApiRoot + 'myredis/hDel',

  UserIndex: WxApiRoot + 'user/index', //个人页面用户相关信息
  IssueList: WxApiRoot + 'issue/list', //帮助信息

};