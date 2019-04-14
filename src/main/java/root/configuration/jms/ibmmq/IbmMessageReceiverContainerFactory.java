package root.configuration.jms.ibmmq;

import root.configuration.jms.common.JmsMessageReceiver;
import root.configuration.jms.common.JmsMessageReceiverContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

public class IbmMessageReceiverContainerFactory extends JmsMessageReceiverContainerFactory {
    public IbmMessageReceiverContainerFactory(String host,
                                              String port,
                                              String queueManager,
                                              String queueName,
                                              Boolean cached,
                                              JmsMessageReceiver jmsMessageReceiver,
                                              Integer numberOfReceiverThreads) {
        super(host, port, queueManager, queueName, cached, jmsMessageReceiver, numberOfReceiverThreads);
    }

    @Override
    protected ConnectionFactory initializeConnectionFactory() throws JMSException {
        return new IbmConnectionFactoryBuilder(getHost(), getPort(), getQueueManager(), isCached()).build();
    }

    @Override
    protected Queue initializeMqQueue() throws JMSException {
        return new IbmQueueFactory(getQueueName()).build();
    }
}
