package hu.oe.nik.szfmv.automatedcar.xml;


import hu.oe.nik.szfmv.automatedcar.model.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * XML feldolgozáshzo segéd osztály.
 */
public class XmlParser
{
    private static Logger logger = LogManager.getLogger();

    private static StopWatch stopWatch;

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
            /* Van egy kis hiba JDK 9-től az xml bind-ban, a 2.4-es verzióban lesz majd javítva,
             * addig be kell állítani a lenti propertyt. Ha bekapcsolva maradna, akkor se okozna gondot,
             * csak nagyjából + 0,5 sec lenne a beolvasás ideje.
             */
            Properties props = System.getProperties();
            props.setProperty("com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize", "true");

            JAXBContext jaxbContext = JAXBContext.newInstance(World.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            world = (World) jaxbUnmarshaller.unmarshal(new File(ClassLoader.getSystemResource(xmlFileName).getFile()));
        } catch (NullPointerException e) {
            logger.error("Hiba lépett fel feldolgozás közben, valószínűleg nem létezik a fájl!", e);
            throw new NullPointerException("Valószínűleg nem létezik a fájl!");
        } catch (Exception ex) {
            logger.error("Az XML parse-olása közben hiba lépett fel!", ex);
        }
        logger.info("Az XML feldolgozása " + getElapsedTimeAndResetStopWatch() + " ms-et vett igénybe.");

        return world;
    }

    private static String getXmlNameWithExtension(String fileName) {
        if ( ! StringUtils.endsWith(fileName, ".xml")) {
            fileName += ".xml";
        }
        return fileName;
    }

    private static void startStopWatch() {
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    private static long getElapsedTimeAndResetStopWatch() {
        stopWatch.stop();
        return stopWatch.getTime();
    }
}
