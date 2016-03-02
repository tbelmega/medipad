package de.pardertec.medipad.fahrtenbuch.model;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Thiemo on 02.03.2016.
 */
public class FahrtenbuchTest {

    @Test
    public void testThatNewFahrtenbuchHasEmptyFahrtenzettel() throws Exception {
        //arrange
        Fahrtenbuch myFahrtenbuch = new Fahrtenbuch();

        //act
        Fahrtenzettel z = myFahrtenbuch.getLastFahrtenzettel();

        //assert
        assertEquals(0, z.size());
    }

    @Test
    public void testThatGettingLastZettelReturnsAddedZettel() throws Exception {
        //arrange
        Fahrtenbuch myFahrtenbuch = new Fahrtenbuch();
        Fahrtenzettel myFahrtenzettel = new Fahrtenzettel();

        //act
        myFahrtenbuch.addFahrtenzettel(myFahrtenzettel);
        Fahrtenzettel z = myFahrtenbuch.getLastFahrtenzettel();

        //assert
        assertEquals(myFahrtenzettel, z);
    }

    @Test
    public void testThatStartFahrtenzettelGrowsFahrtenbuchByOne() throws Exception {
        //arrange
        Fahrtenbuch myFahrtenbuch = new Fahrtenbuch();
        int startsize = myFahrtenbuch.size();

        //act
        myFahrtenbuch.startFahrtenzettel();

        //assert
        int size = myFahrtenbuch.size();
        assertEquals(startsize + 1, size);
    }

}
