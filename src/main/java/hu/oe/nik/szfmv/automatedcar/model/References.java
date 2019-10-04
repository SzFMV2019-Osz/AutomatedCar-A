package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.xml.converter.ReferenceConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;


/**
 * Referenciapontokat tároló objektum.
 * 
 * Arra szolgál, hogy a {@link World} objektumaihoz be tudjuk állítani a forgatási pontokat.
 * A referencia pontok és az objektumok közötti kapcsolatot a fájlnév határozza meg.
 */
@XmlRootElement(name = "Images")
@XmlAccessorType(XmlAccessType.FIELD)
public class References {
    
    /**
     * Az xml-ből beolvasott értékek először ebbe a listába kerülnek,
     * majd a beolvasás végén áttöltjük az adatokat a map-be.
     */
    @XmlElement(name = "Image")
    @XmlJavaTypeAdapter(ReferenceConverter.class)
    private List<Pair<String, Position>> temporaryRefs;

    /**
     * Az alkalmaás inicializálása után ebből szolgáljuk ki a hívó félt,
     * ez a map tárolja az alkalmazás során használt forgatási pontokat.
     * 
     * Azért töltjük át map-be, hogy később kényelmesebben hozzáférhetőek legyenek a pontok.
     */
    @XmlTransient
    private Map<String, Position> references = new HashMap<>();

    /**
     * Megmondja, hogy a kapott képet mely ponton kell forgatni.
     * XML-ből jön, számolást nem végzünk rajta.
     * 
     * @param fileName forgatandó kép neve, kiterjesztést nem muszáj adni (garantálja a ".png" végződést)
     * @return {@link Position} - X és Y pontok, itt kell forgatni a képet
     */
    public Position getReference(String fileName) {
        if ( !StringUtils.endsWith(fileName, Consts.SUFFIX_IMAGE)) {
            fileName += Consts.SUFFIX_IMAGE;
        }
        if (this.references.containsKey(fileName)) {
            return this.references.get(fileName);
        } else {
            return new Position(0, 0);
        }
    }
    
    /**
     * JAXB unmarshaller event listenerje.
     * 
     * Akkor hívódik meg, ha már az összes Image-t beolvastuk az XML-ből.
     * Híváskor átpakoljuk az elemeket egy map-be, mert azzal később kényelmesebb lesz dolgozni,
     * a listát pedig kinullázuk, mert nem lesz rá szükség többé.
     * 
     * @TODO: megmondani a JAXB-nek, hogy map-be töltse be az objektumokat
     *
     * @param u unmarshaller
     * @param parent parent element
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        temporaryRefs.forEach((pair) -> {
            this.references.put(pair.getKey(), pair.getValue());
        });
        temporaryRefs = null; // duplikátumok megszüntetése
    }
}
