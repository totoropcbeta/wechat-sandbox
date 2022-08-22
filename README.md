### 微信公众号消息推送（java版）
<a href="https://blog.csdn.net/qq_39256196/article/details/126473379?spm=1001.2014.3001.5501">CSDN地址</a>
### 项目结构
```wechatTemplate					
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
