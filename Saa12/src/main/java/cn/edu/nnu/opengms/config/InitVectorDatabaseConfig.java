package cn.edu.nnu.opengms.config;

import cn.hutool.crypto.SecureUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class InitVectorDatabaseConfig {
    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("classpath:ops.txt")
    private Resource opsFile;

    @PostConstruct
    public void init()
    {
        //读取文件
        TextReader textReader = new TextReader(opsFile);
        textReader.setCharset(Charset.defaultCharset());

        //文件转换为向量
        List<Document> list = new TokenTextSplitter().transform(textReader.read());

        //写入向量数据库Redisstack
        String sourceMetadata = (String)textReader.getCustomMetadata().get("source");

        String textHash  = SecureUtil.md5(sourceMetadata);
        String redisKey = "vector-xxx:" + textHash;

        //判断是否存入过，rediskey如果可以成功插入表示以前没有过
        Boolean retFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, "1");

        System.out.println("****retFlag : "+retFlag);

        if(Boolean.TRUE.equals(retFlag))
        {
            //键不存在，首次插入，可以保存进向量数据库
            vectorStore.add(list);
        }else
        {
            System.out.println("*****已经存在");
        }
    }
}
