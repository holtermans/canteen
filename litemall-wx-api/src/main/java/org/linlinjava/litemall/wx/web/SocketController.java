package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.wx.service.WebSocketServer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wx/socket")
@Validated
public class SocketController {
    @Resource
    private WebSocketServer webSocket;

    @RequestMapping("send")
    public void send() throws Exception {
        webSocket.sendMessage(12,"hhhh");
    }

}
