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
    }

    public boolean createUser(String username, String email) {
        // returns true if successfully created
        // returns false if username in use
        return true;
    }

    public boolean login(String username) {
        // returns true with successful login
        // returns false if username not in use
        return true;
    }

    public void editCurrentUser(String username, String email) {
        // edits the current logged in user's profile
    }

    public User getCurrentUser() {
        // returns the logged in user
        return this.currentUser;
    }

    public InstrumentList getCurrentUsersOwnedInstruments() {
        // returns all instruments owned by the logged in user
        return this.currentUser.getOwnedInstruments();
    }

    public InstrumentList getCurrentUsersBorrowedInstruments() {
        // returns the instruments borrowed by the current logged in user
        return this.currentUser.getBorrowedInstruments();
    }

    public InstrumentList getCurrentUsersBiddedInstruments() {
        // returns all the instruments that the current user has bid on
        InstrumentList instruments = new InstrumentList();
        BidList bids = this.getCurrentUsersBids();
        for (int i = 0; i < bids.size(); i++) {
            instruments.addInstrument(bids.getBid(i).getInstrument());
        }
        return instruments;
    }

    public BidList getCurrentUsersBids() {
        // returns the bids made by the current user
        return this.currentUser.getBids();
    }

    public void addInstrument(Instrument instrument) {
        // adds an instrument to the current logged in user
    }

    public void addInstrument(String name, String description) {
        // adds an instrument to the current logged in user
    }

    public void editInstrument(Instrument instrument, String name, String description) {
        // edits an instrument from the current logged in user
    }

    public void editInstrument(int index, String name, String description) {
        // edits an instrument from the current logged in user
    }

    public void deleteInstrument(Instrument instrument) {
        // deletes an instrument owned by the current logged in user
    }

    public void deleteInstrument(int index) {
        // deletes an instrument owned by the current logged in user
    }

    public InstrumentList searchInstruments(String keywords) {
        // returns a list of instruments that contain the words in keywords
        // words can be separated by a space and an empty string will return
        // all instruments in the database
        return this.instruments;
    }

    public void makeBidOnInstrument(Instrument instrument, float bid) {
        // current logged in user makes a bid on a instrument
    }

    public void acceptBidOnInstrument(Instrument instrument, Bid bid) {
        // current logged in user accepts a bid on an instrument
    }

    public void declineBidOnInstrument(Instrument instrument, Bid bid) {
        // current logged in user declines a bid on an instrument
    }
}
