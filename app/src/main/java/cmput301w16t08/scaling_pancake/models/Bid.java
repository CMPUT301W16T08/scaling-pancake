package cmput301w16t08.scaling_pancake.models;

import java.util.UUID;

/**
 * <code>Bid</code> is meant to represent a bid made by a <code>User</code> (called the bidder)
 * on an <code>Instrument</code>.
 *
 * @author William
 * @see Instrument
 * @see User
 */
public class Bid {
    private String instrument;
    private String ownerId;
    private String bidderId;
    private float bidAmount;
    private boolean accepted;
    private String id;

    /**
     * Creates a new <code>Bid</code> from the supplied bidder on the supplied instrument
     *
     * @param instrument the instrument to make the bid on
     * @param owner the id of the user that owns the instrument
     * @param bidder the id of the user that is bidding on the instrument
     * @param bidAmount the amount to bid
     * @see Instrument
     * @see User
     */
    public Bid(String instrument, String owner, String bidder, float bidAmount) {
        this.instrument = instrument;
        this.ownerId = owner;
        this.bidderId = bidder;
        this.bidAmount = bidAmount;
        this.accepted = false;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Creates the <code>Bid</code> with the supplied id
     *
     * @param instrument the instrument to make the bid on
     * @param owner the id of the user that owns the instrument
     * @param bidder the id of the user that is bidding on the instrument
     * @param bidAmount the amount to bid
     * @param id the id of the bid
     * @see Instrument
     * @see User
     */
    public Bid(String instrument, String owner, String bidder, float bidAmount, String id) {
        this.instrument = instrument;
        this.ownerId = owner;
        this.bidderId = bidder;
        this.bidAmount = bidAmount;
        this.accepted = false;
        this.id = id;
    }

    /**
     * Returns the id of the <code>Instrument</code> the bid is on
     *
     * @return id of the instrument
     */
    public String getInstrumentId() {
        return instrument;
    }

    /**
     * Returns the id of the <code>User</code> that owns the <code>Instrument</code> the bid is on
     *
     * @return id of the owner
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Returns the id of the <code>User</code> that made the <code>Bid</code>
     *
     * @return id of the bidder
     */
    public String getBidderId() {
        return bidderId;
    }

    /**
     * Returns the amount the <code>Bid</code> is for
     *
     * @return the bid amount
     */
    public float getBidAmount() {
        return bidAmount;
    }

    /**
     * Returns whether the <code>Bid</code> has been accepted or not
     *
     * @return true if accepted, else false
     */
    public boolean getAccepted() {
        return accepted;
    }

    /**
     * Sets the <code>Bid</code> to being accepted
     */
    public void setAccepted() {
        this.accepted = true;
    }

    /**
     * Returns the id of the <code>Bid</code>
     *
     * @return id of the bid
     */
    public String getId() {
        return id;
    }


}
