if(!dojo._hasResource["aipo.widget.DropdownMemberFacilitypicker"]){dojo._hasResource["aipo.widget.DropdownMemberFacilitypicker"]=true;dojo.provide("aipo.widget.DropdownMemberFacilitypicker");dojo.require("aimluck.widget.Dropdown");dojo.require("aipo.widget.MemberFacilitySelectList");dojo.declare("aipo.widget.DropdownMemberFacilitypicker",[aimluck.widget.Dropdown],{inputWidth:"250px",hiddenId:"",hiddenValue:"",iconURL:"",iconAlt:"",selectId:"",inputId:"",inputValue:"",buttonAddId:"",buttonRemoveId:"",memberFromId:"",memberToId:"",memberFromUrl:"",memberFromOptionKey:"",memberFromOptionValue:"",groupSelectId:"",groupSelectOptionKey:"",groupSelectOptionValue:"",memberGroupUrl:"",changeGroupUrl:"",listWidgetId:"",tmpPortretId:"cinit",templateString:'<div class="dijit dijitLeft dijitInline"\n\tdojoAttachEvent="onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse,onclick:_onDropDownClick,onkeydown:_onDropDownKeydown,onblur:_onDropDownBlur,onkeypress:_onKey"\n\t><div class=\'dijitRight\'>\n\t<span class="" type="${type}"\n\t\tdojoAttachPoint="focusNode,titleNode" waiRole="button" waiState="haspopup-true,labelledby-${id}_label"\n\t\t><span class="" \tdojoAttachPoint="containerNode,popupStateNode"\n\t\tid="${id}_label"></span><select name="${selectId}" id="${selectId}" size="10" multiple="multiple" style="display:none" dojoAttachPoint="selectNode"></select><input type="hidden" id="${hiddenId}" name="${hiddenId}" value="${hiddenValue}" dojoAttachPoint="valueNode" /><span name="${inputId}" id="${inputId}" dojoAttachPoint="inputNode">${inputValue}</span>\n<span id="adduser-${tmpPortretId}" class="small addUser">ユーザー追加</span></div></div>\n',postCreate:function(){var a={widgetId:this.listWidgetId,selectId:this.selectId,inputId:this.inputId,buttonAddId:this.buttonAddId,buttonRemoveId:this.buttonRemoveId,memberFromId:this.memberFromId,memberToId:this.memberToId,memberFromUrl:this.memberFromUrl,memberFromOptionKey:this.memberFromOptionKey,memberFromOptionValue:this.memberFromOptionValue,groupSelectId:this.groupSelectId,groupSelectOptionKey:this.groupSelectOptionKey,groupSelectOptionValue:this.groupSelectOptionValue,memberGroupUrl:this.memberGroupUrl,changeGroupUrl:this.changeGroupUrl,tmpPortretId:this.tmpPortretId};this.listWidgetId="memberfacilitylistwidget-"+this.tmpPortretId;var b=dijit.byId(this.listWidgetId);if(b){dijit.registry.remove(this.listWidgetId)}this.dropDown=new aipo.widget.MemberFacilitySelectList(a,this.listWidgetId);this.inherited(arguments)},removeAllOptions:function(a){var b;if(document.all){var c=a.options;for(b=0;b<c.length;b++){c.remove(b);b-=1}}else{var c=a.options;for(b=0;b<c.length;b++){a.removeChild(c[b]);b-=1}}},addOptionSync:function(e,f,g){var b=dojo.byId(this.memberToId);var a=dojo.byId(this.selectId);if(this.memberLimit!=0&&b.options.length>=this.memberLimit){return}if(document.all){var d=document.createElement("OPTION");d.value=e;d.text=f;d.selected=g;var c=document.createElement("OPTION");c.value=e;c.text=f;c.selected=g;if(b.options.length==1&&b.options[0].value==""){b.options.remove(0);a.options.remove(0)}b.add(d,b.options.length);a.add(c,a.options.length)}else{var d=document.createElement("OPTION");d.value=e;d.text=f;d.selected=g;var c=document.createElement("OPTION");c.value=e;c.text=f;c.selected=g;if(b.options.length==1&&b.options[0].value==""){b.removeChild(b.options[0]);a.removeChild(b.options[0])}b.insertBefore(d,b.options[b.options.length]);a.insertBefore(c,a.options[a.options.length])}this.inputMemberSync()},inputMemberSync:function(){var k=dojo.byId(this.selectId);var h=dojo.byId(this.inputId);var d="";var a=k.options;var e=a.length;if(e<=0){return}for(var c=0;c<e;c++){if(c!=0){d+=" "}var b=c%aipo.calendar.maximum_to;var l=a[c].text.replace(/&/g,"&amp;").replace(/"/g,"&quot;").replace(/</g,"&lt;").replace(/>/g,"&gt;");d+='<span class="dispUser color'+b+'">'+l+"</span>"}var g=(dojo.byId("groupselect-"+this.tmpPortretId).value==dojo.byId("groupselect-defaulturl-"+this.tmpPortretId).value);if(g){var m=dojo.byId("picked_memberlist-"+this.tmpPortretId);if(m){this.dropDown.removeMember(m);var f=m.options;for(var c=0;c<f.length;c++){(function(j,i){j.selected=true})(f[c],c)}this.dropDown.addMember(dojo.byId("member_to-"+this.tmpPortretId),dojo.byId("picked_memberlist-"+this.tmpPortretId))}}h.innerHTML=d},_openDropDown:function(){var e=this.dropDown;var f=e.domNode.style.width;var j=this;var b;var i=window.navigator.userAgent.toLowerCase();if(i.indexOf("chrome")>-1){b=document.body.scrollTop}dijit.popup.open({parent:this,popup:e,around:this.domNode,orient:this.isLeftToRight()?{BL:"TL",BR:"TR",TL:"BL",TR:"BR"}:{BR:"TR",BL:"TL",TR:"BR",TL:"BL"},onExecute:function(){j._closeDropDown(true)},onCancel:function(){j._closeDropDown(true)},onClose:function(){aipo.calendar.populateWeeklySchedule(j.tmpPortretId);e.domNode.style.width=f;j.popupStateNode.removeAttribute("popupActive");this._opened=false}});if(this.domNode.offsetWidth>e.domNode.offsetWidth){var c=null;if(!this.isLeftToRight()){c=e.domNode.parentNode;var a=c.offsetLeft+c.offsetWidth}dojo.marginBox(e.domNode,{w:this.domNode.offsetWidth});if(c){c.style.left=a-this.domNode.offsetWidth+"px"}}this.popupStateNode.setAttribute("popupActive","true");this._opened=true;if(e.focus){e.focus()}if(i.indexOf("chrome")>-1||(dojo.isFF&&(dojo.isFF>=3.6))){var h=this.dropDown.domNode.parentNode;var g=h.style.top.replace("px","");top_new=parseInt(g)+window.scrollY;h.style.top=top_new+"px";if(i.indexOf("chrome")>-1){var d=document.activeElement;document.activeElement.blur();d.focus();top_new=dojo.byId("adduser-"+this.tmpPortretId).getBoundingClientRect().top+document.body.scrollTop+20;h.style.top=top_new+"px";document.body.scrollTop=b}}if(dojo.isIE&&(dojo.isIE==11)){var h=this.dropDown.domNode.parentNode;var g=h.style.top.replace("px","");top_new=parseInt(g)-window.pageYOffset;h.style.top=top_new+"px"}},_onDropDownClick:function(b){var a=dojo.byId("groupselect-"+this.tmpPortretId);if(a&&a.value.indexOf("pickup")==-1){return false}aimluck.widget.Dropdown.prototype._onDropDownClick.call(this,b)}})};