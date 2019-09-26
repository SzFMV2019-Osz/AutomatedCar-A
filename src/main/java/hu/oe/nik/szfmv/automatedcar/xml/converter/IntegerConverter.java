package hu.oe.nik.szfmv.automatedcar.xml.converter;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * XML feldolgozáshoz konverter. A beolvasott String-et Integerré alakítja.
 * Null-safe, ha nem számot kap 0-t ad vissza.
 */
public class IntegerConverter extends XmlAdapter<String, Integer> {

    @Override
    public Integer unmarshal(String stringValue) throws Exception {
        Integer result = 0;

        if (StringUtils.isNotBlank(stringValue)){
            try {
                return Integer.parseInt(stringValue);
            } catch (NumberFormatException e) {
                // do nothing
            }
        }

        return result;
    }

    @Override
    public String marshal(Integer integer) throws Exception {
        return integer.toString();
    }
}
