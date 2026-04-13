package cn.edu.nnu.opengms.controller;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    @Resource(name = "menuAgent")
    private ReactAgent menuAgent;

    @GetMapping(value = "/eatAgent")
    public String eatAgent(@RequestParam(name = "msg", defaultValue = "今天吃什么") String msg) throws GraphRunnerException {
        return menuAgent.call(msg).getText();
    }
}
