package cmput301w16t08.scaling_pancake;

import java.util.ArrayList;

/**
 * Created by William on 2016-02-18.
 */
public class BidList {
    private ArrayList<Bid> bids;

    public BidList() {
        this.bids = new ArrayList<Bid>();
    }

    public int size() {
        return this.bids.size();
    }

    public boolean containsBid(Bid bid) {
        return this.bids.contains(bid);
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public void removeBid(Bid bid) {
        if (this.bids.contains(bid)) {
            this.bids.remove(bid);
        } else {
            throw new RuntimeException();
        }
    }

    public void removeBid(int index) {
        if (index < this.bids.size()) {
            this.bids.remove(index);
        } else {
            throw new RuntimeException();
        }
    }

    public Bid getBid(int index) {
        if (index < this.bids.size()) {
            return this.bids.get(index);
        } else {
            throw new RuntimeException();
        }
    }

    public ArrayList<Bid> getArray()
    {
        /* Accessor method for the  adapters to link with the arraylist. */
        return bids;
    }

    public void clearBids() {
        this.bids.clear();
    }
}
