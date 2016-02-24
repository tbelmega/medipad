package pardertec.de.medipad.fahrtenbuch.model;

import junit.framework.Assert;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Thiemo on 23.02.2016.
 */
public class FahrtTest{

    public static final int KILOMETER_BEGINN = 600;
    public static final String ADRESS_STRING = "foo\nbar";

    @Test
    public void testThatFahrtHasData() {
        Fahrt fahrt = new Fahrt();
        fahrt.setKilometerBeginn(KILOMETER_BEGINN);
        fahrt.setZiel(ADRESS_STRING);

        Long abfahrt = System.currentTimeMillis();
        Long ankunft = abfahrt + 10000;

        fahrt.setAbfahrtszeit(abfahrt);
        fahrt.setAnkunftszeit(ankunft);

        fahrt.setSonderrechte(true);
        fahrt.setInf(false);


        assertEquals(KILOMETER_BEGINN, fahrt.getKilometerBeginn());
        assertEquals(ADRESS_STRING, fahrt.getZiel());
        assertEquals(abfahrt, fahrt.getAbfahrtszeit());
        assertEquals(ankunft, fahrt.getAnkunftszeit());
        assertEquals(true, fahrt.getSonderrechte());
        assertEquals(false, fahrt.getInf());
    }

    @Test
    public void testThatFahrtHasStandardData(){
        Fahrt fahrt = new Fahrt();

        assertNotNull(fahrt.uuid);
        assertEquals(0, fahrt.getKilometerBeginn());
        Assert.assertEquals("", fahrt.getZiel());
        assertFalse(fahrt.getSonderrechte());
        assertFalse(fahrt.getInf());
    }



}
