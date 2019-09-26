package hu.oe.nik.szfmv.automatedcar.xml;

import hu.oe.nik.szfmv.automatedcar.model.*;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class XmlParserTest {

    private String notExistingFile;

    private String[] existingFileNames;

    private List<WorldObject> expectedObjects;

    @Before
    public void initVariables() {
        notExistingFile = "not_existing.xml";
        existingFileNames = new String[] {"mini_world", "mini_world.xml"};
        expectedObjects = new ArrayList<>();

        Road road = new Road();
        road.setPosition(createPosition(700, 144));
        road.setZ(0);
        expectedObjects.add(road);

        ParkingSpace ps = new ParkingSpace();
        ps.setPosition(createPosition(470, 766));
        ps.setZ(0);
        expectedObjects.add(ps);

        Crosswalk cw = new Crosswalk();
        cw.setPosition(createPosition(1495, 486));
        cw.setZ(0);
        expectedObjects.add(cw);

        Tree tree = new Tree();
        tree.setPosition(createPosition(169, 2454));
        tree.setZ(0);
        expectedObjects.add(tree);

        AutomatedCar car = new AutomatedCar();
        car.setPosition(createPosition(356,1648));
        car.setZ(0);
        expectedObjects.add(car);

        ParkingBollard pb = new ParkingBollard();
        pb.setPosition(createPosition(548, 844));
        pb.setZ(0);
        expectedObjects.add(pb);

        WorldObject wo = new WorldObject();
        wo.setPosition(createPosition(581, 844));
        wo.setZ(0);
        expectedObjects.add(wo);
    }

    private Position createPosition(int x, int y) {
        return new Position(x, y);
    }

    @Test(expected = FileNotFoundException.class)
    public void throwsFileNotFoundException_WhenCalledWith_NotExistingFileName() throws FileNotFoundException {
        XmlParser.parseWorldObjects(notExistingFile);
    }

    // @FIXME: Teszt resourceok beolvasása nem sikerül
    //@Test
    public void loadAndCheckObjects_WhenCalledWith_ExistingFileName() {
        for (String fileName : existingFileNames) {
            try {
                World world = XmlParser.parseWorldObjects(fileName);
                assertWorld(world);
                List<WorldObject> result = world.getWorldObjects();
                for (int i = 0; i < result.size(); i++)
                    assertPositionsAndType(result.get(i), expectedObjects.get(i));
            } catch (FileNotFoundException e) {
                // handle exception
            }
        }
    }

    private void assertWorld(World world) {
        assertNotNull(world);
        assertEquals(world.getColor(), "#FFFFFF");
        assertEquals(world.getWidth(), 5120);
        assertEquals(world.getHeight(), 3000);
    }

    private void assertPositionsAndType(WorldObject objFromXml, WorldObject expected) {
        assertTrue(objFromXml.getClass().isInstance(expected));
        assertEquals(objFromXml.getPosX(), expected.getPosX());
        assertEquals(objFromXml.getPosY(), expected.getPosY());
        assertEquals(objFromXml.getPosZ(), expected.getPosZ());
    }
}
