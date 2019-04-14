package root.configuration.jms.activemq;

import root.configuration.jms.common.JmsQueueFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.JMSException;
import javax.jms.Queue;

public class AmqQueueFactory extends JmsQueueFactory {
    protected AmqQueueFactory(String queueName) {
        super(queueName);
    }

    @Override
    public Queue build() throws JMSException {
        return new ActiveMQQueue(getQueueName());
    }
}
