package hu.oe.nik.szfmv.automatedcar.xml.converter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.commons.lang3.StringUtils;


/**
 * XML feldolgozáshoz konverter. A beolvasott String-et Doublelé alakítja.
 * Null-safe, ha nem számot kap 0.0-t ad vissza.
 * Tört szám adható meg vesszővel és ponttal elválasztva is.
 */
public class DoubleConverter extends XmlAdapter<String,Double>  {

    @Override
    public Double unmarshal(String stringValue) throws Exception {
        Double result = 0.0;

        if (StringUtils.isNotBlank(stringValue)) {
            try {
                result = Double.parseDouble(stringValue.replaceAll(",", "."));
            } catch (NumberFormatException e) {
                // do nothing
            }
        }

        return result;
    }

    @Override
    public String marshal(Double doubleValue) throws Exception  {
        return doubleValue.toString();
    }
}
