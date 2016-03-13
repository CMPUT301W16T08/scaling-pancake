package cmput301w16t08.scaling_pancake.models;

import java.util.ArrayList;

import cmput301w16t08.scaling_pancake.models.Bid;

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
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getId().equals(bid.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean containsBid(String id) {
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public void removeBid(Bid bid) {
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getId().equals(bid.getId())) {
                this.bids.remove(i);
                return;
            }
        }
        throw new RuntimeException();
    }

    public void removeBid(int index) {
        if (index < this.bids.size()) {
            this.bids.remove(index);
        } else {
            throw new RuntimeException();
        }
    }

    public void removeBid(String id) {
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getId().equals(id)) {
                this.bids.remove(i);
                return;
            }
        }
        throw new RuntimeException();
    }

    public Bid getBid(int index) {
        if (index < this.bids.size()) {
            return this.bids.get(index);
        } else {
            throw new RuntimeException();
        }
    }

    public ArrayList<Bid> getArray() {
        /* Accessor method for the  adapters to link with the arraylist. */
        return bids;
    }

    public Bid getBid(String id) {
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getId().equals(id)) {
                return this.bids.get(i);
            }
        }
        throw new RuntimeException();
    }

    public void clearBids() {
        this.bids.clear();
    }
}
