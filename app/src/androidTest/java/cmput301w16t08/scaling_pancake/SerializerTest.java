package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by William on 2016-03-03.
 */
public class SerializerTest extends ActivityInstrumentationTestCase2 {
    public SerializerTest() {
        super(Serializer.class);
    }

    public void testSerializeUser() {
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
        assertEquals(string,
                "{\"name\" : \"borrower\", \"email\" : \"email2\", \"ownedInstruments\" : [\"instrumentid3\"], \"borrowedInstruments\" : [\"instrumentid1\"], \"bids\" : [\"bidid2\"]}");
    }

    public void testSerializeInstrument() {
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email2");
        owner.setId("ownerid");
        borrower.setId("borrowerid");
        owner.addOwnedInstrument("name1", "description1");
        owner.getOwnedInstruments().getInstrument(0).setId("instrumentid1");

        Serializer serializer = new Serializer();
        String string = serializer.serializeInstrument(owner.getOwnedInstruments().getInstrument(0));
        assertEquals(string, "{\"name\" : \"name1\", \"description\" : \"description1\", \"owner\" : \"ownerid\", \"status\" : \"available\", \"bids\" : []}");

        Bid bid1 = new Bid(owner.getOwnedInstruments().getInstrument(0), owner, borrower, 1.00f);
        owner.getOwnedInstruments().getInstrument(0).addBid(bid1);
        bid1.setId("bidid1");
        owner.getOwnedInstruments().getInstrument(0).acceptBid(bid1);

        string = serializer.serializeInstrument(owner.getOwnedInstruments().getInstrument(0));
        assertEquals(string, "{\"name\" : \"name1\", \"description\" : \"description1\", \"owner\" : \"ownerid\", \"status\" : \"borrowed\", \"borrowedBy\" : \"borrowerid\", \"bids\" : [\"bidid1\"]}");
    }

    public void testSerializeBid() {
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
        assertEquals(string, "{\"instrument\" : \"instrumentid1\", \"owner\" : \"ownerid\", \"bidder\" : \"borrowerid\", \"bidAmount\" : \"1.0\", \"accepted\" : \"false\"}");
    }
}
