package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by William on 2016-03-03.
 */
public class DeserializerTest extends ActivityInstrumentationTestCase2 {
    public DeserializerTest() {
        super(Deserializer.class);
    }

    public void testDeserializeUser() {
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email2");
        owner.setId("ownerid");
        borrower.setId("borrowerid");
        owner.addOwnedInstrument("name1", "description1");
        owner.addOwnedInstrument("name2", "description2");
        borrower.addOwnedInstrument("name3", "description3");
        owner.getOwnedInstruments().getInstrument(0).setId("instrumentid1");
        owner.getOwnedInstruments().getInstrument(1).setId("instrumentid2");
        borrower.getOwnedInstruments().getInstrument(0).setId("instrumentid3");
        Bid bid1 = new Bid(owner.getOwnedInstruments().getInstrument(0), owner, borrower, 1.00f);
        owner.getOwnedInstruments().getInstrument(0).addBid(bid1);
        Bid bid2 = new Bid(owner.getOwnedInstruments().getInstrument(1), owner, borrower, 2.00f);
        owner.getOwnedInstruments().getInstrument(1).addBid(bid2);
        bid1.setId("bidid1");
        bid2.setId("bidid2");
        owner.getOwnedInstruments().getInstrument(0).acceptBid(bid1);
        borrower.addBorrowedInstrument(owner.getOwnedInstruments().getInstrument(0));

        Serializer serializer = new Serializer();
        String string = serializer.serializeUser(borrower);
        Deserializer deserializer = new Deserializer();
        User user = null;
        try {
            user = deserializer.deserializeUser(new JSONObject(string));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertNotNull(user);
        assertEquals(user.getName(), "borrower");
        assertEquals(user.getEmail(), "email2");
        assertEquals(user.getId(), "borrowerid");
        assertEquals(user.getOwnedInstruments().size(), 1);
        assertEquals(user.getOwnedInstruments().getInstrument(0).getId(), "instrumentid3");
        assertEquals(user.getBorrowedInstruments().size(), 1);
        assertEquals(user.getBorrowedInstruments().getInstrument(0).getId(), "instrumentid1");
        assertEquals(user.getBids().size(), 1);
        assertEquals(user.getBids().getBid(0).getId(), "bidid2");
    }

    public void testDeserializeInstrument() {
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email2");
        owner.setId("ownerid");
        borrower.setId("borrowerid");
        owner.addOwnedInstrument("name1", "description1");
        owner.getOwnedInstruments().getInstrument(0).setId("instrumentid1");

        // Test freshly created instrument with  no bids
        Serializer serializer = new Serializer();
        String string = serializer.serializeInstrument(owner.getOwnedInstruments().getInstrument(0));
        Deserializer deserializer = new Deserializer();
        Instrument instrument = null;
        try {
            instrument = deserializer.deserializeInstrument(new JSONObject(string));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertNotNull(instrument);
        assertEquals(instrument.getOwner().getName(), "owner");
        assertEquals(instrument.getDescription(), "description1");
        assertEquals(instrument.getName(), "name1");
        assertEquals(instrument.getId(), "instrumentid1");
        assertEquals(instrument.getStatus(), "available");

        // Test instrument after it is borrowed
        Bid bid1 = new Bid(owner.getOwnedInstruments().getInstrument(0), owner, borrower, 1.00f);
        owner.getOwnedInstruments().getInstrument(0).addBid(bid1);
        bid1.setId("bidid1");
        owner.getOwnedInstruments().getInstrument(0).acceptBid(bid1);

        string = serializer.serializeInstrument(owner.getOwnedInstruments().getInstrument(0));
        instrument = null;
        try {
            instrument = deserializer.deserializeInstrument(new JSONObject(string));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertNotNull(instrument);
        assertEquals(instrument.getOwner().getName(), "owner");
        assertEquals(instrument.getDescription(), "description1");
        assertEquals(instrument.getName(), "name1");
        assertEquals(instrument.getId(), "instrumentid1");
        assertEquals(instrument.getBids().size(), 1);
        assertEquals(instrument.getBorrowedBy().getName(), "borrower");
        assertEquals(instrument.getStatus(), "borrowed");
    }

    public void testDeserializeBid() {
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email2");
        owner.setId("ownerid");
        borrower.setId("borrowerid");
        owner.addOwnedInstrument("name1", "description1");
        owner.getOwnedInstruments().getInstrument(0).setId("instrumentid1");
        Bid bid1 = new Bid(owner.getOwnedInstruments().getInstrument(0), owner, borrower, 1.00f);
        bid1.setId("bidid1");

        Serializer serializer = new Serializer();
        String string = serializer.serializeBid(bid1);

        Deserializer deserializer = new Deserializer();
        Bid returnedBid = null;
        try {
            returnedBid = deserializer.deserializeBid(new JSONObject(string));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertNotNull(returnedBid);
        assertEquals(bid1.getBidder().getName(), returnedBid.getBidder().getName());
        assertEquals(bid1.getOwner().getName(), returnedBid.getOwner().getName());
        assertEquals(bid1.getBidAmount(), returnedBid.getBidAmount());
    }
}
