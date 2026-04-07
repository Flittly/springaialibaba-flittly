package cn.edu.nnu.opengms.controller;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public EmbeddingResponse text2Embed(@RequestParam(name = "msg") String msg)
    {
        //显式指定使用模型
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

    //向量检索：从向量数据库中查找相似的内容
    @GetMapping("/embed2Vector/get")
    public List<Document> getAll(@RequestParam(name = "msg") String msg)
    {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(msg)
                .topK(2)
                .build();

        List<Document> list = vectorStore.similaritySearch(searchRequest);

        System.out.println(list);

        return list;
    }
}
