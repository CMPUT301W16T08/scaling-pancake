package cmput301w16t08.scaling_pancake.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * <code>Deserializer</code> is a class meant to translate between JSON strings used by the
 * <code>ElasticsearchController</code> and </code><code>User</code>,
 * <code>Instrument</code>, and <code>Bid</code> objects.
 *
 * @author William
 * @see User
 * @see Instrument
 * @see Bid
 * @see cmput301w16t08.scaling_pancake.controllers.ElasticsearchController
 */
public class Deserializer {
    /**
     * Translates the supplied JSON string into a <code>User</code>
     * Calls <code>deserializeInstrument()</code> to translate parts of the string to instruments
     *
     * @param string the JSON string to translate
     * @return the user
     */
    public User deserializeUser(String string) {
        JSONObject object = null;
        JSONObject object2 = null;
        User user = null;
        Instrument instrument = null;
        Bid bid = null;
        JSONArray array = null;
        ArrayList<String> user2 = null;

        try {
            object = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (object == null) {throw new RuntimeException();}

        // Create the user
        try {
            user = new User(object.getString("name"), object.getString("email"), object.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create the owned instruments
        try {
            array = object.getJSONArray("ownedInstruments");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (array == null) {throw new RuntimeException();}
        for (int i = 0; i < array.length(); i++) {
            try {
                user.addOwnedInstrument(deserializeInstrument(array.getString(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Create the borrowed instruments
        try {
            array = object.getJSONArray("borrowedInstruments");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (array == null) {throw new RuntimeException();}
        for (int i = 0; i < array.length(); i++) {
            try {
                user.addBorrowedInstrument(deserializeInstrument(array.getString(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Create the bids and the instruments attached to them
        try {
            array = object.getJSONArray("bids");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (array == null) {throw new RuntimeException();}
        for (int i = 0; i < array.length(); i++) {
            try {
                user.addBid(new Bid(array.getJSONObject(i).getString("instrumentId"), array.getJSONObject(i).getString("ownerId"),
                        array.getJSONObject(i).getString("bidderId"), (float) array.getJSONObject(i).getDouble("bidAmount"),
                        array.getJSONObject(i).getString("id")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    /**
     * Translates the supplied JSON string into a <code>Instrument</code>
     *
     * @param string the JSON string to translate
     * @return the instrument
     */
    public Instrument deserializeInstrument(String string) {
        JSONObject object = null;
        JSONObject object2 = null;
        Instrument instrument = null;
        JSONArray array = null;
        Bid bid = null;

        try {
            object = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (object == null) {throw new RuntimeException();}
        try {
            instrument = new Instrument(object.getString("ownerId"), object.getString("name"),
                    object.getString("description"), object.getString("id"));
            instrument.setStatus(object.getString("status"));
            if (instrument.getStatus().equals("borrowed")) {
                instrument.setBorrowedById(object.getString("borrowedById"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            array = object.getJSONArray("bids");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (array == null) {throw new RuntimeException();}
        for (int i = 0; i < array.length(); i++) {
            try {
                object2 = array.getJSONObject(i);
                bid = new Bid(instrument.getId(), object2.getString("ownerId"), object2.getString("bidderId"),
                        (float) object2.getDouble("bidAmount"), object2.getString("id"));
                if (object.getBoolean("accepted")) {
                    bid.setAccepted();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (bid == null) {throw new RuntimeException();}
            instrument.addBid(bid);
        }
        try {
            instrument.setStatus(object.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return instrument;
    }
}
