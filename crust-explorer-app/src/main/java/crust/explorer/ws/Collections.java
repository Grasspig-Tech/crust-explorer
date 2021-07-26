package crust.explorer.ws;


import crust.explorer.enums.ModuleEnum;
import crust.explorer.enums.hint.LogicEnum;
import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.util.Assert;
import crust.explorer.util.Constants;
import crust.explorer.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.yeauty.pojo.Session;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Collections {

    private static volatile ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> MODULE = new ConcurrentHashMap<String, ConcurrentHashMap<String, Session>>();

    static {
        initModule();
    }

    public static void init() {

    }

    public static void initModule() {
        ModuleEnum[] values = ModuleEnum.values();
        for (ModuleEnum e : values) {
            ConcurrentHashMap<String, Session> module = new ConcurrentHashMap<String, Session>(256);
            MODULE.put(e.getModule(), module);
            log.info("初始化频道：{}", e.getModule());
        }
    }

    public static ConcurrentHashMap<String, Session> getModuleSessions(String module) {
        Assert.isNotNull(ModuleEnum.getEnum(module), LogicEnum.MODULE_TYPE_ERROR);
        return MODULE.get(module);
    }

    public static void addSession(String module, Session session) {
        try {
            session.setAttribute(Constants.MODULE, module);
            ConcurrentHashMap<String, Session> sessions = getModuleSessions(module);
            log.info("Collections.addSession 1 module ：{},{}", module, sessions);
            sessions.put(session.id().asLongText(), session);
            log.info("Collections.addSession 2 module ：{},{}", module, sessions);
        } catch (Exception e) {
            log.error("Collections.addSession 失败：", e);
            session.close();
        }
    }

    public static void removeSession(Session session) {
        String module = session.getAttribute(Constants.MODULE);
        ConcurrentHashMap<String, Session> moduleSessions = getModuleSessions(module);
        log.info("Collections.removeSession 1 module ：{},{}", module, moduleSessions);
        moduleSessions.remove(session.id().asLongText());
        log.info("Collections.removeSession 2 module ：{},{}", module, moduleSessions);
    }

    public static void sendMessage(Session session, String message) {
        session.sendText(message);
        countDownLife(session);
    }

    public static void sendMessage(String module, String id, String message) {
        ConcurrentHashMap<String, Session> sessions = getModuleSessions(module);
        Session session = sessions.get(id);
        if (Objects.nonNull(session)) {
            sendMessage(session, message);
        } else {
            log.warn("未找到指定ID会话：{}", id);
        }
    }

    public static void sendMessage(ModuleMessage moduleMessage) {
        Boolean isValidMessage = Objects.nonNull(moduleMessage) && StringUtils.isNotBlank(moduleMessage.getModule()) && StringUtils.isNotBlank(moduleMessage.getId());
        Assert.isTrue(isValidMessage, ValidateEnum.MODULE_MESSAGE_FIELD_ERROR);
        sendMessage(moduleMessage.getModule(), moduleMessage.getId(), moduleMessage.getMessage());
    }

    public static void broadCastMessage(String module, String message) {
        ConcurrentHashMap<String, Session> sessions = getModuleSessions(module);
        sessions.forEach((id, session) -> {
            if (session.isOpen()) {
                sendMessage(session, message);
            }
        });
    }

    public static void broadCastMessage(ModuleMessage moduleMessage) {
        Boolean isValidMessage = Objects.nonNull(moduleMessage) && StringUtils.isNotBlank(moduleMessage.getModule());
        Assert.isTrue(isValidMessage, ValidateEnum.MODULE_MESSAGE_FIELD_ERROR);
        broadCastMessage(moduleMessage.getModule(), moduleMessage.getMessage());
    }

    public static void countDownLife(Session session) {
        if (Objects.nonNull(session)
                && Objects.nonNull(session.getAttribute(Constants.AUTH_BIRTH))
                && Constants.WS_KEY.equals(session.getAttribute(Constants.AUTH))) {
            long now = System.currentTimeMillis();
            long birth = session.getAttribute(Constants.AUTH_BIRTH);
            if (now - birth < Constants.AUTH_LIFE_TIME) {
                return;
            }
        }
        if (Objects.nonNull(session)) {
            session.close();
        }
    }
}
