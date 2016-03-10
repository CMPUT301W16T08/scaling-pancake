package cmput301w16t08.scaling_pancake;

import java.util.UUID;

/**
 * Created by William on 2016-02-12.
 */
public class Bid {
    private String instrument;
    private String ownerId;
    private String bidderId;
    private float bidAmount;
    private boolean accepted;
    private String id;

    public Bid(String instrument, String owner, String bidder, float bidAmount) {
        this.instrument = instrument;
        this.ownerId = owner;
        this.bidderId = bidder;
        this.bidAmount = bidAmount;
        this.accepted = false;
        this.id = UUID.randomUUID().toString();
    }

    public Bid(String instrument, String owner, String bidder, float bidAmount, String id) {
        this.instrument = instrument;
        this.ownerId = owner;
        this.bidderId = bidder;
        this.bidAmount = bidAmount;
        this.accepted = false;
        this.id = id;
    }

    public String getInstrumentId() {
        return instrument;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public float getBidAmount() {
        return bidAmount;
    }

    public boolean getAccepted() {
        return accepted;
    }

    public void setAccepted() {
        this.accepted = true;
    }

    public String getId() {
        return id;
    }
}
