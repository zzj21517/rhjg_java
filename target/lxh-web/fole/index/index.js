var douban = require('../../comm/script/fetch');
var config = require('../../comm/script/config');
var Constant = require('../../util/constant.js');
var util = require('../../util/util.js');

var app = getApp();

Page({
	data: {
		films: [],
		hasMore: true,
		showLoading: true,
		start: 0,
		bannerList: [],
    sortArray: [
          { 'id': '00', 'name': '默认排序' },
          { 'id': '00', 'name': '最新发布' }, 
          { 'id': '03', 'name': '正在招标中' },
          { 'id': '01', 'name': '正在服务中' },
          { 'id': '02', 'name': '项目已完工' }
      ],
    sortIndex: '0',
    sortValue:'00',
    msgList:[],
    selectedTab1:true,
    selectedTab2:false,
    selectedTab2:false,
    selectedFlag:"TAB1",

    profession: '00',
    professional: [{ 'id': '00', 'name': '专业' }, { 'id': '01', 'name': '土建' }, { 'id': '02', 'name': '安装' }, { 'id': '03', 'name': '水利' }, { 'id': '04', 'name': '电力' }, { 'id': '05', 'name': '市政园林' }, { 'id': '06', 'name': '技术标' }, { 'id': '07', 'name': '仿古' }, { 'id': '08', 'name': '公路桥梁' }, { 'id': '12', 'name': '其他' }, { 'id': '13', 'name': '钢结构' }, { 'id': '14', 'name': '装修' }],
    professionalIndex: '0',


    provinces: [],
    province: "",
    citys: [],
    city: "地区",
    countys: [],
    county: '',
    value: [0, 0, 0],
    values: [0, 0, 0],
    condition: false,

    userList:[],
    click_one:false

	},

	onLoad: function() {
		var that = this;

    var uid = wx.getStorageSync('LOGIN_USER_ID');

    if (uid == "" || uid == "undefined") {
      uid = '15144180088-1'
    }else{

    }

    wx.request({
      url: Constant.GET_BANNER_URL,

      data: {
        uid: uid
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {

        var obj = JSON.parse(res.data);
        var is_matain = obj.is_matain;

        that.setData({
          bannerList: obj.subjects,
          msgList: obj.lucky,
          userList: obj.data,
        });
        
        if (is_matain == '0'){
          wx.navigateTo({
            url: "../userInfo/userInfo?uid=" + uid
          })
        }
      }
    });
    

    
	},

  selectCity:function(){
    var that = this;

    wx.navigateTo({
      url: '../selectCity/selectCity',
    })
  },

  //发单
  releaseItem:function(){
    wx.navigateTo({
      url: '../release/release',
    })
  },
	
  //参加活动
  viewGame: function () {
    var uid = wx.getStorageSync('LOGIN_USER_ID');
    if (uid == "" || uid == "undefined") {
      wx.showModal({
        title: '提示',
        content: '您暂时没有登录，不能进行抽奖游戏。',
        showCancel: false
      });
    } else {
      //判断活动是否开展，跳转
      wx.request({
        url: Constant.IS_ACTIVTY_START_URL,
        method: "POST",
        data: {
          
        },
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          var obj = JSON.parse(res.data)
          var statueCode = obj.code;

          if (statueCode == '1') {
            wx.navigateTo({
              url: "../game/game"
            })
          } else {
            wx.showModal({
              title: '提示',
              content: '此次活动暂未开始，请关注通知准时参加。',
              showCancel: false,
              success: function (e) {
              }
            });
          }
        }
      })
    }
  },
  
	onPullDownRefresh: function() {
		var that = this
		that.setData({
			films: [],
			hasMore: true,
			showLoading: true,
			start: 0,
			bannerList: [],
      sortValue:'00',
      userList: []
		});

    that.onLoad();
	},

	onReachBottom: function() {
		var that = this

		if (!that.data.showLoading) {
      douban.fetchFilms.call(that, config.apiList.project_url, that.data.start, '', 'ALL', 'ALL', that.data.selectedFlag, that.data.sortValue, that.data.city, that.data.profession);
		}
	},

	viewProjectDetail: function(e) {
		//判断是否登录，不登录不能查看，暂不做限制
		
		var data = e.currentTarget.dataset;
    var status = data.status;
    
    wx.navigateTo({
      url: "../filmDetail/filmDetail?id=" + data.id + "&frm=popular"
    })
	},

	viewBannerDetail: function(e) {
		var data = e.currentTarget.dataset;
    
		wx.navigateTo({
      url: "../festival/festival"
		})
	},

  viewRecommendDetail: function (e) {
    var data = e.currentTarget.dataset;

    wx.navigateTo({
      url: "../tuijianfuwu/tuijianfuwu"
    })
  },


  //一元抢台历
  viewSelectCalendar: function (e) {
    var that = this;
    var uid = wx.getStorageSync('LOGIN_USER_ID');

    if (uid == "" || uid == "undefined") {
      wx.showModal({
        title: '提示',
        content: '小主，此活动仅限平台用户，请登陆后再购买。',
        showCancel: false
      });
    } else{
      //判断是否存在订单
      wx.request({
        url: Constant.GET_ISEXISTCO_URL,
        method: "POST",
        data: {
          uid: uid,
          key:'4ebfe3a7-f6f6-4948-89a8-c85829f0'
        },
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          var obj = JSON.parse(res.data)
          var statueCode = obj.code;

          if (statueCode == '200') {
           //存在订单，给出存在的提示
            wx.showModal({
              title: '提示',
              content: '小主，您已经购买成功，此商品是优惠商品，只能购买一件。',
              showCancel: false
            });
          } else {
            if (that.data.click_one){
              wx.showModal({
                title: '提示',
                content: '小主，系统正在处理，请稍等片刻。',
                showCancel: false
              });
            }else{
              that.setData({
                click_one: true
              });

              var itemList = [];
              //组装JSON语句，传到后台
              var obj = {
                id: '100001',
                count: 1,
                name: '一元钱抢台历',
                price: '1',
                amt_sum: '1'
              };
              itemList.push(obj);

              //向后台请求，生成预订单STATUS:10
              var uuid = util.getUuid();

              wx.request({
                url: Constant.GET_ADDPREORDER_URL,
                //仅为示例，并非真实的接口地址
                method: "POST",
                data: {
                  uuid: uuid,
                  qty: 1,
                  amt: '1',
                  items: JSON.stringify(itemList),
                  uid: uid,
                  key: '4ebfe3a7-f6f6-4948-89a8-c85829f0'
                },

                header: {
                  'content-type': 'application/x-www-form-urlencoded'
                },
                success: function (res) {

                  var t = JSON.parse(res.data);

                  var status = t.code;
                  var co_num = t.CoNum;

                  if (status == '200') {
                    //直接跳转页面取付款
                    wx.navigateTo({
                      url: "../payMoney/payMoney?id=" + co_num
                    });

                  } else {
                    wx.showToast({
                      title: '获取失败',
                      icon: 'success',
                      duration: 2000
                    });
                  }
                }
              })
            }
          }
        }
      }) 
    }
  },

  

	viewTuiJian: function(e) {
		wx.navigateTo({
			url: '../peopleTui/peopleTui'
		})
	},

	editProject:function(e){
		var data = e.currentTarget.dataset;

		wx.navigateTo({
			url: "../project/project?classify=" + data.id
		})
	},

  sortProject: function (e) {
    var that = this;
    var usedIndex = e.detail.value;
    var sort = '00';
    if (usedIndex != "null") {
      sort = that.data.sortArray[usedIndex].id;
      that.setData({
        sortIndex: usedIndex,
        sortValue: that.data.sortArray[usedIndex].id,
        films: [],
        hasMore: true,
        showLoading: true,
        start: 0,
        bannerList: []
      })
    }

    //搜索项目
    douban.fetchFilms.call(that, config.apiList.project_url, that.data.start, '', 'ALL', 'ALL', that.data.selectedFlag, that.data.sortValue, that.data.city, that.data.profession);
  },

  onShareAppMessage: function (res) {
    var that = this;
    return {
      title: '我在融汇精工平台接私活，3个月挣了5万块，你还不快来。',
      desc: '我在融汇精工平台接私活，3个月挣了5万块，你还不快来。',
      path: '/pages/index/index',
      imageUrl: "https://www.ronghuijinggong.com/Uploads/rhjg01/home_share.jpg",
      success: function (res) {
        wx.showToast({
          title: '分享成功',
          icon: 'success',
          duration: 2000
        })
      },
      fail: function (res) {
        wx.showToast({
          title: '取消分享',
          icon: 'success',
          duration: 2000
        })
      }
    }
  },

  //新年红包
  PayGetRedBag:function(e){
    var that = this;
    var uid = wx.getStorageSync('LOGIN_USER_ID');

    if (uid == "" || uid == "undefined") {
      wx.showModal({
        title: '提示',
        content: '小主，此活动仅限平台用户参与，请登陆后再参加该活动。',
        showCancel: false
      });
    } else {
      wx.navigateTo({
        url: '../payredbag/payredbag',
      });
    }
  },

  selectTab1: function (e) {
    //收件箱操作
    var that = this;

    that.setData({
      films: [],
      hasMore: true,
      showLoading: true,
      start: 0,
      selectedTab1: true,
      selectedTab2: false,
      selectedTab3: false,
      selectedFlag: "TAB1"
    });

    //请求消息
    douban.fetchFilms.call(that, config.apiList.project_url, that.data.start, '', 'ALL', 'ALL', that.data.selectedFlag, that.data.sortValue, that.data.city, that.data.profession);
  },

  selectTab2: function (e) {
    //收件箱操作
    var that = this;

    that.setData({
      films: [],
      hasMore: true,
      showLoading: true,
      start: 0,
      selectedTab1: false,
      selectedTab2: true,
      selectedTab3: false,
      selectedFlag: "TAB2",
    });

    //请求消息
    douban.fetchFilms.call(that, config.apiList.project_url, that.data.start, '', 'ALL', 'ALL', that.data.selectedFlag, that.data.sortValue, that.data.city, that.data.profession);
  },

  selectTab3: function (e) {
    //收件箱操作
    var that = this;

    that.setData({
      films: [],
      hasMore: true,
      showLoading: true,
      start: 0,
      selectedTab1: false,
      selectedTab2: false,
      selectedTab3: true,
      selectedFlag: "TAB3"
    });

    //请求消息
    douban.fetchFilms.call(that, config.apiList.project_url, that.data.start, '', 'ALL', 'ALL', that.data.selectedFlag, that.data.sortValue, that.data.city, that.data.profession);
  },


  onShow: function () {
    var that = this;

    that.setData({
      films: [],
      hasMore: true,
      showLoading: true,
      start: 0
    });

    douban.fetchFilms.call(that, config.apiList.project_url, that.data.start, '', 'ALL', 'ALL', that.data.selectedFlag, that.data.sortValue, that.data.city, that.data.profession);
  },

  bindPickerChange: function (e) {
    var that = this;
    var index = e.detail.value;
    this.setData({
      professionalIndex: e.detail.value,
      profession: that.data.professional[index].id
    });
    //搜索
    that.setData({
      films: [],
      hasMore: true,
      showLoading: true,
      start: 0
    });
    
    douban.fetchFilms.call(that, config.apiList.project_url, that.data.start, '', 'ALL', 'ALL', that.data.selectedFlag, that.data.sortValue, that.data.city, that.data.profession);
  },

//获取优惠券
getYouHuiCard:function(e){
  var uid = wx.getStorageSync('LOGIN_USER_ID');

  if(uid == "" || uid == "undefined") {
    wx.showModal({
      title: '提示',
      content: '您未登陆系统，请登陆后领取优惠券。',
      showCancel: false,
      success: function (e) {
        
      }
    });
  }else{
    wx.request({
      url: Constant.GET_YOUHUI_CARD_URL,
      method: "POST",
      data: {
        uid: uid,
        key: '00ac5f6868beba155e0c05d13ee8199a'
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var obj = JSON.parse(res.data)
        var statueCode = obj.code;

        if (statueCode == '200') {
          wx.showModal({
            title: '提示',
            content: '恭喜你，领取推荐服务费五折优惠券成功，请至我的券包中使用。',
            showCancel: false,
            success: function (e) {
            }
          });
        } else if (statueCode == '100'){
          wx.showModal({
            title: '提示',
            content: '您已经领取该优惠券，请至我的券包中使用。',
            showCancel: false,
            success: function (e) {
            }
          });
        }else {
          wx.showModal({
            title: '提示',
            content: '页面加载出现错误，请重新打开。',
            showCancel: false,
            success: function (e) {
            }
          });
        }
      }
    })
  }
},

  viewPeopleDetail: function (e) {
    //判断是否登录，不登录不能查看
    var data = e.currentTarget.dataset;
    wx.navigateTo({
      url: "../people_newDetail/people_newDetail?id=" + data.id
    })
  },

  viewSearch: function () {
    wx.navigateTo({
      url: '../search/search'
    })
  },

  HelpFindMan:function(){
    wx.navigateTo({
      url: '../../packageA/pages/find_man/find_man'
    })
  },

  PingTaiHeZuo: function () {
    wx.navigateTo({
      url: '../../packageA/pages/cooperation/cooperation?opt_type=02'
    })
  },

  MaoSuiZiJian: function () {
    wx.navigateTo({
      url: '../maosuizijian/maosuizijian?opt_type=03'
    })
  },

  TuiJianRenYuan: function () {
    wx.navigateTo({
      url: '../../packageA/pages/people_tui_jian/people_tui_jian'
    })
  }

})