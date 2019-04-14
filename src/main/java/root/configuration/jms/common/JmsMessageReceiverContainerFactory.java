package root.configuration.jms.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

public abstract class JmsMessageReceiverContainerFactory implements InitializingBean {
    private final String host;
    private final String port;
    private final String queueManager;
    private final String queueName;
    private final Boolean cached;
    private final JmsMessageReceiver jmsMessageReceiver;
    private final Integer numberOfReceiverThreads;
    private DefaultMessageListenerContainer defaultMessageListenerContainer;

    public JmsMessageReceiverContainerFactory(String host,
                                              String port,
                                              String queueManager,
                                              String queueName,
                                              Boolean cached,
                                              JmsMessageReceiver jmsMessageReceiver,
                                              Integer numberOfReceiverThreads) {
        this.host = host;
        this.port = port;
        this.queueManager = queueManager;
        this.queueName = queueName;
        this.cached = cached;
        this.jmsMessageReceiver = jmsMessageReceiver;
        this.numberOfReceiverThreads = numberOfReceiverThreads;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getQueueManager() {
        return queueManager;
    }

    public String getQueueName() {
        return queueName;
    }

    public Boolean isCached() {
        return cached;
    }

    public DefaultMessageListenerContainer getDefaultMessageListenerContainer() {
        return defaultMessageListenerContainer;
    }

    protected abstract ConnectionFactory initializeConnectionFactory() throws JMSException;

    protected abstract Queue initializeMqQueue() throws JMSException;

    @Override
    public void afterPropertiesSet() throws Exception {
        defaultMessageListenerContainer = initializeDefaultMessageListenerContainer();
    }

    private DefaultMessageListenerContainer initializeDefaultMessageListenerContainer() throws JMSException {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(initializeConnectionFactory());
        defaultMessageListenerContainer.setDestination(initializeMqQueue());
        defaultMessageListenerContainer.setMessageListener(jmsMessageReceiver);
        defaultMessageListenerContainer.setConcurrentConsumers(numberOfReceiverThreads);
        defaultMessageListenerContainer.setAcceptMessagesWhileStopping(false);
        defaultMessageListenerContainer.setRecoveryInterval(10000);
        defaultMessageListenerContainer.afterPropertiesSet();
        defaultMessageListenerContainer.start();
        return defaultMessageListenerContainer;
    }
}
