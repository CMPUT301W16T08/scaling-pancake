package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest() {
        super(Bid.class);
    }

    public void testGetInstrument() {
        User owner = new User("Owner", "Owner email");
        User bidder = new User("Bidder", "Bidder email");
        Instrument instrument = new Instrument(owner, "Description");
        Bid bid = new Bid(instrument, owner, bidder, 1.00f);
        Instrument returnedInstrument = bid.getInstrument();
        assertEquals(returnedInstrument.getOwner, instrument.getOwner);
        assertEquals(returnedInstrument.getDescription, instrument.getDescription);
    }

    public void testGetOwner() {
        User owner = new User("Owner", "Owner email");
        User bidder = new User("Bidder", "Bidder email");
        Instrument instrument = new Instrument(owner, "Description");
        Bid bid = new Bid(instrument, owner, bidder, 1.00f);
        User returnedOwner = bid.getOwner();
        assertEquals(returnedOwner.getName(), owner.getName());
        assertEquals(returnedOwner.getEmail(), owner.getEmail());
    }

    public void testGetBidder() {
        User owner = new User("Owner", "Owner email");
        User bidder = new User("Bidder", "Bidder email");
        Instrument instrument = new Instrument(owner, "Description");
        Bid bid = new Bid(instrument, owner, bidder, 1.00f);
        User returnedBidder = bid.getBidder();
        assertEquals(returnedBidder.getName(), bidder.getName());
        assertEquals(returnedBidder.getEmail(), bidder.getEmail());
    }

    public void testGetBidAmount() {
        User owner = new User("Owner", "Owner email");
        User bidder = new User("Bidder", "Bidder email");
        Instrument instrument = new Instrument(owner, "Description");
        Bid bid = new Bid(instrument, owner, bidder, 1.00f);
        assertEquals(bid.getBidAmount(), 1.00f);
    }

    public void testGetAccepted() {
        User owner = new User("Owner", "Owner email");
        User bidder = new User("Bidder", "Bidder email");
        Instrument instrument = new Instrument(owner, "Description");
        Bid bid = new Bid(instrument, owner, bidder, 1.00f);
        assertFalse(bid.getAccepted());
        bid.setAccepted();
        assertTrue(bid.getAccepted());
    }
    
    // test use case US 05.02.01: ViewCurrentBidsAsBorrower
    public void testViewCurrentBidsAsBorrower(){
        // ViewCurrentBidsAsBorrower should return an ArrayList of Instruments
 
        ArrayList<Instrument> result;
        Instrument ins1 = new Instrument(“owner1”, “guitar,1”);
        Instrument ins2 = new Instrument(“owner2”, “guitar,2”);
      	ins1.setStatus(“bidded”);
        ins2.setStatus(“bidded”);
 
        result = ViewCurrentBidsAsBorrower(“borrower1”);
      	assertEquals(result.size(),0);
     
      	BidOnInstrument(“borrower1”,ins1,10);
      	BidOnInstrument(“borrower2”,ins2,10.5);
        result = ViewCurrentBidsAsBorrower(“borrower1”);
        assertEquals(result.size(),2);
        assertTrue(result.contains(ins1));
        assertTrue(result.contains(ins2));
    }
    
    // test use case US 05.04.01: ViewCurrentBidsAsOwner
    public testViewCurrentBidsAsOwner(){
        // ViewCurrentBidsAsOwner should return an ArrayList of Instruments
     
        ArrayList<Instrument> result;
      	Instrument ins1 = new Instrument(“owner1”, “guitar,1”);
      	Instrument ins2 = new Instrument(“owner1”, “guitar,2”);
     
        result = ViewCurrentBidsAsOwner(“owner1”);
      	assertEquals(result.size(),0);
     
      	ins1.setStatus(“bidded”);
      	ins2.setStatus(“bidded”);
     
      	result = ViewCurrentBidsAsOwner(“owner1”);
        assertEquals(result.size(),2);
        assertTrue(result.contains(ins1));
        assertTrue(result.contains(ins2));
    }
    
}
