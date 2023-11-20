package com.brycehan.boot.framework.common;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket服务
 * <p>
 * 注入Spring容器bean时，在无参数构造器中用SpringContextHolder获取bean，有参数的构造器有问题
 *
 * @since 2022/5/24
 * @author Bryce Han
 */
@Slf4j
@ServerEndpoint(value = "/websocket/{code}")
@Component
public class WebSocketServer {

    /**
     * 存放每个客户端对应的WebSocket对象
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与客户端的连接会话
     */
    private Session session;

    /**
     * 接收识别码
     */
    private String code = "";

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     * @param code
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("code") String code) {
        this.session = session;
        this.code = code;
        // 如果存在就先删除一个，防止重复推送消息，这里用Set，不删除问题也不大
        webSocketSet.removeIf(webSocketServer -> webSocketServer.code.equals(code));
        webSocketSet.add(this);
        log.info("建立WebSocket连接，code：{}，当前连接数：{}", code, webSocketSet.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("关闭WebSocket连接，code：{}，当前连接数：{}", code, webSocketSet.size());
    }

    /**
     * 收到客户端消息后的调用的方法
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来[{}]的消息：{}", code, message);
    }

    public void onError(Session session, Throwable throwable) {
        log.error("websocket发生错误");
        throwable.printStackTrace();
    }

    /**
     * 服务器主动推送消息
     *
     * @param message
     * @throws IOException
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发消息
     *
     * @param message
     */
    public void sendAll(String message) {
        log.info("推送消息到：{}，推送内容：{}", code, message);
        webSocketSet.forEach(webSocket -> {
            try {
                webSocket.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 定点推送消息
     *
     * @param message 消息
     * @param code    接收识别码
     */
    public void sendTo(String message, @PathParam("code") String code) {
        Optional<WebSocketServer> webSocketServer = webSocketSet.stream()
                .filter(webSocket -> webSocket.code.equals(code))
                .findFirst();
        if (webSocketServer.isPresent()) {
            try {
                webSocketServer.get().sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebSocketServer that = (WebSocketServer) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

}
