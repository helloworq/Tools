/*
package com.zlutil.tools.toolpackage.WebSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

*/
/**
 * 每次刷新前端页面session的id将被改变
 * 每次启动项目都会重置session的id
 *//*

@Slf4j
@ServerEndpoint(value = "/websocketTest/{userId}")
@Component//此注解必须加上
public class WebSocketHandler {
    //用户标识
    private String deviceCode;
    //会话标识
    private Session session;
    //静态方法确保每个类生成的对象都只有一个map，ConcurrentHashMap确保并发安全
    private static ConcurrentHashMap<WebSocketHandler, String> deviceConnections = new ConcurrentHashMap<>();

    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("userId") String deviceCode, Session session) {
        log.info("注意到新连接");
    }

    //关闭时执行
    @OnClose
    public void onClose(){
        sendToAll(this.session.getId() + " 断开连接 ！");
        //断开连接触发
        boolean deviceConnections = AppWebSocketUtil.removeDeviceConnections(this);
        if (deviceConnections){
            System.out.println(this.session.getId()+ " 断开连接成功 ！");
        }else {
            System.out.println(this.session.getId()+ " 连接已经失效 ！");
        }
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String s) throws IOException {
        if (null != s && s.startsWith("online")) {
            String[] msg = s.split(",", 2);
            AppWebSocketUtil.addDeviceConnections(msg[1], this); //接入到连接池

            sendToAll(this.session.getId() + " 连接成功 ！");
            System.out.println("HostAddress: " + this.session.getId() + " 连接成功");
        } else if (null != s && s.startsWith("offline")) {
            AppWebSocketUtil.removeDeviceConnections(this); //从连接池中移除

            sendToAll(this.session.getId() + " 断开连接 ！");
            //断开连接触发
            boolean deviceConnections = AppWebSocketUtil.removeDeviceConnections(this);
            if (deviceConnections) {
                System.out.println(this.session.getId() + " 断开连接成功 ！");
            } else {
                System.out.println(this.session.getId() + " 连接已经失效 ！");
            }
        } else {
            String[] msg = s.split("@", 2);//以@为分隔符把字符串分为xxx和xxxxx两部分,msg[0]表示发送至的用户名，all则表示发给所有人
            if (msg[0].equals("all")) {
                AppWebSocketUtil.sendMessageToOnlineAllDevice(msg[1]);
            } else {
                //指定用户发送消息
                sendMessageToUser(msg[0], msg[1]);
            }
        }
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
        deviceConnections.keySet()
                .stream()
                .forEach(client -> {
                    try {
                        client.send(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    //发送消息给指定连接者
    private void sendMessageToUser(String deviceCode, String message) throws IOException {
        //获取在线用户的连接
        WebSocketHandler conn = AppWebSocketUtil.getWebSocketByDevice(deviceCode);
        if (null != conn) {
            String deviceCodeMessage = "接收消息为：【 " + message + " 】";
            //向在线特定用户发送消息
            AppWebSocketUtil.sendMessageToOnlineDevice(conn, deviceCodeMessage);
            //同时发送消息给当前用户
            String currentDeviceCode = "您发送的信息为：【 " + message + " 】";
            AppWebSocketUtil.sendMessageToOnlineDevice(this, currentDeviceCode);
        } else {
            AppWebSocketUtil.sendMessageToOnlineDevice(this, "[" + deviceCode + "] 设备不在线,请您稍后发送！");
            System.out.println("[" + deviceCode + "] 设备不在线,请您稍后发送！");
        }
    }
}*/
