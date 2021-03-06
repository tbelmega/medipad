package de.pardertec.medipad.fahrtenbuch.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thiemo on 23.02.2016.
 */
public class FahrtenzettelTest {

    public static final String KENNZEICHEN = "B-BB 1337";
    public static final String RUDOLF_RENTIER = "Rudolf Rentier";
    public static final String SANTA_CLAUS = "Santa Claus";

    @Test
    public void testThatFahrtenZettelHasData(){
        Fahrtenzettel zettel = new Fahrtenzettel();

        zettel.setKennzeichen(KENNZEICHEN);
        zettel.setFahrer(RUDOLF_RENTIER);
        zettel.setSani(SANTA_CLAUS);

        assertEquals(KENNZEICHEN, zettel.getKennzeichen());
        assertEquals(RUDOLF_RENTIER, zettel.getFahrer());
        assertEquals(SANTA_CLAUS, zettel.getSani());
    }

    @Test
    public void testThatFahrtenZettelHasFahrten(){
        Fahrt fahrt = new Fahrt();

        Fahrtenzettel zettel = new Fahrtenzettel();
        zettel.addFahrt(fahrt);

        List<Fahrt> fahrten = zettel.getFahrten();
        assertEquals(fahrt, fahrten.get(0));

    }

    @Test
    public void testThatFahrtenZettelReturnsLastFahrt(){
        Fahrt fahrt1 = new Fahrt();
        fahrt1.setKilometerBeginn(0);
        Fahrt fahrt2 = new Fahrt();
        fahrt2.setKilometerBeginn(10);
        Fahrt fahrt3 = new Fahrt();
        fahrt3.setKilometerBeginn(20);

        Fahrtenzettel zettel = new Fahrtenzettel();
        zettel.addFahrt(fahrt2);
        zettel.addFahrt(fahrt3);
        zettel.addFahrt(fahrt1);

        Fahrt lastFahrt = zettel.getLastFahrt();

        assertEquals(fahrt3, lastFahrt);
    }


}
