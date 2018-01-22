﻿//定义常量
function getHost() {
	var HOST_BUSINESS_DEVOLP = "http://61.161.238.158:8071/api152"; //开发环境地址
	//var HOST_BUSINESS_TEST = "http://61.161.238.158:8071/hyapipro/hongyan"; //测试环境基地址
	//http://59.46.97.115
	var HOST_BUSINESS_UAT = "http://uat.tls.sih.cq.cn:8000/api/hongyan"; //公网测试环境基地址
	var HOST_BUSINESS_TEST = "http://59.46.97.115:8071/hyapipro/hongyan"; //公网测试环境基地址
	var HOST_BUSINESS_RELEASE = "http://itg.sih.cq.cn/api/hongyan"; //线上环境基地址

	var host = HOST_BUSINESS_RELEASE;
	if(typeof __buildConfig != "undefined"){//尝试从自动生成的配置文件中读取 服务器地址url
	    host = __buildConfig["HOST_BUSINESS"];
	    console.log("尝试从自动生成的配置文件中读取 服务器地址url = "+host);
	}
	return host;
	
}
function getLiberateHost(){
	//解放推荐的基础地址：
	/*IP方式： http://219.146.249.190:10106/driver 

	域名是：http://qq.aerohuanyou.com:10106/ */

	var LIBERATE_WORDPRESS_IN_NETWORK = "http://192.168.135.89/blog/";//解放推荐内网 89服务器
	var LIBERATE_WORDPRESS_OUT_NETWORK = "http://219.146.249.190:10106/"//解放推荐外网
	//var LIBERATE_WORDPRESS_OUT_NETWORK = "http://qq.aerohuanyou.com:10106/"//域名访问解放推荐外网
	var host_liberate = LIBERATE_WORDPRESS_OUT_NETWORK;
	return host_liberate;
}
//获取url中参数
function getparms(){
	var str = window.location.href;
	var num = str.indexOf("?");
	str = str.substr(num + 1); 
	var arr = str.split("&");
	var dictArr ={};
	
	var k;
	var v;
	for (var i = 0;i < arr.length;i++) {
		num=arr[i].indexOf("="); 
		if(num<=0) continue;
		k = arr[i].substr(0,(num));
		v=arr[i].substr(num+1); 		    
	   dictArr[k]=v;
	}
	return dictArr;
}

//加密处理
 function makeSign(terminalId,timestap){
        if(typeof(terminalId)=="undefined")
          return null;
        if(typeof(timestap)=="undefined")
          return null;
        var sign = md5("hong" + terminalId + timestap + "yan");
        return typeof(sign)=="undefined"? null : sign.toUpperCase();
    }

//ajax请求  post请求 

function ajaxJson(url,json_data,fn){
	    var json_str = JSON.stringify(json_data);
		console.log("发送请求url="+url)
		$.ajax({
			type:"post",
			url:url,
			dateType:"json",
			data:json_str,
			contentType:"application/json",
			success:fn,
			erro:function(){
				console.log("请求失败");
				
			}
		});
	}
	
	//get请求
function ajaxJsonGet(url,fn){
 console.log("get请求函数"+url);
	$.ajax({
		type:"get",
		url:url,
		async:true,
		success:fn,
		erro:function(err){
			console.log("请求失败");
			
		}
	});
}
//获取时间 
function getTripTime(time,className){
		var s = Math.round(time /1000);
		var h =parseInt(s /3600);
		var m = Math.round((s/3600-h)*60);	
		var str = h + ":"+m;
		$(className).text(str);	
		return str;
}	
function getTime(time,className){
	var newTime = new Date(time);
	var nowTime = new Date();
	
	var year = newTime.getFullYear();
	var month = newTime.getMonth()+1;
	var day = newTime.getDate();
	
	var now_year = nowTime.getFullYear();
	var now_month = nowTime.getMonth()+1;
	var now_day = nowTime.getDate();
	
	var h = newTime.getHours();
	var m = newTime.getMinutes();
	var s = newTime.getMinutes();
	month = month <10?"0"+month:month;
	day = day<10?"0"+day:day;
	h = h <10?"0"+h:h;
	m= m <10?"0"+m:m;
	s = s <10?"0"+s:s;
	var str;
	if ((year == now_year)) {
		str = month+"-"+day+ " "+h+":"+m;
		if((year == now_year) &&(month == now_month)&&(day == now_day)){
			
			str = "今天  "+ h+":"+m;
		}
	}else{
		str = year+"-"+month+"-"+day+ " "+h+":"+m;
	}
	
	$(className).text(str);
	return str;
}
//获取当天时间
function getDayTime(time){
	var newTime = new Date(time);
	//var newTime = new Date();
	var year = newTime.getFullYear();
	var month = newTime.getMonth()+1;
	var day = newTime.getDate();
	var h = newTime.getHours();
	var m = newTime.getMinutes();
	var s = newTime.getMinutes();
	month = month <10?"0"+month:month;
	day = day<10?"0"+day:day;
	h = h <10?"0"+h:h;
	m= m <10?"0"+m:m;
	s = s <10?"0"+s:s;
	var str = h+":"+m + ":"+ s;
	return str;
}


/*接口整理*/
//司机版
//行车管家  
/*
 url_query = host + "/qingqi/tocapp/queryMaintainReminder //查询保养里程url
 url_maintained = host + "/qingqi/tocapp/carMaintained //已保养url
 url_query = host + "/qingqi/tocapp/queryMaintainReminder//查询保养里程url
 */

//故障诊断
/*
 var url_query = host + "/qingqi/tocapp/phyExam
 var url_code = host + "/qingqi/tocapp/queryFaultSolutionByCode
 
 * */
//油耗排行榜
/*
 var url0 = host + "/qingqi/tripAnalysis/queryYesterdayAvgOilWear //昨日平均油耗
 var url1 = host + "/qingqi/tripAnalysis/queryYesterdayAvgOilWearRanking //昨日油耗排行榜
 var url2 = host + "/qingqi/tripAnalysis/queryMonthAvgOilWear //本月平均油耗
 var url3 = host + "/qingqi/tripAnalysis/queryMonthAvgOilWearRanking//本月油耗排行榜
 * */
//积分
//任务中心 
/*
  会员日 	 var navUrl = host + "/qingqi/accumulate/queryScoreLogoList
 *总积分	 var scoreUrl = host + "/qingqi/accumulate/queryScore
 *会员日或特殊日期 var member_url = host+"/qingqi/accumulate/queryIsVipDate
 *每日任务 var dayUrl = host + "/qingqi/accumulate/queryDayTaskList 
 *新手任务  var newUrl = host + "/qingqi/accumulate/queryNewbieTaskList 
 *长期任务  var longUrl = host + "/qingqi//accumulate/queryLongTermTaskList
 *签到列表 var signList_url = host + "/qingqi/accumulate/querySignList
 * 签到成功  var sign_url = host + "/qingqi/accumulate/sign	
 * 积分明细 var url0 = host+"/qingqi/accumulate/queryScoreInfoList
 * 签到规则 var url_rule = host+"/qingqi/accumulate/queryScoreExplain 
 * 
 * */
//行程详情
/*
 var url_trip = host +"/qingqi/tripAnalysis/queryTripInfo
 * */
//行程分享
// var url_trip = host +"/qingqi/tocapp/shareTrip


//解放推荐
/*var url_index = http://219.146.249.190:10106/?json=get_category_index";
var url_cont = http://219.146.249.190:10106/?json=get_category_posts*/

//车队版
//单车报表   var url_car_list = host+"/qingqi/realTimeMonitor/queryRealTimeCarList
//单车运营报表  var url_running_car_list = host+"/qingqi/tocapp/statisticalCarReport
//驾驶时长 var url_running_car_list = host+"/qingqi/tocapp/statisRunningTimeCarList
//油耗统计 按路线  var url_fuel_route_list = host+"/qingqi/tocapp/statisFuelListByRoute
//        按车辆 url_fuel_car_list = host+"/qingqi/tocapp/statisFuelListByCar
//        柱状图 var url_fuel_chart = host + "/qingqi/tocapp/statisFuelChart
//里程统计 var url_car_list = host+"/qingqi/tocapp/statisMileageCarList 
//  图表  var url_mileage_chart = host + "/qingqi/tocapp/statisMileageChart

//车队报表
//http://61.161.238.158:8071/api153/qingqi/tocapp/statisticalTeamReportDetail
// http://61.161.238.158:8071/api153/qingqi/tocapp/statisticalTeamReport