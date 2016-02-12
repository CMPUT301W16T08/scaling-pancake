package cmput301w16t08.scaling_pancake;

/**
 * Created by William on 2016-02-12.
 */
public class Bid {
    private Instrument instrument;
    private User owner;
    private User bidder;
    private float bidAmount;
    private boolean accepted;

    public Bid(Instrument instrument, User owner, User bidder, float bidAmount) {
        this.instrument = instrument;
        this.owner = owner;
        this.bidder = bidder;
        this.bidAmount = bidAmount;
        this.accepted = false;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public User getOwner() {
        return owner;
    }

    public User getBidder() {
        return bidder;
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
}
