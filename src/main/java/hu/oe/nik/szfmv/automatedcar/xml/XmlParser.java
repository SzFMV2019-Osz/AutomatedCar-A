package hu.oe.nik.szfmv.automatedcar.xml;


import hu.oe.nik.szfmv.automatedcar.model.World;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.function.Supplier;

/**
 *
 */
public class XmlParser
{
    private static Logger logger = LoggerFactory.getLogger(XmlParser.class);
    
    public static World parse(String xmlFileName){
        World world = null;
        try {
            // van egy kis hiba jdk 9-től az xml bind-ban, 2.4-ben lesz javítva,
            // addig be kell állítani a lenti propertyt
            Properties props = System.getProperties();
            props.setProperty("com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize", "true");
            JAXBContext jaxbContext = JAXBContext.newInstance(World.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            world = (World) jaxbUnmarshaller.unmarshal(new File(ClassLoader.getSystemResource(xmlFileName).getFile()));
        } catch (Exception e) {
            Supplier<String> details  = () -> "Az XML parse-olása közben hiba lépett fel!";
            logger.error(e, details);
        }
        return world;
    }
}
