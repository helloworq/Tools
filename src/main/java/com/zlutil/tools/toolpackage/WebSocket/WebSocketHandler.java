package com.zlutil.tools.toolpackage.WebSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 第一次请求open将会带上Session变量，若需带上传入值只能传入pathvarible
 * 后续方法无须传入session，可在方法括号内写上传入值无须填入pathvarible
 * 每次刷新前端页面session的id将被改变
 * 每次启动项目都会重置session的id
 */
@Slf4j
@ServerEndpoint(value = "/websocketTest/")
@Component//此注解必须加上
public class WebSocketHandler {
    //用户标识
    public String deviceCode;
    //会话标识
    public Session session;

    public WebSocketHandler() {
    }

    //连接时执行
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String user) {
        WebSocketUtil.put(user, this);
        log.info("当前用户sessionId, user{}---{}", this.session.getId(), user);
    }

    //关闭时执行
    @OnClose
    public void onClose() throws IOException {
        log.info("连接：{} 关闭", this.session.getId());
    }

    //连接错误时执行
    @OnError
    public void onError(Throwable error) {
        log.info("用户id为：{}的连接发送错误", this.session.getId());
        error.printStackTrace();
    }

}