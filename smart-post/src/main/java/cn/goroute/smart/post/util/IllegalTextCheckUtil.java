package cn.goroute.smart.post.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.dfa.WordTree;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alickx
 * @description 敏感词过滤工具类
 */
@Component
public class IllegalTextCheckUtil {

    private final static WordTree WORD_TREE = new WordTree();

    /**
     * 返回是否含有违规词的结果
     * @param content 检查文本
     * @return true = 含有 false = 不含有
     */
    public boolean checkText(String content) {

        return CollUtil.isNotEmpty(WORD_TREE.matchAll(content));

    }

    @PostConstruct
    private void initWordTree() throws IOException {
        Set<String> set = new HashSet<>();

        ClassPathResource classPathResource = new ClassPathResource("IllegalWord.txt");
        BufferedReader reader = classPathResource.getReader(Charset.defaultCharset());

        String str;

        while ((str = reader.readLine()) != null) {
            set.add(str);
        }

        WORD_TREE.addWords(set);
    }

}
