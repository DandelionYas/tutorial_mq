package root.configuration.jms.common;

import javax.jms.JMSException;
import javax.jms.Queue;

public abstract class JmsQueueFactory {
    private final String queueName;

    protected JmsQueueFactory(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public abstract Queue build() throws JMSException;
}
