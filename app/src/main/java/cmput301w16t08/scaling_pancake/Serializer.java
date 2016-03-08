package cmput301w16t08.scaling_pancake;

/**
 * Created by William on 2016-03-03.
 */
public class Serializer {
    public String serializeUser(User user) {
        String string = "{\"name\" : \"" + user.getName() +
                "\", \"email\" : \"" + user.getEmail() +

                "\", \"ownedInstruments\" : [";
        if (user.getOwnedInstruments().size() > 0) {
            string = string + "\"" + user.getOwnedInstruments().getInstrument(0).getId() + "\"";
        }
        for (int i = 1; i < user.getOwnedInstruments().size(); i++) {
            string = string + ", \"" + user.getOwnedInstruments().getInstrument(i).getId() + "\"";
        }

        string = string + "], \"borrowedInstruments\" : [";
        if (user.getBorrowedInstruments().size() > 0) {
            string = string + "\"" + user.getBorrowedInstruments().getInstrument(0).getId() + "\"";
        }
        for (int i = 1; i < user.getBorrowedInstruments().size(); i++) {
            string = string + ", \"" + user.getBorrowedInstruments().getInstrument(i).getId() + "\"";
        }

        string = string + "], \"bids\" : [";
        if (user.getBids().size() > 0) {
            string = string + "\"" + user.getBids().getBid(0).getId() + "\"";
        }
        for (int i = 1; i < user.getBids().size(); i++) {
            string = string + ", \"" + user.getBids().getBid(i).getId() + "\"";
        }
        string = string + "]}";
        return string;
    }

    public String serializeInstrument(Instrument instrument) {
        String string = "{\"name\" : \"" + instrument.getName() +
                "\", \"description\" : \"" + instrument.getDescription() +
                "\", \"owner\" : \"" + instrument.getOwner().getId() +
                "\", \"status\" : \"" + instrument.getStatus();
        if (instrument.getStatus().equals("borrowed")) {
            string = string + "\", \"borrowedBy\" : \"" + instrument.getBorrowedBy().getId();
        }
        string = string + "\", \"bids\" : [";
        if (instrument.getBids().size() > 0) {
            string = string + "\"" + instrument.getBids().getBid(0).getId() + "\"";
        }
        for (int i = 1; i < instrument.getBids().size(); i++) {
            string = string + ", \"" + instrument.getBids().getBid(i).getId() + "\"";
        }
        string = string + "]}";
        return string;
    }

    public String serializeBid(Bid bid) {
        String string = "{\"instrument\" : \"" + bid.getInstrument().getId() +
                "\", \"owner\" : \"" + bid.getOwner().getId() +
                "\", \"bidder\" : \"" + bid.getBidder().getId() +
                "\", \"bidAmount\" : \"" + Float.toString(bid.getBidAmount()) +
                "\", \"accepted\" : \"" + Boolean.toString(bid.getAccepted()) + "\"}";
        return string;
    }
}
