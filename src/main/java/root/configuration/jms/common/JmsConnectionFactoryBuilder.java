package root.configuration.jms.common;

import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public abstract class JmsConnectionFactoryBuilder {
    private final String host;
    private final String port;
    private final String queueManager;
    private final Boolean cached;

    protected JmsConnectionFactoryBuilder(String host, String port, String queueManager, Boolean cached) {
        this.host = host;
        this.port = port;
        this.queueManager = queueManager;
        this.cached = cached;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    protected String getQueueManager() {
        return queueManager;
    }

    protected Boolean isCached() {
        return cached;
    }

    public abstract ConnectionFactory build() throws JMSException;

    protected CachingConnectionFactory coverWithCachingConnectionFactory(ConnectionFactory connectionFactory) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(connectionFactory);
        cachingConnectionFactory.setReconnectOnException(true);
        cachingConnectionFactory.setSessionCacheSize(1);
        cachingConnectionFactory.afterPropertiesSet();
        return cachingConnectionFactory;
    }
}
