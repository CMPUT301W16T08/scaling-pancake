package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.InstrumentList;

/**
 * Created by William on 2016-03-10.
 */
public class InstrumentListTest extends ActivityInstrumentationTestCase2 {
    public InstrumentListTest() {
        super(InstrumentList.class);
    }

    public void testSize() {
        InstrumentList instrumentList = new InstrumentList();
        Instrument instrument1 = new Instrument("owner1","name1","descroption1");

        assertEquals(instrumentList.size(),0);

        instrumentList.addInstrument(instrument1);
        assertEquals(instrumentList.size(),1);
    }

    public void testContainsInstrument() {
        InstrumentList instrumentList = new InstrumentList();
        Instrument instrument1 = new Instrument("owner1","name1","descroption1","id1",true);

        assertFalse(instrumentList.containsInstrument(instrument1));
        assertFalse(instrumentList.containsInstrument("id1"));

        instrumentList.addInstrument(instrument1);

        assertTrue(instrumentList.containsInstrument(instrument1));
        assertTrue(instrumentList.containsInstrument("id1"));
    }

    public void testAddInstrument() {
        InstrumentList instrumentList = new InstrumentList();
        Instrument instrument1 = new Instrument("owner1","name1","descroption1","id1",true);

        assertFalse(instrumentList.containsInstrument(instrument1));

        instrumentList.addInstrument(instrument1);
        assertTrue(instrumentList.containsInstrument(instrument1));
    }

    public void testRemoveInstrument() {
        InstrumentList instrumentList = new InstrumentList();
        Instrument instrument1 = new Instrument("owner1","name1","descroption1","id1",true);

        instrumentList.addInstrument(instrument1);
        instrumentList.removeInstrument(instrument1);
        assertFalse(instrumentList.containsInstrument(instrument1));

        instrumentList.addInstrument(instrument1);
        instrumentList.removeInstrument("id1");
        assertFalse(instrumentList.containsInstrument(instrument1));

        instrumentList.addInstrument(instrument1);
        instrumentList.removeInstrument(0);
        assertFalse(instrumentList.containsInstrument(instrument1));
    }

    public void testGetInstrument() {
        InstrumentList instrumentList = new InstrumentList();
        Instrument instrument1 = new Instrument("owner1","name1","descroption1","id1",true);

        instrumentList.addInstrument(instrument1);
        assertEquals(instrument1,instrumentList.getInstrument(0));

        instrumentList.addInstrument(instrument1);
        assertEquals(instrument1,instrumentList.getInstrument("id1"));
    }

    public void testGetArray() {
        ArrayList<Instrument> arrayList = new ArrayList<>();
        InstrumentList instrumentList = new InstrumentList();
        Instrument instrument1 = new Instrument("owner1","name1","descroption1","id1",true);

        arrayList.add(instrument1);
        instrumentList.addInstrument(instrument1);

        assertEquals(arrayList,instrumentList.getArray());
    }

    public void testClearInstruments() {
        InstrumentList instrumentList = new InstrumentList();
        Instrument instrument1 = new Instrument("owner1","name1","descroption1","id1",true);
        instrumentList.addInstrument(instrument1);

        assertFalse(instrumentList.getArray().isEmpty());

        instrumentList.clearInstruments();
        assertTrue(instrumentList.getArray().isEmpty());
    }
}
