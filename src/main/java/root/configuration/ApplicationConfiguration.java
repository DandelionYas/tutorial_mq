package root.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import root.domain.SampleRequest;
import root.domain.SampleResponse;

@Configuration
@ComponentScan(value = "root")
@PropertySource({"classpath:config/file.properties",
        "classpath:config/jms.properties"})
public class ApplicationConfiguration {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() throws Exception {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(SampleRequest.class, SampleResponse.class);
        jaxb2Marshaller.afterPropertiesSet();
        return jaxb2Marshaller;
    }
}
