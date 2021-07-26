package crust.explorer;


import crust.explorer.enums.ModuleEnum;
import crust.explorer.enums.hint.LogicEnum;
import crust.explorer.util.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Collections {

    private static volatile ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> MODULE = new ConcurrentHashMap<String, ConcurrentHashMap<String, Session>>();

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
            session.setModule(module);
            ConcurrentHashMap<String, Session> sessions = getModuleSessions(module);
            log.info("Collections.addSession 1 module ：{},{}", module, sessions);
            sessions.put(session.getId(), session);
            log.info("Collections.addSession 2 module ：{},{}", module, sessions);
        } catch (Exception e) {
            log.error("Collections.addSession 失败：", e);
        }
    }

    public static void removeSession(Session session) {
        String module = session.getModule();
        ConcurrentHashMap<String, Session> moduleSessions = getModuleSessions(module);
        log.info("Collections.removeSession 1 module ：{},{}", module, moduleSessions);
        moduleSessions.remove(session.getId());
        log.info("Collections.removeSession 2 module ：{},{}", module, moduleSessions);
    }

}
