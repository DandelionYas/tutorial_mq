package root.configuration.jms.ibmmq;

import com.ibm.mq.jms.MQQueue;
import root.configuration.jms.common.JmsQueueFactory;

import javax.jms.JMSException;
import javax.jms.Queue;

public class IbmQueueFactory extends JmsQueueFactory {
    protected IbmQueueFactory(String queueName) {
        super(queueName);
    }

    @Override
    public Queue build() throws JMSException {
        MQQueue mqQueue = new MQQueue(getQueueName());
        mqQueue.setPersistence(1);
        mqQueue.setTargetClient(1);
        return mqQueue;
    }
}
