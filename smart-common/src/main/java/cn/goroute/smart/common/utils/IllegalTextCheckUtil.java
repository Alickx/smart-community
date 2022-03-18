package cn.goroute.smart.common.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.dfa.WordTree;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class IllegalTextCheckUtil {

    private final static WordTree wordTree = new WordTree();

    /**
     * 返回是否含有违规词的结果
     * @param content 检查文本
     * @return true = 含有 false = 不含有
     */
    public List<String> checkText(String content) {

        return wordTree.matchAll(content);

    }

    @PostConstruct
    private void initWordTree() throws IOException {
        Set<String> set = new HashSet<>();

        ClassPathResource classPathResource = new ClassPathResource("IllegalWord.txt");
        BufferedReader reader = classPathResource.getReader(Charset.defaultCharset());

        String str = "";

        while ((str = reader.readLine()) != null) {
            set.add(str);
        }

        wordTree.addWords(set);
    }

}
