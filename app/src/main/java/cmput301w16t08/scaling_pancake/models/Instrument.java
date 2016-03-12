package cmput301w16t08.scaling_pancake.models;

import java.util.UUID;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.BidList;

/**
 * Created by William on 2016-02-12.
 */
public class Instrument {
    private String status;
    private String name;
    private String description;
    private String ownerId;
    private String borrowedById;
    private BidList bids;
    private String id;

    public Instrument(String owner, String name, String description) {
        this.name = name;
        this.description = description;
        this.ownerId = owner;
        this.status = "available";
        this.borrowedById = null;
        this.bids = new BidList();
        this.id = UUID.randomUUID().toString();
    }

    public Instrument(String owner, String name, String description, String id) {
        this.name = name;
        this.description = description;
        this.ownerId = owner;
        this.status = "available";
        this.borrowedById = null;
        this.bids = new BidList();
        this.id = id;
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

    public String getOwnerId() {
        return ownerId;
    }

    public String getBorrowedById() {
        return borrowedById;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedById = borrowedBy;
    }

    public BidList getBids() {
        return this.bids;
    }

    public void addBid(Bid bid) {
        this.bids.addBid(bid);
        this.setStatus("bidded");
    }

    public void acceptBid(Bid bid) {
        if (this.bids.containsBid(bid)) {
            bid.setAccepted();
            this.bids.clearBids();
            this.bids.addBid(bid);
            this.setStatus("borrowed");
            this.setBorrowedBy(bid.getBidderId());
        }
        else {
            throw new RuntimeException();
        }
    }

    public void acceptBid(int index) {
        if (index < this.bids.size()) {
            Bid bid = this.bids.getBid(index);
            bid.setAccepted();
            this.bids.clearBids();
            this.bids.addBid(bid);
            this.setStatus("borrowed");
            this.setBorrowedBy(bid.getBidderId());
        }
        else {
            throw new RuntimeException();
        }
    }

    public void declineBid(Bid bid) {
        if (this.bids.containsBid(bid)) {
            this.bids.removeBid(bid);
            if (this.bids.size() == 0) {
                this.setStatus("available");
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    public void declineBid(int index) {
        if (index < this.bids.size()) {
            this.bids.removeBid(this.bids.getBid(index));
            if (this.bids.size() == 0) {
                this.setStatus("available");
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    public String getId() {
        return id;
    }
}
