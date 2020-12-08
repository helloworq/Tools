package com.zlutil.tools.toolpackage.WebSocket;


import com.zlutil.tools.toolpackage.JavaBasic.MyIO.MyIOUtil;
import lombok.SneakyThrows;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtil {

    //静态方法确保每个类生成的对象都只有一个map，ConcurrentHashMap确保并发安全
    private static ConcurrentHashMap<String, WebSocketHandler> userPool = new ConcurrentHashMap<>();

    /**
     * 将登录用户名以及session存入连接池
     *
     * @param user
     * @param webSocketHandler
     */
    public static void put(String user, WebSocketHandler webSocketHandler) {
        userPool.put(user, webSocketHandler);
    }

    /**
     * 供与客户端之间的文本通信
     *
     * @param user
     * @param message
     * @throws IOException
     */
    public static void sendText(String user, String message) throws IOException {
        if (isUserExist(user)) {
            userPool.get(user).session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 供客户端下载服务器文件,注解用来消除ioexception
     *
     * @param user     用户名，用来获取对应的Session
     * @param filePath 服务器文件路径
     * @throws IOException
     */
    public static void sendFile(String user, String filePath) throws IOException {
        if (isUserExist(user)) {
            File file = new File(filePath);
            InputStream in = new FileInputStream(file);
            OutputStream out = userPool.get(user).session.getBasicRemote().getSendStream();
            //异步将输入流写到输出流供浏览器下载，防止服务器长时间阻塞
            new Thread(() -> {
                try {
                    MyIOUtil.inputStreamWriteToOutputStream(in,out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    static boolean isUserExist(String user) {
        return userPool.keySet().stream().filter(d -> d.equals(user)).count() != 0;
    }
}
