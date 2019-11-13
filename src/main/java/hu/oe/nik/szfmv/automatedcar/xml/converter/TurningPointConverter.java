package hu.oe.nik.szfmv.automatedcar.xml.converter;

import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.TurningPoint;
import org.w3c.dom.Node;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TurningPointConverter extends XmlAdapter<Object, TurningPoint> {
    @Override
    public TurningPoint unmarshal(Object o) throws Exception {
        Node node = (Node) o;

        var x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
        var y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
        var movingDirection = Integer.parseInt(node.getAttributes().getNamedItem("movingDirection").getNodeValue());
        var movingSpeed = Integer.parseInt(node.getAttributes().getNamedItem("movingSpeed").getNodeValue());
        var turningDirection = Integer.parseInt(node.getAttributes().getNamedItem("turningDirection").getNodeValue());
        var turningSpeed = Integer.parseInt(node.getAttributes().getNamedItem("turningSpeed").getNodeValue());
        return new TurningPoint(new Position(x, y), movingDirection, movingSpeed, turningDirection, turningSpeed);
    }

    @Override
    public Object marshal(TurningPoint turningPoint) throws Exception {
        return null;
    }
}
