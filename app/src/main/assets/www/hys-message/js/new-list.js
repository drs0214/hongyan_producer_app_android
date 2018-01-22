//定义变量
var host = getHost();
//获取参数
var dictArr = getparms();
var token =decodeURIComponent(dictArr['token']),
    pushShowType = dictArr['pushShowType'];


var page_size =20,page_number = 1,push_num = 1,messageId;

	//个人消息列表
	var url_queryUserMessageList = host +"/tocapp/queryUserMessageList";
	//1.1	查询用户推送消息未读数量
	var url_unreadMessageCount = host + "/tocapp/queryUnreadMessageCount";
	//已读消息
	var ur_setMessageRead = host+"/tocapp/setMessageRead";
	
	var json_data_per= {
		"token":token,
		 "page_size":page_size,
         "page_number":page_number,
	     "appType":2
	};
	var json_data_unread= {
		"token":token,
		"appType":2
		
	};
	
	//个人消息全读
	var json_data_perSetReadAll = {
		"token":token,
		"appType":2,
		"messageType":2	
	};
	
window.onload = function(){
	//$(".loading-more").hide();

    //个人消息
    ajaxJson(url_queryUserMessageList,json_data_per,bindDataToPerInfoListView);
   
   //安卓 ios控制显示哪个tab 
   /* if (pushShowType == 1) {
    	$(".per-info-lists").hide();
		$(".car-lists").show();
		$("#J_motorcadeNew").css({"border-bottom":".05rem solid #4F77DB","color":"#4F77DB"}).siblings().css({"border-bottom":"none","color":"#333"});
    } else if(pushShowType == 2){
    	$(".car-lists").hide();
		$(".per-info-lists").show();
		$("#J_personNews").css({"border-bottom":".05rem solid #4F77DB","color":"#4F77DB"}).siblings().css({"border-bottom":"none","color":"#333"});
    }*/
    
   
}


//绑定未读消息的数量 tab
function bindDataToUnreadMessView(result){
	//列表   调用车队消息接口
	console.log("绑定未读消息的数量 tab");
	console.log(result)
	//console.log(result);
	if(result.resultCode != 200){
		console.log(result.resultCode);
		//alert("数据请求失败");
		if (mobileAgent.isRunOnApp()) {
			mobileAgent.sendHttpErrorToApp(result.resultCode,result.message,200);
		}
	}
	if(result.resultCode == 200){
		
		if(result.data != null){
			var item = result.data;
			$(".cir-per").css("background","red").text(item.totalCount);
			
		}
		
	}
}  

//绑定未读消息的数量 tab
function bindDataToUnreadMessViewAll(result){
	//列表   调用车队消息接口
	console.log("绑定未读消息的数量 tab");
	console.log(result)
	//console.log(result);
	if(result.resultCode != 200){
		console.log(result.resultCode);
		//alert("数据请求失败");
		if (mobileAgent.isRunOnApp()) {
			mobileAgent.sendHttpErrorToApp(result.resultCode,result.message,200);
		}
	}
	if(result.resultCode == 200){
		
		if(result.data != null){
			var item = result.data;
			console.log("请求成功");
			/*if (item.userCount == 0) {
				$(".cir-per").css("background","#fff");
			}else if(item.userCount>0){
				$(".cir-per").css("background","red").text(item.userCount);
				
			}
			if (item.teamCount == 0) {
				$(".cir-moto").css("background","#fff");
			}else if(item.teamCount>0){
				$(".cir-moto").css("background","red").text(item.teamCount);
				
			}*/
		}
	//	console.log("访问成功 push_num :"+push_num);
		/*if (push_num == 1) {
			console.log("访问成功 请求刷新列表 pushShowType :"+push_num);
			$(".car-lists-item>.item-icon").html('<img src="img/icon_car_02.png"/>');
			
			
		} else if(push_num == 2){
			console.log("访问成功 请求刷新列表 pushShowType :"+push_num);
			$(".per-info-item>.per-item-lt").html('<img src="img/icon_per_02.png"/>');
			

		}*/
	}


}
//绑定个人消息列表
function bindDataToPerInfoListView(result){
	console.log("个人消息返回");
	console.log(result);
	if(result.resultCode != 200){
		console.log(result.resultCode);
		//alert("数据请求失败");
		if (mobileAgent.isRunOnApp()) {
			mobileAgent.sendHttpErrorToApp(result.resultCode,result.message,200);
		}
	/*$(".per-info-lists").html("<div class='tips-img'><img src='img/img_emptymessage.png'/><p style='width:100%'>暂无消息列表</p></div>");*/
			
	} 
	if(result.resultCode == 200){
		//$(".per-info-lists").empty();
		//console.log(result);
		if(result.data.list.length == 0){	
			$(".per-info-lists").html("<div class='tips-img'><img src='img/img_emptymessage.png'/><p style='width:100%'>暂无消息列表</p></div>");  
		} else if(result.data != null){	
		    var item = result.data.list;
			
			var str;
			for (var i =0;i <item.length;i++) {	
				
				//console.log(item[i].messageExtra,item[i].messageCode);
				str = '<div class="per-info-item" data-extra="'+encodeURIComponent(item[i].messageExtra) +'" data-code="'+item[i].messageCode+'" data-messid="'+item[i].messageId+'" data-readflag="'+item[i].readFlag+'" >'
							 +'<div class="per-item-sendtime">'
								+'<span class="per-sendtime" >'
									+getTime(item[i].sevTime)
								+'</span>'
							+'</div>'
							+'<div class="per-item-rt">'
								
								+'<div class="per-item-title">'
									+item[i].title
								+'</div>'								
								+'<div class="per-item-content">'
									+item[i].content
								+'</div>'
			
							+'</div>'
						+'</div>';
						console.log("item[i].title="+item[i].title)
					//	console.log($(".per-info-item").html())
				$(".per-info-lists").append(str);
			}	
			//分页 加载更多
				
			if (page_number >= result.data.page_total) {
				$(".loading-more").text("已经没有更多数据了");
				
				
			} else if(page_number < result.data.page_total){
				
				$(".loading-more").html("<p style='width:100%;'>加载更多</p>");
				
				page_number ++;
				$(".loading-more>p").click(function(){
					var json_data_per_more= {
						"token":token,
						 "page_size":page_size,
				         "page_number":page_number,
					     "appType":2
					};
					//个人消息
			 	  ajaxJson(url_queryUserMessageList,json_data_per_more,bindDataToPerInfoListView);
				})
				
			}	
			//进入页面全部已读
		readAllNum();
		
			//用delegate 会连续跳转
		//	$(".per-info-lists").delegate(".per-info-item","click",function(){
			$(".per-info-item").click(function(){
			    $(this).children(".per-item-lt").html('<img src="img/icon_per_02.png"/>');
			//与安卓 IOS交互
			 	var messageExtraValue = decodeURIComponent($(this).data("extra"));
			 	var messageCodeValue = $(this).data("code");
			    var readFlag = $(this).data("readflag");
			 	console.log("messageExtraValue="+messageExtraValue)
				console.log("messageCodeValue="+messageCodeValue);
				console.log("goPage");
				if (mobileAgent.isRunOnApp()) {
					
					mobileAgent.goPage("ModelListFragment",{messageExtra:messageExtraValue,messageCode:messageCodeValue});
					
				}/*else{
					location.href = "detail.html";
				}*/
				
			//每列已读
			//个人消息的每条
			var messageId = $(this).data('messid');
			
			var json_data_perSetRead = {
				"token":token,
				"appType":1,
				"messageType":2,
				"messageId":messageId
				
			};
			console.log("messageId="+messageId);
			if (readFlag == 1) {
				//tab 刷新
		  		 ajaxJson(ur_setMessageRead,json_data_perSetRead,bindDataToUnreadMessView);
			} else if(readFlag == 2){
				console.log("readFlag="+readFlag);
				return;
			}
		
			
		 })
		}				
						
	}
}





//全部已读 

/*function readAll(){
	
    readAllNum();

}*/
 console.log(push_num+"chedui");
 /* readAll(push_num);*/

function readAllNum(){
	
	/*if (push_num==1) {
		console.log("车队消息"+push_num);
		$(".car-lists-item>.item-icon").html('<img src="img/icon_car_02.png"/>');
		
		//车队列表ajax post请求
	    ajaxJson(ur_setMessageRead,json_data_carSetReadAll,bindDataToUnreadMessViewAll);
		console.log($(".car-lists-item>.item-icon").html())
		
		//ajaxJson(url_queryTeamMessageList,json_data_mot,bindDataToTeamMessageListView);

	} else if(push_num==2){*/
		//个人消息
   		console.log("个人消息"+push_num);
	//	$(".per-info-item>.per-item-lt").html('<img src="img/icon_per_02.png"/>');
   		
	    ajaxJson(ur_setMessageRead,json_data_perSetReadAll,bindDataToUnreadMessViewAll);	
   	//console.log($(".per-info-item>.per-item-lt").html())
   		//ajaxJson(url_queryUserMessageList,json_data_per,bindDataToPerInfoListView);

	//}
	
		
}

