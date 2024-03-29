package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

import java.awt.geom.Ellipse2D;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.lang3.StringUtils;

/**
 * Tábla alaposztály.
 */
public class Sign extends WorldObject implements IStatic, ICrashable {

    public double getWeight() {
        return 0;
    }

    @Override
    public void crashed() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        super.afterUnmarshal(u, parent);
        this.position.setZ(3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape() {
        int x = this.width / 2 - 5;
        int y = this.height / 2 - 5;
        this.polygons.add(new Ellipse2D.Float(x, y, 10, 10));
    }
    
    public String getSpeedLimit() {
        String speed = StringUtils.substring(getImageFileName(), getImageFileName().length()-2);
        if (StringUtils.isNumeric(speed)) {
            return speed;
        }
        return "No limit";
    }
}
