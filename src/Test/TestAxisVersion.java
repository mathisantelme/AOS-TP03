package Test;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.Constants;
import org.apache.axis2.client.ServiceClient;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.om.OMNamespace;

public class TestAxisVersion
{
    // endpoint reference to which a message is directed
    private static EndpointReference targetEPR = new EndpointReference(
            "http://localhost:8080/axis2/services/Version/");

    public static OMElement getVersionOMElement()
    {
        SOAPFactory fac = OMAbstractFactory.getSOAP12Factory();
        // namespace creation
        OMNamespace omNs = fac.createOMNamespace("http://test.org", "cms");
        // operation called
        OMElement method = fac.createOMElement("getVersion", omNs);
        return method;
    }

    public static void main(String[] args)
    {
        try
        {
            // get message content
            OMElement payload = getVersionOMElement();
            // message parameters
            Options options = new Options();
            // message destination
            options.setTo(targetEPR);
            // transport to use
            options.setTransportInProtocol("tcp");

            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);
            // sending the request...
            OMElement result = sender.sendReceive(payload);

            System.out.println(result.toString());
        }
        catch (Exception e)
        { 
            System.out.println(e.toString());
        }
    }
}
