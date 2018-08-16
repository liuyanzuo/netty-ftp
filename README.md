# 主要功能
- FTP用户登录：支持anonymous登录，服务端白名单配置
- FTP标准命令
- FTP被动和主动模式传输数据

# 用户登录交互

```sequence
User -> Netty:tcp three handshake
Netty -> ActiveReplyHandler:channelActive
ActiveReplyHandler->ActiveReplyHandler:create state machine
User -> Netty:[USER anonymous]
Netty -> LineBasedFrameDecoder:line decode
LineBasedFrameDecoder -> CommandSplitHandler:command split
CommandSplitHandler -> LoginHandler:update state
LoginHandler --> Netty:
Netty --> User:[331 User name ok]
User --> Netty:[PASS XXX]
Netty -> LineBasedFrameDecoder:line decode
LineBasedFrameDecoder -> CommandSplitHandler:command split
CommandSplitHandler -> LoginHandler:check psw
LoginHandler --> Netty:
Netty --> User:[230 user logged]
```

FTP协议是基于文本（标准ASCII)的协议，以`\r\n`作为其结束符，命令与参数间使用`space`作为分隔符，故而这里使用Netty原有的`LineBasedFrameDecoder`进行解码，然后经过自定义的`CommandSplitHandler`对命令进行解析，拆分为`Command`和`Param`两部分。

`LoginHandler`作为服务开始的第一级流水线，提供用户的登录逻辑、用户状态检查逻辑。