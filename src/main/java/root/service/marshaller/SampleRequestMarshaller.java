package root.service.marshaller;

import root.configuration.marshal.Marshaller;
import root.domain.SampleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

@Component
public class SampleRequestMarshaller extends Marshaller<SampleRequest> {

    @Autowired
    protected SampleRequestMarshaller(Jaxb2Marshaller jaxb2Marshaller,
                                      @Value("#{environment['request.archive.path']}") String archivePath) {
        super(jaxb2Marshaller, archivePath);
    }
}
