package root.configuration.jms.ibmmq;

import root.configuration.jms.common.JmsMessageSender;
import root.configuration.jms.common.JmsTemplateNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

public class IbmTextMessageSender implements JmsMessageSender {
    private static final Log LOGGER = LogFactory.getLog(IbmTextMessageSender.class);
    private final IbmTemplateFactory ibmTemplateFactory;

    public IbmTextMessageSender(IbmTemplateFactory ibmTemplateFactory) {
        this.ibmTemplateFactory = ibmTemplateFactory;
    }

    @Override
    public void send(final Serializable message) throws JmsTemplateNotFoundException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(message);
        }

        final String textMessage = (String) message;
        if (ibmTemplateFactory == null) {
            throw new JmsTemplateNotFoundException();
        }

        ibmTemplateFactory.getJmsTemplate().send(
                session -> session.createTextMessage(textMessage)
        );
    }
}
