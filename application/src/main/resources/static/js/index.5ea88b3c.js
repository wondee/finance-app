(function(t){function e(e){for(var r,o,c=e[0],u=e[1],s=e[2],l=0,f=[];l<c.length;l++)o=c[l],Object.prototype.hasOwnProperty.call(i,o)&&i[o]&&f.push(i[o][0]),i[o]=0;for(r in u)Object.prototype.hasOwnProperty.call(u,r)&&(t[r]=u[r]);d&&d(e);while(f.length)f.shift()();return a.push.apply(a,s||[]),n()}function n(){for(var t,e=0;e<a.length;e++){for(var n=a[e],r=!0,o=1;o<n.length;o++){var c=n[o];0!==i[c]&&(r=!1)}r&&(a.splice(e--,1),t=u(u.s=n[0]))}return t}var r={},o={index:0},i={index:0},a=[];function c(t){return u.p+"js/"+({}[t]||t)+"."+{"chunk-8c08309c":"9b472ffc","chunk-2073bb36":"bb98f731","chunk-593c3516":"77476982","chunk-78335625":"535ac06c"}[t]+".js"}function u(e){if(r[e])return r[e].exports;var n=r[e]={i:e,l:!1,exports:{}};return t[e].call(n.exports,n,n.exports,u),n.l=!0,n.exports}u.e=function(t){var e=[],n={"chunk-8c08309c":1,"chunk-593c3516":1,"chunk-78335625":1};o[t]?e.push(o[t]):0!==o[t]&&n[t]&&e.push(o[t]=new Promise((function(e,n){for(var r="css/"+({}[t]||t)+"."+{"chunk-8c08309c":"26da0ea6","chunk-2073bb36":"31d6cfe0","chunk-593c3516":"4154588a","chunk-78335625":"29ac61bb"}[t]+".css",i=u.p+r,a=document.getElementsByTagName("link"),c=0;c<a.length;c++){var s=a[c],l=s.getAttribute("data-href")||s.getAttribute("href");if("stylesheet"===s.rel&&(l===r||l===i))return e()}var f=document.getElementsByTagName("style");for(c=0;c<f.length;c++){s=f[c],l=s.getAttribute("data-href");if(l===r||l===i)return e()}var d=document.createElement("link");d.rel="stylesheet",d.type="text/css",d.onload=e,d.onerror=function(e){var r=e&&e.target&&e.target.src||i,a=new Error("Loading CSS chunk "+t+" failed.\n("+r+")");a.code="CSS_CHUNK_LOAD_FAILED",a.request=r,delete o[t],d.parentNode.removeChild(d),n(a)},d.href=i;var p=document.getElementsByTagName("head")[0];p.appendChild(d)})).then((function(){o[t]=0})));var r=i[t];if(0!==r)if(r)e.push(r[2]);else{var a=new Promise((function(e,n){r=i[t]=[e,n]}));e.push(r[2]=a);var s,l=document.createElement("script");l.charset="utf-8",l.timeout=120,u.nc&&l.setAttribute("nonce",u.nc),l.src=c(t);var f=new Error;s=function(e){l.onerror=l.onload=null,clearTimeout(d);var n=i[t];if(0!==n){if(n){var r=e&&("load"===e.type?"missing":e.type),o=e&&e.target&&e.target.src;f.message="Loading chunk "+t+" failed.\n("+r+": "+o+")",f.name="ChunkLoadError",f.type=r,f.request=o,n[1](f)}i[t]=void 0}};var d=setTimeout((function(){s({type:"timeout",target:l})}),12e4);l.onerror=l.onload=s,document.head.appendChild(l)}return Promise.all(e)},u.m=t,u.c=r,u.d=function(t,e,n){u.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},u.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},u.t=function(t,e){if(1&e&&(t=u(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(u.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var r in t)u.d(n,r,function(e){return t[e]}.bind(null,r));return n},u.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return u.d(e,"a",e),e},u.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},u.p="/",u.oe=function(t){throw console.error(t),t};var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=e,s=s.slice();for(var f=0;f<s.length;f++)e(s[f]);var d=l;a.push([0,"chunk-vendors"]),n()})({0:function(t,e,n){t.exports=n("56d7")},3695:function(t,e,n){"use strict";var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-app",{attrs:{id:"inspire"}},[n("v-navigation-drawer",{attrs:{app:"",clipped:""},model:{value:t.drawer,callback:function(e){t.drawer=e},expression:"drawer"}},[n("v-list",{attrs:{dense:""}},[n("v-list-item",{attrs:{link:"",to:"/"}},[n("v-list-item-action",[n("v-icon",[t._v("fa-chart-line")])],1),n("v-list-item-content",[n("v-list-item-title",[t._v("Überblick")])],1)],1),n("v-list-item",{attrs:{link:"",to:"/fixedcosts"}},[n("v-list-item-action",[n("v-icon",[t._v("fa-money-check-alt")])],1),n("v-list-item-content",[n("v-list-item-title",[t._v("Fixkosten")])],1)],1),n("v-list-item",{attrs:{link:"",to:"/specialcosts"}},[n("v-list-item-action",[n("v-icon",[t._v("fa-money-bill-wave")])],1),n("v-list-item-content",[n("v-list-item-title",[t._v("Sonderkosten")])],1)],1),n("v-list-group",{attrs:{value:""},scopedSlots:t._u([{key:"activator",fn:function(){return[n("v-list-item-title",[t._v("Sonstiges")])]},proxy:!0}])},[n("v-list-item",{attrs:{link:""}},[n("v-list-item-content",[n("v-list-item-title",[t._v("Einstellungen")])],1),n("v-list-item-action",[n("v-icon",[t._v("fa-cog")])],1)],1),n("v-divider"),n("v-list-item",{attrs:{link:""}},[n("v-list-item-content",[n("v-list-item-title",[t._v("Impressum")])],1)],1),n("v-list-item",{attrs:{link:""}},[n("v-list-item-content",[n("v-list-item-title",[t._v("Datenschutzerklärung")])],1)],1)],1)],1)],1),n("v-app-bar",{attrs:{app:"","clipped-left":""}},[n("v-app-bar-nav-icon",{on:{click:function(e){e.stopPropagation(),t.drawer=!t.drawer}}}),n("v-toolbar-title",[t._v("Finanz-App")])],1),n("v-content",[t._t("default")],2),n("v-footer",{attrs:{app:""}},[n("span",[t._v(" © 2019 "),n("a",{attrs:{href:"https://wondee.info"}},[t._v("wondee.info")])])])],1)},o=[],i={data:function(){return{drawer:null}},created:function(){this.$vuetify.theme.dark=!0}},a=i,c=n("2877"),u=n("6544"),s=n.n(u),l=n("7496"),f=n("40dc"),d=n("5bc1"),p=n("a75b"),m=n("ce7e"),v=n("553a"),h=n("132d"),b=n("8860"),g=n("56b0"),y=n("da13"),k=n("1800"),w=n("5d23"),_=n("f774"),j=n("2a7f"),O=Object(c["a"])(a,r,o,!1,null,null,null);e["a"]=O.exports;s()(O,{VApp:l["a"],VAppBar:f["a"],VAppBarNavIcon:d["a"],VContent:p["a"],VDivider:m["a"],VFooter:v["a"],VIcon:h["a"],VList:b["a"],VListGroup:g["a"],VListItem:y["a"],VListItemAction:k["a"],VListItemContent:w["a"],VListItemTitle:w["b"],VNavigationDrawer:_["a"],VToolbarTitle:j["a"]})},"402c":function(t,e,n){"use strict";n("15f5");var r=n("2b0e"),o=n("f309");r["a"].use(o["a"]),e["a"]=new o["a"]({icons:{iconfont:"fa"}})},"543e":function(t,e,n){"use strict";n.d(e,"h",(function(){return a})),n.d(e,"e",(function(){return c})),n.d(e,"c",(function(){return u})),n.d(e,"g",(function(){return f})),n.d(e,"d",(function(){return d})),n.d(e,"k",(function(){return p})),n.d(e,"i",(function(){return m})),n.d(e,"j",(function(){return v})),n.d(e,"l",(function(){return h})),n.d(e,"f",(function(){return g})),n.d(e,"b",(function(){return y})),n.d(e,"a",(function(){return k}));n("99af"),n("4de4"),n("caad"),n("a15b"),n("d81d"),n("b0c0"),n("b64b"),n("d3b7"),n("ac1f"),n("25f0"),n("2532"),n("5319"),n("ddb0"),n("96cf");var r=n("1da1"),o=n("2909"),i=n("3835"),a=function(t){return t.toString().replace(/\B(?=(\d{3})+(?!\d))/g,".")+" €"},c=["Januar","Februar","März","April","Mai","Juni","Juli","August","September","Oktober","November","Dezember"],u=function(t){var e=!(arguments.length>1&&void 0!==arguments[1])||arguments[1],n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"-";if(!t)return n;var r=Object(i["a"])(t,2),o=r[0],a=r[1],u=e&&window.innerWidth<768?a:c[a-1];return"".concat(u," / ").concat(o)},s=function(t){return t.length>2?", ":" und "},l=function(t){return Object(o["a"])(Array(12/t).keys()).map((function(e){return Object(o["a"])(Array(t).keys()).map((function(n){return n*(12/t)+e+1}))})).map((function(t){return t.map((function(t){return c[t-1]})).join(s(t))}))},f=l(4),d=l(2),p=function(t){return f[t-1]},m=function(t){return d[t-1]},v=function(t){return c[t-1]},h=function(t){return t.map((function(t,e){return{text:t,value:e}}))},b=function(t,e){if(t===e)return!0;if(t&&!e||!t&&e)return!1;var n=Object.keys(t),r=Object.keys(e).filter((function(t){return!n.includes(t)}));return 0===r.length&&!n.some((function(n){return t[n]!==e[n]}))},g=function(t){return t?{name:t.name,amount:Math.abs(t.amount),incoming:t.amount>0,fromTo:{from:t.from,to:t.to}}:{name:"",amount:0,incoming:!1,fromTo:{from:null,to:null}}},y=function(t){return{id:t.id,name:t.name,amount:t.amount*(t.incoming?1:-1)}},k=function(t,e,n){return{props:["cost","add"],data:function(){return{form:t(this.cost)}},computed:{changed:function(){return!b(this.form,this.costToForm(this.cost))}},methods:{costToForm:t,formToCost:e,title:function(t){return"".concat(t," ").concat(this.cost&&this.cost.name?"ändern":"hinzufügen")},successMsg:function(t){var e=this;return function(n){return"".concat(t," '").concat(n,"' erfolgreich ").concat(e.cost&&e.cost.name?"geändert":"hinzugefügt")}},saveCost:function(){var t=Object(r["a"])(regeneratorRuntime.mark((function t(){var r,o;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return r=e(this.form),t.next=3,fetch(n,{method:"post",body:JSON.stringify(r),headers:{"Content-Type":"application/json"}});case 3:o=t.sent,window.console.log(o),this.$refs.editform.success();case 6:case"end":return t.stop()}}),t,this)})));function o(){return t.apply(this,arguments)}return o}()}}}},"56d7":function(t,e,n){"use strict";n.r(e);n("4de4"),n("e260"),n("e6cf"),n("cca6"),n("a79d"),n("c4c3");var r=n("2b0e"),o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("layout",[n("router-view")],1)},i=[],a=(n("d3b7"),n("8c4f")),c=n("3695"),u=function(){return Promise.all([n.e("chunk-8c08309c"),n.e("chunk-593c3516")]).then(n.bind(null,"24a5"))},s=function(){return Promise.all([n.e("chunk-8c08309c"),n.e("chunk-78335625")]).then(n.bind(null,"51f0"))},l=function(){return Promise.all([n.e("chunk-8c08309c"),n.e("chunk-2073bb36")]).then(n.bind(null,"4878"))},f=[{path:"/",component:u},{path:"/fixedcosts",component:s},{path:"/specialcosts",component:l}],d=new a["a"]({routes:f}),p={router:d,components:{Layout:c["a"]}},m=p,v=n("2877"),h=Object(v["a"])(m,o,i,!1,null,null,null),b=h.exports,g=n("402c"),y=n("71c9"),k=n("543e");r["a"].use(a["a"]),r["a"].config.productionTip=!1,r["a"].component("loadable-page",y["a"]),r["a"].filter("currency",k["h"]),r["a"].filter("displayMonth",(function(t){var e=t.yearMonth;return Object(k["c"])(e)})),r["a"].filter("displayLongMonth",(function(t){return Object(k["c"])(t,!1)})),new r["a"]({vuetify:g["a"],render:function(t){return t(b)}}).$mount("#app")},"71c9":function(t,e,n){"use strict";n("d3b7"),n("96cf");var r=n("1da1");e["a"]={data:function(){return{loaded:!1}},methods:{fetchData:function(){var t=Object(r["a"])(regeneratorRuntime.mark((function t(e){var n,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,fetch(e);case 2:return n=t.sent,t.next=5,n.json();case 5:return r=t.sent,this.loaded=!0,t.abrupt("return",r);case 8:case"end":return t.stop()}}),t,this)})));function e(e){return t.apply(this,arguments)}return e}()}}},c4c3:function(t,e,n){}});
//# sourceMappingURL=index.5ea88b3c.js.map