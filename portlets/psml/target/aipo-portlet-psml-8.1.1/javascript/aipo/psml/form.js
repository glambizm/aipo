dojo.provide("aipo.psml");aipo.psml.onReceiveMessage=function(b){if(!b){var a=dijit.byId("modalDialog");if(a){a.hide()}aipo.portletReload("psml")}if(dojo.byId("messageDiv")){dojo.byId("messageDiv").innerHTML=b}};aipo.psml.hideDialog=function(){var a=dijit.byId("modalDialog");if(a){a.hide()}aipo.portletReload("psml")};aipo.psml.beforeSubmit=function(a,b){dojo.byId(a+"-mode").value=b};aipo.psml.onReceiveMessageUpdate=function(b){if(!b){var a=dijit.byId("modalDialog");if(!!a){a.hide()}location.reload()}};