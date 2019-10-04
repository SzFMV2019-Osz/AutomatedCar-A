package hu.oe.nik.szfmv.automatedcar.xml.converter;

import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Node;


/**
 * Referenciapontokat beolvasó és parse-oló objektum.
 * 
 * Mivel nem lett volna szép 2 objektumot létrehozni 3 változó tárolására, ezért itt
 * került implementálásra a parseolás.
 */
public class ReferenceConverter extends XmlAdapter<Object, Pair<String, Position>> {
    

    @Override
    public Pair<String, Position> unmarshal(Object element) throws Exception {
        
        Node imageNode = (Node)element;
        Node refPointsNode = imageNode.getFirstChild();
        
        String imageName = imageNode.getAttributes().getNamedItem(Consts.XML_ATTRIBUTE_NAME).getNodeValue();
        String xRef = refPointsNode.getAttributes().getNamedItem(Consts.XML_ATTRIBUTE_X).getNodeValue();
        String yRef = refPointsNode.getAttributes().getNamedItem(Consts.XML_ATTRIBUTE_Y).getNodeValue();

        if ( !StringUtils.isNumeric(xRef)) {
            xRef = Consts.DEFAULT_VALUE_FOR_REFERENCE;
        }
        if ( !StringUtils.isNumeric(yRef)) {
            yRef = Consts.DEFAULT_VALUE_FOR_REFERENCE;
        }
        
        Position refPos = new Position(Integer.parseInt(xRef), Integer.parseInt(yRef));
        refPos.setZ(-1); // nem szükséges a forgatáshoz
        return new ImmutablePair<>(imageName, refPos);
    }

    @Override
    public Object marshal(Pair<String, Position> pair) throws Exception {
        throw new UnsupportedOperationException(Consts.ERROR_XML_WRITING_NOT_ALLOWED);
    }

}