package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;
import hu.oe.nik.szfmv.automatedcar.xml.converter.DoubleConverter;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Position")
public class Transform {
    
    @XmlAttribute(name = "m11", required = true)
    @XmlJavaTypeAdapter(DoubleConverter.class)
    private double m11;

    @XmlAttribute(name = "m12", required = true)
    @XmlJavaTypeAdapter(DoubleConverter.class)
    private double m12;

    @XmlAttribute(name = "m21", required = true)
    @XmlJavaTypeAdapter(DoubleConverter.class)
    private double m21;

    @XmlAttribute(name = "m22", required = true)
    @XmlJavaTypeAdapter(DoubleConverter.class)
    private double m22;

    @XmlTransient
    private double rotation;

    /**
     *
     * @param m11 Distance from top left corner
     * @param m12 Distance from top right corner
     * @param m21 Distance from bottom left corner
     * @param m22 Distance from bottom right corner
     */
    public Transform(double m11, double m12, double m21, double m22){
        this.m11 = m11;
        this.m12 = m12;
        this.m21 = m21;
        this.m22 = m22;
    }

    public Transform(){
        this.m11 = 0.0;
        this.m12 = 0.0;
        this.m21 = 0.0;
        this.m22 = 0.0;
    }

    public double getRotation(){
        return this.rotation;
    }

    public void setRotation(double rotation){
        this.rotation = rotation;
    }

    /**
     * Calculates and gets the rotation value from the distance of the corners
     * @return calculated rotation
     */
    private void calculateRotation(){
        this.rotation = ModelCommonUtil.getRotationValue(m11, m12, m21, m22);
    }

    /**
     * JAXB unmarshaller event listenerje.
     * Az objektum felépítése után hívódik meg, a kép betöltéséért felel.
     *
     * @param u unmarshaller
     * @param parent JAXBElement
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        calculateRotation();
    }
}
