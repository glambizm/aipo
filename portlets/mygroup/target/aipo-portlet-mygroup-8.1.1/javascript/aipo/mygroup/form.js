dojo.provide("aipo.mygroup");dojo.require("aipo.widget.MemberNormalSelectList");dojo.require("aipo.widget.GroupNormalSelectList");aipo.mygroup.onLoadMygroupDialog=function(f){var c=dijit.byId("membernormalselect");if(c){var a=dojo.byId("init_memberlist");var d;var b=a.options;if(b.length==1&&b[0].value==""){return}for(d=0;d<b.length;d++){c.addOptionSync(b[d].value,b[d].text,true)}}var e=dijit.byId("facilityselect");if(e){var a=dojo.byId("init_facilitylist");var d;var b=a.options;if(b.length==1&&b[0].value==""){return}for(d=0;d<b.length;d++){e.addOptionSync(b[d].value,b[d].text,true)}}dojo.byId("group_alias_name").focus()};aipo.mygroup.onReceiveMessage=function(b){if(!b){var a=dijit.byId("modalDialog");if(a){a.hide()}aipo.portletReload("mygroup")}if(dojo.byId("messageDiv")){dojo.byId("messageDiv").innerHTML=b}};