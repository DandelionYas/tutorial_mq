package root.domain;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"header","request"})
@XmlRootElement(name = "SampleRequest")
public class SampleRequest {
    @XmlElement(name = "Header", required = true)
    private String header;
    @XmlElement(name = "Request")
    private String request;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "SampleRequest{" +
                "header='" + header + '\'' +
                ", request='" + request + '\'' +
                '}';
    }
}
