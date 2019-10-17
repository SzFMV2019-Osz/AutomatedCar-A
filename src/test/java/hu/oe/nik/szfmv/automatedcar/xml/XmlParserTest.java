package hu.oe.nik.szfmv.automatedcar.xml;

import hu.oe.nik.szfmv.automatedcar.model.Car;
import hu.oe.nik.szfmv.automatedcar.model.Crosswalk;
import hu.oe.nik.szfmv.automatedcar.model.ParkingBollard;
import hu.oe.nik.szfmv.automatedcar.model.ParkingSpace;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.References;
import hu.oe.nik.szfmv.automatedcar.model.Road;
import hu.oe.nik.szfmv.automatedcar.model.Sign;
import hu.oe.nik.szfmv.automatedcar.model.Tree;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import java.util.ArrayList;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class XmlParserTest {

    /**
     * Mivel a double kalkulált érték, meg kell adni egy tűréshatárt, amit még elfogadunk.
     */
    private final Double maxDelta = 1e-10;

    private String notExistingFile;

    private String existingFileNameForWorldParser;

    private String existingFileNameForReferenceParser;
    
    private World expectedWorld;

    private List<Pair<String, Position>> expectedReferences;
    
    @Before
    public void initVariables() {
        notExistingFile = "not_existing.xml";
        initWorldParserVariables();
        initReferenceParserVariables();
    }
    
    private void initWorldParserVariables() {
        existingFileNameForWorldParser = "mini_world";

        expectedWorld = new World(5120, 3000, "#FFFFFF");

        Road road = new Road();
        road.setPosition(createPosition(1700, 144));
        road.setZ(0);
        road.setRotation(ModelCommonUtil.getRotationValue(0, 1, -1, 0));
        expectedWorld.getWorldObjects().add(road);

        ParkingSpace ps = new ParkingSpace();
        ps.setPosition(createPosition(470, 766));
        ps.setZ(1);
        ps.setRotation(ModelCommonUtil.getRotationValue(1, 0, 0, 1));
        expectedWorld.getWorldObjects().add(ps);

        Crosswalk cw = new Crosswalk();
        cw.setPosition(createPosition(1495, 486));
        cw.setZ(1);
        cw.setRotation(ModelCommonUtil.getRotationValue(0, -1, 1, 0));
        expectedWorld.getWorldObjects().add(cw);

        Sign sign = new Sign();
        sign.setPosition(createPosition(4039, 1431));
        sign.setZ(3);
        sign.setRotation(ModelCommonUtil.getRotationValue(0, -1, 1, 0));
        expectedWorld.getWorldObjects().add(sign);

        Tree tree = new Tree();
        tree.setPosition(createPosition(169, 2454));
        tree.setZ(4);
        tree.setRotation(ModelCommonUtil.getRotationValue(1, 0, 0, 1));
        expectedWorld.getWorldObjects().add(tree);

        Car car1 = new Car();
        car1.setPosition(createPosition(356, 1648));
        car1.setZ(0);
        car1.setRotation(ModelCommonUtil.getRotationValue(1, 0, 0, 1));
        expectedWorld.getWorldObjects().add(car1);
        
        Car car2 = new Car();
        car2.setPosition(createPosition(656, 2758));
        car2.setZ(0);
        car2.setRotation(ModelCommonUtil.getRotationValue(1, 0, 0, 1));
        expectedWorld.getWorldObjects().add(car2);
        
        Car car3 = new Car();
        car3.setPosition(createPosition(356, 1648));
        car3.setZ(0);
        car3.setRotation(ModelCommonUtil.getRotationValue(1, 0, 0, 1));
        expectedWorld.getWorldObjects().add(car3);

        ParkingBollard pb = new ParkingBollard();
        pb.setPosition(createPosition(581, 844));
        pb.setZ(2);
        pb.setRotation(ModelCommonUtil.getRotationValue(1, 0, 0, 1));
        expectedWorld.getWorldObjects().add(pb);

        WorldObject wo = new WorldObject();
        wo.setPosition(createPosition(581, 844));
        wo.setZ(0);
        wo.setRotation(ModelCommonUtil.getRotationValue(1, 0, 0, 1));
        expectedWorld.getWorldObjects().add(wo);
    }

    private void initReferenceParserVariables() {
        existingFileNameForReferenceParser = "mini_reference";
        expectedReferences = new ArrayList<>();
        expectedReferences.add(createReference("road_2lane_rotary.png", 234, 875));
        expectedReferences.add(createReference("2_crossroad_1.png", 0, 875));
        expectedReferences.add(createReference("road_2lane_90right", 349, 525));
        expectedReferences.add(createReference("road_2lane_45left.png", 51, 371));
        expectedReferences.add(createReference("car_2_red", 51, 104));
    }
    
    
    private Pair createReference(String fileName, int x, int y) {
        Position refs = createPosition(x, y);
        return new ImmutablePair<>(fileName, refs);
    }
    
    private Position createPosition(int x, int y) {
        return new Position(x, y);
    }

    @Test(expected = NullPointerException.class)
    public void worldParser_ThrowsNullPointerException_WhenCalledWith_NotExistingFileName()
                                                                throws NullPointerException {
        XmlParser.parseWorldObjects(notExistingFile);
    }

    @Test
    public void worldParser_LoadAndCheckObjects_WhenCalledWith_ExistingFileName() {
        World world = XmlParser.parseWorldObjects(existingFileNameForWorldParser);
        assertWorld(world);
        List<IObject> result = expectedWorld.getWorldObjects();
        for (int i = 0; i < result.size(); i++) {
            assertPositionsAndType(world.getWorldObjects().get(i), expectedWorld.getWorldObjects().get(i));
        }
    }

    private void assertWorld(World world) {
        assertNotNull(world);
        assertEquals(expectedWorld.getColor(), world.getColor());
        assertEquals(expectedWorld.getWidth(), world.getWidth());
        assertEquals(expectedWorld.getHeight(), world.getHeight());
    }

    private void assertPositionsAndType(IObject objFromXml, IObject expected) {
        assertEquals(expected.getClass().toString(), objFromXml.getClass().toString());
        assertEquals(expected.getPosX(), objFromXml.getPosX());
        assertEquals(expected.getPosY(), objFromXml.getPosY());
        assertEquals(expected.getPosZ(), objFromXml.getPosZ());
        assertEquals(expected.getRotation(), objFromXml.getRotation(), maxDelta);
        if (StringUtils.equals(((WorldObject)objFromXml).getImageFileName(), "unknown_type")) {
            assertNull(objFromXml.getImage());
        } else {
            assertNotNull(objFromXml.getImage());
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void referenceParser_ThrowsNullPointerException_WhenCalledWith_NotExistingFileName() {
        XmlParser.parseReferences(notExistingFile);
    }
    
    @Test
    public void referenceParser_LoadAndCheckReferencePoint_WhenCalledWith_ExistingFileName() {
        References referencesFromXml = XmlParser.parseReferences(existingFileNameForReferenceParser);
        for (Pair<String, Position> expectedReference : expectedReferences) {
            String fileName = expectedReference.getKey();
            assertReferences(expectedReference.getValue(), referencesFromXml.getReference(fileName));
        }
    }
    
    @Test
    public void xmlParserSettedProperty_NoOptimize_ToExpectedConstantValue_After_Parsing() {
        Properties prop = System.getProperties();
        String noOptProp = prop.getProperty(Consts.PROP_KEY_XML_NO_OPTIMIZE);
        assertTrue("The no optimize property is null.", StringUtils.isNotBlank(noOptProp));
        if (Boolean.parseBoolean(Consts.PROP_VALUE_XML_NO_OPTIMIZE)) {
            assertTrue("The no optimize property is false.", Boolean.parseBoolean(noOptProp));
        } else {
            assertFalse("The no optimize property is true.", Boolean.parseBoolean(noOptProp));
        }
    }
    
    private void assertReferences(Position expected, Position objFromXml) {
        assertEquals(expected.getX(), objFromXml.getX());
        assertEquals(expected.getY(), objFromXml.getY());
    }

    @Test
    public void signZIndex_ShouldBeHigherThan_RoadZIndex() {
        World world = XmlParser.parseWorldObjects(existingFileNameForWorldParser);
        Road road = (Road)findObjectInWorld(world, Road.class);
        Sign sign = (Sign)findObjectInWorld(world, Sign.class);

        assertNotNull(world);
        assertNotNull(road);
        assertNotNull(sign);
        assertTrue(sign.getPosZ() > road.getPosZ());
    }

    @Test
    public void treeZIndex_ShouldBeHigherThan_RoadZIndex() {
        World world = XmlParser.parseWorldObjects(existingFileNameForWorldParser);
        Road road = (Road)findObjectInWorld(world, Road.class);
        Tree tree = (Tree)findObjectInWorld(world, Tree.class);

        assertNotNull(world);
        assertNotNull(road);
        assertNotNull(tree);
        assertTrue(tree.getPosZ() > road.getPosZ());
    }

    @Test
    public void treeZIndex_ShouldBeHigherThan_SignZIndex() {
        World world = XmlParser.parseWorldObjects(existingFileNameForWorldParser);
        Sign sign = (Sign)findObjectInWorld(world, Sign.class);
        Tree tree = (Tree)findObjectInWorld(world, Tree.class);

        assertNotNull(world);
        assertNotNull(sign);
        assertNotNull(tree);
        assertTrue(tree.getPosZ() > sign.getPosZ());
    }

    private <T> IObject findObjectInWorld(World world, Class<T> type) {
        for (IObject object : world.getWorldObjects()) {
            if (type.isInstance(object)) {
                return object;
            }
        }

        return null;
    }
}
