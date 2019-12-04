package hu.oe.nik.szfmv.automatedcar.xml;

import hu.oe.nik.szfmv.automatedcar.model.References;
import hu.oe.nik.szfmv.automatedcar.model.TurningPoints;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * XML feldolgozáshoz segéd osztály.
 */
public class XmlParser {
    
    private static Logger logger = LogManager.getLogger();

    private static StopWatch stopWatch;

    /**
     * A JAXB instance létrehozás meglehetősen költséges folyamat, ezért nem hozunk létre újat minden objektum
     * példányosításakor, hanem ebből a cache-ből dolgozunk. Ezt nyugodtan megtehetjük,
     * mert a {@link JAXBContext} thread-safe.
     *
     * Ugyanezt az {@link Unmarshaller}-el nem tehetjük meg (nem thread-safe).
     * Szerencsére létrehozása kevésbé is költséges.
     * 
     * TODO: elgondolkozni és utánanézni, hogy érdemes-e hardcode-olni az instanceokat
     *       (sebesség vs memóriagazdálkodás? esetleg a beolvasás végén invalidálni?)
     */
    private static Map<Class<?>, JAXBContext> jaxbInstanceCache = new HashMap<>();

    static {
        /* Van egy kis hiba JDK 9-től az xml bind-ban, a 2.4-es verzióban lesz majd javítva,
         * addig be kell állítani a lenti propertyt. Ha bekapcsolva maradna, akkor se okozna gondot,
         * csak nagyjából + 0,5 sec lenne a beolvasás ideje.
         */
        Properties props = System.getProperties();
        props.setProperty(Consts.PROP_KEY_XML_NO_OPTIMIZE, Consts.PROP_VALUE_XML_NO_OPTIMIZE);
    }
    
    /**
     * Beolvassa a kapott XML fájlt, majd felépíti belőle a world-öt és a benne elhelyezkedő objektumokat.
     * <b>Ha a feldolgozás közben hiba lépne fel, kezeli és logolja, majd null-al tér vissza!</b>
     *
     * @param xmlFileName Feldolgozandó fájl neve, kiterjesztés nélkül is megadható.
     * @return Felépített világ a betöltött képekkel.
     * @throws NullPointerException Ha nem található a megadott fájl.
     */
    public static World parseWorldObjects(String xmlFileName) throws NullPointerException {
        World world = null;
        xmlFileName = getXmlNameWithExtension(xmlFileName);

        try {
            startStopWatch();

            JAXBContext jaxbContext = createJAXBContext(World.class); // nem cacheljük, mert elég belőle egy instance
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            world = (World) jaxbUnmarshaller.unmarshal(ModelCommonUtil.getFileFromName(xmlFileName));
            logger.debug(MessageFormat.format(Consts.XML_WORLD_OBJECT_NUMBER, world.getWorldObjects().size()));
        } catch (NullPointerException e) {
            logger.warn(Consts.ERROR_IN_PROCESSING + " " + Consts.ERROR_FILE_LIKELY_DOESNT_EXIST);
            throw new NullPointerException(Consts.ERROR_FILE_LIKELY_DOESNT_EXIST);
        } catch (Exception ex) {
            logger.error(MessageFormat.format(Consts.ERROR_IN_XML_PARSING, xmlFileName), ex);
        } finally {
            invalidateCache();
            logger.info(MessageFormat.format(Consts.XML_MS_DURATION_MESSAGE, getElapsedTimeAndResetStopWatch()));
        }
        return world;
    }
    
    /**
     * Beolvassa a kapott reference XML-t.
     * 
     * Eltárolja az értékeket egy {@link References} objektumban, melyhez a kulcs a képfájl neve.
     * 
     * @param xmlFileName Feldolgozandó fájl neve, kiterjesztés nélkül is megadható.
     * @return Beolvasott referencia pontok
     * @throws NullPointerException Ha nem található a megadott fájl.
     */
    public static References parseReferences(String xmlFileName) {
        xmlFileName = getXmlNameWithExtension(xmlFileName);
        References refs = null;
        
        try {
            startStopWatch();

            JAXBContext jaxbContext = createJAXBContext(References.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            refs = (References) jaxbUnmarshaller.unmarshal(ModelCommonUtil.getFileFromName(xmlFileName));
        } catch (NullPointerException e) {
            logger.warn(Consts.ERROR_IN_PROCESSING + " " + Consts.ERROR_FILE_LIKELY_DOESNT_EXIST);
            throw new NullPointerException(Consts.ERROR_FILE_LIKELY_DOESNT_EXIST);
        } catch (Exception ex) {
            logger.error(MessageFormat.format(Consts.ERROR_IN_XML_PARSING, xmlFileName), ex);
        } finally {
            invalidateCache();
            logger.info(MessageFormat.format(Consts.XML_MS_DURATION_MESSAGE, getElapsedTimeAndResetStopWatch()));
        }
        return refs;
    }

    public static TurningPoints parseTurningPoints(String xmlFileName) {
        xmlFileName = getXmlNameWithExtension(xmlFileName);
        TurningPoints turningPoints = null;

        try {
            startStopWatch();

            JAXBContext jaxbContext = createJAXBContext(TurningPoints.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            turningPoints = (TurningPoints) jaxbUnmarshaller.unmarshal(ModelCommonUtil.getFileFromName(xmlFileName));
        } catch (NullPointerException e) {
            logger.warn(Consts.ERROR_IN_PROCESSING + " " + Consts.ERROR_FILE_LIKELY_DOESNT_EXIST);
            throw new NullPointerException(Consts.ERROR_FILE_LIKELY_DOESNT_EXIST);
        } catch (Exception ex) {
            logger.error(MessageFormat.format(Consts.ERROR_IN_XML_PARSING, xmlFileName), ex);
        } finally {
            invalidateCache();
            logger.info(MessageFormat.format(Consts.XML_MS_DURATION_MESSAGE, getElapsedTimeAndResetStopWatch()));
        }
        return turningPoints;
    }

    private static String getXmlNameWithExtension(String fileName) {
        if (!StringUtils.endsWith(fileName, Consts.SUFFIX_XML)) {
            fileName += Consts.SUFFIX_XML;
        }
        return fileName;
    }

    private static void startStopWatch() {
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    private static long getElapsedTimeAndResetStopWatch() {
        stopWatch.stop();
        long elapsedTime = stopWatch.getTime();
        stopWatch.reset();
        return elapsedTime;
    }
    
    private static JAXBContext createJAXBContext(Class<?> newClass) throws JAXBException {
        return JAXBContext.newInstance(newClass);
    }

    public static JAXBContext getJAXBContextFromCache(Class<?> neededClass) throws JAXBException {
        if (! jaxbInstanceCache.containsKey(neededClass)) {
            putJAXBContextToCache(neededClass);
        }
        return jaxbInstanceCache.get(neededClass);
    }

    private static void putJAXBContextToCache(Class<?> newClass) throws JAXBException {
        jaxbInstanceCache.put(newClass, createJAXBContext(newClass));
    }

    private static void invalidateCache() {
        jaxbInstanceCache.clear();
    }
}
