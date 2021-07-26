package crust.explorer.controller;

import crust.explorer.ws.Collections;
import crust.explorer.ws.ModuleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws/test")
@Slf4j
public class WebSocketTestController {


    /**
     * @param moduleMessage
     * @return
     */
    @PostMapping("/broadCast")
    public String broadCast(ModuleMessage moduleMessage) {
        Collections.broadCastMessage(moduleMessage);
        return "ok";
    }

    /**
     * @param moduleMessage
     * @return
     */
    @PostMapping("/sendMessage")
    public String sendMessage(ModuleMessage moduleMessage) {
        Collections.sendMessage(moduleMessage);
        return "ok";
    }

}
