package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

public class InstrumentTest extends ActivityInstrumentationTestCase2 {
    public InstrumentTest() {
        super(Instrument.class);
    }

    public void testAddInstrument() {
        // Test use case 01.01.01 (Add instruments)

        // Create a new owner, give them some intruments
        User owner = new User("David", "david@gmail.com");
        owner.addInstrument("Trumpet", "Brass instrument");
        owner.addInstrument("Piano", "Grand piano");
        owner.addInstrument("Piano", "upright piano");
        ArrayList<Instrument> instruments = owner.getOwnedInstruments();

        // Check we have the right amount of instruments
        assertEquals(instruments.size(), 3);
    }

    public void testViewInstrument() {
        // Test use case 01.03.01

        // Create a new owner, give them some instruments
        User owner = new User("David", "david@gmail.com");
        owner.addInstrument("Trumpet", "Brass instrument");
        owner.addInstrument("Piano", "Grand piano");
        owner.addInstrument("Piano", "upright piano");
        ArrayList<Instrument> instruments = owner.getOwnedInstruments();

        // Check that we have the correct descriptions:
        Instrument instrument = instruments.get(0);
        assertEquals(instrument.getName(), "Trumpet");
        instrument = instruments.get(1);
        assertEquals(instrument.getDescription(), "Grand piano");


    }

    public void testEditInstrument() {
        // Test use case 01.04.01

        // Create a new owner, give them an instrument
        User owner = new User("David", "david@gmail.com");
        owner.addInstrument("Trumpet", "Brass instrument");
        ArrayList<Instrument> instruments = owner.getOwnedInstruments();
        Instrument instrument = instruments.get(0);

        //Owner edits their instrument
        instrument.setName("Flute");
        assertEquals(instrument.getName(), "Flute");
        instrument.setDescription("Made of tin");
        assertEquals(instrument.getDescription(), "Made of tin");
    }


    public void testGetStatus() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        assertEquals(instrument.getStatus(), "available");
        instrument.addBid(bid1);
        assertEquals(instrument.getStatus(), "bidded");
        instrument.acceptBid(0);
        assertEquals(instrument.getStatus(), "borrowed");
    }

    public void testGetDescription() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        assertEquals(instrument.getDescription(), "Description");
    }

    public void testGetOwner() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        User returnedOwner = instrument.getOwner();
        assertEquals(returnedOwner.getName(), "Owner");
        assertEquals(returnedOwner.getEmail(), "Owner email");
    }

    public void testGetBorrowedBy() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        instrument.addBid(bid1);
        instrument.acceptBid(0);
        User returnedBorrower = instrument.getBorrowedBy();
        assertEquals(returnedBorrower.getName(), "Bidder1");
        assertEquals(returnedBorrower.getEmail(), "Bidder1 email");
    }

    // test use case US 05.05.01: ViewBidsOnInstrument
    public void testGetBids() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        User bidder2 = new User("Bidder2", "Bidder2 email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        Bid bid2 = new Bid(instrument, owner, bidder2, 2.00f);
        ArrayList<Bid> returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 0);
        instrument.addBid(bid1);
        instrument.addBid(bid2);
        returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 2);

        // Test first bid
        Bid returnedBid1 = returnedBids.get(0);
        User returnedOwner1 = returnedBid1.getOwner();
        User returnedBidder1 = returnedBid1.getBidder();
        Instrument returnedInstrument1 = returnedBid1.getInstrument();
        assertEquals(returnedOwner1.getName(), "Owner");
        assertEquals(returnedOwner1.getEmail(), "Owner email");
        assertEquals(returnedBidder1.getName(), "Bidder1");
        assertEquals(returnedBidder1.getEmail(), "Bidder1 email");
        assertEquals(returnedInstrument1.getDescription(), "Description");
        User returnedOwnerThroughInstrument1 = returnedInstrument1.getOwner();
        assertEquals(returnedOwnerThroughInstrument1.getName(), "Owner");
        assertEquals(returnedOwnerThroughInstrument1.getEmail(), "Owner email");

        // Test second bid
        Bid returnedBid2 = returnedBids.get(1);
        User returnedOwner2 = returnedBid2.getOwner();
        User returnedBidder2 = returnedBid2.getBidder();
        Instrument returnedInstrument2 = returnedBid2.getInstrument();
        assertEquals(returnedOwner2.getName(), "Owner");
        assertEquals(returnedOwner2.getEmail(), "Owner email");
        assertEquals(returnedBidder2.getName(), "Bidder2");
        assertEquals(returnedBidder2.getEmail(), "Bidder2 email");
        assertEquals(returnedInstrument2.getDescription(), "Description");
        User returnedOwnerThroughInstrument2 = returnedInstrument2.getOwner();
        assertEquals(returnedOwnerThroughInstrument2.getName(), "Owner");
        assertEquals(returnedOwnerThroughInstrument2.getEmail(), "Owner email");
    }

    // test use case US 05.06.01: AcceptBidOnInstrument
    public void testAcceptBid() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        User bidder2 = new User("Bidder2", "Bidder2 email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        Bid bid2 = new Bid(instrument, owner, bidder2, 2.00f);
        instrument.addBid(bid1);
        instrument.addBid(bid2);
        instrument.acceptBid(0);
        assertEquals(instrument.getStatus(), "borrowed");
        User borrower = instrument.getBorrowedBy();
        assertEquals(borrower.getName(), "Bidder1");
        assertEquals(borrower.getEmail(), "Bidder1 email");
        ArrayList<Bid> returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 1);
        Bid returnedBid = returnedBids.get(0);
        borrower = returnedBid.getBidder();
        assertEquals(borrower.getName(), "Bidder1");
        assertEquals(borrower.getEmail(), "Bidder1 email");
        assertEquals(returnedBid.getBidAmount(), 2.00f);
    }

    // test use case US 05.07.01: DeclineBidOnInstrument
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
        ArrayList<Bid> returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 1);
        Bid returnedBid = returnedBids.get(0);
        User returnedBidder = returnedBid.getBidder();
        assertEquals(returnedBidder.getName(), "Bidder2");
        assertEquals(returnedBidder.getEmail(), "Bidder2 email");
        assertEquals(returnedBid.getBidAmount(), 2.00f);
    }

    // test use case US 04.01.01: SearchForInstruments
    public void testSearchForInstruments() {
        // SearchForInstruments should return an ArrayList of instruments
        ArrayList<Instrument> totalList = new ArrayList<Instrument>();
        ArrayList<Instrument> result;
        User owner = new User("name", "email");
        Instrument ins1 = new Instrument(owner, "owner1", "guitar,red,1999");
        Instrument ins2 = new Instrument(owner, "owner2", "guitar,red,2000");
        Instrument ins3 = new Instrument(owner, "owner3", "guitar,blue,2001");
        Instrument ins4 = new Instrument(owner, "owner4", "guitar,red,2002");
        Instrument ins5 = new Instrument(owner, "owner5", "guitar,yellow,2003");

        totalList.add(ins1);
        totalList.add(ins2);
        totalList.add(ins3);
        totalList.add(ins4);
        totalList.add(ins5);

        result = SearchForInstruments(totalList, "guitar, red");
        assertEquals(result.size(), 3);
        assertTrue(result.contains(ins1));
        assertTrue(result.contains(ins2));
        assertTrue(result.contains(ins4));

        result = SearchForInstruments(totalList, "piano");
        assertEquals(result.size(), 0);
    }

    // test use case US 05.01.01: BidOnInstrument
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

    public void testDeleteInstrument() {
        InstrumentList instrumentList = new InstrumentList();

        Instrument instrument = new Instrument();

        instrumentList.addNewInstrument(instrument);

        assertTrue(instrumentList.haveInstrument(instrument));
    }
    // test use case US 06.02.01: view owned but being borrowed item as an owner
    public void testListofOwnedItem() {
        ArrayList<Instrument> result;
        Instrument i1 = new Instrument("Owner1", "guitar1");
        Instrument i2 = new Instrument("Owner2", "guitar2");
        Instrument i3 = new Instrument("Owner2", "piano1");
        Instrument i4 = new Instrument("Owner2", "piano2");
//check for the empty list
        result = listofOwnedItem("Owner3");
        assertEquals(result.size(),0);

        result = listofOwnedItem("Owner2");


        assertEquals(result.size(),3);
        assertTrue(result.contains(ins2));
        assertTrue(result.contains(ins3));
        assertTrue(result.contains(ins4));
    }
    // test use case US 06.01.01: view borrowed item as a borrower
    public void testListofBorrowedItem() {
        ArrayList<Instrument> result;
        Instrument i1 = new Instrument("borrower1", "guitar1");
        Instrument i2 = new Instrument("borrower2", "guitar2");
        Instrument i3 = new Instrument("borrower2", "piano1");
        Instrument i4 = new Instrument("borrower2", "piano2");
        //check for the empty list
        result = listofBorrowedItem("borrower3");
        assertEquals(result.size(),0);

        result = listofBorrowedItem("borrrower2");
        
        
        assertEquals(result.size(),3);
        assertTrue(result.contains(ins2));
        assertTrue(result.contains(ins3));
        assertTrue(result.contains(ins4));    
        
   }
   // test use case US 07.01.01: set borrowed item available
    public void testMarkReturnedItem {
        ArrayList<Instrument> result;
        
        //false means being borrowed true means available
        Instrument i1 = new Instrument(“Owner1”, “guitar1”, false);
        
        result = listofOwnedItem(“Owner1”);
        assertEquals(result.size(),1);

        result = markReturnedItem(“Owner1”);


        assertEquals(result.size(),0);
        
        
    }



}
