package com.zlutil.tools.toolpackage.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WebSocketController{

    @RequestMapping("/downloadFile")
    public void downloadFile(@RequestParam("user")String user,
                     @RequestParam("filePath")String filePath) throws Exception {
        WebSocketUtil.sendFile(user,"C:\\Users\\12733\\Desktop\\"+filePath);
    }

    @RequestMapping("/sendMessage")
    public void sendMessage(@RequestParam("user")String user,
                     @RequestParam("message")String message) throws Exception {
        WebSocketUtil.sendText(user,message);
    }
}
