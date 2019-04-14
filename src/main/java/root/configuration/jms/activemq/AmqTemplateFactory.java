package root.configuration.jms.activemq;

import root.configuration.jms.common.JmsTemplateFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

public class AmqTemplateFactory extends JmsTemplateFactory {
    public AmqTemplateFactory(String host, String port, String queueName, Boolean cached) {
        super(host, port, null, queueName, cached);
    }

    @Override
    protected ConnectionFactory initializeConnectionFactory() throws JMSException {
        return new AmqConnectionFactoryBuilder(getHost(), getPort(), getCached()).build();
    }

    @Override
    protected Queue initializeQueue() throws JMSException {
        return new AmqQueueFactory(getQueueName()).build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setJmsTemplate(initializeJmsTemplate());
    }
}
