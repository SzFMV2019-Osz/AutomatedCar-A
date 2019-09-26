package hu.oe.nik.szfmv.automatedcar.xml.converter;

import hu.oe.nik.szfmv.automatedcar.model.*;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

public class ObjectConverter extends XmlAdapter<Object, WorldObject> {

    @Override
    public WorldObject unmarshal(Object element) throws Exception {
        Node node = (Node)element;
        String classType = node.getAttributes().getNamedItem("type").getNodeValue();
        Class<?> clazz;

        if (StringUtils.startsWith(classType,"road_2")){
            clazz = Road.class;
        } else if (StringUtils.startsWith(classType,"tree")) {
            clazz = Tree.class;
        } else if (StringUtils.startsWith(classType,"parking_space")) {
            clazz = ParkingSpace.class;
        } else if (StringUtils.startsWith(classType,"crosswalk")) {
            clazz = Crosswalk.class;
        } else if (StringUtils.startsWith(classType,"roadsign")) {
            clazz = Sign.class;
        } else if (StringUtils.startsWith(classType, "car")){
            clazz = AutomatedCar.class;
        }  else { // a fail-safe működés miatt került be, lehetne exceptiont is dobni
            clazz = WorldObject.class;
        }

        JAXBContext jc = JAXBContext.newInstance(clazz);
        Binder<Node> binder = jc.createBinder();
        JAXBElement<?> jaxBElement = binder.unmarshal(node, clazz);
        return (WorldObject) jaxBElement.getValue();
    }

    @Override
    public Object marshal(WorldObject worldObject) throws Exception {
        return null;
    }
}
