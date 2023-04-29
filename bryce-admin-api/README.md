# bryce-wechat

## 国际化
```
url?lang=zh_CN
url?lang=en_US
```
## 一、微信公众号

## 二、微信小程序

### 1、测试号申请

[https://developers.weixin.qq.com/miniprogram/dev/devtools/sandbox.html](https://developers.weixin.qq.com/miniprogram/dev/devtools/sandbox.html)
申请 appId, appSecret

### 2、登录文档

[https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html](https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html)

## 一、引入依赖

```
<!-- 微信 -->
<dependency>
    <groupId>com.github.binarywang</groupId>
    <artifactId>weixin-java-mp</artifactId>
    <version>4.3.3.B</version>
</dependency>
```

## 二、添加配置

```
bryce:
  wechat:
    app-id: wx6cfd85726096b99c
    secret: 804922b0e8969fbb14d8429da40975af

WechatProperties
WechatConfiguration
```

## 三、配置微信

测试号
[https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login)

相关名词

```
URL：进行相关接口开发的URL

TOKEN:验证接口的令牌，可以自己定义（需要与在后台自定义的token进行校验，验证成功后才会配置成功）

JS域名：安全设置，调用微信JS-SDK的时候，域名与此相同。一般调试的话填写映射地址，无http://
```

## 四、应用层进行获取openID以及重定向路由

```

第二个GET请求/wechat/authorize主要是用来重定向路由的，如果你的请求在微信直接访问是会报错的，微信有专门的访问机制，先到微信服务器然后回调你自己的路由才能正确的跳转返回数据。

WxMpservice里面的函数oauth2buildAuthorizationUrl()
第一个参数是你要跳转的路由,也就是回调地址;
第二个参数是作用域范围
,(snsapi_userinfo指的是弹出用户授权页面,获取用户的基本信息,即使用户未关注的情况下,只要用户点击确定就可以获取基本信息;
snsapi_base指的是不跳出授权页面只能获取用到的openid信息)
第三个参数state一般是用户自定义的附加信息,可以加密后进行后续调用验证.(个人理解)
,函数里面的实现都是封装好的,拿来用就可以,也可以你自己去拼接路由

例如:

https://open.weixin.qq.com/connect/oauth2/authorize?appid=你的APPID&redirect_uri=你的回调地址(也就是跳转地址)&response_type=code&scope=选择的作用域&state=如果有附带信息就写没有就是null#wechat_redirect

第二个GET请求返回的结果就是上面的这种格式

```

## 五、请求第二个GET请求

[https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html](https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html)

网页授权流程分为四步：

1、引导用户进入授权页面同意授权，获取code[或扫码获取code]

2、通过 code 换取网页授权access_token（与基础支持中的access_token不同）

3、如果需要，开发者可以刷新网页授权access_token，避免过期

4、通过网页授权access_token和 openid 获取用户基本信息（支持 UnionID 机制）

```
https://2df2-39-71-43-216.jp.ngrok.io/
https://2df2-39-71-43-216.jp.ngrok.io/auth/authorize?state=null&url=https://2df2-39-71-43-216.jp.ngrok.io/auth/openId

https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6cfd85726096b99c&redirect_uri=https%3A%2F%2F2df2-39-71-43-216.jp.ngrok.io%2Fauth%2FopenId&response_type=code&scope=snsapi_base&state=null&connect_redirect=1#wechat_redirect

这里的参数是第一个GET请求的路由，目的就是为了获取openID

发送请求后返回的重定向结果也就是之前处理过并且拼接好的字符串放到微信开发者工具里面进行调试，发送请求后既可以得到redictUrl.(并且会把code以及state携带到重定向后的url中)
```



