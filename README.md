## 微信公众号消息推送（java版）

前言：专属女朋友的微信推送消息，简单的写个文档。

gitee地址：<a href="">消息推送</a>

### 所用框架及Api

* springboot搭建应用
* 天行数据Api（彩虹屁、早安语句、天气）

### 前置条件

*  申请微信公众号测试号及微信模板配置

  * 申请一个微信公众号测试号。传送门：<a href="https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login">测试号申请</a>

  ​	![image-20220822190708727](C:\Users\86187\AppData\Roaming\Typora\typora-user-images\image-20220822190708727.png)

  * 扫码登陆注册，注册成功后就会生成微信公号的appID和appsecret

    ![image-20220822191636023](C:\Users\86187\AppData\Roaming\Typora\typora-user-images\image-20220822191636023.png)

  * 需要接收消息的人微信扫码关注 测试号二维码，微信给我们返回我们的openid，这个openid在推送时特别重要。

    ![image-20220822191125841](C:\Users\86187\AppData\Roaming\Typora\typora-user-images\image-20220822191125841.png)

  * 新增消息模板，模板中参数内容必须以".DATA"结尾，否则视为保留字，模板保留符号"{{ }}"; 例如{{date.DATA}}

    ![image-20220822191549524](C:\Users\86187\AppData\Roaming\Typora\typora-user-images\image-20220822191549524.png)

    ```tex
    我的模板：
    {{morning.DATA}} 
    {{date.DATA}} {{week.DATA}} 
    城市：{{city.DATA}} 
    天气：{{weather.DATA}} 
    最低气温: {{lowest.DATA}} 
    最高气温: {{highest.DATA}} 
    降雨概率：{{pop.DATA}} 
    今日建议： {{tips.DATA}} 
    今天是我们恋爱的第{{loveDay.DATA}}天 
    距离我的生日还有{{myBirthday.DATA}}天 
    距离崽崽生日还有{{babyBirthday.DATA}}天 
    
    彩虹屁： {{pipi.DATA}}
    ```

    

  * 配置之后就完成了微信公众号的配置

* 注册一个天行数据API的账户，申请一个接口获取一个自己apiKey。

  * 传送门：<a href="https://www.tianapi.com/">天行数据注册</a>

    ![image-20220822192043213](C:\Users\86187\AppData\Roaming\Typora\typora-user-images\image-20220822192043213.png)

  * 我这里用到了3个接口，早安语句、彩虹屁及天气接口，其中天气接口是计次的，在使用完免费次数后扣天豆，具体看接口详细

    ![image-20220822192223357](C:\Users\86187\AppData\Roaming\Typora\typora-user-images\image-20220822192223357.png)

    ![image-20220822192238127](C:\Users\86187\AppData\Roaming\Typora\typora-user-images\image-20220822192238127.png)

### 项目结构

```tex
wechatTemplate					
|———src                          # 程序根目录
│   ├── main                     # 程序源文件目录
    	├── java			     # JAVA源文件目录
    		├── com		         
    			├── wechat  	 
    				├── template 		# 包路径
    					├── config 		# 配置包（微信公众号配置类及天行Api配置类）
    					├── constants 	# 常量包（api地址类及常量类）
    					├── domain 		# 实体类包
    					├── service 	# 接口包
    						├── impl 	# 实现类
    					├── Task 		# 定时任务
    					TemplateApplication #启动类
    	├── resource		     	 # 资源文件夹目录
    		├── application.yml		 # 配置文件（微信的appid及其他配置）
│   ├── test                     	 # 测试源文件目录
        ├── java			    	 # 测试JAVA源文件目录
                ├── com		         
                    ├── wechat  	 
                        ├── template 		# 测试包路径
                        	TemplateApplicationTests #单元测试启动类
```

### 实现逻辑

* 根据微信公众测试号的appID，appsecret调用微信接口文档获取accessToken

  ```java
      @Override
      public String getAccessToken(String appId, String appSecret) {
          String requestUrl = UrlConstant.ACCESS_TOKEN_URL + "appid=" + appId + "&secret=" + appSecret;
          String resp = HttpUtil.get(requestUrl);
          JSONObject result = JSONUtil.parseObj(resp);
          logger.info("获取access_token:" + resp);
          String token = result.getStr("access_token");
          logger.info("token:" + token);
          return token;
      }
  
  ```

  

* 根据accessToken调用微信接口文档获取关注用户，也可写死用户的openId

  ```jav
      @Override
      public List<String> getUserList(String accessToken) {
          String requestUrl =  UrlConstant.GET_USER_LIST+ accessToken;
          String resp = HttpUtil.get(requestUrl);
          JSONObject result = JSONUtil.parseObj(resp);
          logger.info("用户列表:" + resp);
          JSONArray openIdJsonArray = result.getJSONObject("data").getJSONArray("openid");
          List<String> openIds = JSONUtil.toList(openIdJsonArray, String.class);
          return openIds;
      }
  
  ```

* 定时任务推送（我设置的是每天早上7点，可自行调整），根据上面新增的模板格式，填充数据，设置模板id、accessToken、openId发送消息。

  ```java
      @Scheduled(cron="0 0 7 * * ? ")
      private void sendTemplateMsg() throws ParseException {
          //配置及数据
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
          String date = simpleDateFormat.format(new Date());
          String appId = wechatConfig.getAppId();
          String appSecret = wechatConfig.getAppSecret();
          String babyBirthday = wechatConfig.getBabyBirthday();
          String myBirthday = wechatConfig.getMyBirthday();
          String loveDay = wechatConfig.getLoveDay();
          String appKey = tianApiConfig.getAppKey();
          String area = tianApiConfig.getArea();
          //获取微信token
          String token = weiXinService.getAccessToken(appId,appSecret);
          //获取关注用户
          List<String> userList = weiXinService.getUserList(token);
          for (String openId : userList) {
              //发送消息实体
              WechatSendMsgVo sendMsgVo = new WechatSendMsgVo();
              //设置模板id
              sendMsgVo.setTemplate_id(wechatConfig.getTempId());
              //设置接收用户
              sendMsgVo.setTouser(openId);
              Map<String, WechatTemplateVo> map = new HashMap<>();
              //获取早安语句
              String zaoAnInfo = weiXinService.getZaoAnInfo(appKey);
              map.put("morning", new WechatTemplateVo("Baby 早安！"+zaoAnInfo,"#ff6666"));
              //获取天气
              WeatherInfo weatherInfo = weiXinService.getWeatherInfo(appKey, area);
              //日期
              map.put("date", new WechatTemplateVo(weatherInfo.getDate(),null));
              //星期
              map.put("week",new WechatTemplateVo(weatherInfo.getWeek(),null));
              //城市
              map.put("city",new WechatTemplateVo(weatherInfo.getArea(),"#9900ff"));
              //天气
              map.put("weather",new WechatTemplateVo(weatherInfo.getWeather(),"#CD96CD"));
              //最低气温
              map.put("lowest",new WechatTemplateVo(weatherInfo.getLowest(),"#A4D3EE"));
              //最高气温
              map.put("highest",new WechatTemplateVo(weatherInfo.getHighest(),"#CD3333"));
              //降水概率
              map.put("pop",new WechatTemplateVo(weatherInfo.getPop()+"%","#A4D3EE"));
              //今日建议
              map.put("tips",new WechatTemplateVo(weatherInfo.getTips(),"#FF7F24"));
              //相爱天数
              int loveDays = fun(loveDay, date);
              map.put("loveDay",new WechatTemplateVo(loveDays+"","#EE6AA7"));
              //我的生日
              int myDay = fun2(myBirthday, date);
              map.put("myBirthday",new WechatTemplateVo(myDay+"","#EE6AA7"));
              //宝贝生日
              int babyDay = fun2(babyBirthday, date);
              map.put("babyBirthday",new WechatTemplateVo(babyDay+"","#EE6AA7"));
              //彩虹屁
              String caiHongPiInfo = weiXinService.getCaiHongPiInfo(appKey);
              map.put("pipi",new WechatTemplateVo(caiHongPiInfo,"#E066FF"));
  
              sendMsgVo.setData(map);
              JSONObject entries = weiXinService.sendMsg(sendMsgVo,token, openId);
          }
      }
  ```

  

### 配置修改

resource目录下“application.yml”中配置文件修改。

```yaml
#公众号配置
wechat:
  appId: 测试号的appID
  appSecret: 测试号的appSecret
  tempId: 测试号的模板id
  myBirthday: 自己的生日(1998-10-16)
  babyBirthday: 女朋友的生日
  loveDay: 相恋的日子

#彩虹屁接口
tianapi:
  appKey: 天行数据Api的apiKey
  area: 要获取天气的城市


	
```

### 测试

test目录下“TemplateApplicationTests”类，单元测试，可不启动服务，测试代码是否有问题

![image-20220822194308205](C:\Users\86187\AppData\Roaming\Typora\typora-user-images\image-20220822194308205.png)



### 部署

每天定时的话最好部署在服务器上，放在tomcat下启动就好了

