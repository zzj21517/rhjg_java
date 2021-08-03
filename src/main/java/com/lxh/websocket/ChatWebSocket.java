package com.lxh.websocket;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
/**
 * @author 。
 */
@ServerEndpoint(value = "/chat_websocket")
public class ChatWebSocket {
    public ChatWebSocket() {
        WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();
    }
    /**
     * 存储用户id
     */
    public static Map userMap = new HashMap();
    /**
     * 聊天记录
     */
    public static Map chatRecordMap = new HashMap();


    /**
     * 管理员列表session
     */
    public static Session adminSession;


    /**
     * 创建
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        List<String> strs = requestParameterMap.get("msg");
        if (strs != null && strs.size() > 0) {
            String json = strs.get(0);
            //从聊天集中去掉该集合

        }
    }

    /**
     * 发送消息
     *
     * @param json {userId:'',message:'',create_time:'',create_date:'',chat_type:'admin_list/admin_chat/user_chat'}
     *             admin_list:表示客服列表数据请求
     *             admin_chat:表示客服回复页面请求
     *             user_chat表示用户消息页面请求
     *
     *
     * @throws Exception
     */
    @OnMessage
    public void onMessage(Session session, String json) {


    }

    /**
     * 关闭
     */
    @OnClose
    public void onClose(Session session) {


    }


    /**
     * 发生错误
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 消息广播
     *
     * @param sessions
     * @param messages
     */
    public void chat(List<Session> sessions, List messages) {


    }

    /**
     * 聊天列表显示
     */
    public void chat_list_show(Session session, List list) {

    }
    /**
     * 通过id获取用户数据
     *
     * @return
     */
    public List getUserList(Map userMap) {
        List list = new ArrayList();
        return list;
    }


}