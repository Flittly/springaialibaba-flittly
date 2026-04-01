package cn.edu.nnu.opengms.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatHelloController {

    @Resource
    private ChatModel chatModel;

    @GetMapping(value = "/hello/dochat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "请介绍一下你自己") String msg) {
        return chatModel.call(msg);
    }
}
