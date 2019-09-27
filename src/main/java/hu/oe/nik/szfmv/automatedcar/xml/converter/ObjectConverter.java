package hu.oe.nik.szfmv.automatedcar.xml.converter;

import hu.oe.nik.szfmv.automatedcar.model.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.Crosswalk;
import hu.oe.nik.szfmv.automatedcar.model.ParkingBollard;
import hu.oe.nik.szfmv.automatedcar.model.ParkingSpace;
import hu.oe.nik.szfmv.automatedcar.model.Road;
import hu.oe.nik.szfmv.automatedcar.model.Sign;
import hu.oe.nik.szfmv.automatedcar.model.Tree;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;

import hu.oe.nik.szfmv.automatedcar.xml.XmlParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;

/**
 * Nem sikerült mindent annotációval megoldani, ezért jött létre ez a segédosztály,
 * ami megmondja a JAXB-nek, hogy mit példányosítson.
 */
public class ObjectConverter extends XmlAdapter<Object, WorldObject> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public WorldObject unmarshal(Object element) throws Exception {
        Node node = (Node)element;
        String classType = node.getAttributes().getNamedItem("type").getNodeValue();
        Class<?> clazz = getClassByString(classType);

        JAXBContext jaxbContext = XmlParser.getJAXBContextFromCache(clazz);
        Binder<Node> binder = jaxbContext.createBinder();
        JAXBElement<?> jaxBElement = binder.unmarshal(node, clazz);
        return (WorldObject) jaxBElement.getValue();
    }

    private Class<?> getClassByString(String classType) {
        if (StringUtils.startsWith(classType,"road_2")){
            return Road.class;
        } else if (StringUtils.startsWith(classType,"tree")) {
            return Tree.class;
        } else if (StringUtils.startsWith(classType,"parking_space")) {
            return ParkingSpace.class;
        } else if (StringUtils.startsWith(classType,"crosswalk")) {
            return Crosswalk.class;
        } else if (StringUtils.startsWith(classType,"roadsign")) {
            return Sign.class;
        } else if (StringUtils.startsWith(classType, "car")) {
            return AutomatedCar.class;
        }  else if (StringUtils.startsWith(classType, "parking_bollard")) {
            return ParkingBollard.class;
        } else { // a fail-safe működés miatt került be, exception nem dobható, mert akkor az egész feldolgozás leáll
            logger.warn("Ismeretlen típus található az XML-ben: " + classType);
            return WorldObject.class;
        }
    }

    @Override
    public Object marshal(WorldObject worldObject) throws Exception {
        return worldObject;
    }
}
