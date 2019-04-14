package root.configuration.jms.activemq;

import root.configuration.jms.common.JmsMessageSender;
import root.configuration.jms.common.JmsTemplateNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

public class AmqTextMessageSender implements JmsMessageSender {
    private static final Log LOGGER = LogFactory.getLog(AmqTextMessageSender.class);
    private final AmqTemplateFactory amqTemplateFactory;

    public AmqTextMessageSender(AmqTemplateFactory amqTemplateFactory) {
        this.amqTemplateFactory = amqTemplateFactory;
    }

    @Override
    public void send(Serializable message) throws JmsTemplateNotFoundException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(message);
        }

        final String textMessage = (String) message;
        if (amqTemplateFactory == null) {
            throw new JmsTemplateNotFoundException();
        }

        amqTemplateFactory.getJmsTemplate().send(session -> session.createTextMessage(textMessage));
    }
}
