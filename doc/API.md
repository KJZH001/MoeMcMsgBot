## EasyBot-API

+ EasyBot 的 API 开发示例

### 如何使用

 将本插件提供的 API 作为您插件的依赖即可,

 调用时请从 `BotAPI` 获取对应的接口；

### 开发示例

```java
public class testClass {
    /**
     * 获取 Bot 的工具类接口
     */
    IBotUtils ibu = BotAPI.getIbu();

    /**
     * 示例内容
     * 该示例会向群号为 "123456789" 的qq群发送一条测试消息
     * 目前仅支持发送 Bot 启用的群
     */
    public void testFunction() {
        // 获取群，仅支持获取 Bot 启用的群
        IGroup group = ibu.getGroup(123456789);
        
        // 建立一个消息段
        // group.id -> 群号码
        // 0L -> 通过群发送临时消息时选定的对象的qq
        // 如果是普通的群消息直接填示例内容即可。
        MessageSection section = new MessageSection(group.id(), 0L);
        
        // 建立一条消息链
        MessageChain chain = new MessageChain().addPlain("这是测试消息");
        
        // 将消息链放入 section 中
        section.addMessageChain(chain);
        
        // 发送这条消息
        group.sendMessage(section);
    }
}
```