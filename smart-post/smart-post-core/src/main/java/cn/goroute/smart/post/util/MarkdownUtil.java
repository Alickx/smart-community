package cn.goroute.smart.post.util;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

/**
 * @Author: Alickx
 * @Date: 2023/03/04/14:41
 * @Description: markdown工具类
 */
public class MarkdownUtil {

    /**
     * 将markdown转换为html
     * @param markdown markdown
     * @return html
     */
    public static String markdownToHtml(String markdown) {
        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    /**
     * 将markdown转换为纯文本
     * @param markdown markdown
     * @return 纯文本
     */
    public static String markdownToText(String markdown) {
        String html = markdownToHtml(markdown);
        return Html2TextUtil.Html2Text(html);
    }

}
