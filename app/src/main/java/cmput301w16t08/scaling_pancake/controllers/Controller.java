package cmput301w16t08.scaling_pancake.controllers;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.BidList;
import cmput301w16t08.scaling_pancake.util.Deserializer;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.InstrumentList;
import cmput301w16t08.scaling_pancake.models.User;
import cmput301w16t08.scaling_pancake.util.PrePostActionWrapper;

/**
 * The <code>Controller</code> class controls access to the model classes (which can access
 * the <code>Controller</code> class with <code>(Controller) getApplicationContext()</code>), and
 * uses the classes in <code>ElasticsearchController</code> to update the data on the server
 * wherever needed.
 *
 * @author William
 * @see ElasticsearchController
 * @see User
 * @see Instrument
 * @see Bid
 * @see InstrumentList
 * @see BidList
 */
public class Controller extends Application {
    private User currentUser;

    /**
     * Creates a new controller
     * Should NEVER be called - the models access the same controller object through the application context
     */
    public Controller() {
        super();
        this.currentUser = null;
    }

    /**
     * Returns the <code>User</code> that is currently logged in
     *
     * @return the current user, or null if no user logged in
     * @see User
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Searches for a <code>User</code> with the supplied username
     *
     * @param username the username of the user to search for
     * @return the user, or null if not found
     * @see User
     */
    public User getUserByName(String username) {
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

    /**
     * Searches for a <code>User</code> with the supplied id
     * @param id the id of the user to search for
     * @return the user, or null if not found
     * @see User
     */
    public User getUserById(String id) {
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

    /**
     * Attempts to create a <code>User</code> with the supplied username and email
     *
     * @param username the username of the user
     * @param email the email of the user
     * @return true if created, or false if username already in use
     * @see User
     */
    public boolean createUser(String username, String email) {
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

    /**
     * Deletes the currently logged in <code>User</code>
     * If no <code>User</code> logged in then does nothing
     *
     * @see User
     */
    public void deleteUser() {
        if (this.currentUser != null) {
            ElasticsearchController.DeleteUserTask deleteUserTask = new ElasticsearchController.DeleteUserTask();
            deleteUserTask.execute(this.currentUser);
            this.currentUser = null;
        }
    }

    /**
     * Deletes the <code>User</code> with the supplied id
     * If there is no <code>User</code> with the id then does nothing
     *
     * @param id
     * @see User
     */
    public void deleteUserById(String id) {
        ElasticsearchController.DeleteUserByIdTask deleteUserTask = new ElasticsearchController.DeleteUserByIdTask();
        deleteUserTask.execute(id);
    }

    /**
     * Attempts to login and make the currently logged in <code>User</code> the <code>User</code>
     * with the supplied username
     *
     * @param username the username of the user
     * @return true if logged in, or false if username not currently in use
     * @see User
     */
    public boolean login(String username) {
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

    /**
     * Logs out the currently logged in <code>User</code>
     *
     * @see User
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Attempts to edit the username and email of the currently logged in <code>User</code>
     *
     * @param username the new username
     * @param email the new email
     * @return true if successful edit, or false if username already in use
     * @see User
     */
    public boolean editCurrentUser(String username, String email) {
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

    /**
     * Attempts to edit the username of the currently logged in <code>User</code>
     *
     * @param username the new username
     * @return true if successful edit, or false if username currently in use
     * @see User
     */
    public boolean editCurrentUserName(String username) {
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
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
        return true;
    }

    /**
     * Edits the email of the currently logged in <code>User</code>
     *
     * @param email the new email
     * @return true
     * @see User
     */
    public boolean editCurrentUserEmail(String email) {
        this.currentUser.setEmail(email);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
        return true;
    }

    /**
     * Returns an <code>InstrumentList</code> of all the <code>Instrument</code>s owned by the
     * currently logged in <code>User</code>
     * Returns null if no <code>User</code> currently logged in
     *
     * @return list of owned instruments
     * @see InstrumentList
     * @see Instrument
     */
    public InstrumentList getCurrentUsersOwnedInstruments() {
        if (this.currentUser == null) {
            return null;
        }
        return this.currentUser.getOwnedInstruments();
    }

    /**
     * Returns an <code>InstrumentList</code> of all the <code>Instrument</code>s owned by the
     * currently logged in <code>User</code> that have a status of borrowed
     * Returns null if no <code>User</code> currently logged in
     *
     * @return list of owned borrowed instruments
     * @see InstrumentList
     * @see Instrument
     */
    public InstrumentList getCurrentUsersOwnedBorrowedInstruments() {
        if (this.currentUser == null) {
            return null;
        }
        return this.currentUser.getOwnedBorrowedInstruments();
    }

    /**
     * Returns an <code>InstrumentList</code> of all the <code>Instrument</code>s that are being
     * borrowed by the currently logged in <code>User</code>
     * Returns null if no <code>User</code> currently logged in
     *
     * @return list of borrowed instruments
     * @see InstrumentList
     * @see Instrument
     */
    public InstrumentList getCurrentUsersBorrowedInstruments() {
        if (this.currentUser == null) {
            return null;
        }
        return this.currentUser.getBorrowedInstruments();
    }

    /**
     * Returns an <code>InstrumentList</code> of all the <code>Instrument</code>s that the currently
     * logged in <code>User</code> has pending bids on
     * Returns null if no <code>User</code> currently logged in
     *
     * @return list of bidded instruments
     * @see InstrumentList
     * @see Instrument
     */
    public InstrumentList getCurrentUsersBiddedInstruments() {
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

    /**
     * Returns a <code>BidList</code> of all the pending <code>Bid</code>s of the currently logged
     * in <code>User</code>
     * Returns null if no <code>User</code> currently logged in
     *
     * @return list of bids
     * @see BidList
     * @see Bid
     */
    public BidList getCurrentUsersBids() {
        if (this.currentUser == null) {
            return null;
        }
        return this.currentUser.getBids();
    }

    /**
     * Attempts to find the <code>Instrument</code> with the supplied id
     * It returns the found <code>Instrument</code>, or null if not found
     *
     * @param id the id of the instrument to search
     * @return the instrument, or null
     * @see Instrument
     */
    public Instrument getInstrumentById(String id) {
        ElasticsearchController.GetUserByInstrumentIdTask getUserByInstrumentId = new ElasticsearchController.GetUserByInstrumentIdTask();
        getUserByInstrumentId.execute(id);
        ArrayList<String> users = null;
        try {
            users = getUserByInstrumentId.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        User user = new Deserializer().deserializeUser(users.get(0));
        for (int i = 0; i < user.getOwnedInstruments().size(); i++) {
            if (user.getOwnedInstruments().getInstrument(i).getId().equals(id)) {
                return user.getOwnedInstruments().getInstrument(i);
            }
        }
        return null;
    }

    /**
     * Adds an <code>Instrument</code> to the currently logged in <code>User</code>'s <code>Instrument</code>s
     *
     * @param instrument the new instrument
     * @see Instrument
     */
    public void addInstrument(Instrument instrument) {
        // adds an instrument to the current logged in user
        this.currentUser.addOwnedInstrument(instrument);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    /**
     * Creates then adds an <code>Instrument</code> to the currently logged in
     * <code>User</code>'s <code>Instrument</code>s
     *
     * @param name the name of the new instrument
     * @param description the description of the new instrument
     * @see Instrument
     */
    public void addInstrument(String name, String description) {
        Instrument instrument = new Instrument(this.currentUser.getId(), name, description);
        this.currentUser.addOwnedInstrument(instrument);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    /**
     * Edits an <code>Instrument</code> owned by the currently logged in <code>User</code>
     *
     * @param instrument the instrument to edit
     * @param name the new name for the instrument
     * @param description the new description for the instrument
     * @see Instrument
     */
    public void editInstrument(Instrument instrument, String name, String description) {
        instrument.setName(name);
        instrument.setDescription(description);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    /**
     * Edits the <code>Instrument</code> at the supplied index that is owned by the currently
     * logged in <code>User</code>
     *
     * @param index the index of the instrument to edit
     * @param name the new name for the instrument
     * @param description the new description for the instrument
     * @see Instrument
     */
    public void editInstrument(int index, String name, String description) {
        Instrument instrument = this.currentUser.getOwnedInstruments().getInstrument(index);
        instrument.setName(name);
        instrument.setDescription(description);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    /**
     * Deletes an <code>Instrument</code> owned by the currently logged in <code>User</code>
     *
     * @param instrument the instrument to delete
     * @see Instrument
     */
    public void deleteInstrument(Instrument instrument) {
        this.currentUser.deleteOwnedInstrument(instrument);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    /**
     * Deletes the <code>Instrument</code> at the supplied index that is owned by the currently
     * logged in <code>User</code>
     *
     * @param index the index of the instrument to delete
     * @see Instrument
     */
    public void deleteInstrument(int index) {
        // deletes an instrument owned by the current logged in user
        Instrument instrument = this.currentUser.getOwnedInstruments().getInstrument(index);
        this.currentUser.deleteOwnedInstrument(instrument);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    /**
     * Searches for any non-borrowed <code>Instrument</code>s that contain the keywords in the
     * supplied string. Returns an <code>InstrumentList</code> containing the <code>Instrument</code>s
     * that were found
     *
     * @param keywords the space-separated keywords to search
     * @param prePostActionWrapper a wrapper for functions defined in the view layer (can be null)
     * @return the list of found instruments
     * @see InstrumentList
     * @see Instrument
     */
    public void searchInstruments(PrePostActionWrapper prePostActionWrapper, String keywords) {
        ElasticsearchController.SearchInstrumentsTask searchInstrumentsTask = new ElasticsearchController.SearchInstrumentsTask(prePostActionWrapper);
        searchInstrumentsTask.execute(keywords);
    }

    /**
     * The currently logged in <code>User</code> makes a <code>Bid</code> on the supplied
     * <code>Instrument</code> and for the supplied amount
     *
     * @param instrument the instrument to bid on
     * @param amount the bid amount
     * @see Bid
     * @see Instrument
     */
    public void makeBidOnInstrument(Instrument instrument, float amount) {

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

    /**
     * The currently logged in <code>User</code> accepts a <code>Bid</code> on one of it's
     * <code>Instrument</code>s. The instrument's status is set to borrowed and all other bids are declined.
     *
     * @param bid the bid to accept
     * @see Bid
     * @see Instrument
     */
    public void acceptBidOnInstrument(Bid bid) {
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

    /**
     * The currently logged in <code>User</code> declines a <code>Bid</code> on one of it's
     * <code>Instrument</code>s
     *
     * @param bid the bid to decline
     * @see Bid
     * @see Instrument
     */
    public void declineBidOnInstrument(Bid bid) {
        if (!this.currentUser.getOwnedInstruments().containsInstrument(bid.getInstrumentId())) {
            throw new RuntimeException("logged in user does not own instrument");
        }
        if (!this.currentUser.getOwnedInstruments().getInstrument(bid.getInstrumentId()).getBids().containsBid(bid)) {
            throw new RuntimeException("instrument does not contain that bid");
        }
        //Instrument instrument = this.currentUser.getOwnedInstruments().getInstrument(bid.getInstrumentId());

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

    /**
     * The currently logged in <code>User</code> returns a borrowed <code>Instrument</code>
     *
     * @param instrument the instrument to return
     * @see Instrument
     */
    public void returnInstrument(Instrument instrument) {
        if (!this.getCurrentUsersBorrowedInstruments().containsInstrument(instrument)) {
            throw new RuntimeException();
        }
        this.getCurrentUsersBorrowedInstruments().removeInstrument(instrument);

        // get the owner of the instrument and set the flag on his instrument
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
        User owner = new Deserializer().deserializeUser(users.get(0));
        owner.getOwnedInstruments().getInstrument(instrument.getId()).setReturnedFlag(true);

        // update both users
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.getCurrentUser());
        ElasticsearchController.UpdateUserTask updateUserTask1 = new ElasticsearchController.UpdateUserTask();
        updateUserTask1.execute(owner);
    }

    /**
     * The currently logged in <code>User</code> returns a borrowed <code>Instrument</code>
     *
     * @param index the index of the instrument to return
     * @see Instrument
     */
    public void returnInstrument(int index) {
        if (index >= this.getCurrentUsersBorrowedInstruments().size()) {
            throw new RuntimeException();
        }
        Instrument instrument = this.getCurrentUsersBorrowedInstruments().getInstrument(index);
        this.getCurrentUsersBorrowedInstruments().removeInstrument(instrument);

        // get the owner of the instrument and set the flag on his instrument
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
        User owner = new Deserializer().deserializeUser(users.get(0));
        owner.getOwnedInstruments().getInstrument(instrument.getId()).setReturnedFlag(true);

        // update both users
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.getCurrentUser());
        ElasticsearchController.UpdateUserTask updateUserTask1 = new ElasticsearchController.UpdateUserTask();
        updateUserTask1.execute(owner);
    }

    /**
     * The currently logged in <code>User</code> sets a returned <code>Instrument</code> back to available
     *
     * @param instrument the instrument to set to available
     * @see Instrument
     */
    public void acceptReturnedInstrument(Instrument instrument) {
        instrument.setReturnedFlag(false);
        instrument.getBids().clearBids();
        instrument.setBorrowedById(null);
        instrument.setStatus("available");
        instrument.clearLocation();
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.getCurrentUser());
    }

    /**
     * The currently logged in <code>User</code> sets a returned <code>Instrument</code> back to available
     *
     * @param index the index of the instrument to set to available
     * @see Instrument
     */
    public void acceptReturnedInstrument(int index) {
        // NOTE: INDEX IS FOR USERS OWNED BORROWED INSTRUMENTS
        Instrument instrument = this.getCurrentUsersOwnedBorrowedInstruments().getInstrument(index);
        instrument.setReturnedFlag(false);
        instrument.getBids().clearBids();
        instrument.setBorrowedById(null);
        instrument.setStatus("available");
        instrument.clearLocation();
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.getCurrentUser());
    }

    /**
     * Resets the NewBidFlag for the currently logged in <code>User</code>
     */
    public void resetNewBidFlag() {
        this.currentUser.setNewBidFlag(false);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.getCurrentUser());
    }

    /**
     * Adds a photo to an <code>Instrument</code>
     *
     * @param instrument the instrument to add a photo to
     * @param photo the photo to add
     * @see Instrument
     */
    public void addPhotoToInstrument(Instrument instrument, Bitmap photo) {
        if (!this.currentUser.getOwnedInstruments().containsInstrument(instrument)) {
            throw new RuntimeException();
        }
        instrument.addThumbnail(photo);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    /**
     * Deletes a photo from an <code>Instrument</code>
     *
     * @param instrument the instrument to delete a photo from
     * @see Instrument
     */
    public void deletePhotoFromInstrument(Instrument instrument) {
        if (!this.currentUser.getOwnedInstruments().containsInstrument(instrument)) {
            throw new RuntimeException();
        }
        instrument.deleteThumbnail();
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    /**
     * Sets the location to pick up the <code>Instrument</code>
     *
     * @param instrument
     * @param longitude
     * @param latitude
     */
    public void setLocationForInstrument(Instrument instrument, float longitude, float latitude) {
        if (!this.currentUser.getOwnedInstruments().containsInstrument(instrument)) {
            throw new RuntimeException();
        }
        instrument.setLocation(longitude, latitude);
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }

    /**
     * Clears the pick up location of the <code>Instrument</code>
     *
     * @param instrument
     */
    public void clearLocationForInstrument(Instrument instrument) {
        if (!this.currentUser.getOwnedInstruments().containsInstrument(instrument)) {
            throw new RuntimeException();
        }
        instrument.clearLocation();
        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(this.currentUser);
    }
}
