package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.xml.converter.TurningPointConverter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "TurningPoints")
@XmlAccessorType(XmlAccessType.FIELD)
public class TurningPoints {
    @XmlElement(name = "TurningPoint")
    @XmlJavaTypeAdapter(TurningPointConverter.class)
    public List<TurningPoint> turningPoints;
}
