package cmput301w16t08.scaling_pancake.models;

import java.util.UUID;

/**
 * <code>User</code> is meant to represent a user, and contains lists of <code>Instrument</code>s
 * he owns and has borrowed, as well as pending <code>Bid</code>s.
 * It is basically a wrapper around an <code>ArrayList</code>   .
 *
 * @author William
 * @see Instrument
 * @see Bid
 */
public class User {
    private String name;
    private String email;
    private InstrumentList ownedInstruments;
    private InstrumentList borrowedInstruments;
    private BidList bids;
    private String id;
    private boolean newBidFlag;

    /**
     * Creates a new user with username <code>name</code> and email <code>email</code>, and assigns it a unique id
     *
     * @param name the username of the user
     * @param email the email of the user
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.ownedInstruments = new InstrumentList();
        this.borrowedInstruments = new InstrumentList();
        this.bids = new BidList();
        this.id = UUID.randomUUID().toString();
        this.newBidFlag = false;
    }

    /**
     * Creates a new user with username <code>name</code> and email <code>email</code> and id <code>id</code>
     * @param name
     * @param email
     * @param id
     */
    public User(String name, String email, String id, boolean newBid) {
        this.name = name;
        this.email = email;
        this.ownedInstruments = new InstrumentList();
        this.borrowedInstruments = new InstrumentList();
        this.bids = new BidList();
        this.id = id;
        this.newBidFlag = newBid;
    }

    /**
     * Returns the user's username
     *
     * @return the username of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Changes the username of the user
     *
     * @param name the new username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user's email
     *
     * @return the email of the user
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Changes the email of the user
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns an <code>InstrumentList</code> containing all the owned <code>Instrument</code>s of the user
     *
     * @return list of owned instruments
     * @see InstrumentList
     */
    public InstrumentList getOwnedInstruments() {
        return this.ownedInstruments;
    }

    /**
     * Returns an <code>InstrumentList</code> containing all the borrowed <code>Instrument</code>s of the user
     *
     * @return list of borrowed instruments
     * @see InstrumentList
     */
    public InstrumentList getBorrowedInstruments() {
        return this.borrowedInstruments;
    }

    /**
     * Returns an <code>InstrumentList</code> containing all the owned <code>Instrument</code>s of the user that are currently borrowed
     *
     * @return list of owned instruments that are currently borrowed
     * @see InstrumentList
     */
    public InstrumentList getOwnedBorrowedInstruments() {
        InstrumentList list = new InstrumentList();
        for (int i = 0; i < this.getOwnedInstruments().size(); i++) {
            if (this.getOwnedInstruments().getInstrument(i).getStatus().equals("borrowed")) {
                list.addInstrument(this.getOwnedInstruments().getInstrument(i));
            }
        }
        return list;
    }

    /**
     * Returns a <code>BidList</code> containing all the pending <code>Bid</code>s of the user
     *
     * @return list of bids
     * @see BidList
     */
    public BidList getBids() {
        return this.bids;
    }

    /**
     * Adds an <code>Instrument</code> to the user's owned <code>Instrument</code>s
     *
     * @param instrument the instrument to add
     * @see Instrument
     */
    public void addOwnedInstrument(Instrument instrument) {
        this.ownedInstruments.addInstrument(instrument);
    }

    /**
     * Creates an <code>Instrument</code> with the given name  and description and adds it to the
     * user's owned <code>Instrument</code>s
     *
     * @param name the name for the instrument
     * @param description the description for the instrument
     * @see Instrument
     */
    public void addOwnedInstrument(String name, String description) {
        Instrument instrument = new Instrument(this.getId(), name, description);
        this.ownedInstruments.addInstrument(instrument);
    }

    /**
     * Adds an <code>Instrument</code> to the user's borrowed <code>Instrument</code>s
     *
     * @param instrument the instrument to add
     * @see Instrument
     */
    public void addBorrowedInstrument(Instrument instrument) {
        this.borrowedInstruments.addInstrument(instrument);
    }

    /**
     * Edits the name and description of an <code>Instrument</code> owned by the user
     *
     * @param instrument the instrument to edit
     * @param name the new name for the instrument
     * @param description the new description for the instrument
     * @throws RuntimeException
     *          if the user does not own the instrument
     * @see Instrument
     */
    public void editOwnedInstrument(Instrument instrument, String name, String description) {
        if (this.ownedInstruments.containsInstrument(instrument)) {
            instrument.setName(name);
            instrument.setDescription(description);
            if (!this.ownedInstruments.containsInstrument(instrument)) {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Edits the name and description of the user's owned <code>Instrument</code> at the specified index
     *
     * @param index the index of the instrument to edit
     * @param name the new name for the instrument
     * @param description the new description for the instrument
     * @throws RuntimeException
     *          if the user does not own the instrument
     * @see Instrument
     */
    public void editOwnedInstrument(int index, String name, String description) {
        if (index < this.ownedInstruments.size()) {
            Instrument instrument = this.ownedInstruments.getInstrument(index);
            instrument.setName(name);
            instrument.setDescription(description);
            if (!this.ownedInstruments.containsInstrument(instrument)) {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Deletes an <code>Instrument</code> owned by the user
     *
     * @param instrument the instrument to delete
     * @throws RuntimeException
     *          if the user does not own the instrument
     * @see Instrument
     */
    public void deleteOwnedInstrument(Instrument instrument) {
        if (this.ownedInstruments.containsInstrument(instrument)) {
            this.ownedInstruments.removeInstrument(instrument);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Removes an <code>Instrument</code> currently being borrowed by the user
     *
     * @param instrument the instrument to delete
     * @throws RuntimeException
     *          if the user is not currently borrowing the instrument
     * @see Instrument
     */
    public void deleteBorrowedInstrument(Instrument instrument) {
        if (this.borrowedInstruments.containsInstrument(instrument)) {
            this.borrowedInstruments.removeInstrument(instrument);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Adds a new <code>Bid</code> to the user's <code>Bid</code>s
     *
     * @param bid the bid to add
     * @see Bid
     */
    public void addBid(Bid bid) {
        this.bids.addBid(bid);
    }

    /**
     * Removes a <code>Bid</code> from the user's <code>Bid</code>s
     *
     * @param bid the bid to remove
     * @throws RuntimeException
     *          if user did not make the bid
     * @see Bid
     */
    public void deleteBid(Bid bid) {
        if (this.bids.containsBid(bid)) {
            this.bids.removeBid(bid);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Returns the unique id of the user
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the user
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns true if the user has a new <code>Bid</code> on an owned <code>Instrument</code>
     *
     * @return true/false
     */
    public boolean getNewBidFlag() {
        return this.newBidFlag;
    }

    /**
     * Sets the newBidFlag for the User
     *
     * @param b boolean for if there is a new bid on an owned instrument
     */
    public void setNewBidFlag(boolean b) {
        this.newBidFlag = b;
    }
}
