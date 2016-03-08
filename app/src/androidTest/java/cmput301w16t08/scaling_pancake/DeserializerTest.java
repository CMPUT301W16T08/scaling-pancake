package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

public class DeserializerTest extends ActivityInstrumentationTestCase2 {
    public DeserializerTest() {
        super(Deserializer.class);
    }

    public void testDeserializeUser() {
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email2");
        owner.addOwnedInstrument("name1", "description1");
        owner.addOwnedInstrument("name2", "description2");
        borrower.addOwnedInstrument("name3", "description3");
        Bid bid1 = new Bid(owner.getOwnedInstruments().getInstrument(0).getId(), owner.getId(), borrower.getId(), 1.00f);
        Bid bid2 = new Bid(owner.getOwnedInstruments().getInstrument(1).getId(), owner.getId(), borrower.getId(), 2.00f);
        owner.getOwnedInstruments().getInstrument(0).addBid(bid1);
        owner.getOwnedInstruments().getInstrument(1).addBid(bid2);
        owner.getOwnedInstruments().getInstrument(0).acceptBid(bid1);
        borrower.addBid(bid2);
        borrower.addBorrowedInstrument(owner.getOwnedInstruments().getInstrument(0));

        Serializer serializer = new Serializer();
        String string = serializer.serializeUser(borrower);
        Deserializer deserializer = new Deserializer();
        User user = deserializer.deserializeUser(string);
        assertEquals(user.getName(), "borrower");
        assertEquals(user.getEmail(), "email2");
        assertEquals(user.getOwnedInstruments().size(), 1);
        assertEquals(user.getOwnedInstruments().getInstrument(0).getId(),borrower.getOwnedInstruments().getInstrument(0).getId());
        assertEquals(user.getBorrowedInstruments().size(), 1);
        assertEquals(user.getBorrowedInstruments().getInstrument(0).getId(), borrower.getBorrowedInstruments().getInstrument(0).getId());
        assertEquals(user.getBids().size(), 1);
        assertEquals(user.getBids().getBid(0).getId(), bid2.getId());
    }

    public void testDeserializeInstrument() {
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email2");
        owner.addOwnedInstrument("name1", "description1");

        // Test freshly created instrument with  no bids
        Serializer serializer = new Serializer();
        String string = serializer.serializeInstrument(owner.getOwnedInstruments().getInstrument(0));
        Deserializer deserializer = new Deserializer();
        Instrument instrument = deserializer.deserializeInstrument(string);
        assertEquals(instrument.getOwnerId(), owner.getId());
        assertEquals(instrument.getDescription(), "description1");
        assertEquals(instrument.getName(), "name1");
        assertEquals(instrument.getId(), owner.getOwnedInstruments().getInstrument(0).getId());
        assertEquals(instrument.getStatus(), "available");

        // Test instrument after it is borrowed
        Bid bid1 = new Bid(owner.getOwnedInstruments().getInstrument(0).getId(), owner.getId(), borrower.getId(), 1.00f);
        owner.getOwnedInstruments().getInstrument(0).addBid(bid1);
        owner.getOwnedInstruments().getInstrument(0).acceptBid(bid1);

        string = serializer.serializeInstrument(owner.getOwnedInstruments().getInstrument(0));
        instrument = deserializer.deserializeInstrument(string);
        assertEquals(instrument.getOwnerId(), owner.getId());
        assertEquals(instrument.getDescription(), "description1");
        assertEquals(instrument.getName(), "name1");
        assertEquals(instrument.getId(), owner.getOwnedBorrowedInstruments().getInstrument(0).getId());
        assertEquals(instrument.getBids().size(), 1);
        assertEquals(instrument.getBorrowedById(), borrower.getId());
        assertEquals(instrument.getStatus(), "borrowed");
    }
}
