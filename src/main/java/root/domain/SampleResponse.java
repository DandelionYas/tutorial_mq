package root.domain;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"header", "response"})
@XmlRootElement(name = "SampleResponse")
public class SampleResponse {
    @XmlElement(name = "Header", required = true)
    private String header;
    @XmlElement(name = "Response")
    private String response;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SampleRequest{" +
                "header='" + header + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}