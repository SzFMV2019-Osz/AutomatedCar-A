package hu.oe.nik.szfmv.automatedcar.xml.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DoubleConverterTest {

    Logger logger = LogManager.getLogger();

    private DoubleConverter doubleConverter;

    private String[] validDoubles;

    private Double[] expectedValidDoubles;

    private String[] invalidDoubles;

    /**
     * Mivel a double kalkulált érték, meg kell adni egy tűréshatárt, amit még elfogadunk.
     */
    private Double maxDelta = 1e-10;

    private Double defaultValue = 0.0;

    @Before
    public void initVariables(){
        doubleConverter = new DoubleConverter();

        validDoubles = validDoubles = new String[]{"123", "23432.23423421", "567.1231", "0.00001", "345345.321",
                                                    "-234.131", "-3242,412363", "-0.1213435", "2,", "123,00"};

        expectedValidDoubles = new Double[] {123.00, 23432.23423421, 567.1231, 0.00001, 345345.321,
                                                -234.131, -3242.412363, -0.1213435, 2.0, 123.00};

        invalidDoubles = new String[] {"324234..1231", "", "   ", "valami.szam", "-123,.243", "-0.00000f",
                                        "tizenháromegészöttized", ".", ",", "2,Z"};
    }

    @Test
    public void checkDoubleConverterWithValidDoubles() {
        Double result;
        for (int i = 0; i < validDoubles.length; i++) {
            String number = validDoubles[i];
            Double expected = expectedValidDoubles[i];
            try {
                result = doubleConverter.unmarshal(number);
                assertEquals(expected, result, maxDelta);
            } catch (Exception e) {
                logger.error("A DoubleConverter tesztelése közben hiba lépett fel: " + e.getMessage());
            }
        }
    }

    @Test
    public void checkDoubleConverterWithInvalidDoubles() {
        Double result;
        for (int i = 0; i < invalidDoubles.length; i++) {
            String number = invalidDoubles[i];
            try {
                result = doubleConverter.unmarshal(number);
                assertEquals(defaultValue, result, maxDelta);
            } catch (Exception e) {
                logger.error("A DoubleConverter tesztelése közben hiba lépett fel: " + e.getMessage());
            }
        }
    }
}
