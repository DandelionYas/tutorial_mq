package root.service.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import root.configuration.jms.common.JmsMessageReceiver;
import root.domain.SampleResponse;
import root.service.marshaller.SampleResponseMarshaller;

import static java.lang.String.format;

@Component
public class SampleResponseReceiver extends JmsMessageReceiver<SampleResponse> {

    @Autowired
    protected SampleResponseReceiver(SampleResponseMarshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected void onReceive(SampleResponse sampleResponse) {
        System.out.println(format("Message received successfully. [Response: %s]", sampleResponse));
    }
}
