// 空字符串判断
function isEmpty(v, allowBlank) {
   return v === null || v === undefined || (!allowBlank ? v.trim() === "" : false);
}
//判断数组包含某值
function contains(arr, obj) {  
    var i = arr.length;  
    while (i--) {  
        if (arr[i] === obj) {  
            return true;  
        }  
    }  
    return false;  
} 
//数组移除值
function removeByValue(arr, val) {
	for(var i=0; i<arr.length; i++) {
		if(arr[i] == val) {
	    	arr.splice(i, 1);
	    	break;
	    }
	}
}
//下拉框多选时数据回显
function chose_mult_set_init(id, values) {
    var arr = values.split(",");
    var length = arr.length;
    var value = "";
    for (i = 0; i < length; i++) {
        value = arr[i];
        $("#"+ id + " option[value='" + value + "']").attr("selected", "selected");
    }
    $("#"+ id).trigger("chosen:updated");
}
//根据日期获取年数
function getAge(strBirthday){    
    if(""==strBirthday){
        return "";
    }
    var returnAge;  
    var strBirthdayArr=strBirthday.split("-");  
    var yearBirth = strBirthdayArr[0];  
    var monthBirth = strBirthdayArr[1];  
    var birthDay = strBirthdayArr[2];  

    var date = new Date();  
    var nowYear = date.getFullYear();  
    var nowMonth = date.getMonth() + 1;  
    var nowDay = date.getDate();  
      
    if(nowYear == yearBirth){  
        returnAge = 0;//同年 则为0岁  
    }else{  
        var ageDiff = nowYear - yearBirth ; 
        if(ageDiff > 0){  
            if(nowMonth == monthBirth) {  
                var dayDiff = nowDay - birthDay;//日之差  
                if(dayDiff < 0){  
                    returnAge = ageDiff - 1;  
                }else{  
                    returnAge = ageDiff ;  
                }  
            }else {  
                var monthDiff = nowMonth - monthBirth;//月之差  
                if(monthDiff < 0){  
                    returnAge = ageDiff - 1;  
                }else{  
                    returnAge = ageDiff ;  
                }  
            }  
        }else {  
            returnAge = -1;//错误的年龄
        }  
    }  
    return returnAge;//返回周岁年龄  
}  
/**
 * ajax 异步提交表单
 * @param formId  表单ID
 * @param callback  回调函数名
 */
function ajaxFormSubmit(formId,callbackFun){
	
	jQuery("#"+formId).ajaxSubmit({
		type : "post", 
		dataType : "json", 
		success : function(data) { 
			eval(callbackFun+"(data)");
		},
		error : function() {
			jQuery("#loadingBar").hide();
			alert("信息提交错误");
		}
	});
}
//默认表单提交回调函数
function defaultAjaxSaveCallBack(data){
	if(data && data.result){
		if("function"==typeof(unBindEvent)){
			unBindEvent();
		}
		jQuery("#loadingBar").hide();
		toastr.success(data.msg);
		var count=1;
        var interval = window.setInterval(function(){
        	count--;
        	if(count==0) {
        		window.location.href = data.data;
            	clearInterval(interval);	    	
            }
       },1000);
	}else{
		jQuery("#loadingBar").hide();
		if("timeout"==data.msg){
			toastr.error("登录超时,请重新登录","操作失败");
			var count=1;
	        var interval = window.setInterval(function(){
	        	count--;
	        	if(count==0) {
	        		window.location.href = data.data;
	            	clearInterval(interval);	    	
	            }
	       },1000);
		}else{
			toastr.error(data.msg,"操作失败");	
		}
	}
}

//列表删除用ajax（暂未用）
function doDeleteAjaxSubmit(url, id, currentPage){
	jQuery("#loadingBar").show();
	jQuery.ajax({
		type:"post",
		async:false, //同步请求
	    url:url,
	    data:{"id":id, "currentPage":currentPage},
	    dataType : "json", 
	    success:function(data){
			jQuery("#loadingBar").hide();
			if(data && data.result){
				toastr.success(data.msg);
				var count=1;
		        var interval = window.setInterval(function(){
		        	count--;
		        	if(count==0) {
		        		window.location.href = data.data;
		            	clearInterval(interval);	    	
		            }
		       },1000);
			}else{
				toastr.error(data.msg,"操作失败");
			}
        },
		error : function() {
			jQuery("#loadingBar").hide();
			alert("信息提交错误");
		}
	});
}

