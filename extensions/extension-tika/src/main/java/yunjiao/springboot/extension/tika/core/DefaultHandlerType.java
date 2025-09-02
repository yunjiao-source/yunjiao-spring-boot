package yunjiao.springboot.extension.tika.core;

import lombok.Getter;
import org.xml.sax.ContentHandler;

/**
 * TODO
 *
 * @author yangyunjiao
 */
@Getter
public enum DefaultHandlerType {
    body(new DefaultHandlerBuilder.BodyBuilder()),
    line(new DefaultHandlerBuilder.LinkBuilder()),
    tee(new DefaultHandlerBuilder.TeeBuilder()),
    expandedTitle(new DefaultHandlerBuilder.ExpandedTitleBuilder()),
    phoneExtracting(new DefaultHandlerBuilder.PhoneExtractingBuilder()),
    standardsExtracting(new DefaultHandlerBuilder.StandardsExtractingBuilder()),
    toHTML(new DefaultHandlerBuilder.ToHTMLBuilder()),
    toText(new DefaultHandlerBuilder.ToTextBuilder()),
    toXML(new DefaultHandlerBuilder.ToXMLBuilder()),
    writeOutBuilder(new DefaultHandlerBuilder.WriteOutBuilder());

    private final DefaultHandlerBuilder<?> builder;

    DefaultHandlerType(DefaultHandlerBuilder<?> builder) {
        this.builder = builder;
    }

    public ContentHandler create() {
        return builder.build();
    }
}
