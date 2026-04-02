package cn.edu.nnu.opengms.controller;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Text2ImageController {
    @Resource
    private ImageModel imageModel;

    @GetMapping(value = "/t2i/image")
    public String image(@RequestParam(name = "prompt", defaultValue = "一只罗罗") String prompt) {
        DashScopeImageOptions options = DashScopeImageOptions.builder()
                .withModel("wanx2.1-t2i-plus")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(prompt, options);

        ImageResponse imageResponse = imageModel.call(imagePrompt);

        return imageResponse.getResult().getOutput().getUrl();
    }
}
