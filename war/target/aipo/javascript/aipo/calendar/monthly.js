dojo.provide("aipo.calendar.monthly");aipo.calendar.monthly_calendar_data={selected_month:"",selected_day:"",next_month:"",prev_month:"",json_url:"",portlet_id:"",oneday_link:""};aipo.calendar.showMonthlyCalendar=function(b){var a=aipo.calendar.monthly_calendar_data.json_url;a+="&monthly_calendar_month="+b;aipo.calendar.createMonthlyCalendar(a)};aipo.calendar.showNextMonthlyCalendar=function(){aipo.calendar.showMonthlyCalendar(aipo.calendar.monthly_calendar_data.next_month)};aipo.calendar.showPreviousMonthlyCalendar=function(){aipo.calendar.showMonthlyCalendar(aipo.calendar.monthly_calendar_data.prev_month)};aipo.calendar.initMonthlyCalendar=function(d,c,b,f,a){var e=aipo.calendar.monthly_calendar_data;e.portlet_id=d;e.json_url=c;e.oneday_link=b;e.selected_month=f;e.selected_day=a;if(ptConfig[e.portlet_id].xhrUrl!=c){ptConfig[e.portlet_id].xhrUrl=c;dojo.xhrGet({portletId:e.portlet_id,url:c,encoding:"utf-8",handleAs:"json-comment-filtered",load:function(i,h){ptConfig[e.portlet_id].xhrUrl="";var g=i;e.next_month=g.next_month;e.prev_month=g.prev_month}})}};aipo.calendar.reloadMonthlyCalendar=function(){aipo.calendar.createMonthlyCalendar(aipo.calendar.monthly_calendar_data.json_url)};aipo.calendar.createMonthlyCalendar=function(a){var b=aipo.calendar.monthly_calendar_data;if(ptConfig[b.portlet_id].xhrUrl!=a){ptConfig[b.portlet_id].xhrUrl=a;dojo.xhrGet({portletId:b.portlet_id,url:a,encoding:"utf-8",handleAs:"json-comment-filtered",load:function(h,c){ptConfig[b.portlet_id].xhrUrl="";var q=h;if(q.error==1){location.reload()}if(dojo.byId("mc_year").innerText){dojo.byId("mc_year").innerText=q.year;dojo.byId("mc_month").innerText=q.month}else{dojo.byId("mc_year").innerHTML=q.year;dojo.byId("mc_month").innerHTML=q.month}b.next_month=q.next_month;b.prev_month=q.prev_month;var p=dojo.byId("mc_table");if(!aipo.calendar.monthly_calendar_data.is_first){var d=new Array();for(var k=0;k<p.childNodes.length;k++){var f=p.childNodes[k];if(f.className=="monthlyCalendarAutoTr"){d.push(f)}}for(var k=0;k<d.length;k++){p.removeChild(d[k])}for(var k=0;k<q.monthly_container.length;k++){var l=q.monthly_container[k];var e=document.createElement("tr");p.appendChild(e);e.className="monthlyCalendarAutoTr";for(var g=0;g<l.length;g++){var o=l[g];var n=document.createElement("td");e.appendChild(n);if(o.is_holiday){n.className=n.className+" holiday"}else{if(g==0){n.className=n.className+" sunday"}if(g==6){n.className=n.className+" saturday"}}if(o.month!=q.month){n.className=n.className+" out"}if(o.today==q.today){n.className+=" today"}if(o.month==b.selected_month&&o.day==b.selected_day){n.className+=" selected"}var m=document.createElement("a");n.appendChild(m);m.setAttribute("href","javascript:void(0);");m.setAttribute("data-date",o.today);m.setAttribute("data-link",b.oneday_link+"&view_start="+o.today);dojo.query(m).onclick(function(){aipo.schedule.setIndicator(b.portlet_id);aipo.viewPage(this.getAttribute("data-link"),b.portlet_id)});if(m.innerText){m.innerText=o.day}else{m.innerHTML=o.day}}}}aipo.calendar.monthly_calendar_data.is_first=false}})}};