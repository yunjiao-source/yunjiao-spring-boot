package yunjiao.springboot.extension.tika.core;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.filter.MetadataFilter;
import org.apache.tika.sax.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Optional;

/**
 * {@link DefaultHandler} 子类建造器
 *
 * @author yangyunjiao
 */
public interface DefaultHandlerBuilder<T extends DefaultHandler> {
    /**
     * 构建实例
     *
     * @return 实例
     */
    T build();

    /**
     *  {@link BodyContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class BodyBuilder implements DefaultHandlerBuilder<BodyContentHandler> {
        private ContentHandler handler;

        private Writer writer;

        private Integer writeLimit;

        @Override
        public BodyContentHandler build() {
            if (handler != null) {
                return new BodyContentHandler(handler);
            }

            if (writer != null) {
                return new BodyContentHandler(writer);
            }

            if (writeLimit != null) {
                return new BodyContentHandler(writeLimit);
            }

            return new BodyContentHandler();
        }
    }

    /**
     * {@link LinkContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class LinkBuilder implements DefaultHandlerBuilder<LinkContentHandler> {
        private Boolean collapseWhitespaceInAnchor;

        @Override
        public LinkContentHandler build() {
            if (collapseWhitespaceInAnchor != null) {
                return new LinkContentHandler(collapseWhitespaceInAnchor);
            }

            return new LinkContentHandler();
        }
    }

    /**
     * {@link TeeContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class TeeBuilder implements DefaultHandlerBuilder<TeeContentHandler> {
        private ContentHandler[] handlers;

        @Override
        public TeeContentHandler build() {
            assert handlers != null;

            return new TeeContentHandler(handlers);
        }
    }

    /**
     * {@link DIFContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class DIFBuilder implements DefaultHandlerBuilder<DIFContentHandler> {
        private ContentHandler handler;

        private Metadata metadata;

        @Override
        public DIFContentHandler build() {
            assert handler != null;
            assert metadata != null;

            return new DIFContentHandler(handler, metadata);
        }
    }

    /**
     * {@link EmbeddedContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class EmbeddedBuilder implements DefaultHandlerBuilder<EmbeddedContentHandler> {
        private ContentHandler handler;

        @Override
        public EmbeddedContentHandler build() {
            assert handler != null;

            return new EmbeddedContentHandler(handler);
        }
    }

    /**
     * {@link EndDocumentShieldingContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class EndDocumentShieldingBuilder implements DefaultHandlerBuilder<EndDocumentShieldingContentHandler> {
        private ContentHandler handler;

        @Override
        public EndDocumentShieldingContentHandler build() {
            assert handler != null;

            return new EndDocumentShieldingContentHandler(handler);
        }
    }

    /**
     * {@link ExpandedTitleContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class ExpandedTitleBuilder implements DefaultHandlerBuilder<ExpandedTitleContentHandler> {
        private ContentHandler handler;

        @Override
        public ExpandedTitleContentHandler build() {
            if (handler != null) {
                return new ExpandedTitleContentHandler(handler);
            }

            return new ExpandedTitleContentHandler();
        }
    }

    /**
     * {@link OfflineContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class OfflineBuilder implements DefaultHandlerBuilder<OfflineContentHandler> {
        private ContentHandler handler;

        @Override
        public OfflineContentHandler build() {
            assert handler != null;

            return new OfflineContentHandler(handler);
        }
    }

    /**
     * {@link PhoneExtractingContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class PhoneExtractingBuilder implements DefaultHandlerBuilder<PhoneExtractingContentHandler> {
        private ContentHandler handler;

        private Metadata metadata;

        @Override
        public PhoneExtractingContentHandler build() {
            if (handler == null) {
                handler = new DefaultHandler();
            }
            if (metadata == null) {
                metadata = new Metadata();
            }

            return new PhoneExtractingContentHandler(handler, metadata);
        }
    }

    /**
     * {@link RecursiveParserWrapperHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class RecursiveParserWrapperBuilder implements DefaultHandlerBuilder<RecursiveParserWrapperHandler> {
        private ContentHandlerFactory contentHandlerFactory;

        private Integer maxEmbeddedResources;

        private MetadataFilter metadataFilter;

        @Override
        public RecursiveParserWrapperHandler build() {
            assert contentHandlerFactory != null;

            if (maxEmbeddedResources != null && metadataFilter != null ) {
                return new RecursiveParserWrapperHandler(contentHandlerFactory, maxEmbeddedResources, metadataFilter);
            }

            if (maxEmbeddedResources != null) {
                return new RecursiveParserWrapperHandler(contentHandlerFactory, maxEmbeddedResources);
            }

            return new RecursiveParserWrapperHandler(contentHandlerFactory);
        }
    }

    /**
     * {@link RichTextContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class RichTextBuilder implements DefaultHandlerBuilder<RichTextContentHandler> {
        private Writer writer;

        @Override
        public RichTextContentHandler build() {
            assert writer != null;

            return new RichTextContentHandler(writer);
        }
    }

    /**
     * {@link SafeContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class SafeBuilder implements DefaultHandlerBuilder<SafeContentHandler> {
        private ContentHandler handler;

        @Override
        public SafeContentHandler build() {
            assert handler != null;

            return new SafeContentHandler(handler);
        }
    }

    /**
     * {@link SecureContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class SecureBuilder implements DefaultHandlerBuilder<SecureContentHandler> {
        private ContentHandler handler;

        private TikaInputStream stream;

        private Long threshold;

        private Long ratio;

        private Integer maxDepth;

        private Integer maxPackageEntryDepth;

        @Override
        public SecureContentHandler build() {
            assert handler != null;
            assert stream != null;

            SecureContentHandler result = new SecureContentHandler(handler, stream);
            Optional.ofNullable(threshold).ifPresent(result::setOutputThreshold);
            Optional.ofNullable(ratio).ifPresent(result::setMaximumCompressionRatio);
            Optional.ofNullable(maxDepth).ifPresent(result::setMaximumDepth);
            Optional.ofNullable(maxPackageEntryDepth).ifPresent(result::setMaximumPackageEntryDepth);
            return result;
        }
    }

    /**
     * {@link StandardsExtractingContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class StandardsExtractingBuilder implements DefaultHandlerBuilder<StandardsExtractingContentHandler> {
        private ContentHandler handler;

        private Metadata metadata;

        private Double threshold;

        private Integer maxBufferLength;

        @Override
        public StandardsExtractingContentHandler build() {
            if (handler == null) {
                handler = new DefaultHandler();
            }
            if (metadata == null) {
                metadata = new Metadata();
            }

            StandardsExtractingContentHandler result = new StandardsExtractingContentHandler(handler, metadata);
            Optional.ofNullable(threshold).ifPresent(result::setThreshold);
            Optional.ofNullable(maxBufferLength).ifPresent(result::setMaxBufferLength);
            return result;
        }
    }

    /**
     * {@link TaggedContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class TaggedBuilder implements DefaultHandlerBuilder<TaggedContentHandler> {
        private ContentHandler handler;

        @Override
        public TaggedContentHandler build() {
            assert handler != null;

            return new TaggedContentHandler(handler);
        }
    }

    /**
     * {@link TextContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class TextBuilder implements DefaultHandlerBuilder<TextContentHandler> {
        private ContentHandler handler;

        private Boolean addSpaceBetweenElements;

        @Override
        public TextContentHandler build() {
            assert handler != null;

            return new TextContentHandler(handler,
                    Optional.ofNullable(addSpaceBetweenElements).orElse(false));
        }
    }

    /**
     * {@link ToHTMLContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class ToHTMLBuilder implements DefaultHandlerBuilder<ToHTMLContentHandler> {
        private OutputStream stream;

        private String encoding;

        @Override
        @SneakyThrows
        public ToHTMLContentHandler build() {
            if (stream != null && encoding != null) {
                return new ToHTMLContentHandler(stream, encoding);
            }

            return new ToHTMLContentHandler();
        }
    }

    /**
     * {@link ToTextContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class ToTextBuilder implements DefaultHandlerBuilder<ToTextContentHandler> {
        private Writer writer;

        private OutputStream stream;

        private String encoding;

        @Override
        @SneakyThrows
        public ToTextContentHandler build() {
            if (writer != null) {
                return new ToTextContentHandler(writer);
            }

            if (stream != null && encoding != null) {
                return new ToTextContentHandler(stream, encoding);
            }

            return new ToTextContentHandler();
        }
    }

    /**
     * {@link ToXMLContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class ToXMLBuilder implements DefaultHandlerBuilder<ToXMLContentHandler> {
        private OutputStream stream;

        private String encoding;

        @Override
        @SneakyThrows
        public ToXMLContentHandler build() {
            if (stream != null && encoding != null) {
                return new ToXMLContentHandler(stream, encoding);
            }

            return new ToXMLContentHandler();
        }
    }

    /**
     * {@link WriteOutContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class WriteOutBuilder implements DefaultHandlerBuilder<WriteOutContentHandler> {
        private ContentHandler handler;

        private Integer writeLimit;

        private Writer writer;

        @Override
        public WriteOutContentHandler build() {
            if (handler != null && writeLimit != null) {
                return new WriteOutContentHandler(handler, writeLimit);
            }

            if (writer != null && writeLimit != null) {
                return new WriteOutContentHandler(writer, writeLimit);
            }

            if (writeLimit != null) {
                return new WriteOutContentHandler(writeLimit);
            }

            return new WriteOutContentHandler();
        }
    }

    /**
     * {@link XHTMLContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class XHTMLBuilder implements DefaultHandlerBuilder<XHTMLContentHandler> {
        private ContentHandler handler;

        private Metadata metadata;

        @Override
        public XHTMLContentHandler build() {
            assert handler != null;
            assert metadata != null;

            return new XHTMLContentHandler(handler, metadata);
        }
    }

    /**
     * {@link XMPContentHandler} 建造器
     */
    @Setter
    @Accessors(chain = true, fluent = true)
    class XMPBuilder implements DefaultHandlerBuilder<XMPContentHandler> {
        private ContentHandler handler;

        @Override
        public XMPContentHandler build() {
            assert handler != null;

            return new XMPContentHandler(handler);
        }
    }
}
