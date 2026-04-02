package cn.edu.nnu.opengms.controller;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.DocumentName;
import java.util.Arrays;
import java.util.List;

@RestController
public class Embed2VectorController {
    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private VectorStore vectorStore;

    // 文本向量化
    @GetMapping("/text2embed")
    public EmbeddingResponse text2Embed(String msg)
    {
        EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg),
                DashScopeEmbeddingOptions.builder().withModel("text-embedding-v3").build()));

        System.out.println(Arrays.toString(embeddingResponse.getResult().getOutput()));

        return embeddingResponse;
    }

    //文本向量化后存入向量数据库RedisStack
    @GetMapping("/embed2Vector/add")
    public void add()
    {
        List<Document> documents = List.of(
                new Document("i study LLM"),
                new Document("i love java")
        );
        vectorStore.add(documents);
    }
}
