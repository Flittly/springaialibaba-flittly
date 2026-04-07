package cn.edu.nnu.opengms.utils;

import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDateTime;

public class DateTimeTools {
    @Tool(description = "获取当前时间", returnDirect = false)
    public String getCurrentTime() {
        return LocalDateTime.now().toString();
    }
}
