package Test;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.Constants;
import org.apache.axis2.client.ServiceClient;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.om.OMNamespace;

public class ServiceWebClient
{
    // endpoint reference to which a message is directed
    private static EndpointReference targetEPR = new EndpointReference(
            "http://localhost:11000/axis2/services/ServiceCalculette");

    public static OMElement creeAdditionnerOMElement()
    {
        SOAPFactory fac = OMAbstractFactory.getSOAP12Factory();
        OMNamespace omNs = fac.createOMNamespace("http://test.org",
                                                 "foo");
        OMElement method = fac.createOMElement("additionner", omNs);
        OMElement param1 = fac.createOMElement("param1", omNs);
        OMElement param2 = fac.createOMElement("param2", omNs);
        param1.addChild(fac.createOMText(param1, "1"));
        param2.addChild(fac.createOMText(param2, "2"));
        method.addChild(param1);
        method.addChild(param2);

        return method;
    }

    public static void main(String[] args)
    {
        try
        {
            OMElement payload = creeAdditionnerOMElement();
            Options options = new Options();
            // message destination
            options.setTo(targetEPR);
            // transport to use
            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);

            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);
            // sending the request...
            OMElement result = sender.sendReceive(payload);

            System.out.println(result.toString());
        }
        catch (Exception e)
        { // (XMLStreamException e) {
            System.out.println(e.toString());
        }
    }
}
