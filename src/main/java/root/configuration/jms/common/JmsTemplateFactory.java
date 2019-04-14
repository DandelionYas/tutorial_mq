package root.configuration.jms.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.Assert;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;

public abstract class JmsTemplateFactory  implements InitializingBean {
    private static final String ERROR_MSG = "JmsTemplate must not be null, Please call afterPropertiesSet method after construction.";
    private final String host;
    private final String port;
    private final String queueManager;
    private final String queueName;
    private final Boolean cached;
    private JmsTemplate jmsTemplate;

    public JmsTemplateFactory(String host, String port, String queueManager, String queueName, Boolean cached) {
        this.host = host;
        this.port = port;
        this.queueManager = queueManager;
        this.queueName = queueName;
        this.cached = cached;
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

    public Boolean getCached() {
        return cached;
    }

    protected JmsTemplate initializeJmsTemplate() throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory((initializeConnectionFactory()));
        jmsTemplate.setDefaultDestination(initializeQueue());
        jmsTemplate.afterPropertiesSet();
        return jmsTemplate;
    }

    protected abstract ConnectionFactory initializeConnectionFactory() throws JMSException;

    protected abstract Destination initializeQueue() throws JMSException;

    public JmsTemplate getJmsTemplate() {
        Assert.notNull(jmsTemplate, ERROR_MSG);
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
