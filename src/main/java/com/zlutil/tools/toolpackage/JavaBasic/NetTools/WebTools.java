package com.zlutil.tools.toolpackage.JavaBasic.NetTools;

import com.zlutil.tools.toolpackage.JavaBasic.MyIO.MyIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebTools implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    /**
     * 返回项目的ip+port，避免手动写死
     * @return
     */
    public String getUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://"+address.getHostAddress() +":"+this.serverPort;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

    public String getCookie(Cookie[] cookies, String key){
        if (cookies!=null){
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals(key)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}