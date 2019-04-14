package root.configuration.jms;

import root.configuration.jms.activemq.AmqMessageReceiverContainerFactory;
import root.configuration.jms.activemq.AmqTemplateFactory;
import root.configuration.jms.activemq.AmqTextMessageSender;
import root.configuration.jms.common.JmsMessageReceiverContainerFactory;
import root.configuration.jms.common.JmsMessageSender;
import root.configuration.jms.ibmmq.IbmMessageReceiverContainerFactory;
import root.configuration.jms.ibmmq.IbmTemplateFactory;
import root.configuration.jms.ibmmq.IbmTextMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import root.service.receiver.SampleResponseReceiver;

import static java.lang.Integer.valueOf;

@Configuration
@SuppressWarnings("Duplicates")
public class JmsConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public JmsMessageSender jmsMessageSender() throws Exception {
        String host = environment.getProperty("request.host");
        String port = environment.getProperty("request.port");
        String queueManager = environment.getProperty("request.queue.manager");
        String queueName = environment.getProperty("request.queue.name");
        String jmsImplType = environment.getProperty("jms.impl.type");

        if (jmsImplType.equalsIgnoreCase("IBM")) {
            IbmTemplateFactory ibmTemplateFactory = new IbmTemplateFactory(host, port, queueManager, queueName, true);
            ibmTemplateFactory.afterPropertiesSet();
            return new IbmTextMessageSender(ibmTemplateFactory);
        } else {
            AmqTemplateFactory amqTemplateFactory = new AmqTemplateFactory(host, port, queueName, true);
            amqTemplateFactory.afterPropertiesSet();
            return new AmqTextMessageSender(amqTemplateFactory);
        }
    }

    @Bean
    @Autowired
    public JmsMessageReceiverContainerFactory jmsMessageReceiverContainerFactory(SampleResponseReceiver sampleResponseReceiver)
            throws Exception {
        String host = environment.getProperty("receive.host");
        String port = environment.getProperty("receive.port");
        String queueManager = environment.getProperty("receive.queue.manager");
        String queueName = environment.getProperty("receive.queue.name");
        String jmsImplType = environment.getProperty("jms.impl.type");
        Integer ibmReceiverThreadCount = valueOf(environment.getProperty("ibm.receiver.thread.count"));
        Integer amqReceiverThreadCount = valueOf(environment.getProperty("activemq.receiver.thread.count"));

        if (jmsImplType.equalsIgnoreCase("IBM")) {
            IbmMessageReceiverContainerFactory ibmMessageReceiverContainerFactory = new IbmMessageReceiverContainerFactory(
                    host, port, queueManager, queueName, true, sampleResponseReceiver, ibmReceiverThreadCount);

            ibmMessageReceiverContainerFactory.afterPropertiesSet();
            return ibmMessageReceiverContainerFactory;
        } else {
            AmqMessageReceiverContainerFactory amqMessageReceiverContainerFactory = new AmqMessageReceiverContainerFactory(
                    host, port, queueName, true, sampleResponseReceiver, amqReceiverThreadCount);

            amqMessageReceiverContainerFactory.afterPropertiesSet();
            return amqMessageReceiverContainerFactory;
        }
    }
}
