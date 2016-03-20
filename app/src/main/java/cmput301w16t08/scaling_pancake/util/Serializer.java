package cmput301w16t08.scaling_pancake.util;

import org.json.JSONException;
import org.json.JSONObject;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * <code>Serializer</code> is a class meant to translate between <code>User</code>s,
 * <code>Instrument</code>s, and <code>Bid</code>s, and JSON strings that can be used by
 * the <code>ElasticsearchController</code>.
 *
 * @author William
 * @see User
 * @see Instrument
 * @see Bid
 * @see cmput301w16t08.scaling_pancake.controllers.ElasticsearchController
 */
public class Serializer {
    /**
     * Translates a <code>User</code> into a JSON string
     * Calls <code>serializeInstrument()</code> to translate owned and borrowed instruments
     * Calls <code>serializeBid()</code> to translate bids
     *
     * @param user the user to translate
     * @return the JSON string
     */
    public String serializeUser(User user) {
        String string = "{\"name\" : \"" + user.getName() +
                "\", \"email\" : \"" + user.getEmail() +
                "\", \"id\" : \"" + user.getId() +
                "\", \"newBidFlag\" : \"" + String.valueOf(user.getNewBidFlag()) +

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

    /**
     * Translates a <code>Instrument</code> into a JSON string
     * Calls <code>serializeBid()</code> to translate bids
     *
     * @param instrument the instrument to translate
     * @return the JSON string
     */
    public String serializeInstrument(Instrument instrument) {
        String string = "{\"id\" : \"" + instrument.getId() +
                "\", \"name\" : \"" + instrument.getName() +
                "\", \"description\" : \"" + instrument.getDescription() +
                "\", \"ownerId\" : \"" + instrument.getOwnerId() +
                "\", \"status\" : \"" + instrument.getStatus() +
                "\", \"returnedFlag\" : \"" + String.valueOf(instrument.getReturnedFlag()) +
                "\", \"thumbnailBase64\" : \"" + instrument.getThumbnailBase64() +
                "\", \"longitude\" : \"" + String.valueOf(instrument.getLongitude()) +
                "\", \"latitude\" : \"" + String.valueOf(instrument.getLatitude());

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

    /**
     * Translates a <code>Bid</code> into a JSON string
     *
     * @param bid the bid to translate
     * @return the JSON string
     */
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
