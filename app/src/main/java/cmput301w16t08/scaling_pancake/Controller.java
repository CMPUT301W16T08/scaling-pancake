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

    public User getUserByName(String username) {
        // returns the found user or null if not found
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
                return user;
            }
        }
        return null;
    }

    public User getUserById(String id) {
        // returns the found user or null if not found
        ArrayList<String> users = null;
        Deserializer deserializer = new Deserializer();
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(id);
        try {
            users = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < users.size(); i++) {
            User user = deserializer.deserializeUser(users.get(i));
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
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
        Instrument instrument = new Instrument(this.currentUser.getId(), name, description);
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

    public void makeBidOnInstrument(Instrument instrument, float amount) {
        // current logged in user makes a bid on a instrument

        // load the user that owns the instrument
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(instrument.getOwnerId());
        ArrayList<String> users = null;
        try {
            users = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        User user = new Deserializer().deserializeUser(users.get(0));

        // make the bid and update both users
        if (user.getOwnedInstruments().containsInstrument(instrument)) {
            Bid bid = new Bid(instrument.getId(), user.getId(), this.currentUser.getId(), amount);
            user.getOwnedInstruments().getInstrument(instrument.getId()).addBid(bid);
            this.currentUser.addBid(bid);
        } else {
            throw new RuntimeException("Instrument not found");
        }
        ElasticsearchController.UpdateUserTask updateUserTask1 = new ElasticsearchController.UpdateUserTask();
        ElasticsearchController.UpdateUserTask updateUserTask2 = new ElasticsearchController.UpdateUserTask();
        updateUserTask1.execute(user);
        updateUserTask2.execute(this.currentUser);
    }

    public void acceptBidOnInstrument(Bid bid) {
        // current logged in user accepts a bid on an instrument
        if (!this.currentUser.getOwnedInstruments().containsInstrument(bid.getInstrumentId())) {
            throw new RuntimeException("logged in user does not own instrument");
        }
        if (!this.currentUser.getOwnedInstruments().getInstrument(bid.getInstrumentId()).getBids().containsBid(bid)) {
            throw new RuntimeException("instrument does not contain that bid");
        }
        Instrument instrument = this.currentUser.getOwnedInstruments().getInstrument(bid.getInstrumentId());

        // remove all bids
        for (int i = 0; i < instrument.getBids().size(); i++) {
            // get the bidder
            ElasticsearchController.GetUserTask getUserTask1 = new ElasticsearchController.GetUserTask();
            getUserTask1.execute(instrument.getBids().getBid(i).getBidderId());
            ArrayList<String> users = null;
            try {
                users = getUserTask1.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            User bidder1 = new Deserializer().deserializeUser(users.get(0));

            // remove the bid and update user
            bidder1.deleteBid(instrument.getBids().getBid(i));
            ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
            updateUserTask.execute(bidder1);
        }

        // update both users
        instrument.acceptBid(bid);
        ElasticsearchController.GetUserTask getUserTask1 = new ElasticsearchController.GetUserTask();
        getUserTask1.execute(bid.getBidderId());
        ArrayList<String> users = null;
        try {
            users = getUserTask1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        User bidder = new Deserializer().deserializeUser(users.get(0));
        bidder.addBorrowedInstrument(instrument);
        ElasticsearchController.UpdateUserTask updateUserTask1 = new ElasticsearchController.UpdateUserTask();
        ElasticsearchController.UpdateUserTask updateUserTask2 = new ElasticsearchController.UpdateUserTask();
        updateUserTask1.execute(bidder);
        updateUserTask2.execute(this.currentUser);
    }

    public void declineBidOnInstrument(Bid bid) {
        // current logged in user declines a bid on an instrument
        if (!this.currentUser.getOwnedInstruments().containsInstrument(bid.getInstrumentId())) {
            throw new RuntimeException("logged in user does not own instrument");
        }
        if (!this.currentUser.getOwnedInstruments().getInstrument(bid.getInstrumentId()).getBids().containsBid(bid)) {
            throw new RuntimeException("instrument does not contain that bid");
        }
        Instrument instrument = this.currentUser.getOwnedInstruments().getInstrument(bid.getInstrumentId());

        // get the bidder
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(bid.getBidderId());
        ArrayList<String> users = null;
        try {
            users = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        User bidder = new Deserializer().deserializeUser(users.get(0));

        // remove the bid and update users
        bidder.deleteBid(bid);
        this.currentUser.getOwnedInstruments().getInstrument(bid.getInstrumentId()).getBids().removeBid(bid);
        ElasticsearchController.UpdateUserTask updateUserTask1 = new ElasticsearchController.UpdateUserTask();
        ElasticsearchController.UpdateUserTask updateUserTask2 = new ElasticsearchController.UpdateUserTask();
        updateUserTask1.execute(bidder);
        updateUserTask2.execute(this.currentUser);
    }
}
