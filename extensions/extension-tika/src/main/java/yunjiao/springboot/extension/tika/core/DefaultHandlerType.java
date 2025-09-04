package yunjiao.springboot.extension.tika.core;

import lombok.Getter;
import org.apache.tika.sax.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

/**
 * {@link DefaultHandler} 子类类型(有无参数构造器的)
 *
 * @author yangyunjiao
 */
@Getter
public enum DefaultHandlerType {
    /**
     * {@link BodyContentHandler} 类型实例构建
     */
    body(new DefaultHandlerBuilder.BodyBuilder()),

    /**
     * {@link LinkContentHandler} 类型实例构建
     */
    link(new DefaultHandlerBuilder.LinkBuilder()),

    /**
     * {@link BodyContentHandler} 类型实例构建
     */
    tee(new DefaultHandlerBuilder.TeeBuilder()),

    /**
     * {@link TeeContentHandler} 类型实例构建
     */
    expandedTitle(new DefaultHandlerBuilder.ExpandedTitleBuilder()),

    /**
     * {@link PhoneExtractingContentHandler} 类型实例构建
     */
    phoneExtracting(new DefaultHandlerBuilder.PhoneExtractingBuilder()),

    /**
     * {@link StandardsExtractingContentHandler} 类型实例构建
     */
    standardsExtracting(new DefaultHandlerBuilder.StandardsExtractingBuilder()),

    /**
     * {@link ToHTMLContentHandler} 类型实例构建
     */
    toHTML(new DefaultHandlerBuilder.ToHTMLBuilder()),

    /**
     * {@link ToTextContentHandler} 类型实例构建
     */
    toText(new DefaultHandlerBuilder.ToTextBuilder()),

    /**
     * {@link ToXMLContentHandler} 类型实例构建
     */
    toXML(new DefaultHandlerBuilder.ToXMLBuilder()),

    /**
     * {@link WriteOutContentHandler} 类型实例构建
     */
    writeOutBuilder(new DefaultHandlerBuilder.WriteOutBuilder());

    private final DefaultHandlerBuilder<?> builder;

    DefaultHandlerType(DefaultHandlerBuilder<?> builder) {
        this.builder = builder;
    }

    /**
     * 创建
     *
     * @return 实例
     */
    public ContentHandler create() {
        return builder.build();
    }
}
