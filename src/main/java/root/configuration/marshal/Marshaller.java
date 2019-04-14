package root.configuration.marshal;

import org.apache.commons.logging.Log;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3c.dom.Node;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import static java.lang.String.format;
import static org.apache.commons.logging.LogFactory.getLog;
import static root.service.util.DateUtils.toShortDate;
import static root.service.util.ExtensionUtils.XML_TYPE;
import static root.service.util.FileUtils.write;
import static root.service.util.SymbolUtils.DASH;
import static root.service.util.SymbolUtils.SLASH;

public abstract class Marshaller<Source> {
    private static final String MARSHAL_ERROR_MESSAGE = "Failed to marshal object. [%s:%s]";
    private static final String UNMARSHAL_ERROR_MESSAGE = "Failed to unmarshal text. [Text:%s]";
    private static final String SUCCESS_MESSAGE = "%s marshaled successfully. [Text:%s]";
    private static final Log LOGGER = getLog(Marshaller.class);
    private final Jaxb2Marshaller jaxb2Marshaller;
    private final String archivePath;
    private final Boolean store;

    public Marshaller(Jaxb2Marshaller jaxb2Marshaller) {
        this.jaxb2Marshaller = jaxb2Marshaller;
        this.archivePath = null;
        this.store = false;
    }

    protected Marshaller(Jaxb2Marshaller jaxb2Marshaller, String archivePath) {
        this.jaxb2Marshaller = jaxb2Marshaller;
        this.archivePath = archivePath;
        this.store = true;
    }

    public MarshalResult marshal(Source source) throws MarshalException {
        try {
            MarshalResult marshalResult = store ? marshalWithStore(source) : marshalWithoutStore(source);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(format(SUCCESS_MESSAGE, source.getClass().getSimpleName(), marshalResult.getMessage()));
            }

            return marshalResult;
        } catch (Exception e) {
            throw new MarshalException(format(MARSHAL_ERROR_MESSAGE, source.getClass().getSimpleName(), source), e);
        }
    }

    public Source unmarshal(String text) throws MarshalException {
        try {
            return store ? unmarshalWithStore(text) : unmarshalWithoutStore(text);
        } catch (Exception e) {
            throw new MarshalException(format(UNMARSHAL_ERROR_MESSAGE, text), e);
        }
    }

    private MarshalResult marshalWithStore(Source source) throws MarshalException, IOException, TransformerException {
        DOMResult result = new DOMResult();
        jaxb2Marshaller.marshal(source, result);
        Node node = result.getNode();
        javax.xml.transform.Source domSource = new DOMSource(node);
        String filename = getArchivePath() + SLASH + getFileName(source) + XML_TYPE;
        File file = new File(filename);
        Result streamResult = new StreamResult(file);
        StreamResult output = new StreamResult(new StringWriter());
        Transformer xFormer = TransformerFactory.newInstance().newTransformer();
        xFormer.transform(domSource, streamResult);
        xFormer.transform(domSource, output);
        String text = output.getWriter().toString();
        return new MarshalResult(text, file);
    }

    private MarshalResult marshalWithoutStore(Source source) {
        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);
        jaxb2Marshaller.marshal(source, result);
        return new MarshalResult(sw.toString());
    }

    private Source unmarshalWithStore(String text) throws MarshalException, IOException {
        Source source = (Source) jaxb2Marshaller.unmarshal(new StreamSource(new StringReader(text)));
        String filename = getArchivePath() + SLASH + getFileName(source) + XML_TYPE;
        File file = new File(filename);
        write(file, text);
        return source;
    }

    private Source unmarshalWithoutStore(String text) throws MarshalException {
        return (Source) jaxb2Marshaller.unmarshal(new StreamSource(new StringReader(text)));
    }

    private String getArchivePath() throws IOException {
        String archivePath = toShortDate(new Date()).replace(SLASH, DASH);

        File directory = new File(this.archivePath + SLASH + archivePath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        return directory.getAbsolutePath();
    }

    protected String getFileName(Source source) {
        return String.valueOf(System.currentTimeMillis());
    }
}
