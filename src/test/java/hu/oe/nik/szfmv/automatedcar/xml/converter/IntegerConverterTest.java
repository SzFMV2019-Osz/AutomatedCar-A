package hu.oe.nik.szfmv.automatedcar.xml.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntegerConverterTest {

    Logger logger = LogManager.getLogger();

    IntegerConverter integerConverter;

    private String[] validIntegers;

    private Integer[] expectedValidIntegers;

    private String[] invalidIntegers;

    private Integer defaultValue = 0;

    @Before
    public void initVariables() {
        integerConverter = new IntegerConverter();

        validIntegers = new String[] { "123", "1065894186", "0", "99999999", "-5146", "-1", "-555555555"};

        expectedValidIntegers = new Integer[] { 123, 1065894186, 0, 99999999, -5146, -1, -555555555 };

        invalidIntegers = new String[] { "66666666666666", "-24,12", "2134.324", "három", "-32222222233333333", "321L", "t463453" };
    }

    @Test
    public void checkIntegerConverterWithValidIntegers() {
        Integer result;
        for (int i = 0; i < validIntegers.length; i++) {
            String number = validIntegers[i];
            Integer expected = expectedValidIntegers[i];
            try {
                result = integerConverter.unmarshal(number);
                assertEquals(expected, result);
            } catch (Exception e) {
                logger.error("A IntegerConverter tesztelése közben hiba lépett fel: " + e.getMessage());
            }
        }
        logger.info("IntegerConverter validated " + validIntegers.length + " valid values.");
    }

    @Test
    public void checkIntegerConverterWithInvalidIntegers() {
        Integer result;
        for (int i = 0; i < invalidIntegers.length; i++) {
            String number = invalidIntegers[i];
            try {
                result = integerConverter.unmarshal(number);
                assertEquals(defaultValue, result);
            } catch (Exception e) {
                logger.error("A IntegerConverter tesztelése közben hiba lépett fel: " + e.getMessage());
            }
        }
        logger.info("IntegerConverter validated " + invalidIntegers.length + " invalid values.");
    }

}
