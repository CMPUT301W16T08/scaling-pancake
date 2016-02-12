package cmput301w16t08.scaling_pancake;

import java.util.ArrayList;

/**
 * Created by William on 2016-02-12.
 */
public class User {
    private String name;
    private String email;
    private ArrayList<Instrument> ownedInstruments = new ArrayList<Instrument>();
    private ArrayList<Instrument> borrowedInstruments = new ArrayList<Instrument>();
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Instrument> getOwnedInstruments() {
        return ownedInstruments;
    }

    public ArrayList<Instrument> getBorrowedInstruments() {
        return borrowedInstruments;
    }

    public ArrayList<Instrument> getOwnedBiddedInstruments() {
        //TODO: add method body
    }

    public ArrayList<Instrument> getBiddedInstruments() {
        //TODO: add method body
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }


    public void addInstrument(Instrument instrument) {
        this.ownedInstruments.add(instrument);
    }

    public void addInstrument(String name, String description) {
        Instrument instrument = new Instrument(this, name, description);
        this.ownedInstruments.add(instrument);
    }

    public void deleteInstrument(Instrument instrument) {
        this.ownedInstruments.remove(instrument);
    }

    public void acceptBid(Bid bid) {
        //TODO: add method body
    }

    public void declineBid(Bid bid) {
        //TODO: add method body
    }
}
