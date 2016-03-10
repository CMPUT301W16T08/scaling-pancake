/*

package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;


*/
/**
 * Created by William on 2016-02-21.
 *//*



public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest() {
        super(User.class);
    }
    
    // Test use case 01.01.01 (Add instruments)
    public void testAddInstrument() {
        // Create a new owner, give them some instruments
        User owner = new User("David", "david@gmail.com");
        owner.addOwnedInstrument("Trumpet", "Brass instrument");
        owner.addOwnedInstrument("Piano", "Grand piano");
        owner.addOwnedInstrument("Piano", "upright piano");
        InstrumentList instruments = owner.getOwnedInstruments();

        // Check we have the right amount of instruments
        assertEquals(instruments.size(), 3);
    }

    // Test use case 01.02.01
    public void testGetOwnedInstruments() {
        User owner = new User("owner", "email1");
        assertEquals(0, owner.getOwnedInstruments().size());
        Instrument instrument = new Instrument(owner, "name", "description");
        owner.addOwnedInstrument(instrument);
        assertEquals(1, owner.getOwnedInstruments().size());
        assertEquals("name", owner.getOwnedInstruments().getInstrument(0).getName());
        assertEquals("description", owner.getOwnedInstruments().getInstrument(0).getDescription());
    }

    // Test use case 01.03.01
    public void testViewInstrument() {
        // Create a new owner, give them some instruments
        User owner = new User("David", "david@gmail.com");
        owner.addOwnedInstrument("Trumpet", "Brass instrument");
        owner.addOwnedInstrument("Piano", "Grand piano");
        owner.addOwnedInstrument("Piano", "upright piano");
        InstrumentList instruments = owner.getOwnedInstruments();

        // Check that we have the correct descriptions:
        Instrument instrument = instruments.getInstrument(0);
        assertEquals(instrument.getName(), "Trumpet");
        instrument = instruments.getInstrument(1);
        assertEquals(instrument.getDescription(), "Grand piano");
    }

    // Test use case 01.04.01
    public void testEditInstrument() {
        // Create a new owner, give them an instrument
        User owner = new User("David", "david@gmail.com");
        owner.addOwnedInstrument("Trumpet", "Brass instrument");
        InstrumentList instruments = owner.getOwnedInstruments();
        Instrument instrument = instruments.getInstrument(0);

        //Owner edits their instrument
        owner.editOwnedInstrument(instrument, "Flute", "Made of tin");
        assertEquals(instrument.getName(), "Flute");
        assertEquals(instrument.getDescription(), "Made of tin");
    }

    // Test use case 01.05.01
    public void testDeleteInstrument() {
        User owner = new User("owner", "description");
        owner.addOwnedInstrument("name", "description");
        assertEquals(1, owner.getOwnedInstruments().size());
        owner.deleteOwnedInstrument(owner.getOwnedInstruments().getInstrument(0));
        assertEquals(0, owner.getOwnedInstruments().size());
    }

    // test use case 05.01.01: BidOnInstrument
    public void testBidOnInstrument() {
        // BidOnInstrument should return true if the bid is successful
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email2");
        Instrument ins1 = new Instrument(owner, "owner1", "guitar,1");
        Instrument ins2 = new Instrument(owner, "owner2", "guitar,2");
        Bid bid1 = new Bid(ins1, owner, borrower, 1.00f);

        assertEquals(ins1.getStatus(), "available");
        assertEquals(ins2.getStatus(), "available");

        ins1.addBid(bid1);
        assertEquals(ins1.getStatus(), "bidded");
        assertEquals(ins2.getStatus(), "available");
    }

    // test use case 05.02.01: ViewCurrentBidsAsBorrower
    public void testViewCurrentBidsAsBorrower() {
        // ViewCurrentBidsAsBorrower should return an ArrayList of Instruments

        User owner1 = new User("User1", "email1");
        User borrower1 = new User("User2", "email2");
        Instrument ins1 = new Instrument(owner1, "owner1", "guitar1");
        Instrument ins2 = new Instrument(owner1, "owner1", "guitar2");
        Bid bid1 = new Bid(ins1, owner1, borrower1, 1.00f);
        Bid bid2 = new Bid(ins2, owner1, borrower1, 2.00f);

        BidList result = borrower1.getBids();
        assertEquals(result.size(), 0);

        ins1.addBid(bid1);
        ins2.addBid(bid2);
        result = borrower1.getBids();
        assertEquals(result.size(), 2);
        assertTrue(result.containsBid(bid1));
        assertTrue(result.containsBid(bid2));
    }

    // test use case 05.04.01: ViewCurrentBidsAsOwner
    public void testViewCurrentBidsAsOwner() {
        // ViewCurrentBidsAsOwner should return an InstrumentList

        User owner1 = new User("User1", "email1");
        Instrument ins1 = new Instrument(owner1, "name1", "guitar1");
        Instrument ins2 = new Instrument(owner1, "name2", "guitar2");
        owner1.addOwnedInstrument(ins1);
        owner1.addOwnedInstrument(ins2);

        InstrumentList result = owner1.getOwnedInstruments();
        InstrumentList result2 = new InstrumentList();
        for (int i = 0; i < result.size(); i++) {
            if (result.getInstrument(i).getStatus().equals("bidded")) {
                result2.addInstrument(result.getInstrument(i));
            }
        }
        assertEquals(result2.size(), 0);

        ins1.setStatus("bidded");
        ins2.setStatus("bidded");

        result = owner1.getOwnedInstruments();
        result2 = new InstrumentList();
        for (int i = 0; i < result.size(); i++) {
            if (result.getInstrument(i).getStatus().equals("bidded")) {
                result2.addInstrument(result.getInstrument(i));
            }
        }
        assertEquals(2, result2.size());
        assertTrue(result2.containsInstrument(ins1));
        assertTrue(result2.containsInstrument(ins2));
    }

    // test use case 05.06.01: AcceptBidOnInstrument
    public void testAcceptBid() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        User bidder2 = new User("Bidder2", "Bidder2 email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        owner.addOwnedInstrument(instrument);
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        Bid bid2 = new Bid(instrument, owner, bidder2, 2.00f);
        instrument.addBid(bid1);
        bidder1.addBid(bid1);
        instrument.addBid(bid2);
        bidder2.addBid(bid2);
        instrument.acceptBid(bid1);
        assertEquals(instrument.getStatus(), "borrowed");
        User borrower = instrument.getBorrowedBy();
        assertEquals(borrower.getName(), "Bidder1");
        assertEquals(borrower.getEmail(), "Bidder1 email");
        BidList returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 1);
        Bid returnedBid = returnedBids.getBid(0);
        borrower = returnedBid.getBidder();
        assertEquals(borrower.getName(), "Bidder1");
        assertEquals(borrower.getEmail(), "Bidder1 email");
        assertEquals(returnedBid.getBidAmount(), 1.00f);
    }

    // test use case 05.07.01: DeclineBidOnInstrument
    public void testDeclineBid() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        User bidder2 = new User("Bidder2", "Bidder2 email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        Bid bid2 = new Bid(instrument, owner, bidder2, 2.00f);
        instrument.addBid(bid1);
        instrument.addBid(bid2);
        instrument.declineBid(0);
        BidList returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 1);
        Bid returnedBid = returnedBids.getBid(0);
        User returnedBidder = returnedBid.getBidder();
        assertEquals(returnedBidder.getName(), "Bidder2");
        assertEquals(returnedBidder.getEmail(), "Bidder2 email");
        assertEquals(returnedBid.getBidAmount(), 2.00f);
    }

    // test use case 06.01.01: view borrowed item as a borrower
    public void testListofBorrowedItem() {
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email1");

        assertEquals(0, borrower.getBorrowedInstruments().size());
        Instrument instrument = new Instrument(owner, "name", "description");
        Bid bid = new Bid(instrument, owner, borrower, 1.00f);
        instrument.addBid(bid);
        instrument.acceptBid(bid);
        borrower.addBorrowedInstrument(instrument);

        assertEquals(1, borrower.getBorrowedInstruments().size());
        assertEquals("name", borrower.getBorrowedInstruments().getInstrument(0).getName());
        assertEquals("description", borrower.getBorrowedInstruments().getInstrument(0).getDescription());
    }

    // test use case 06.02.01: view owned but being borrowed item as an owner
    public void testListofOwnedItem() {
        User owner = new User("owner", "email1");
        Instrument instrument = new Instrument(owner, "name", "description");
        owner.addOwnedInstrument(instrument);

        assertEquals(0, owner.getOwnedBorrowedInstruments().size());
        instrument.setStatus("borrowed");
        assertEquals(1, owner.getOwnedBorrowedInstruments().size());
        assertEquals("name", owner.getOwnedBorrowedInstruments().getInstrument(0).getName());
        assertEquals("description", owner.getOwnedBorrowedInstruments().getInstrument(0).getDescription());
    }

    // test use case 07.01.01: set borrowed item available
    public void testMarkReturnedItem() {
        User owner = new User("owner", "email1");
        User borrower = new User("borrower", "email2");
        Instrument instrument = new Instrument(owner, "name", "description");
        owner.addOwnedInstrument(instrument);
        Bid bid = new Bid(instrument, owner, borrower, 1.00f);
        instrument.addBid(bid);
        instrument.acceptBid(bid);
        assertEquals("borrowed", instrument.getStatus());
        owner.getOwnedInstruments().getInstrument(0).setStatus("available");
        assertEquals("available", instrument.getStatus());
    }
}

*/
