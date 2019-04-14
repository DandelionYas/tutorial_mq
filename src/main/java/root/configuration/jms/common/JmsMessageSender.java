package root.configuration.jms.common;

import java.io.Serializable;

public interface JmsMessageSender {
    void send(final Serializable message) throws JmsTemplateNotFoundException;
}
