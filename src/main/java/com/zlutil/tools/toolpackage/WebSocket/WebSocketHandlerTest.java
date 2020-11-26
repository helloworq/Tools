package com.zlutil.tools.toolpackage.WebSocket;


import com.zlutil.tools.toolpackage.Collectionutil.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 每次刷新前端页面session的id将被改变
 * 每次启动项目都会重置session的id
 */
@Slf4j
@ServerEndpoint(value = "/websocketTest/{userId}")
@Component//此注解必须加上
public class WebSocketHandlerTest {
    //用户标识
    private String deviceCode;
    //会话标识
    private Session session;
    //静态方法确保每个类生成的对象都只有一个map，ConcurrentHashMap确保并发安全
    private static ConcurrentHashMap<String, WebSocketHandlerTest> userPool = new ConcurrentHashMap<>();

    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("userId") String deviceCode, Session session) {
        ListUtil.stringToList("test");
        this.deviceCode = deviceCode;
        this.session = session;
        log.info("新连接用户id: {}, 当前session id {}", deviceCode, session.getId());
        userPool.put(deviceCode, this);
    }

    //关闭时执行
    @OnClose
    public void onClose() throws IOException {
        this.session.close();
        userPool.remove(this.deviceCode);
        sendToAll("用户 "+this.session.getId()+" 已断开连接！");
        log.info("连接：{} 关闭", this.deviceCode);
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("收到用户 {} 的消息 {} 当前用户sessionId ", this.deviceCode, message, this.session.getId());
        this.session.getBasicRemote().sendText("服务器消息: 已收到--" + this.deviceCode + "--的消息 "); //回复用户
    }

    //连接错误时执行
    @OnError
    public void onError(Throwable error) {
        log.info("用户id为：{}的连接发送错误", this.deviceCode);
        error.printStackTrace();
    }

    void send(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    //发送消息给所有的连接者
    private void sendToAll(String text) {
        userPool.keySet()
                .stream()
                .forEach(client -> {
                    try {
                        userPool.get(client).send(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    //发送消息给指定连接者
    private void sendMessageToUser(String deviceCode, String message) {
        userPool.keySet()
                .stream()
                .filter(code -> code.equals(deviceCode))
                .forEach(code -> {
                    try {
                        userPool.get(code).send(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}