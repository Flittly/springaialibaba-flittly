package cn.edu.nnu.opengms.controller;

import com.alibaba.cloud.ai.dashscope.audio.tts.DashScopeAudioSpeechModel;
import com.alibaba.cloud.ai.dashscope.audio.tts.DashScopeAudioSpeechOptions;
import com.alibaba.cloud.ai.dashscope.spec.DashScopeModel;
import jakarta.annotation.Resource;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@RestController
public class Text2VoiceController {

    @Resource(name = "dashScopeSpeechSynthesisModel")
    private DashScopeAudioSpeechModel speechModel;

    public static final String BAILIAN_VOICE_MODEL = DashScopeModel.AudioModel.COSYVOICE_V3_FLASH.getValue();
    public static final String BAILIAN_VOICE_TIMBER = "longanyang";

    @GetMapping("/t2v/voice")
    public String voice(
            @RequestParam(name = "msg", defaultValue = "温馨提醒，支付宝到账100元请注意查收") String msg) {

        String filePath = System.getProperty("java.io.tmpdir") + UUID.randomUUID() + ".mp3";

        DashScopeAudioSpeechOptions options = DashScopeAudioSpeechOptions.builder()
                .model(BAILIAN_VOICE_MODEL)
                .voice(BAILIAN_VOICE_TIMBER)
                .format("mp3")
                .sampleRate(22050)
                .textType("PlainText")
                .build();

        TextToSpeechPrompt prompt = new TextToSpeechPrompt(msg, options);

        byte[] audioBytes = collectStreamBytes(speechModel.stream(prompt));

        if (audioBytes == null || audioBytes.length == 0) {
            throw new IllegalStateException("TTS generated no audio data");
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(audioBytes);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to write audio file", e);
        }

        return filePath;
    }

    private byte[] collectStreamBytes(Flux<TextToSpeechResponse> stream) {
        List<byte[]> chunks = stream
                .filter(r -> r != null && r.getResult() != null && r.getResult().getOutput() != null)
                .map(r -> r.getResult().getOutput())
                .collectList()
                .block();

        if (chunks == null || chunks.isEmpty()) {
            return new byte[0];
        }

        int total = chunks.stream().mapToInt(b -> b.length).sum();
        byte[] result = new byte[total];
        int offset = 0;

        for (byte[] chunk : chunks) {
            System.arraycopy(chunk, 0, result, offset, chunk.length);
            offset += chunk.length;
        }

        return result;
    }
}