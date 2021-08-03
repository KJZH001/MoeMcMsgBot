### EasyBot-1.8-Reloaded
#### **此版本对内部代码改动较大，因此该版本往前的附属插件将不会正常工作**
+ 每次重载插件时对 `config` 的 value 进行刷新，防止了一些配置没法加载的BUG
+ 采用了 `fast-json` 作为本插件新的json解析工具
+ 对事件里面的方法进行了调整
+ 因为暂时不会去支持发送好友消息和转发好友消息，将 [me.ed333.easyBot.BOT](https://gitee.com/ed3/easyBot_Reloaded/blob/master/src/main/java/me/ed333/easyBot/BOT.java) 中的 [sendFriendMessage()](https://gitee.com/ed3/easyBot_Reloaded/blob/master/src/main/java/me/ed333/easyBot/BOT.java#L167) 移除
+ 移除了以下事件, 原因：不常用，不想再维护以下事件

```text
BotGroupPermissionChangeEvent
BorJoinGroupEvent
BotLeaveEventActive
BotLeaveEventKick
BotMuteEvent
BotUnmuteEvent

GroupEntranceAnnouncementChangeEvent
GroupNameChangeEvent
```

+ Bot 发送的验证消息现在支持换行了，让排版更美观吧 φ(゜▽゜*)♪<br/>
    - 具体使用方法: 前往 `lang.yml` 中修改 `verify_text`， 在你想要换行的地方写上 `\n` 就好

+ 支持了转发消息的查看，该类型的消息会在游戏中以书本GUI的形式呈现。<br/>
  但很遗憾，以作者现在的技术能力无法做到书本中以悬浮信息的形式显示详细内容<br/>

### 2021-7-23 EasyBot-1.7-Reloaded
+ 修复了某些事件触发时报错的BUG
+ 从根本上解决了消息复制两次的BUG
+ 当 `DEBUG` 选项打开时, 收到消息后不再输出非bot启用的群的消息
+ 支持了游戏转发到群时的自定义前缀, 请在 `lang.yml` 中修改 `groupFormat` 选项
+ 添加了papi变量: 
    - `%txt_sendTime%` 表示消息发送的时间戳
    - `%txt_sendTime_formatted%` 表示消息发送时间，时间格式可在 `config.yml` 中设置: `timeFormat: yyyy.MM.dd HH:mm:ss`
    - `%txt_faceName%` 表示 `face` 表情名称
    - `%txt_faceID%` 表示 `face` 表情的 id
+ 事件更加完善，添加了`发送群聊消息` `发送临时消息` 以及 `发送好友消息` 的方法
+ 消息获取类型新增 `表情消息` 和 `@全体成员`
+ 尝试避免BUG: [游戏内发送消息时过段时间才能看到](https://gitee.com/ed3/easyBot_Reloaded/blob/master/README.md#游戏内发送消息时过段时间才能看到)

### 2021-7-20 EasyBot-1.6-Reloaded
+ 适配了 `mirai-api-http` 2.0
+ `config.yml` 中的 `authKey` 选项 改为了 `Key`

### 2021-7-16 EasyBot-1.5-Reloaded
+ 优化了部分代码
+ 不再提供更新检查
+ 新的未知的BUG(doge)
+ 抛弃了直接给bot发送任意消息的验证方式, 改用了验证码验证

---

### 2021-4-3 EasyBot-1.4
+ 修复了 1.3-BETA 版本在1.13及以上服务器报错的BUG
+ 优化部分代码

### 2021.3.1 EasyBot-1.3
+ 代码优化
+ 事件系统完善
+ 添加更新检查选项
+ 可以自动创建新的配置项了

### 2021.2.27 EasyBot-1.2
+ 修复了游戏内执行重载命令后来自QQ的消息会复制双份的bug
+ 添加了 DEBUG 配置项目
+ 添加了 receive_type 配置

```yaml
# 接收类型
# event 为只接收 bot 的事件
# message 为只接收 bot 收到的消息
# all 为两者都接收
receive_type: message

# 调试模式
# 非必要无需打开
# 否则只会增多无用的日志输出！
DEBUG: false
```

+ 更新了事件(部分)
+ 更新了一个向群里发送纯文本消息的api
+ 更新了新的未知的BUG(doge）

### 2021.2.25 EasyBot-1.1
+ 修复了重载没用的bug
+ 删除了烦人的后台提示
+ 移除了重复代码

### 2021.2.4 EasyBot-1.0 
+ 本插件的第一个版本