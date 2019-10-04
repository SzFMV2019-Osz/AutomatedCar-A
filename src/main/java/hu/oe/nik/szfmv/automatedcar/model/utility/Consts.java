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
    public static final String RES_IDENTIFIER_TREE = "tree";
    public static final String RES_IDENTIFIER_PARKINGSPACE = "parking_space";
    public static final String RES_IDENTIFIER_CROSSWALK = "crosswalk";
    public static final String RES_IDENTIFIER_SIGN = "roadsign";
    public static final String RES_IDENTIFIER_CAR = "car";
    public static final String RES_IDENTIFIER_BOLLARD = "parking_bollard";
    public static final String XML_ATTRIBUTE_TYPE = "type";
    public static final String XML_ATTRIBUTE_NAME = "name";
    public static final String XML_ATTRIBUTE_X = "x";
    public static final String XML_ATTRIBUTE_Y = "y";

    // --- Errors

    public static final String ERROR_COULDNT_LOAD_IMG_FILE = "Nem sikerült a képfájl betöltése! Fájl neve: {0}";
    public static final String ERROR_IN_PROCESSING = "Hiba lépett fel feldolgozás közben!";
    public static final String ERROR_FILE_LIKELY_DOESNT_EXIST = "Valószínűleg nem létezik a fájl!";
    public static final String ERROR_IN_XML_PARSING = "A {0} XML parse-olása közben hiba lépett fel!";
    public static final String ERROR_XML_WRITING_NOT_ALLOWED = "XML manipuláció/kiírás nem engedélyezett!";
    public static final String ERROR_IN_WORLDOBJECT_PARSING = "A WorldObjectek parseolása közben hiba lépett fel!";

    // --- Messages

    public static final String XML_MS_DURATION_MESSAGE = "Az XML feldolgozása {0} ms-et vett igénybe.";
    public static final String XML_UNKNOWN_TYPE = "Ismeretlen típus található az XML-ben: {0}";
    public static final String XML_WORLD_OBJECT_NUMBER = "Beolvasott WorldObjectek száma: {0}";
    
    
    // --- Properties
    public static final String PROP_KEY_XML_NO_OPTIMIZE = "com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize";
    public static final String PROP_VALUE_XML_NO_OPTIMIZE = "true";
}
