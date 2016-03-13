package cmput301w16t08.scaling_pancake.util;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * Created by William on 2016-03-03.
 */
public class Serializer {
    public String serializeUser(User user) {
        String string = "{\"name\" : \"" + user.getName() +
                "\", \"email\" : \"" + user.getEmail() +
                "\", \"id\" : \"" + user.getId() +

                "\", \"ownedInstruments\" : [";
        if (user.getOwnedInstruments().size() > 0) {
            string = string + serializeInstrument(user.getOwnedInstruments().getInstrument(0));
        }
        for (int i = 1; i < user.getOwnedInstruments().size(); i++) {
            string = string + ", " + serializeInstrument(user.getOwnedInstruments().getInstrument(i));
        }

        string = string + "], \"borrowedInstruments\" : [";
        if (user.getBorrowedInstruments().size() > 0) {
            string = string + serializeInstrument(user.getBorrowedInstruments().getInstrument(0));
        }
        for (int i = 1; i < user.getBorrowedInstruments().size(); i++) {
            string = string + ", " + serializeInstrument(user.getBorrowedInstruments().getInstrument(i));
        }

        string = string + "], \"bids\" : [";
        if (user.getBids().size() > 0) {
            string = string + serializeBid(user.getBids().getBid(0));
        }
        for (int i = 1; i < user.getBids().size(); i++) {
            string = string + ", " + serializeBid(user.getBids().getBid(i));
        }
        string = string + "]}";
        return string;
    }

    public String serializeInstrument(Instrument instrument) {
        String string = "{\"id\" : \"" + instrument.getId() +
                "\", \"name\" : \"" + instrument.getName() +
                "\", \"description\" : \"" + instrument.getDescription() +
                "\", \"ownerId\" : \"" + instrument.getOwnerId() +
                "\", \"status\" : \"" + instrument.getStatus();
        if (instrument.getStatus().equals("borrowed")) {
            string = string + "\", \"borrowedById\" : \"" + instrument.getBorrowedById();
        }
        string = string + "\", \"bids\" : [";
        if (instrument.getBids().size() > 0) {
            string = string + serializeBid(instrument.getBids().getBid(0));
        }
        for (int i = 1; i < instrument.getBids().size(); i++) {
            string = string + ", " + serializeBid(instrument.getBids().getBid(i));
        }
        string = string + "]}";
        return string;
    }

    public String serializeBid(Bid bid) {
        String string = "{\"id\" : \"" + bid.getId() +
                "\", \"instrumentId\" : \"" + bid.getInstrumentId() +
                "\", \"ownerId\" : \"" + bid.getOwnerId() +
                "\", \"bidderId\" : \"" + bid.getBidderId() +
                "\", \"bidAmount\" : \"" + Float.toString(bid.getBidAmount()) +
                "\", \"accepted\" : \"" + Boolean.toString(bid.getAccepted()) + "\"}";
        return string;
    }
}
