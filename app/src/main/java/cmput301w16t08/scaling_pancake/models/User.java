package cmput301w16t08.scaling_pancake.models;

import java.util.UUID;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.BidList;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.InstrumentList;

/**
 * Created by William on 2016-02-12.
 */
public class User {
    private String name;
    private String email;
    private InstrumentList ownedInstruments;
    private InstrumentList borrowedInstruments;
    private BidList bids;
    private String id;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.ownedInstruments = new InstrumentList();
        this.borrowedInstruments = new InstrumentList();
        this.bids = new BidList();
        this.id = UUID.randomUUID().toString();
    }

    public User(String name, String email, String id) {
        this.name = name;
        this.email = email;
        this.ownedInstruments = new InstrumentList();
        this.borrowedInstruments = new InstrumentList();
        this.bids = new BidList();
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public InstrumentList getOwnedInstruments() {
        return this.ownedInstruments;
    }

    public InstrumentList getBorrowedInstruments() {
        return this.borrowedInstruments;
    }

    public InstrumentList getOwnedBorrowedInstruments() {
        InstrumentList list = new InstrumentList();
        for (int i = 0; i < this.getOwnedInstruments().size(); i++) {
            if (this.getOwnedInstruments().getInstrument(i).getStatus().equals("borrowed")) {
                list.addInstrument(this.getOwnedInstruments().getInstrument(i));
            }
        }
        return list;
    }

    public BidList getBids() {
        return this.bids;
    }

    public void addOwnedInstrument(Instrument instrument) {
        this.ownedInstruments.addInstrument(instrument);
    }

    public void addOwnedInstrument(String name, String description) {
        Instrument instrument = new Instrument(this.getId(), name, description);
        this.ownedInstruments.addInstrument(instrument);
    }

    public void addBorrowedInstrument(Instrument instrument) {
        this.borrowedInstruments.addInstrument(instrument);
    }

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

    public void deleteOwnedInstrument(Instrument instrument) {
        if (this.ownedInstruments.containsInstrument(instrument)) {
            this.ownedInstruments.removeInstrument(instrument);
        } else {
            throw new RuntimeException();
        }
    }

    public void deleteBorrowedInstrument(Instrument instrument) {
        if (this.borrowedInstruments.containsInstrument(instrument)) {
            this.borrowedInstruments.removeInstrument(instrument);
        } else {
            throw new RuntimeException();
        }
    }

    public void addBid(Bid bid) {
        this.bids.addBid(bid);
    }

    public void deleteBid(Bid bid) {
        if (this.bids.containsBid(bid)) {
            this.bids.removeBid(bid);
        } else {
            throw new RuntimeException();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}