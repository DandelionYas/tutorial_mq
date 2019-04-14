package root.service.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import root.configuration.jms.common.JmsMessageSender;
import root.configuration.jms.common.JmsTemplateNotFoundException;
import root.configuration.marshal.MarshalException;
import root.domain.SampleRequest;
import root.service.marshaller.SampleRequestMarshaller;

import static java.lang.String.format;

@Component
public class SampleRequestSender {
    private final SampleRequestMarshaller sampleMarshaller;
    private final JmsMessageSender jmsMessageSender;

    @Autowired
    public SampleRequestSender(SampleRequestMarshaller sampleMarshaller, JmsMessageSender jmsMessageSender) {
        this.sampleMarshaller = sampleMarshaller;
        this.jmsMessageSender = jmsMessageSender;
    }

    public void send(SampleRequest sampleRequest) throws MarshalException, JmsTemplateNotFoundException {
        jmsMessageSender.send(sampleMarshaller.marshal(sampleRequest).getMessage());
        System.out.println(format("Message has been sent successfully.[Message: %s]", sampleRequest));
    }
}
