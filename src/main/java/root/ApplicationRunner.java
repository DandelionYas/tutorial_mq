package root;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import root.configuration.ApplicationConfiguration;
import root.domain.SampleRequest;
import root.service.sender.SampleRequestSender;

public class ApplicationRunner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        SampleRequestSender sampleRequestSender =(SampleRequestSender) ctx.getBean("sampleRequestSender");
        System.out.println("MQ service started successfully...");
        SampleRequest request = new SampleRequest();
        request.setHeader("RequestHeader");
        request.setRequest("TestRequest");
        try {
            sampleRequestSender.send(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
