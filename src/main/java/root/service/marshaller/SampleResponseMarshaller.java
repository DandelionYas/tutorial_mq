package root.service.marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import root.configuration.marshal.Marshaller;
import root.domain.SampleResponse;

@Component
public class SampleResponseMarshaller extends Marshaller<SampleResponse> {

    @Autowired
    protected SampleResponseMarshaller(Jaxb2Marshaller jaxb2Marshaller,
                                       @Value("#{environment['response.archive.path']}") String archivePath) {
        super(jaxb2Marshaller, archivePath);
    }
}
