function isNotEmptyByTrim(val) {
	if(val!=null&&val!=undefined&&$.trim(val)!=''){
		return true;
	}
	return false;
}

function apply(jssdkInfo) {
	wx.config({
		debug : false,
		appId : jssdkInfo[0],
		timestamp : jssdkInfo[1],
		nonceStr : jssdkInfo[2],
		signature : jssdkInfo[3],
		jsApiList : jssdkInfo[4]
	});
	wx.ready(function() {
		// 1 判断当前版本是否支持指定 JS 接口，支持批量判断
		wx.checkJsApi({
			jsApiList : jssdkInfo[4],
			success : function(res) {
//				lert(JSON.stringify(res));
			}
		});
		wx.hideAllNonBaseMenuItem();
	});
}

function fen2yuan(val){
	return Number(val/100).toFixed(2);
}

function yuan2fen(val){
	return Number(val*100).toFixed(0);
}

function request(url, data, success, error, before) {
	$.ajax({
		timeout : 20000,
		type : "POST",
		url : url,
		data : data,
		beforeSend : before,
		error : function(jqXHR, textStatus, errorThrown) {
			closeCover();
			if(textStatus == "timeout"){
				alert("网络超时，请稍候再试");
			}else{
				alert("网络异常，请稍后再试");
			}
		},
		success : function(datas, status) {
			if (datas.result == "SUCCESS") {
				success(datas);
			} else {
				closeCover();
				error(datas);
			}
		}
	});
}

function pay(payDates) {
	if(isNotEmptyByTrim(payDates.realBuyMoney)){
		payDates.realBuyMoney = yuan2fen(payDates.realBuyMoney);
	}else{
		return;
	}
	request(
			"cxy/webAction/creatOrderAndBuy",
			payDates,
			function(dates) {
				wx.chooseWXPay({
					timestamp : dates.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
					nonceStr : dates.nonceStr, // 支付签名随机串，不长于 32 位
					package : dates.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
					signType : dates.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
					paySign : dates.paySign, // 支付签名
					success : function(res) {
						buyafter(dates.orderId);
					},
					cancel : function(res){
						cancel(payDates.carNo);
					},
					complete : function(res){
						closeCover();
					}
				});
			},
			function(dates) {
				if (dates.expCode != null) {
					alert(dates.expMsg);
				} else {
					alert("下订单失败[" + dates.return_code + "][" + dates.return_msg + "]");
				}
			},
			function() {openCover();});
}

// 罩层的打开与关闭
function openCover() {
	if ($("#coverLayer") != undefined) {
		$("#coverLayer").attr("class", "floatCover_active");
	}
}
function closeCover() {
	if ($("#coverLayer") != undefined) {
		$("#coverLayer").attr("class", "floatCover");
	}
}

function loadBrandAndCarXl(selectBrandCode,selectXlCode) {
	var carXl_list = {};
	request("cxy/webAction/loadCarBrandAndCarXl",null,function(dates){
		
		$("select[name='brand']").append($("<option value='-2' class='selectOptionDefault'>").text("请选择--"));
		$.each(dates.date.CarBrand,function(key,value){
			var option = $("<option class='selectOptionDefault'>").val(value.code).text(value.name)
			if(selectBrandCode!=null&&value.code==selectBrandCode){
				option.attr("selected",true);
			}
			$("select[name='brand']").append(option);
		});
		$("select[name='brand']").append($("<option class='selectOptionDefault'>").val("-10").text("其他"));
		
		$("select[name='brand']").change(function(){
			var list = carXl_list[$(this).find("option:selected").val()];
			$("select[name='carXl']").empty();
			$("select[name='carXl']").append($("<option value='-2' class='selectOptionDefault'>").text("请选择--"));
			if(list!=null){
				$.each(list,function(key,value){
					$("select[name='carXl']").append($("<option class='selectOptionDefault'>").val(value.code).text(value.name));
				})
			}
			$("select[name='carXl']").append($("<option class='selectOptionDefault'>").val("-1").text("其他"));
		});
		
		$("select[name='carXl']").append($("<option value='-2' class='selectOptionDefault'>").text("请选择--"));
		$.each(dates.date.CarXl,function(key,value){
			var list = carXl_list[value.brandCode];
			if(list==undefined){
				list = (carXl_list[value.brandCode] = [value]);
			}else{
				list.push(value);
			}
			var option = $("<option class='selectOptionDefault'>").val(value.code).text(value.name);
			if(selectXlCode!=null&&value.code==selectXlCode){
				option.attr("selected",true);
			}
			$("select[name='carXl']").append(option);
		});
		$("select[name='carXl']").append($("<option class='selectOptionDefault'>").val("-1").text("其他"));
	});
}

Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

