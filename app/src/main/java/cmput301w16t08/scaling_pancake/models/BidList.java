package cmput301w16t08.scaling_pancake.models;

import java.util.ArrayList;

/**
 * <code>BidList</code> is meant to hold a list of <code>Bid</code> objects.
 * It is basically a wrapper around an <code>ArrayList</code>   .
 *
 * @author William
 * @see Bid
 */
public class BidList {
    private ArrayList<Bid> bids;

    /**
     * Creates a new list of <code>Bid</code>s
     *
     * @see Bid
     */
    public BidList() {
        this.bids = new ArrayList<Bid>();
    }

    /**
     * Returns the size of the list
     *
     * @return the size
     */
    public int size() {
        return this.bids.size();
    }

    /**
     * Checks whether the supplied <code>Bid</code> is contained in the list
     *
     * @param bid the bid in question
     * @return true if bid contained in list, else false
     */
    public boolean containsBid(Bid bid) {
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getId().matches(bid.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a <code>Bid</code> with the supplied id is contained in the list
     *
     * @param id the id of the bid in question
     * @return true if bid contained in list, else false
     */
    public boolean containsBid(String id) {
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getId().matches(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a <code>Bid</code> to the list
     *
     * @param bid the bid to add
     */
    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    /**
     * Removes a <code>Bid</code> from the list
     *
     * @param bid the bid to remove
     * @throws RuntimeException
     *          if bid not contained in the list
     */
    public void removeBid(Bid bid) {
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getId().matches(bid.getId())) {
                this.bids.remove(i);
                return;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Removes the <code>Bid</code> at the supplied index from the list
     *
     * @param index the index of the bid to remove
     * @throws RuntimeException
     *          if the index is not in range <code>0</code> to <code>size()-1</code>
     */
    public void removeBid(int index) {
        if (index < this.bids.size() && index >= 0) {
            this.bids.remove(index);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Removes the <code>Bid</code> with the supplied id from the list
     *
     * @param id the id of the bid to remove
     * @throws RuntimeException
     *          if there is no bid in the list with the supplied id
     */
    public void removeBid(String id) {
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getId().matches(id)) {
                this.bids.remove(i);
                return;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Returns the <code>Bid</code> at the supplied index
     *
     * @param index the index of the bid to return
     * @return the bid
     * @throws RuntimeException
     *          if the index is not in range <code>0</code> to <code>size()-1</code>
     */
    public Bid getBid(int index) {
        if (index < this.bids.size() && index >= 0) {
            return this.bids.get(index);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Returns the list as an <code>ArrayList</code>
     *
     * @return the ArrayList
     */
    public ArrayList<Bid> getArray() {
        /* Accessor method for the  adapters to link with the arraylist. */
        return bids;
    }

    /**
     * Returns the <code>Bid</code> with the supplied id
     *
     * @param id the id of the bid to return
     * @return the bid
     * @throws RuntimeException
     *          if the index is not in range <code>0</code> to <code>size()-1</code>
     */
    public Bid getBid(String id) {
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getId().matches(id)) {
                return this.bids.get(i);
            }
        }
        throw new RuntimeException("Bid not found in model");
    }

    /**
     * Removes all <code>Bid</code>s in the list
     */
    public void clearBids() {
        this.bids.clear();
    }

    /**
     * Find the <code>Bid</code> with the largest bid amount.
     *
     * @return
     */
    public Bid getMaxBid()
    {
        Bid max = null;
        /* Negative bid amounts should not be possible */
        float amount = -1;

        for(int i = 0;i < bids.size();i++)
        {
            if(bids.get(i).getBidAmount() > amount)
            {
                max = bids.get(i);
                amount = max.getBidAmount();
            }
        }

        /* If there are no bids, the result should be null */
        return max;
    }
}
