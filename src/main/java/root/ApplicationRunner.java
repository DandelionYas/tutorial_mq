package root;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import root.configuration.ApplicationConfiguration;
import root.domain.SampleRequest;
import root.service.sender.SampleRequestSender;

import java.util.Scanner;

public class ApplicationRunner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        SampleRequestSender sampleRequestSender =(SampleRequestSender) ctx.getBean("sampleRequestSender");
        System.out.println("MQ service started successfully...");
        System.out.println("Do you want to send a sample request?(N/y)");
        Scanner scanner = new Scanner(System.in);
        if("y".equals(scanner.nextLine())) {
            sendSampleRequest(sampleRequestSender);
        }
        System.out.println("Listening to receive Response...");
    }

    private static void sendSampleRequest(SampleRequestSender sampleRequestSender) {
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
