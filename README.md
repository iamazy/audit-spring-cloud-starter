## Audit-Spring-Cloud-Start

### 1.日志审计组件
#### a. 已实现功能
```
1. 记录所有的Controller下的WebApi操作信息,包含字段如下:
   a. uuid 审计日志的uuid
   b. actor 执行操作的用户,默认为anonymous,若集成到带有Spring Security模块的子系统中则会变成当前已登录的用户
   c. serverIp 服务端Ip
   d. clientIp 客户端请求Ip
   e. url 请求的url,以[GET],[POST]为前缀
   f. action 码猿指定某个web api执行的操作名称信息
   g. startTime 请求开始时间
   h. endTime 请求的结束时间
   i. status web api请求返回的状态码
   j. clazz 执行操作所在的类
   k. method 执行操作所在的方法
   l. args 执行操作方法的参数列表
   m. result 执行操作返回的结果
2. 自定义审计日志的模板
   在resources文件夹中新建audit.hbs文件,在其中书写审计日志的模板,模板语法兼容mustache语法,若无audit.hbs文件,则使用默认模板
   默认模板如下
   =========================={{ startTime }} Start ===================================
   Uuid: {{ uuid }}
   Actor: {{ actor }}
   ServerIp: {{ serverIp }}
   ClientIp: {{ clientIp }}
   Url: {{ url }}
   Action: {{ action }}
   Class: {{ clazz }}
   Method: {{ method }}
   Args: {{#each args }} {{ this }} {{/each}}
   Result: {{ result }}
   Status: {{ status }}
   =========================={{ endTime }} End =====================================
3. 支持注解的方式
   目前默认所有的Controller方法都执行审计操作
   以有注解：
   a. DeIdentify: 隐藏敏感字段的部分内容
      1) left:int 隐藏左边若干字符
      2) right:int 隐藏右边若干字符
      3) fromLeft:int 从左边若干字符起隐藏所有字符
      4) fromRight:int 从右边若干字符起隐藏所有字符
   b. IgnoreAudit: 将停止对该函数，该字段的审计功能
   c. Audit: 对类，函数，参数进行审计
   注: 这些注解可以搭配使用
4. 搭配审计开关
   在application.properties中输入audit.enabled=true,审计功能才会开启，审计功能默认是关闭的
```
#### b.待实现功能
```
1. 剥离Spring Boot框架
2....
```
