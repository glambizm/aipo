dojo.provide("aipo.exttimecard");dojo.require("aimluck.widget.Contentpane");dojo.require("aipo.widget.DropdownDatepicker");dojo.require("dojo.string");dojo.require("aimluck.io");dojo.requireLocalization("aipo","locale");aipo.exttimecard.onReceiveMessage=function(c){if(!c){var a=dijit.byId("modalDialog");if(a){a.hide()}aipo.portletReload("exttimecard")}if(dojo.byId("messageDiv")){dojo.byId("messageDiv").innerHTML=c}var b=document.getElementById("modalDialog");if(b&&c!=""){var d=document.getElementById("wrapper");d.style.minHeight=b.clientHeight+"px"}};aipo.exttimecard.onListReceiveMessage=function(b){if(!b){var a=dijit.byId("modalDialog");if(a){a.hide()}aipo.portletReload("exttimecard")}if(dojo.byId("exttimecardmessageDiv")){dojo.byId("exttimecardmessageDiv").innerHTML=b}};aipo.exttimecard.removeHiddenValue=function(b,a){if(b[a]&&document.getElementsByName(a).item(0)){b.removeChild(b[a])}};aipo.exttimecard.addHiddenValue=function(b,a,d){if(b[a]&&document.getElementsByName(a).item(0)){b[a].value=d}else{var c=document.createElement("input");c.type="hidden";c.name=a;c.value=d;b.appendChild(c)}};aipo.exttimecard.addYearMonthDayHiddenValue=function(b,a){var d=a+"_hour";var f=a+"_minute";var j=a+"_year";var e=a+"_month";var c=a+"_day";if(b[d].value!="-1"&&b[f].value!="-1"){var h=b.punch_date_year.value;var g=b.punch_date_month.value;var i=b.punch_date_day.value;aipo.exttimecard.addHiddenValue(b,j,h);aipo.exttimecard.addHiddenValue(b,e,g);aipo.exttimecard.addHiddenValue(b,c,i)}else{aipo.exttimecard.removeHiddenValue(b,j);aipo.exttimecard.removeHiddenValue(b,e);aipo.exttimecard.removeHiddenValue(b,c)}};aipo.exttimecard.onSubmit=function(a){aipo.exttimecard.addYearMonthDayHiddenValue(a,"clock_in_time");aipo.exttimecard.addYearMonthDayHiddenValue(a,"clock_out_time");aipo.exttimecard.addYearMonthDayHiddenValue(a,"outgoing_time1");aipo.exttimecard.addYearMonthDayHiddenValue(a,"outgoing_time2");aipo.exttimecard.addYearMonthDayHiddenValue(a,"outgoing_time3");aipo.exttimecard.addYearMonthDayHiddenValue(a,"outgoing_time4");aipo.exttimecard.addYearMonthDayHiddenValue(a,"outgoing_time5");aipo.exttimecard.addYearMonthDayHiddenValue(a,"comeback_time1");aipo.exttimecard.addYearMonthDayHiddenValue(a,"comeback_time2");aipo.exttimecard.addYearMonthDayHiddenValue(a,"comeback_time3");aipo.exttimecard.addYearMonthDayHiddenValue(a,"comeback_time4");aipo.exttimecard.addYearMonthDayHiddenValue(a,"comeback_time5")};aipo.exttimecard.displayOutCome=function(b){var d="";var c=null;var a=1;for(a=1;a<=5;a++){if(a==5){dojo.byId("plus").style.display="none"}d="rest_num"+a;c=dojo.byId(d);if(c!=null&&c.style.display=="none"){c.style.display="block";break}}aipo.exttimecard.setRestNum()};aipo.exttimecard.displayBox=function(a){obj=dojo.byId(a);if(obj!=null){obj.style.display=""}};aipo.exttimecard.hideOutCome=function(a){var b=a.id;if(b=="minus1"){aipo.exttimecard.moveDataOutCome(1);aipo.exttimecard.hideOutComeBox()}else{if(b=="minus2"){aipo.exttimecard.moveDataOutCome(2);aipo.exttimecard.hideOutComeBox()}else{if(b=="minus3"){aipo.exttimecard.moveDataOutCome(3);aipo.exttimecard.hideOutComeBox()}else{if(b=="minus4"){aipo.exttimecard.moveDataOutCome(4);aipo.exttimecard.hideOutComeBox()}else{if(b=="minus5"){aipo.exttimecard.hideOutComeBox()}}}}}dojo.byId("plus").style.display="block";aipo.exttimecard.setRestNum()};aipo.exttimecard.moveDataOutCome=function(a){var b=a;for(b;b<=4;b++){var d=b+1;var c=b;dojo.byId("outgoing_time"+c+"_hour").selectedIndex=dojo.byId("outgoing_time"+d+"_hour").selectedIndex;dojo.byId("outgoing_time"+c+"_minute").selectedIndex=dojo.byId("outgoing_time"+d+"_minute").selectedIndex;dojo.byId("comeback_time"+c+"_hour").selectedIndex=dojo.byId("comeback_time"+d+"_hour").selectedIndex;dojo.byId("comeback_time"+c+"_minute").selectedIndex=dojo.byId("comeback_time"+d+"_minute").selectedIndex}dojo.byId("outgoing_time"+5+"_hour").selectedIndex=0;dojo.byId("outgoing_time"+5+"_minute").selectedIndex=0;dojo.byId("comeback_time"+5+"_hour").selectedIndex=0;dojo.byId("comeback_time"+5+"_minute").selectedIndex=0};aipo.exttimecard.hideOutComeBox=function(){var c="";var b=null;var a=5;for(a;a>=1;a--){c="rest_num"+a;b=dojo.byId(c);if(b!=null&&b.style.display!="none"){b.style.display="none";break}}};aipo.exttimecard.setRestNum=function(){var b=0;for(var a=1;a<=5;a++){var d="rest_num"+a;var c=dojo.byId(d);if(c!=null&&c.style.display!="none"){b++}}dojo.byId("rest_num").value=b};aipo.exttimecard.hideBox=function(a){obj=dojo.byId(a);if(obj!=null){obj.style.display="none"}};aipo.exttimecard.hideDialog=function(){var a=dijit.byId("modalDialog");if(a){a.hide()}aipo.portletReload("exttimecard")};aipo.exttimecard.hideTimeBox=function(){aipo.exttimecard.hideBox("clock_time_box");aipo.exttimecard.hideBox("outgoing_comeback_box")};aipo.exttimecard.displayTimeBox=function(){aipo.exttimecard.displayBox("clock_time_box");aipo.exttimecard.displayBox("outgoing_comeback_box")};aipo.exttimecard.submit=function(c,a,b,f){aimluck.io.disableForm(c,true);var e=dojo.byId(a+b);if(e){dojo.style(e,"display","")}try{dojo.xhrPost({url:c.action,timeout:30000,form:c,encoding:"utf-8",handleAs:"json-comment-filtered",headers:{X_REQUESTED_WITH:"XMLHttpRequest"},load:function(h,g){var i="";if(dojo.isArray(h)&&h.length>0){if(h[0]=="PermissionError"){i+="<ul>";i+="<li><span class='caution'>"+h[1]+"</span></li>";i+="</ul>"}else{i+="<ul>";dojo.forEach(h,function(j){i+="<li><span class='caution'>"+j+"</span></li>"});i+="</ul>"}}f.call(f,i);e=dojo.byId(a+b);if(e){dojo.style(e,"display","none")}if(i!=""){aimluck.io.disableForm(c,false)}},error:function(g){e=dojo.byId(a+b);if(e){e.innerHTML="エラーが発生しました";e.style.paddingLeft="0px";e.style.backgroundImage="none"}}})}catch(d){}return false};