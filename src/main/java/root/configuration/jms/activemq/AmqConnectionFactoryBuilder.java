package root.configuration.jms.activemq;

import root.configuration.jms.common.JmsConnectionFactoryBuilder;
import org.apache.activemq.spring.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import static java.lang.String.format;

public class AmqConnectionFactoryBuilder extends JmsConnectionFactoryBuilder {
    private static final String URL_PATTERN = "tcp://%s:%s";

    protected AmqConnectionFactoryBuilder(String host, String port, Boolean cashed) {
        super(host, port, null, cashed);
    }

    @Override
    public ConnectionFactory build() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(format(URL_PATTERN, getHost(), getPort()));
        if (isCached()) {
            return coverWithCachingConnectionFactory(connectionFactory);
        } else {
            return connectionFactory;
        }
    }
}
