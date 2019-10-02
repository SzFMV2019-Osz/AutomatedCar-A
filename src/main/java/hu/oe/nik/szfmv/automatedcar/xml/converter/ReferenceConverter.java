package hu.oe.nik.szfmv.automatedcar.xml.converter;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.xml.XmlParser;
import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Node;


/**
 * Referenciapontokat beolvasó és parse-oló objektum.
 * 
 * Mivel nem lett volna szép 2 objektumot létrehozni 3 változó tárolására, ezért itt
 * került implementálásra a parseolás.
 */
public class ReferenceConverter extends XmlAdapter<Object, Pair<String, Pair<Integer, Integer>>> {
    

    @Override
    public Pair<String, Pair<Integer, Integer>> unmarshal(Object element) throws Exception {
        
        Node imageNode = (Node)element;
        Node refPointsNode = imageNode.getFirstChild();
        
        String imageName = imageNode.getAttributes().getNamedItem(Consts.XML_ATTRIBUTE_NAME).getNodeValue();
        String xRef = refPointsNode.getAttributes().getNamedItem(Consts.XML_ATTRIBUTE_X).getNodeValue();
        String yRef = refPointsNode.getAttributes().getNamedItem(Consts.XML_ATTRIBUTE_Y).getNodeValue();

        Pair<Integer, Integer> refPair = new ImmutablePair<>(Integer.parseInt(xRef), Integer.parseInt(yRef));
        return new ImmutablePair<>(imageName, refPair);
    }

    @Override
    public Object marshal(Pair<String, Pair<Integer, Integer>> pair) throws Exception {
        throw new UnsupportedOperationException("Not allowed.");
    }

}