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
    private BidList bids;
    private String id;

    public Instrument(User owner, String name, String description) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.status = "available";
        this.borrowedBy = null;
        this.bids = new BidList();
        this.id = "";
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

    public BidList getBids() {
        return this.bids;
    }

    public void addBid(Bid bid) {
        this.bids.addBid(bid);
        bid.getBidder().addBid(bid);
        this.setStatus("bidded");
    }

    public void acceptBid(Bid bid) {
        if (this.bids.containsBid(bid)) {
            bid.setAccepted();
            for (int i = 0; i < this.bids.size(); i++) {
                if (!(this.bids.getBid(i) == bid)) {
                    this.bids.getBid(i).getBidder().deleteBid(this.bids.getBid(i));
                }
            }
            this.bids.clearBids();
            this.bids.addBid(bid);
            this.setStatus("borrowed");
            this.setBorrowedBy(bid.getBidder());
        }
        else {
            throw new RuntimeException();
        }
    }

    public void acceptBid(int index) {
        if (index < this.bids.size()) {
            Bid bid = this.bids.getBid(index);
            bid.setAccepted();
            for (int i = 0; i < this.bids.size(); i++) {
                if (!(this.bids.getBid(i) == bid)) {
                    this.bids.getBid(i).getBidder().deleteBid(this.bids.getBid(i));
                }
            }
            this.bids.clearBids();
            this.bids.addBid(bid);
            this.setStatus("borrowed");
            this.setBorrowedBy(bid.getBidder());
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

    public void setId(String id) {
        this.id = id;
    }
}
