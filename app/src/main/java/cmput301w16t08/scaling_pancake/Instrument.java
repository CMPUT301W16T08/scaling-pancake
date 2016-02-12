package cmput301w16t08.scaling_pancake;

import java.util.ArrayList;

/**
 * Created by William on 2016-02-12.
 */
public class Instrument {
    private String status;
    private String name;
    private String description;
    private User owner;
    private User borrowedBy;
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    public Instrument(User owner, String name, String description) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(User borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public ArrayList<Bid> getBids() {
        return this.bids;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public void acceptBid(Bid bid) {
        //TODO: add method body
    }

    public void acceptBid(int index) {
        //TODO: add method body
    }

    public void declineBid(Bid bid) {
        //TODO: add method body
    }

    public void declineBid(int index) {
        //TODO: add method body
    }
}
