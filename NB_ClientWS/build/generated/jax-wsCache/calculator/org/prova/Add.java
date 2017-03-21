
package org.prova;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per add complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="add">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="num1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="num2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "add", propOrder = {
    "num1",
    "num2"
})
public class Add {

    protected int num1;
    protected int num2;

    /**
     * Recupera il valore della proprietà num1.
     * 
     */
    public int getNum1() {
        return num1;
    }

    /**
     * Imposta il valore della proprietà num1.
     * 
     */
    public void setNum1(int value) {
        this.num1 = value;
    }

    /**
     * Recupera il valore della proprietà num2.
     * 
     */
    public int getNum2() {
        return num2;
    }

    /**
     * Imposta il valore della proprietà num2.
     * 
     */
    public void setNum2(int value) {
        this.num2 = value;
    }

}
