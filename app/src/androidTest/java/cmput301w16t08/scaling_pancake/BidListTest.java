package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.BidList;

/**
 * Created by William on 2016-03-10.
 */
public class BidListTest extends ActivityInstrumentationTestCase2 {

    public BidListTest( ) {
        super(BidList.class);
    }

    public void testSize() {
        BidList bidList = new BidList();

        assertEquals(bidList.size(),0);

        Bid bid1 = new Bid("instrument1","onwer1","bidder1",10);
        bidList.addBid(bid1);
        assertEquals(bidList.size(),1);
    }

    public void testContainsBid() {
        BidList bidList = new BidList();
        Bid bid1 = new Bid("instrument1","onwer1","bidder1",10,"id1");

        assertFalse(bidList.containsBid(bid1));
        assertFalse(bidList.containsBid("id1"));

        bidList.addBid(bid1);
        assertTrue(bidList.containsBid(bid1));
        assertTrue(bidList.containsBid("id1"));
    }

    public void testAddBid() {
        BidList bidList = new BidList();
        Bid bid1 = new Bid("instrument1","onwer1","bidder1",10,"id1");

        bidList.addBid(bid1);
        assertTrue(bidList.containsBid(bid1));
    }

    public void testRemoveBid() {
        BidList bidList = new BidList();
        Bid bid1 = new Bid("instrument1","onwer1","bidder1",10,"id1");

        bidList.addBid(bid1);
        assertTrue(bidList.containsBid(bid1));
        bidList.removeBid(bid1);
        assertFalse(bidList.containsBid(bid1));

        bidList.addBid(bid1);
        assertTrue(bidList.containsBid(bid1));
        bidList.removeBid("id1");
        assertFalse(bidList.containsBid(bid1));

        bidList.addBid(bid1);
        assertTrue(bidList.containsBid(bid1));
        bidList.removeBid(0);
        assertFalse(bidList.containsBid(bid1));
    }

    // Use case: US 05.05.01 view bid on instrument
    public void testGetBid() {
        BidList bidList = new BidList();
        Bid bid1 = new Bid("instrument1","onwer1","bidder1",10,"id1");
        bidList.addBid(bid1);

        assertEquals(bid1, bidList.getBid(0));
        assertEquals(bid1,bidList.getBid("id1"));
    }

    public void testGetArray() {
        ArrayList<Bid> arrayList = new ArrayList<Bid>();
        BidList bidList = new BidList();
        Bid bid1 = new Bid("instrument1","onwer1","bidder1",10,"id1");
        arrayList.add(bid1);
        bidList.addBid(bid1);

        assertEquals(bidList.getArray(),arrayList);
    }

    public void testClearBids(){
        BidList bidList = new BidList();
        Bid bid1 = new Bid("instrument1","onwer1","bidder1",10,"id1");
        bidList.addBid(bid1);

        bidList.clearBids();

        assertTrue(bidList.getArray().isEmpty());
    }

    public void testGetMaxBid() {
        BidList bidList = new BidList();
        Bid bid1 = new Bid("instrument1","onwer1","bidder1",10,"id1");
        Bid bid2 = new Bid("instrument2","onwer2","bidder2",20,"id2");
        Bid bid3 = new Bid("instrument3","onwer3","bidder3",30,"id3");
        bidList.addBid(bid1);
        bidList.addBid(bid2);
        bidList.addBid(bid3);

        assertEquals(bid3,bidList.getMaxBid());
    }
}
