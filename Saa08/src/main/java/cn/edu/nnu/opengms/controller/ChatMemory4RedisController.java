package cn.edu.nnu.opengms.controller;

import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMemory4RedisController {

    @Resource(name = "redisMemoryAgent")
    private ReactAgent redisMemoryAgent;

    @GetMapping("/chatmemory/chat")
    public String chat(
            @RequestParam(name = "msg", defaultValue = "你好") String msg,
            @RequestParam(name = "userId", defaultValue = "user001") String userId) throws GraphRunnerException {
        RunnableConfig config = RunnableConfig.builder()
                .threadId(userId)
                .build();

        return redisMemoryAgent.call(msg, config).getText();
    }
}
