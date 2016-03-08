package cmput301w16t08.scaling_pancake;

import android.app.Application;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by William on 2016-02-19.
 */
public class Controller extends Application {
    private User currentUser;

    public Controller() {
        super();
        this.currentUser = null;
    }

    public User getCurrentUser() {
        // returns the logged in user or null if not logged in
        return this.currentUser;
    }

    public boolean createUser(String username, String email) {
        // returns true if successfully created
        // returns false if username in use
        ArrayList<String> users = null;
        Deserializer deserializer = new Deserializer();
        ElasticsearchController.GetUserByNameTask getUserByNameTask = new ElasticsearchController.GetUserByNameTask();
        getUserByNameTask.execute(username);
        try {
            users = getUserByNameTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < users.size(); i++) {
            User user = deserializer.deserializeUser(users.get(i));
            if (user.getName().equals(username)) {
                return false;
            }
        }
        ElasticsearchController.CreateUserTask createUserTask = new ElasticsearchController.CreateUserTask();
        createUserTask.execute(new User(username, email));
        return true;
    }

    public void deleteUser() {
        ElasticsearchController.DeleteUserTask deleteUserTask = new ElasticsearchController.DeleteUserTask();
        deleteUserTask.execute(this.currentUser);
        this.currentUser = null;
    }

    public boolean login(String username) {
        // returns true with successful login
        // returns false if username not in use
        ArrayList<String> users = null;
        Deserializer deserializer = new Deserializer();
        ElasticsearchController.GetUserByNameTask getUserByNameTask = new ElasticsearchController.GetUserByNameTask();
        getUserByNameTask.execute(username);
        try {
            users = getUserByNameTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < users.size(); i++) {
            User user = deserializer.deserializeUser(users.get(i));
            if (user.getName().equals(username)) {
                this.currentUser = user;
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
        ArrayList<String> users = null;
        Deserializer deserializer = new Deserializer();
        ElasticsearchController.GetUserByNameTask getUserByNameTask = new ElasticsearchController.GetUserByNameTask();
        getUserByNameTask.execute(username);
        try {
            users = getUserByNameTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < users.size(); i++) {
            User user = deserializer.deserializeUser(users.get(i));
            if (user.getName().equals(username)) {
                return false;
            }
        }
        this.currentUser.setName(username);
        this.currentUser.setEmail(email);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
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
        for (int i = 0; i < this.getCurrentUsersBids().size(); i++) {
            ArrayList<String> users = null;
            Deserializer deserializer = new Deserializer();
            ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
            getUserTask.execute(this.getCurrentUsersBids().getBid(i).getOwnerId());
            try {
                users = getUserTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            User user = deserializer.deserializeUser(users.get(0));
            for (int j = 0; j < user.getOwnedInstruments().size(); j++) {
                if (user.getOwnedInstruments().getInstrument(i).getId().equals(
                        this.getCurrentUsersBids().getBid(i).getInstrumentId())) {
                    instruments.addInstrument(user.getOwnedInstruments().getInstrument(i));
                }
            }
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
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    public void addInstrument(String name, String description) {
        // adds an instrument to the current logged in user
        Instrument instrument = new Instrument(this.currentUser.getName(), name, description);
        this.currentUser.addOwnedInstrument(instrument);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    public void editInstrument(Instrument instrument, String name, String description) {
        // edits an instrument from the current logged in user
        instrument.setName(name);
        instrument.setDescription(description);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    public void editInstrument(int index, String name, String description) {
        // edits an instrument from the current logged in user
        Instrument instrument = this.currentUser.getOwnedInstruments().getInstrument(index);
        instrument.setName(name);
        instrument.setDescription(description);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    public void deleteInstrument(Instrument instrument) {
        // deletes an instrument owned by the current logged in user
        this.currentUser.deleteOwnedInstrument(instrument);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    public void deleteInstrument(int index) {
        // deletes an instrument owned by the current logged in user
        Instrument instrument = this.currentUser.getOwnedInstruments().getInstrument(index);
        this.currentUser.deleteOwnedInstrument(instrument);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    public InstrumentList searchInstruments(String keywords) {
        // returns a list of instruments that contain the words in keywords
        // words can be separated by a space and an empty string will return
        // all instruments in the database
        //TODO: add search instrument functionality
        return null;
    }

    /*public void makeBidOnInstrument(Instrument instrument, float amount) {
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
    }*/
}
