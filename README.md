# EasyBot —— 一个 Minecraft 服务器与 QQ群 消息互通的插件！

+ [MCBBS 介绍](https://www.mcbbs.net/forum.php?mod=viewthread&tid=1175227)
+ [演示视频](https://www.bilibili.com/video/BV1VN41197uU/)
+ [更新日志](UpdateLog.md)

### 目录
+ [[插件分类]](#插件分类)
+ [[升级新版本]](#升级新版本)
+ [[插件命令和权限]](#插件命令和权限)
+ [[PAPI变量]](#papi变量)
+ [[目标]](#目标)
+ [[构建]](#构建插件)
+ [[常见问题]](#常见问题)
    + [[变量名无法正常显示]](#变量名无法正常显示)
    + [[一定要绑定才可使用本插件吗？]](#一定要绑定才可使用本插件吗？)
    + [[关于滑块验证]](#关于滑块验证)
    + [[关于提问/寻求帮助]](#关于提问/寻求帮助)

---
### 插件分类
|类型|说明|备注|
|---|---|---|
|EasyBot-API|提供的标准开发API|[[开发示例]](https://gitee.com/ed3/easyBot_Reloaded/blob/master/doc/API.md) & [[JavaDoc(施工中)]]()|
|Plugin_withMirai-api-http|使用mirai-api-http作为媒介来进行消息互通的插件|[[详情]](https://gitee.com/ed3/easyBot_Reloaded/blob/master/doc/plugin_withMirai-api-http.md)|
|...待开发中(咕咕咕)|to be continued...|未完待续...|

### 升级新版本
如果你想从 1.x 升级到 2.0你需要做以下事情：

+ 你需要将 `lang.yml` 手动删除，以防止报错。
+ 2.0 及以上版本的插件启动时会对原有的数据进行自动迁移，您无需担心升级新版本数据丢失的问题。迁移后，旧版本的文件将会被移至 `migrated` 文件夹内。**在数据迁移完毕后，你需要重新启动一次服务器来加载迁移后的数据。**

### 插件命令和权限
+ 命令:

```text
/bot help
/bot on 全局启用对群内的消息转发
/bot off 全局关闭对群内的消息转发
/bot reload 重载bot
/bot enable 打开对自己群内的消息转发
/bot disable 关闭对自己群内的消息转发
/bot verify <code> 验证身份
/bot bind <qq> 绑定qq到游戏帐户
/bot debug 即刻输出一些调试信息供作者参用

-------------------------

 以下是 2.0 版本及以上特有的 
 
/bot init 初始化 Bot， 用户可根据引导配置本插件
/bot migrate 对 2.0 版本以下的数据进行迁移

/bot bindChange|bc <之前绑定的qq> <新绑定的qq> 修改绑定的QQ
```

+ 权限:

```text
bot.reload 重载插件
bot.debug 调试输出信息
```

### PAPI变量
**2.0 版本与 1.x 版本的papi变量有较大差距，如果你想查看旧版本的变量请点击** [这里](https://gitee.com/ed3/easyBot_Reloaded/wikis/EasyBot%20%E7%9A%84%E6%97%A7%E7%89%88%20PlaceholderAPI%20%E5%8F%98%E9%87%8F)

```text
%txt_senderQQ%  最新的消息发送者的QQ号
%txt_senderGroupID% 最新消息发送者所在的群号码
%txt_senderGroupName% 消息发送者所在的群名称
%txt_senderNick% 最新的消息发送者的群名片
%txt_senderGameName% 最新的消息发送者的游戏id
%txt_imageID% 最新的图片消息的图片id
%txt_imageURL% 最新的图片消息的图片地址
%txt_atTargetID% 最新的消息中被AT的人的qq
%txt_atTargetNick% 最新的消息中被AT的人的名字
%txt_atTargetGameName% 最新的消息中被AT的人的游戏名字
%txt_sendTime% 消息的发送时间, 时间戳形式
%txt_sendTimeF% 消息的发送时间，格式化的形式
%txt_faceName% 表情的名称
%txt_faceID% 表情的id
%txt_msgID% 消息的id
%txt_group_<int>% bot启用的群号, <int> 代表第几个启用的群，从 0 开始，
%txt_botID% bot的QQ号码
```

### 目标
把这个插件做的功能强大的同时又易于上手。（**当然得用心看文档啊喂！！！！！不看文档神仙也救不来你**。)

### 构建插件
克隆仓库到本地然后文件夹内执行 `./gradlew build`

### 常见问题
- #### **变量名无法正常显示**
    - 请检查服务器是否装上了 `PlaceHolderAPI` 插件，[[下载地址]](https://www.spigotmc.org/resources/placeholderapi.6245/)
    - 为了省事（懒），作者在对于显示玩家名这里直接用了扩展变量，请检查是否装了 `player` 扩展变量。
      如果没有安装，请输入 `/papi ecloud download player` 以安装

- #### **一定要绑定才可使用本插件吗？**
    - 并非，绑定只是为了能够在游戏中区分群内发送消息的人的游戏id，无需绑定也可使用本插件。
      若要将未绑定 qq 的内容显示成为群员的群名片，则仅需将 `lang.yml` 中的 `unBound_QQ.text` 内容换成 `%txt_sender_name%` 这个变量即可。

- #### **关于滑块验证**
    - 请使用工具(手机app): [[滑动验证助手(Github地址)]](https://github.com/mzdluo123/TxCaptchaHelper) <br/>
      考虑到部分用户无法正常访问Github， 故贴上[[网盘]](https://wwr.lanzoui.com/ivUoirq79yd) ，密码:5cyj<br/>
      有条件的还是尽量选择 Github 并给个 Star 吧，这也是对作者的一份支持嘛(也别忘了我啊[doge])

- #### **关于提问/寻求帮助**
    - 有任何问题欢迎开 `issue` / [[加群提问]](https://jq.qq.com/?_wv=1027&k=luSrM89l) ，为了高效率的解决问题，请你在**确保读完所有文档内容**之后进行提问。同时需要您尽可能提供以下内容:
  
1. 您服务器核心的版本，示例：`Spigot-1.12.2` / `paper 1.12`
2. 您目前使用的本插件的版本，示例： `EasyBot-2.0-B1`/ `2.0-B1` ...
3. 提供 `Bot` 的 `DEBUG` 输出日志或者提供完整的服务器日志。同时您还可以提供 `/bot debug` 的输出内容供作者作为参考。
4. 如果**有报错内容**请一并截图提供。
