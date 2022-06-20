package com.learn.admin.socket.server;

import com.alibaba.fastjson.JSONObject;
import com.learn.admin.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocketServer  服务端
 *
 * @author lijun
 * @date 2021/10/29 14:26
 */
@Slf4j
@Component
@ServerEndpoint("/webSocketServer/{card}")
public class WebSocketServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static final ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**当前登陆人*/
    private String card="";

    /**
     *  建立连接成功
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("card") String card){
        this.session = session;
        this.card=card;
        if(webSocketMap.containsKey(card)){
            webSocketMap.remove(card);
            webSocketMap.put(card,this);
            //加入set中
        }else{
            webSocketMap.put(card,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        System.out.println(webSocketMap);
        log.info("用户连接:"+card+",当前在线人数为:" + getOnlineCount());
//
//        sendMessage("连接成功");
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(){
        if(webSocketMap.containsKey(card)){
            webSocketMap.remove(card);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+card+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 接收客户端消息
     * @param message 消息文本
     */
    @OnMessage
    public void onMessage(String message){
        log.info("当前登录人:"+card);
        //可以群发消息
        //消息保存到数据库、redis
        if(StringUtils.isNotBlank(message)){
            try {
                //解析发送的报文
                JSONObject jsonObject = JSONObject.parseObject(message);
//                jsonObject.put("msg",message);
                String userId = (String)jsonObject.get("userId");
                String msg = (String)jsonObject.get("msg");
                String type = (String)jsonObject.get("type");
                log.info("接收人:"+userId+",报文:"+msg);
                //传送给对应toUserId用户的websocket
                if(StringUtils.isNotBlank(userId)){
                    if("群发".equals(type)){
                        sendInfoAll(userId,msg);
                    }else{
                        sendInfo(userId,msg);
                    }
                }else{
                    throw new ServiceException("请求的userId:"+userId+"不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * 给指定用户 发送消息
     * @param message 消息
     */
    public void sendMessage(String message){
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 给指定用户发送消息
    * @param message 消息
    * @return void
    * @author lijun
    * @date 2021/10/29
    */
    /**
     * 发送自定义消息
     * */
    public void sendInfo(String userId,String message) throws IOException {
        log.info("发送消息到:"+userId+"，报文:"+message);
        webSocketMap.get(userId).sendMessage(message);
//        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
//            webSocketMap.get(userId).sendMessage(message);
//        }else{
//            log.error("用户"+userId+",不在线！");
//        }
    }



    public void sendInfoAll(String cards,String message) throws IOException {
        String[] split = cards.split(",");
        for (String s : split) {
            if(webSocketMap.containsKey(s)){
                sendInfo(s,message);
            }

        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
