package crust.explorer.ws;

import crust.explorer.util.Constants;
import crust.explorer.util.RedisKeys;
import crust.explorer.util.RedisUtils;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.util.Objects;

@ServerEndpoint(path = "/ws/{module}", host = "${ws.host}", port = "${ws.port}")
@Slf4j
public class WebSocketServer {

    @Autowired
    RedisUtils redisUtils;

    @BeforeHandshake
    public void handshake(Session session, @RequestParam String tag, @RequestParam Long sid) {
        if (!Constants.WS_KEY.equals(tag) || Objects.isNull(sid)) {
            log.error("handshake failed! tag:{}, sid:{}", tag, sid);
            session.close();
            return;
        }
        String nextId = redisUtils.get(RedisKeys.AUTH_KEY.concat(String.valueOf(sid)));
        if (Objects.isNull(nextId) || !nextId.equals(String.valueOf(sid))) {
            log.error("handshake failed! nextId:{}", nextId);
            session.close();
            return;
        }
        session.setAttribute(Constants.AUTH, Constants.WS_KEY);
        session.setAttribute(Constants.AUTH_BIRTH, System.currentTimeMillis());
    }

    @OnOpen
    public void onOpen(Session session, @PathVariable String module) {
        Collections.addSession(module, session);
        log.info("new connection");
        log.info(session.id().asLongText());
//        session.sendText("id|" + session.id().asLongText());
    }

    @OnClose
    public void onClose(Session session) {
        if (Objects.nonNull(session) && Constants.WS_KEY.equals(session.getAttribute(Constants.AUTH))) {
            Collections.removeSession(session);
        }
        log.info("one connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        if (Objects.nonNull(session) && Constants.WS_KEY.equals(session.getAttribute(Constants.AUTH))) {
            Collections.removeSession(session);
        }
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.debug(message);
//        session.sendText("Hello Netty!");
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        session.sendBinary(bytes);
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    log.info("read idle");
                    break;
                case WRITER_IDLE:
                    log.info("write idle");
                    break;
                case ALL_IDLE:
                    log.info("all idle");
                    break;
                default:
                    break;
            }
        }
    }

}

