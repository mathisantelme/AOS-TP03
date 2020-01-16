package Test;

import javax.xml.stream.XMLStreamException;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPFactory;

public class ServiceWeb
{

    public OMElement additionner(OMElement element)
            throws XMLStreamException
    {

        // permet de s'assurer que l'ensemble des donnes a bien ete recu
        element.build();
        // detache l'element de son document pour l'utiliser
        element.detach();

        // analyse du message recu
        String methodElementName = element.getLocalName();
        // a completer !...

        SOAPFactory factory = OMAbstractFactory.getSOAP12Factory();
        OMNamespace namespace = factory.createOMNamespace(
                "http://test.org", "foo");
        OMElement resultElem = factory.createOMElement("resultat",
                namespace);

        resultElem.setText("test");

        return resultElem;
    }
}
