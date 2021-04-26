package org.linlinjava.litemall.wx.service;


import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@ServerEndpoint("/websocket/{userID}")
public class WebSocketServer {
    //每个客户端都会有相应的session,服务端可以发送相关消息
    private Session session;

    //接收userID
    private Integer userID;

    //J.U.C包下线程安全的类，主要用来存放每个客户端对应的webSocket连接
    private static CopyOnWriteArraySet<WebSocketServer> copyOnWriteArraySet = new CopyOnWriteArraySet<WebSocketServer>();

    public Integer getUserID() {
        return userID;
    }

    /**
     * @Name：onOpen
     * @Description：打开连接。进入页面后会自动发请求到此进行连接
     * @Author：mYunYu
     * @Create Date：14:46 2018/11/15
     * @Parameters：@PathParam("userID") Integer userID
     * @Return：
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userID") Integer userID) {
        this.session = session;
        this.userID = userID;
        System.out.println(this.session.getId());
        //System.out.println("userID:" + userID);
        copyOnWriteArraySet.add(this);
        System.out.println("websocket有新的连接, 总数:"+ copyOnWriteArraySet.size());

    }

    /**
     * @Name：onClose
     * @Description：用户关闭页面，即关闭连接
     * @Author：mYunYu
     * @Create Date：14:46 2018/11/15
     * @Parameters：
     * @Return：
     */
    @OnClose
    public void onClose() {
        copyOnWriteArraySet.remove(this);
        System.out.println("websocket连接断开, 总数:"+ copyOnWriteArraySet.size());
    }

    /**
     * @Name：onMessage
     * @Description：测试客户端发送消息，测试是否联通
     * @Author：mYunYu
     * @Create Date：14:46 2018/11/15
     * @Parameters：
     * @Return：
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("websocket收到客户端发来的消息:"+message);
    }

    /**
     * @Name：onError
     * @Description：出现错误
     * @Author：mYunYu
     * @Create Date：14:46 2018/11/15
     * @Parameters：
     * @Return：
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误：" + error.getMessage() + "; sessionId:" + session.getId());
        error.printStackTrace();
    }

    public void sendMessage(Object object){
        //遍历客户端
        for (WebSocketServer webSocket : copyOnWriteArraySet) {
            System.out.println("websocket广播消息：" + object.toString());
            try {
                //服务器主动推送
                webSocket.session.getBasicRemote().sendObject(object) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Name：sendMessage
     * @Description：用于发送给客户端消息（群发）
     * @Author：mYunYu
     * @Create Date：14:46 2018/11/15
     * @Parameters：
     * @Return：
     */
    public void sendMessage(String message) {
        //遍历客户端
        for (WebSocketServer webSocket : copyOnWriteArraySet) {
            System.out.println("websocket广播消息：" + message);
            try {
                //服务器主动推送
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @throws Exception
     * @Name：sendMessage
     * @Description：用于发送给指定客户端消息
     * @Author：mYunYu
     * @Create Date：14:47 2018/11/15
     * @Parameters：
     * @Return：
     */
    public void sendMessage(Integer userID, String message) throws Exception {
        Session session = null;
        WebSocketServer tempWebSocket = null;
        for (WebSocketServer webSocket : copyOnWriteArraySet) {
            if (webSocket.getUserID() == userID) {
                tempWebSocket = webSocket;
                session = webSocket.session;
                break;
            }
        }
        if (session != null) {
            //服务器主动推送
            tempWebSocket.session.getBasicRemote().sendText(message);

        } else {
            System.out.println("没有找到你指定ID的会话：{}"+ "; userId:" + userID);
        }
    }
}
