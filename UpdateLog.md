#### 2021-7-20 EasyBot-1.6-Reloaded
+ 适配了 `mirai-api-http` 2.0
+ `config.yml` 中的 `authKey` 选项 改为了 `Key`

#### 2021-7-16 EasyBot-1.5-Reloaded
+ 优化了部分代码
+ 不再提供更新检查
+ 新的未知的BUG(doge)
+ 抛弃了直接给bot发送任意消息的验证方式, 改用了验证码验证

#### 2021-4-3 EasyBot-1.4
+ 修复了 1.3-BETA 版本在1.13及以上服务器报错的BUG
+ 优化部分代码

#### 2021.3.1 EasyBot-1.3
+ 代码优化
+ 事件系统完善
+ 添加更新检查选项
+ 可以自动创建新的配置项了

#### 2021.2.27 EasyBot-1.2
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

#### 2021.2.25 EasyBot-1.1
+ 修复了重载没用的bug
+ 删除了烦人的后台提示
+ 移除了重复代码

#### 2021.2.4 EasyBot-1.0 
+ 本插件的第一个版本