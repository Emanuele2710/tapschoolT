
package org.giodabg.calculator;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.giodabg.calculator package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Studente_QNAME = new QName("http://calculator.giodabg.org/", "Studente");
    private final static QName _StudenteResponse_QNAME = new QName("http://calculator.giodabg.org/", "StudenteResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.giodabg.calculator
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Studente }
     * 
     */
    public Studente createStudente() {
        return new Studente();
    }

    /**
     * Create an instance of {@link StudenteResponse }
     * 
     */
    public StudenteResponse createStudenteResponse() {
        return new StudenteResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Studente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://calculator.giodabg.org/", name = "Studente")
    public JAXBElement<Studente> createStudente(Studente value) {
        return new JAXBElement<Studente>(_Studente_QNAME, Studente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StudenteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://calculator.giodabg.org/", name = "StudenteResponse")
    public JAXBElement<StudenteResponse> createStudenteResponse(StudenteResponse value) {
        return new JAXBElement<StudenteResponse>(_StudenteResponse_QNAME, StudenteResponse.class, null, value);
    }

}
