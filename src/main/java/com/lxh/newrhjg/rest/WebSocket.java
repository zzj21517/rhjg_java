package com.lxh.newrhjg.rest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
/**
 * WebSocket连接 sessionKey是url中的参数
 */
@ServerEndpoint("/websocket1/{sessionKey}")
public class WebSocket {

    private static final Logger log = Logger.getLogger(WebSocket.class.getName());

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static Map<String, WebSocket> webSockets = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session    可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @param sessionKey url地址参数
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sessionKey") String sessionKey) {

        if (!webSockets.containsKey(sessionKey)) {
            this.session = session;
            webSockets.put(sessionKey, this);
            addOnlineCount();
            log.info("当前websocket连接数：" + onlineCount);
        }
    }

    /**
     * 连接关闭调用的方法
     *
     * @param sessionKey url地址参数
     */
    @OnClose
    public void onClose(@PathParam("sessionKey") String sessionKey) {

        if (webSockets.containsKey(sessionKey)) {
            webSockets.remove(sessionKey);
            subOnlineCount();
            log.info("当前websocket连接数：" + onlineCount);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
    }

    /**
     * 发生错误时调用
     *
     * @param session 可选的参数
     * @param error   错误消息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("websocket发生错误：" + error);
    }

    /**
     * 该方法没有用注解，是根据自己需要添加的方法。在自己的业务中调用，发送消息给前端。
     *
     * @param sessionKey
     * @param message    返回的结果
     * @throws IOException
     */
    public static void sendMessage(String sessionKey, String message) throws IOException {

        WebSocket webSocket = webSockets.get(sessionKey);

        if (null != webSocket) {

            log.info("websocket发送消息：" + message);

            //同步发送 发送第二条时，必须等第一条发送完成
            webSocket.session.getBasicRemote().sendText(message);

            //异步发送
            //webSocket.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        onlineCount--;
    }

}