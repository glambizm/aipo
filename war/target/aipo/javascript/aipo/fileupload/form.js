dojo.provide("aipo.fileupload");aipo.fileupload.getFolderName=function(){var a=dojo.byId("folderName")};aipo.fileupload.onAddFileInfo=function(e,d,g,f){var a=dojo.byId("attachments_"+f);if(a.nodeName.toLowerCase()=="ul"){aimluck.io.addFileToList(a,d,g)}else{aimluck.io.addOption(a,d,g,false)}dojo.byId("folderName_"+f).value=e;var b=document.getElementById("modalDialog");if(b){var c=document.getElementById("wrapper");c.style.minHeight=b.clientHeight+"px"}};aipo.fileupload.replaceFileInfo=function(d,c,a,e){var b=dojo.byId("attachments_"+e);if(b.nodeName.toLowerCase()=="ul"){aimluck.io.replaceFileToList(b,c,a)}else{aimluck.io.addOption(b,c,a,false)}dojo.byId("folderName_"+e).value=d};aipo.fileupload.openAttachment=function(i,d){var c=430;var b=130;var g=(screen.width-c)/2;var f=(screen.height-b)/2;var e=dojo.byId("attachments_"+d);if(e.nodeName.toLowerCase()=="ul"){var h=e.children.length}else{var h=e.options.length;if(h==1&&e.options[0].value==""){h=0}}var a=dojo.byId("folderName_"+d).value;var j=window.open(i+"&nsize="+h+"&folderName="+a,"attachment_window","left="+g+",top="+f+",width="+c+",height="+b+",resizable=yes,status=yes");j.focus()};aipo.fileupload.ImageDialog;aipo.fileupload.showImageDialog=function(c,a,b){var d=dojo.byId("imageDialog");aipo.fileupload.ImageDialog=dijit.byId("imageDialog");dojo.query(".auiPopup:not(.imgPopup)").addClass("mb_dialoghide");dojo.query("#imageDialog").addClass("mb_dialog");if(!aipo.fileupload.ImageDialog){aipo.fileupload.ImageDialog=new aipo.fileupload.widget.FileuploadViewDialog({widgetId:"imageDialog",_portlet_id:a,_callback:b},"imageDialog")}else{aipo.fileupload.ImageDialog.setCallback(a,b)}if(aipo.fileupload.ImageDialog){aipo.fileupload.ImageDialog.setHref(c);aipo.fileupload.ImageDialog.show()}};aipo.fileupload.hideImageDialog=function(){var a=dijit.byId("imageDialog");if(a){a.hide();var b=dojo.byId("imageDialog");if(b){b.style.height="";b.style.width=""}}};aipo.fileupload.onLoadImage=function(b){dojo.style(b,"display","");dojo.query(".indicatorDialog").style("display","none");var a=dojo.byId("imageDialog");if(dojo.style(b,"max-width")){a.style.width=Math.min(b.width,dojo.style(b,"max-width"))+"px"}else{a.style.width=b.width+"px"}if(dojo.style(b,"max-height")){a.style["min-height"]=Math.min(b.height,dojo.style(b,"max-height"))+"px"}else{a.style["min-height"]=b.height+"px"}aipo.fileupload.ImageDialog._position()};aipo.fileupload.removeFileFromList=function(a,b,c){dojo.style("facephoto_"+c,"display","none");return a.removeChild(b)};aipo.fileupload.YoutubeDialog;aipo.fileupload.showYoutubeDialog=function(c,d,a,b){if(!aipo.fileupload.YoutubeDialog){aipo.fileupload.YoutubeDialog=new aipo.fileupload.widget.YoutubeDialog({widgetId:"YoutubeDialog",_portlet_id:a,_callback:b},"YoutubeDialog")}else{aipo.fileupload.YoutubeDialog.setCallback(a,b)}if(aipo.fileupload.YoutubeDialog){aipo.fileupload.YoutubeDialog.setHref(d);aipo.fileupload.YoutubeDialog.show()}};aipo.fileupload.hideYoutubeDialog=function(){var a=dijit.byId("aipo_fileupload_widget_YoutubeDialog_0");if(a){a.hide()}};aipo.fileupload.onLoadYoutube=function(b){var a=dojo.byId("aipo_fileupload_widget_YoutubeDialog_0");a.style.width=b.width+"px";aipo.fileupload.YoutubeDialog._position();dojo.query("#youtubeDialog").removeClass("preLoadImage")};