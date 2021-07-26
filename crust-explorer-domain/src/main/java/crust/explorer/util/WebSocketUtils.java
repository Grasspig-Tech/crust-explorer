package crust.explorer.util;

import crust.explorer.ws.FrontMessage;
import crust.explorer.ws.ModuleMessage;

public class WebSocketUtils {

    public static ModuleMessage buildModuleMessage(String id, String module, String channel, Object body) {
        FrontMessage frontMessage = FrontMessage.builder()
                .channel(channel)
                .body(body).build();
        String message = GsonUtils.BeanToJson(frontMessage);
        return ModuleMessage.builder()
                .id(id)
                .module(module)
                .message(message)
                .build();
    }
}
