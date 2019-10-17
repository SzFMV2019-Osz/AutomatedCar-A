package hu.oe.nik.szfmv.automatedcar.model.utility;

public final class Consts {

    // --- Values

    public static final double MIN_DEGREE = 0;
    public static final double MAX_DEGREE = 360;
    public static final String DEFAULT_VALUE_FOR_REFERENCE = "0";

    // --- Resources

    public static final String SUFFIX_IMAGE = ".png";
    public static final String SUFFIX_XML = ".xml";
    public static final String RES_FILENAME_BOLLARD = "bollard";
    public static final String RES_IDENTIFIER_ROAD = "road_2";
    public static final String RES_IDENTIFIER_ROAD_CROSSROAD = "2_crossroad";
    public static final String RES_IDENTIFIER_ROAD_STRAIGHT = "road_2lane_straight";
    public static final String RES_IDENTIFIER_ROAD_90_LEFT = "road_2lane_90left";
    public static final String RES_IDENTIFIER_ROAD_90_RIGHT = "road_2lane_90right";
    public static final String RES_IDENTIFIER_ROAD_45_LEFT = "road_2lane_45left";
    public static final String RES_IDENTIFIER_ROAD_45_RIGHT = "road_2lane_45right";
    public static final String RES_IDENTIFIER_ROAD_T_JUNCTION_LEFT = "road_2lane_tjunctionleft";
    public static final String RES_IDENTIFIER_ROAD_T_JUNCTION_RIGHT = "road_2lane_tjunctionright";
    public static final String RES_IDENTIFIER_ROAD_CROSSROAD_1 = "2_crossroad_1";
    public static final String RES_IDENTIFIER_ROAD_CROSSROAD_2 = "2_crossroad_2";
    public static final String RES_IDENTIFIER_ROAD_ROTARY = "road_2lane_rotary";
    public static final String RES_IDENTIFIER_TREE = "tree";
    public static final String RES_IDENTIFIER_PARKINGSPACE = "parking_space";
    public static final String RES_IDENTIFIER_PARKINGSPACE_PARALLEL = "parking_space_parallel";
    public static final String RES_IDENTIFIER_PARKINGSPACE_90 = "parking_90";
    public static final String RES_IDENTIFIER_CROSSWALK = "crosswalk";
    public static final String RES_IDENTIFIER_SIGN = "roadsign";
    public static final String RES_IDENTIFIER_CAR = "car";
    public static final String RES_IDENTIFIER_CAR_1 = "car1";
    public static final String RES_IDENTIFIER_CAR_2 = "car2";
    public static final String RES_IDENTIFIER_CAR_3 = "car3";
    public static final String RES_IDENTIFIER_CAR_PREFIX = "car_";
    public static final String RES_IDENTIFIER_CAR_BLACK = "car_3_black";
    public static final String RES_IDENTIFIER_COLOR_RED_SUFFIX = "_red";
    public static final String RES_IDENTIFIER_COLOR_WHITE_SUFFIX = "_white";
    public static final String RES_IDENTIFIER_COLOR_BLUE_SUFFIX = "_blue";
    public static final String RES_IDENTIFIER_BOLLARD = "parking_bollard";
    public static final String XML_ATTRIBUTE_TYPE = "type";
    public static final String XML_ATTRIBUTE_NAME = "name";
    public static final String XML_ATTRIBUTE_X = "x";
    public static final String XML_ATTRIBUTE_Y = "y";

    // --- Errors

    public static final String ERROR_COULDNT_LOAD_IMG_FILE = "Nem sikerült a képfájl betöltése! Fájl neve: {0}";
    public static final String ERROR_IN_PROCESSING = "Hiba lépett fel feldolgozás közben!";
    public static final String ERROR_FILE_LIKELY_DOESNT_EXIST = "Valószínűleg nem létezik a fájl!";
    public static final String ERROR_IN_XML_PARSING = "A(z) {0} XML parse-olása közben hiba lépett fel!";
    public static final String ERROR_XML_WRITING_NOT_ALLOWED = "XML manipuláció/kiírás nem engedélyezett!";
    public static final String ERROR_IN_WORLDOBJECT_PARSING = "A WorldObjectek parseolása közben hiba lépett fel!";
    public static final String ERROR_IN_SHAPE_CREATION = "Nem sikerült a poligon beállítás a következő world objectnél: {0}";

    // --- Messages

    public static final String XML_MS_DURATION_MESSAGE = "Az XML feldolgozása {0} ms-et vett igénybe.";
    public static final String XML_UNKNOWN_TYPE = "Ismeretlen típus található az XML-ben: {0}";
    public static final String XML_WORLD_OBJECT_NUMBER = "Beolvasott WorldObjectek száma: {0}";

    // --- Properties

    public static final String PROP_KEY_XML_NO_OPTIMIZE = "com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize";
    public static final String PROP_VALUE_XML_NO_OPTIMIZE = "true";

    // --- Indexes

    public static final int Z_ROAD = 0;
}
