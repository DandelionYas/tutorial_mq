package root.configuration.jms.activemq;

import root.configuration.jms.common.JmsMessageReceiver;
import root.configuration.jms.common.JmsMessageReceiverContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

public class AmqMessageReceiverContainerFactory extends JmsMessageReceiverContainerFactory {
    public AmqMessageReceiverContainerFactory(String host, String port, String queueName, Boolean cached, JmsMessageReceiver jmsMessageReceiver, Integer numberOfReceiverThreads) {
        super(host, port, null, queueName, cached, jmsMessageReceiver, numberOfReceiverThreads);
    }

    @Override
    protected ConnectionFactory initializeConnectionFactory() throws JMSException {
        return new AmqConnectionFactoryBuilder(getHost(), getPort(), isCached()).build();
    }

    @Override
    protected Queue initializeMqQueue() throws JMSException {
        return new AmqQueueFactory(getQueueName()).build();
    }
}
