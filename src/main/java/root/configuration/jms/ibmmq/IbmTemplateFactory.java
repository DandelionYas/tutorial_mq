package root.configuration.jms.ibmmq;

import com.ibm.mq.jms.MQQueue;
import root.configuration.jms.common.JmsTemplateFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

public class IbmTemplateFactory extends JmsTemplateFactory {
    public IbmTemplateFactory(String host, String port, String queueManager, String queueName, Boolean cached) {
        super(host, port, queueManager, queueName, cached);
    }

    @Override
    protected ConnectionFactory initializeConnectionFactory() throws JMSException {
        return new IbmConnectionFactoryBuilder(getHost(), getPort(), getQueueManager(), getCached()).build();
    }

    @Override
    protected Queue initializeQueue() throws JMSException {
        MQQueue mqQueue = new MQQueue(getQueueName());
        mqQueue.setPersistence(1);
        mqQueue.setTargetClient(1);
        return mqQueue;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setJmsTemplate(initializeJmsTemplate());
    }
}
