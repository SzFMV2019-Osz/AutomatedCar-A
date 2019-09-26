package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.xml.converter.DoubleConverter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Position")
public class Transform {
    
    @XmlAttribute(name = "m11", required = true)
    @XmlJavaTypeAdapter(DoubleConverter.class)
    private Double m11;

    @XmlAttribute(name = "m12", required = true)
    @XmlJavaTypeAdapter(DoubleConverter.class)
    private Double m12;

    @XmlAttribute(name = "m21", required = true)
    @XmlJavaTypeAdapter(DoubleConverter.class)
    private Double m21;

    @XmlAttribute(name = "m22", required = true)
    @XmlJavaTypeAdapter(DoubleConverter.class)
    private Double m22;

    @XmlTransient
    private Double rotation;

    /**
     *
     * @param m11 Distance from top left corner
     * @param m12 Distance from top right corner
     * @param m21 Distance from bottom left corner
     * @param m22 Distance from bottom right corner
     */
    public Transform(double m11, Double m12, Double m21, Double m22){
        this.m11 = m11;
        this.m12 = m12;
        this.m21 = m21;
        this.m22 = m22;
        this.rotation = this.calculateRotation();
    }

    public Transform(){
        this.m11 = 0.0;
        this.m12 = 0.0;
        this.m21 = 0.0;
        this.m22 = 0.0;
        this.rotation = this.calculateRotation();
    }

    public Double getRotation(){
        return this.rotation;
    }

    public void setRotation(double rotation){
        this.rotation = rotation;
    }

    /**
     * Calculates and gets the rotation value from the distance of the corners
     * @return calculated rotation
     */
    private Double calculateRotation(){
        // TODO calculate rotation
        return 0.0;
    }
}
