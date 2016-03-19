package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.User;
import cmput301w16t08.scaling_pancake.util.Serializer;

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
        owner.addOwnedInstrument("name1", "description1");
        owner.addOwnedInstrument("name2", "description2");
        borrower.addOwnedInstrument("name3", "description3");
        Bid bid1 = new Bid(owner.getOwnedInstruments().getInstrument(0).getId(), owner.getName(), borrower.getName(), 1.00f);
        Bid bid2 = new Bid(owner.getOwnedInstruments().getInstrument(1).getId(), owner.getName(), borrower.getName(), 2.00f);
        owner.getOwnedInstruments().getInstrument(0).addBid(bid1);
        owner.getOwnedInstruments().getInstrument(1).addBid(bid2);
        owner.getOwnedInstruments().getInstrument(0).acceptBid(bid1);
        borrower.addBid(bid2);
        borrower.addBorrowedInstrument(owner.getOwnedInstruments().getInstrument(0));

        Serializer serializer = new Serializer();
        String string = serializer.serializeUser(borrower);
        assertEquals(string,
                "{\"name\" : \"borrower\", \"email\" : \"email2\", \"id\" : \"" + borrower.getId() +
                        "\", \"newBidFlag\" : \"false\", \"ownedInstruments\" : [" +
                        serializer.serializeInstrument(borrower.getOwnedInstruments().getInstrument(0)) + "], \"borrowedInstruments\" : [" +
                        serializer.serializeInstrument(borrower.getBorrowedInstruments().getInstrument(0)) + "], \"bids\" : [" +
                        serializer.serializeBid(borrower.getBids().getBid(0)) + "]}");
    }

    public void testSerializeInstrument() {
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email2");
        owner.addOwnedInstrument("name1", "description1");

        Serializer serializer = new Serializer();
        String string = serializer.serializeInstrument(owner.getOwnedInstruments().getInstrument(0));
        assertEquals(string, "{\"id\" : \"" + owner.getOwnedInstruments().getInstrument(0).getId() +
                "\", \"name\" : \"name1\", \"description\" : \"description1\", \"ownerId\" : \"" +
                owner.getId() + "\", \"status\" : \"available\", \"returnedFlag\" : \"false\"," +
                " \"thumbnailBase64\" : \"" + owner.getOwnedInstruments().getInstrument(0).getThumbnailBase64() +
                 "\", \"bids\" : []}");

        // try adding a bid
        Bid bid1 = new Bid(owner.getOwnedInstruments().getInstrument(0).getId(), owner.getId(), borrower.getId(), 1.00f);
        owner.getOwnedInstruments().getInstrument(0).addBid(bid1);
        owner.getOwnedInstruments().getInstrument(0).acceptBid(bid1);

        string = serializer.serializeInstrument(owner.getOwnedInstruments().getInstrument(0));
        assertEquals(string, "{\"id\" : \"" + owner.getOwnedInstruments().getInstrument(0).getId() +
                "\", \"name\" : \"name1\", \"description\" : \"description1\", \"ownerId\" : \"" +
                owner.getId() + "\", \"status\" : \"borrowed\", \"returnedFlag\" : \"false\"," +
                " \"thumbnailBase64\" : \"" + owner.getOwnedInstruments().getInstrument(0).getThumbnailBase64() +
                "\", \"borrowedById\" : \"" + borrower.getId() + "\", \"bids\" : [" + serializer.serializeBid(bid1) + "]}");
    }

    public void testSerializeBid() {
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email2");
        owner.addOwnedInstrument("name1", "description1");
        Bid bid1 = new Bid(owner.getOwnedInstruments().getInstrument(0).getId(), owner.getName(), borrower.getName(), 1.00f);

        Serializer serializer = new Serializer();
        String string = serializer.serializeBid(bid1);
        assertEquals(string, "{\"id\" : \"" + bid1.getId() + "\", \"instrumentId\" : \"" + owner.getOwnedInstruments().getInstrument(0).getId() +
                "\", \"ownerId\" : \"" + bid1.getOwnerId() + "\", \"bidderId\" : \"" + bid1.getBidderId() + "\", \"bidAmount\" : \"1.0\", \"accepted\" : \"false\"}");
    }
}
