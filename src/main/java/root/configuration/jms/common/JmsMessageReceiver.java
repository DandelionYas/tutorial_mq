package root.configuration.jms.common;

import root.configuration.marshal.Marshaller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import static java.lang.String.format;

public abstract class JmsMessageReceiver<T> implements MessageListener {
    private static final String NEW_MESSAGE_RECEIVED = "A new message has been received. [Message:%s]";
    private static final String FAILED_TO_PROCESS_MESSAGE = "The system could not process message. [Message:%s]";
    private static final Log LOGGER = LogFactory.getLog(JmsMessageReceiver.class);
    private final Marshaller<T> marshaller;

    protected JmsMessageReceiver(Marshaller<T> marshaller) {
        this.marshaller = marshaller;
    }

    public void onMessage(Message message) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(format(NEW_MESSAGE_RECEIVED, ((TextMessage) message).getText()));
            }

            onReceive(marshaller.unmarshal(((TextMessage) message).getText()));
        } catch (Exception e) {
            LOGGER.error(format(FAILED_TO_PROCESS_MESSAGE, message), e);
        }
    }

    protected abstract void onReceive(T t);
}
