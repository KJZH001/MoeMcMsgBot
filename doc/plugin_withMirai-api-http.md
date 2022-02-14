# EasyBot-Plugin_withMirai-api-http

+ 使用 `mirai-api-http` 对接的 EasyBot 插件
+ 该版本插件是 `EasyBot` 的最初诞生的版本
+ 该版本适用于独立主机类型的 Minecraft 服务器，可以做到多服兼容

**[目录]()**
+ [Mirai机器人部署](#mirai机器人的部署)
  + [前期准备](#前期准备)
  + [更改api-http的设置](#更改api-http的设置)
+ [服务器插件设置](#服务器插件设置)
+ [配置文件](#配置文件)
+ [多服兼容教程](#多服兼容教程)
+ [常见问题](#常见问题)
  - [消息无法转发](#消息无法转发)
  - [加载插件时会报错](#加载插件时会报错)
  - [游戏内发送消息时过段时间才能看到](#游戏内发送消息时过段时间才能看到)

---

### 使用说明
### Mirai机器人的部署

+ #### 前期准备
  + 本插件需要mirai机器人实现功能，所以为了方便起见，请使用 [Mirai-Console-Loader(简称 MCL)](https://github.com/iTXTech/mirai-console-loader) ，这个是一个 `模块化、轻量级且支持完全自定义的 mirai 加载器。`
  + 下载 [mirai-api-http](https://github.com/project-mirai/mirai-api-http), 这是一个必须的mirai插件, 不安装此插件，则本插件将无法正常运行。

> ps: 不要告诉我你不会下载

+ #### 更改api-http的设置

  启动一次 `MCL` 来生成一些配置文件，之后输入 `stop` 来关掉它。
 
  将 `Mirai-api-Http` 插件丢进 `MCL` 生成的 `plugins` 文件夹内，

  然后再启动一次并生成插件的配置，修改 `.\config\net.mamoe.mirai-api-http\settings.yml` 。请按以下内容修改。

```yaml
####
# NOTICE: 仅列出必须修改项目，其余请根据需要自行修改。
####

# 适配器
# 即使用的协议
adapters:
  - http
  - ws

# 开启验证流程
enableVerify: true

# 用于身份验证的Key
verifyKey: 123456

# 需手动添加内容
adapterSettings: {
        # 配置 ws 的地址和端口
        # port 和 host要求和 http 端口一致
        #
        # http 默认 port 为 8080
        # host 为 localhost
        # 该内容需手动添加
  ws:
    host: localhost
    port: 8080
}
```

 这样机器人这边就配置完毕了！现在你只需要重启一次 `MCL` 然后登陆 Bot 的 QQ 就可以了

 登录的指令为 `login <账号> <密码>` 在 MCL 控制台输入

---

### 服务端的部署

  + 将插件丢进服务端的 plugins 文件夹里，启动服务器来生成配置。
  + 修改 config.yml

```yaml
# api-http 的地址
# 默认为 localhost:8080
# 该地址要求 MCL 与 服务端 同在一个服务器中
host: localhost:8080
# api-http 中的 verifyKey 的内容
Key: 123456
# bot 的 QQ
botID: 123456789

# bot 默认启用的群号
# 可以为多个群
groupID:
  - 987654321
  - 123456789
# 启用bot
# 因为首次启动需要配置以上内容所以默认关闭
enableBot: false
```
然后运行指令 `/bot reload` 即可重新加载本插件的配置。

### 配置文件
```yaml
#=====+=====+=====+=====+=====+=====+=====+
# ${desc}-${version}
#
# 确保 mirai 机器人安装了 mirai-api-http
# 插件兼容 mirai-api-http 2.X 版本
#=====+=====+=====+=====+=====+=====+=====+
# DO NOT CHANGE THIS
# 不要修改此选项
version: ${priority}
# 启用更新检查
updateCheck: false

# api-http 的地址
host: localhost:8080
# api-http 中的 authKey 用于验证身份
Key: 123456
# bot 的QQ
botID: 123456789
# bot 默认启用的群号
# 可以为多个群
groupID:
  - 987654321
  - 123456789

# 启用bot
enableBot: false

# 游戏消息转发到群里的间隔
# 防止莫名其妙的转发不了游戏内的消息
# 单位为 tick, 每 20tick 为 1s
sendDelay: 10

# 验证消息失效时间， 单位: 分钟
codeExpireTime: 5
# 发送验证消息冷却时间，单位：分钟
codeCoolDown: 1

# 时间格式
# 用于展示群消息发送时间
timeFormat: yyyy.MM.dd HH:mm:ss

# 调试模式
# 非必要无需打开
# 否则只会增多无用的日志输出！
DEBUG: false
```

### 常见问题

- #### **消息无法转发**
  - 检查机器人账号是否登录
  - 检查本插件配置中的 `Key` 是否填写正确
  - 检查本插件配置中的 `host` 是否填写正确
  - 检查本插件配置中的 `groupID` 是填写否正确
  - 检查本插件配置中的 `enable_Bot` 是否为 `true`
  - 检查本插件配置中的 `botID` 是否为机器人登录的账号
  - 检查一下bot后台是否还在线， 如果在线的话请帖内或者群里反馈(附加 `/bot debug` 输出的内容)

- #### **加载插件时会报错**
  - 该 BUG 已于 2.0 解决
  - 插件版本于 1.5 - 1.7 之间：其具体内容为 `JSONObject["type"] not found.`<br/>
  - 插件版本于 1.8 及以上：其具体内容为 `java.lang.NullPointerException` <br/>

  该Bug是因为使用 `mirai-api-http 2.0` 导致的，总体对插件的影响不大。不影响其功能的正常使用，如果此报错内容并不是在加载插件时发生的，请将 `DEBUG` 信息连同报错内容一并反馈给作者,
  方式可通过开 `issue` 或者群内提问。

- #### **游戏内发送消息时过段时间才能看到**
  - 作者我也遇到过，经测试，这个很可能是机器人本身的问题。与本插件无关。<br/>
    该问题出现在转发群消息的过程中，且发生此问题时游戏内消息无法转发到群中。经查阅 `issue` 于 `EasyBot-1.7` 尝试避免此BUG<br/>
    如果确认出现此BUG，请使用常用的设备登录BOT的QQ，在对应群内发送任意消息即可解决，如果不能解决，则尝试开关设备锁。