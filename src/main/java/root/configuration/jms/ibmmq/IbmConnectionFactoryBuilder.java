package root.configuration.jms.ibmmq;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import root.configuration.jms.common.JmsConnectionFactoryBuilder;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import static com.ibm.msg.client.jms.JmsConstants.CCSID_UTF8;
import static com.ibm.msg.client.jms.JmsConstants.JMS_IBM_CHARACTER_SET;
import static com.ibm.msg.client.wmq.common.CommonConstants.WMQ_CM_CLIENT;
import static com.ibm.msg.client.wmq.common.CommonConstants.WMQ_CONNECTION_MODE;
import static java.lang.Integer.parseInt;

public class IbmConnectionFactoryBuilder extends JmsConnectionFactoryBuilder {
    protected IbmConnectionFactoryBuilder(String host, String port, String queueManager, Boolean cached) {
        super(host, port, queueManager, cached);
    }

    @Override
    public ConnectionFactory build() throws JMSException {
        MQQueueConnectionFactory connectionFactory = new MQQueueConnectionFactory();
        connectionFactory.setHostName(getHost());
        connectionFactory.setPort(parseInt(getPort()));
        connectionFactory.setQueueManager(getQueueManager());
        connectionFactory.setIntProperty(WMQ_CONNECTION_MODE, WMQ_CM_CLIENT);
        connectionFactory.setIntProperty(JMS_IBM_CHARACTER_SET, CCSID_UTF8);
        if (isCached()) {
            return coverWithCachingConnectionFactory(connectionFactory);
        } else {
            return connectionFactory;
        }
    }
}
