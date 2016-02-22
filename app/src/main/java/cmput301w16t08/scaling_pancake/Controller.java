package cmput301w16t08.scaling_pancake;

import android.app.Application;

/**
 * Created by William on 2016-02-19.
 */
public class Controller extends Application {
    private UserList users;
    private InstrumentList instruments;
    private User currentUser;

    public Controller() {
        super();
        this.users = new UserList();
        this.instruments = new InstrumentList();
        this.currentUser = null;
    }

    public UserList getUserList() {
        // returns a list of all the users
        return this.users;
    }

    public InstrumentList getInstrumentList() {
        // returns a list of all non borrowed instruments
        return this.instruments;
    }

    public User getCurrentUser() {
        // returns the logged in user or null if not logged in
        return this.currentUser;
    }

    public boolean createUser(String username, String email) {
        // returns true if successfully created
        // returns false if username in use
        User user = new User(username, email);
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.getUser(0).getName().equals(username)) {
                return false;
            }
        }
        this.users.addUser(user);
        return true;
    }

    public boolean login(String username) {
        // returns true with successful login
        // returns false if username not in use
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.getUser(i).getName().equals(username)) {
                this.currentUser = this.users.getUser(i);
                return true;
            }
        }
        return false;
    }

    public void logout() {
        this.currentUser = null;
    }

    public boolean editCurrentUser(String username, String email) {
        // returns true with successful edit
        // returns false if username already in use
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.getUser(i).getName().equals(username)) {
                if (!(this.users.getUser(i) == this.currentUser)) {
                    return false;
                }
            }
        }
        this.currentUser.setName(username);
        this.currentUser.setEmail(email);
        return true;
    }

    public InstrumentList getCurrentUsersOwnedInstruments() {
        // returns all instruments owned by the logged in user or null if not logged in
        if (this.currentUser == null) {
            return null;
        }
        return this.currentUser.getOwnedInstruments();
    }

    public InstrumentList getCurrentUsersOwnedBorrowedInstruments() {
        // returns the instruments owned by the current logged in user that are currently borrowed
        // or null if not logged in
        if (this.currentUser == null) {
            return null;
        }
        return this.currentUser.getOwnedBorrowedInstruments();
    }

    public InstrumentList getCurrentUsersBorrowedInstruments() {
        // returns the instruments borrowed by the current logged in user or null if not logged in
        if (this.currentUser == null) {
            return null;
        }
        return this.currentUser.getBorrowedInstruments();
    }

    public InstrumentList getCurrentUsersBiddedInstruments() {
        // returns all the instruments that the current user has bid on or null if not logged in
        if (this.currentUser == null) {
            return null;
        }
        InstrumentList instruments = new InstrumentList();
        BidList bids = this.getCurrentUsersBids();
        for (int i = 0; i < bids.size(); i++) {
            instruments.addInstrument(bids.getBid(i).getInstrument());
        }
        return instruments;
    }

    public BidList getCurrentUsersBids() {
        // returns the bids made by the current user or null if not logged in
        if (this.currentUser == null) {
            return null;
        }
        return this.currentUser.getBids();
    }

    public void addInstrument(Instrument instrument) {
        // adds an instrument to the current logged in user
        this.currentUser.addOwnedInstrument(instrument);
        this.instruments.addInstrument(instrument);
    }

    public void addInstrument(String name, String description) {
        // adds an instrument to the current logged in user
        Instrument instrument = new Instrument(this.currentUser, name, description);
        this.currentUser.addOwnedInstrument(instrument);
        this.instruments.addInstrument(instrument);
    }

    public void editInstrument(Instrument instrument, String name, String description) {
        // edits an instrument from the current logged in user
        instrument.setName(name);
        instrument.setDescription(description);
    }

    public void editInstrument(int index, String name, String description) {
        // edits an instrument from the current logged in user
        Instrument instrument = this.currentUser.getOwnedInstruments().getInstrument(index);
        instrument.setName(name);
        instrument.setDescription(description);
    }

    public void deleteInstrument(Instrument instrument) {
        // deletes an instrument owned by the current logged in user
        this.currentUser.deleteOwnedInstrument(instrument);
        this.instruments.removeInstrument(instrument);
    }

    public void deleteInstrument(int index) {
        // deletes an instrument owned by the current logged in user
        Instrument instrument = this.currentUser.getOwnedInstruments().getInstrument(index);
        this.currentUser.deleteOwnedInstrument(instrument);
        this.instruments.removeInstrument(instrument);
    }

    public InstrumentList searchInstruments(String keywords) {
        // returns a list of instruments that contain the words in keywords
        // words can be separated by a space and an empty string will return
        // all instruments in the database
        //TODO: add search instrument functionality
        return this.instruments;
    }

    public void makeBidOnInstrument(Instrument instrument, float amount) {
        // current logged in user makes a bid on a instrument
        Bid bid = new Bid(instrument, instrument.getOwner(), this.currentUser, amount);
        instrument.addBid(bid);
    }

    public void acceptBidOnInstrument(Instrument instrument, Bid bid) {
        // current logged in user accepts a bid on an instrument
        instrument.acceptBid(bid);
        this.instruments.removeInstrument(instrument);
        bid.getBidder().addBorrowedInstrument(instrument);
        bid.getBidder().deleteBid(bid);
    }

    public void declineBidOnInstrument(Instrument instrument, Bid bid) {
        // current logged in user declines a bid on an instrument
        instrument.declineBid(bid);
        bid.getBidder().deleteBid(bid);
    }
}
