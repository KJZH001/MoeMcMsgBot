# EasyBot 
一个基于 mirai-api-http 插件的 Minecraft 与 QQ群互通的插件

+ [MCBBS 介绍](https://www.mcbbs.net/forum.php?mod=viewthread&tid=1175227)
+ [演示视频](https://www.bilibili.com/video/BV1VN41197uU/)

---------------
因为目前国内访问 Github 不太稳定

因此将项目迁移到码云这里

------

**[目录]()**
+ [mirai机器人的部署](#mirai机器人部署)
+ [插件设置](#插件设置)
+ [插件命令和权限](#插件命令和权限)
+ [配置文件](#配置文件)
+ [PlaceholderAPI](#PlaceholderAPI)
+ [多服兼容教程](#多服兼容教程)
+ [常见问题](#常见问题)

### mirai机器人部署
+ #### 前期准备
    本插件需要mirai机器人实现功能 [[mirai 机器人的Github]](https://github.com/mamoe/mirai/) <br/>
    为了方便起见，请使用 [[Mirai-Console-Loader(简称 MCL)]](https://github.com/iTXTech/mirai-console-loader) <br/>
    下载 [[mirai-api-http]](https://github.com/project-mirai/mirai-api-http), 这是一个必须的mirai插件, 不安装此插件，则本插件将无法正常运行。

+ #### 更改mirai插件设置
    启动一次MCL 来生成一些配置文件<br/>
    将 Mirai-api-Http 插件丢进 MCL 生成的 plugins 文件夹内  再启动一次并生成配置<br/>
    修改 .\config\net.mamoe.mirai-api-http\settings.yml 配置
    
```yaml
# mirai-api-http 2.0以下请按此修改
# 部分配置
host: localhost
port: 8080
# 用于身份认证
authKey: 'demoKey'
# 这个是必须要开启的
# 用于实时监听群内消息
enableWebsocket: true
```
```yaml
#mirai-api-http 2.0及以上请按此修改
# 适配器
# 即使用的协议
adapters:
  - http
  - ws

# 开启验证流程
# 公网建议开启
enableVerify: true

# 用于身份验证
verifyKey: 123456

# 需手动添加内容
adapterSettings: {
  ws:
    ## websocket server 监听的本地地址
    ## 一般为 localhost 即可, 如果多网卡等情况，自定设置
    host: localhost
    ## websocket server 监听的端口
    ## 与 http server 可以重复, 由于协议与路径不同, 不会产生冲突
    port: 8080
}
```
到这里 Mirai 机器人的配置就完毕了!  现在你只需要运行 MCL 让它在后台挂着就好。

### 插件设置
+ 插件丢进服务端的 plugins 文件夹, 启动一次来生成配置
+ 修改config.yml

```yaml
#####
# 部分配置
#####

# api-http 的地址
host: localhost:8080
# api-http 中的 authKey 用于验证身份
# EasyBot 1.6 及以上配置项名称修改为 Key
authKey: 123456
# bot 的QQ
botID: 123456789
# bot 默认启用的群号
groupID: 987654321
# 启用bot
# 要进行基本的设置， 默认关闭
enable_Bot: false
```

### 插件命令和权限
+ 命令:

```text
/bot help
/bot on 启用bot
/bot off 禁用bot
/bot reload 重载bot
/bot enable 开启自己的bot
bot disable 关闭自己的bot
/bot verify <code> 验证身份
/bot bind <qq> 绑定qq到游戏帐户
/bot debug 即刻输出一些调试信息供作者参用
```

+ 权限:

```text
bot.reload 重载插件
bot.on 启用bot
bot.off 禁用bot
bot.debug 调试输出信息
```

### 配置文件
+ config.yml

```yaml
# 确保 mirai 安装了 mirai-api-http

# api-http 的地址
host: localhost:8080
# api-http 中的 authKey 用于验证身份
# EasyBot 1.6 及以上配置项名称修改为 Key
authKey: 123456
# bot 的QQ
botID: 123456789
# bot 默认启用的群号
groupID: 987654321
# 启用bot
enable-bot: false

# 验证消息失效时间， 单位: 分钟
time: 5

# QQ号的正则表达式， 用于绑定时检测有没有输入正确的QQ号
regex: '[1-9][0-9]{8,10}'

# 调试模式
# 非必要无需打开
# 否则只会增多无用的日志输出！
DEBUG: false

#消息抓取默认设置
catch:
  # 消息抓取的类型
  # text 为只抓取纯文本类消息
  # multi 为抓取复合消息
  # ## 只有当此选项为 multi 类型时
  # img, at, emoji 等类型的抓取才有效
  type: multi
  # 如果为multi 则下面选项中必须有一个为true
  # 否则将不会向游戏内推送消息e
  # 抓取纯文本消息
  text: true
  # 抓取图片消息
  img: true
  # 抓取at消息
  at: true
```

### PlaceholderAPI

```text
%txt_sender_qq%  最新的消息发送者的QQ号
%txt_sender_name% 最新的消息发送者的群名片
%txt_sender_gameName% 最新的消息发送者的游戏id
%txt_image_id% 最新的图片消息的图片id
%txt_image_url% 最新的图片消息的图片地址
%txt_at_targetID% 最新的消息中被AT的人的qq
%txt_at_targetName% 最新的消息中被AT的人的名字
%txt_at_target_gameName% 最新的消息中被AT的人的游戏名字
%txt_group% bot启用的群号
%txt_botID% bot的QQ号码
```

### 多服兼容教程
+ 将 EasyBot 放入所有你想同步的服务器中即可
+ 如果你想不同群发送不同服务器的消息请在装了EasyBot的服务器中修改以下配置项:
+ 多个服务器的消息同步到一个群可以将选项修改成同一个群

```yaml
# Bot 启用的群号
groupID: 123456789
```

## 常见问题

-  <font color="#00dd00">游戏里的消息转发不到群里 或者 群里的消息转发不到游戏里</font><br/>
检查一下bot后台是否还在线， 如果在线的话请帖内或者群里反馈(附加 /bot debug 输出的内容)

- <font color="#00dd00">1.5 以后的版本并且搭载 mirai-api-http 2.0 启用插件时会报错 `JSONObject["type"] not found.`</font><br/>
这是一个无法修复的 BUG，不过总体对插件的影响不大，不影响其功能的正常使用