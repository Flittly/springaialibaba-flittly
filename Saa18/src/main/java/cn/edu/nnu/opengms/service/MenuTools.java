package cn.edu.nnu.opengms.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class MenuTools {
    @Tool(description = "根据口味推荐菜单")
    public String recommendMenu(String taste) {
        if (taste == null || taste.isBlank()) {
            return "请先告诉我口味偏好（甜/咸/辣）。";
        }

        if (taste.equalsIgnoreCase("甜")) {
            return "推荐菜单：蛋挞、奶茶、甜甜圈";
        } else if (taste.equalsIgnoreCase("咸")) {
            return "推荐菜单：炸鸡、薯条、汉堡";
        } else if (taste.equalsIgnoreCase("辣")) {
            return "推荐菜单：麻辣香锅、辣子鸡、火锅";
        } else {
            return "抱歉，暂时没有相关口味的菜单推荐。";
        }
    }

    @Tool(description = "根据天气状况和温度推荐菜单，参数示例：weather=小雨, temperature=12")
    public String recommendByWeather(String weather, Integer temperature) {
        if (weather == null || weather.isBlank() || temperature == null) {
            return "请提供完整信息，例如：weather=小雨, temperature=12。";
        }

        String w = weather.toLowerCase();

        if (temperature <= 5) {
            return "当前" + weather + "，约" + temperature + "度，推荐暖身菜单：羊肉汤、番茄牛腩、砂锅米线。";
        }

        if (temperature <= 15) {
            if (w.contains("雨") || w.contains("雪")) {
                return "当前" + weather + "，约" + temperature + "度，推荐热汤类：酸辣汤面、菌菇鸡汤、馄饨。";
            }
            return "当前" + weather + "，约" + temperature + "度，推荐家常热菜：土豆炖牛肉、青椒肉丝、米饭。";
        }

        if (temperature <= 25) {
            if (w.contains("雨")) {
                return "当前" + weather + "，约" + temperature + "度，推荐温和口味：鲜虾粥、番茄鸡蛋面、清蒸鱼。";
            }
            if (w.contains("晴")) {
                return "当前" + weather + "，约" + temperature + "度，推荐轻食搭配：鸡胸沙拉、玉米浓汤、全麦三明治。";
            }
            return "当前" + weather + "，约" + temperature + "度，推荐均衡菜单：宫保鸡丁、时蔬、紫菜蛋花汤。";
        }

        if (w.contains("雨") || w.contains("闷")) {
            return "当前" + weather + "，约" + temperature + "度，推荐清爽低负担：绿豆粥、凉拌鸡丝、蒸南瓜。";
        }

        return "当前" + weather + "，约" + temperature + "度，推荐夏季清凉菜单：凉面、手撕鸡、冬瓜排骨汤。";
    }
}
