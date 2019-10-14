package hu.oe.nik.szfmv.automatedcar.xml.converter;

import hu.oe.nik.szfmv.automatedcar.model.Car;
import hu.oe.nik.szfmv.automatedcar.model.Crosswalk;
import hu.oe.nik.szfmv.automatedcar.model.ParkingBollard;
import hu.oe.nik.szfmv.automatedcar.model.ParkingSpace;
import hu.oe.nik.szfmv.automatedcar.model.Road;
import hu.oe.nik.szfmv.automatedcar.model.Sign;
import hu.oe.nik.szfmv.automatedcar.model.Tree;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;

import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.xml.XmlParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;

import java.text.MessageFormat;

/**
 * Nem sikerült mindent annotációval megoldani, ezért jött létre ez a segédosztály,
 * ami megmondja a JAXB-nek, hogy mit példányosítson.
 */
public class ObjectConverter extends XmlAdapter<Object, IObject> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public IObject unmarshal(Object element) throws Exception {
        try {
            Node node = (Node)element;
            String classType = node.getAttributes().getNamedItem(Consts.XML_ATTRIBUTE_TYPE).getNodeValue();
            Class<?> clazz = getClassByString(classType);

            JAXBContext jaxbContext = XmlParser.getJAXBContextFromCache(clazz);
            Binder<Node> binder = jaxbContext.createBinder();
            JAXBElement<?> jaxBElement = binder.unmarshal(node, clazz);
            return (WorldObject) jaxBElement.getValue();
        } catch (Exception e) {
            // logolunk, mert a JAXB elnyeli az exceptiont és üres listával tér vissza
            logger.error(Consts.ERROR_IN_WORLDOBJECT_PARSING, e);
            throw e;
        }
    }

    // @TODO: static blokkban inicializálni egy map-et és azt használni
    private Class<?> getClassByString(String classType) {
        if (StringUtils.startsWith(classType, Consts.RES_IDENTIFIER_ROAD) || StringUtils.startsWith(classType, Consts.RES_IDENTIFIER_ROAD_CROSSROAD)) {
            return Road.class;
        } else if (StringUtils.startsWith(classType, Consts.RES_IDENTIFIER_TREE)) {
            return Tree.class;
        } else if (StringUtils.startsWith(classType, Consts.RES_IDENTIFIER_PARKINGSPACE)) {
            return ParkingSpace.class;
        } else if (StringUtils.startsWith(classType,  Consts.RES_IDENTIFIER_CROSSWALK)) {
            return Crosswalk.class;
        } else if (StringUtils.startsWith(classType, Consts.RES_IDENTIFIER_SIGN)) {
            return Sign.class;
        } else if (StringUtils.startsWith(classType, Consts.RES_IDENTIFIER_CAR)) {
            return Car.class;
        }  else if (StringUtils.startsWith(classType, Consts.RES_IDENTIFIER_BOLLARD)) {
            return ParkingBollard.class;
        } else { // a fail-safe működés miatt került be, exception nem dobható, mert akkor az egész feldolgozás leáll
            logger.warn(MessageFormat.format(Consts.XML_UNKNOWN_TYPE, classType));
            return WorldObject.class;
        }
    }

    @Override
    public Object marshal(IObject v) throws Exception {
        throw new UnsupportedOperationException(Consts.ERROR_XML_WRITING_NOT_ALLOWED);
    }

}
