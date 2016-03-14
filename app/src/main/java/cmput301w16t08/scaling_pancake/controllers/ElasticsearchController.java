package cmput301w16t08.scaling_pancake.controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;

import cmput301w16t08.scaling_pancake.util.Serializer;
import cmput301w16t08.scaling_pancake.models.User;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * <code>ElasticsearchController</code> is a class containing classes that deal with saving <code>User</code>s
 * using elasticsearch. All nested classes extend <code>AsyncTask</code> and are thus run ASYNCHRONOUSLY.
 *
 * <code>ElasticsearchController</code> should only be used within the <code>Controller</code>.
 *
 * @author William
 * @see User
 * @see Controller
 */
public class ElasticsearchController {
    private static JestDroidClient client;
    private static String url = "http://cmput301.softwareprocess.es:8080";
    private static String index = "cmput301w16t08";

    /**
     * <code>CreateUserTask</code> is meant to save a new <code>User</code>
     *
     * @see User
     * @see Serializer
     */
    public static class CreateUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();
            Serializer serializer = new Serializer();
            Index ind = new Index.Builder(serializer.serializeUser(users[0])).index(index).type("user").id(users[0].getId()).refresh(true).build();
            try {
                DocumentResult result = client.execute(ind);
                if (result.isSucceeded()) {
                    Log.d("ESC", "CreateUserTask completed.");
                    users[0].setId(result.getId());
                } else {
                    throw new RuntimeException("adding new user failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * <code>GetUserTask</code> is meant to search for the <code>User</code> with the supplied id
     * Returns an <code>ArrayList</code> of JSON strings that can be translated into <code>User</code>s
     * using the <code>Deserializer</code> class.
     * The returned <code>ArrayList</code> should only contain 1 <code>User</code> (due to unique ids).
     *
     * @see User
     * @see cmput301w16t08.scaling_pancake.util.Deserializer
     */
    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<String>> {
         @Override
        protected ArrayList<String> doInBackground(String... strings) {
             // NOTE: only EVER called with one string (the id to search for)
             // returns an empty list if no users with that name found
             verifyClient();
             String string = "{\"query\": {\"match\": {\"id\": \"" + strings[0] + "\"}}}";
             Search search = new Search.Builder(string).addIndex(index).addType("user").build();

             ArrayList<String> returnedStrings = null;
             try {
                 SearchResult result = client.execute(search);
                 if (result.isSucceeded()) {
                     Log.d("ESC", "GetUserTask completed.");
                     returnedStrings = (ArrayList) result.getSourceAsStringList();
                 } else {
                     throw new RuntimeException("search did not succeed");
                 }
             } catch (IOException e) {
                 throw new RuntimeException("search did not succeed");
             }
             return returnedStrings;
         }
    }

    /**
     * <code>GetUserByNameTask</code> is meant to search for the <code>User</code> with the supplied username
     * Returns an <code>ArrayList</code> of JSON strings that can be translated into <code>User</code>s
     * using the <code>Deserializer</code> class.
     * The returned <code>ArrayList</code> may contain more then 1 <code>User</code> (due to other <code>User</code>s
     * having usernames that begin the same).
     *
     * @see User
     * @see cmput301w16t08.scaling_pancake.util.Deserializer
     */
    public static class GetUserByNameTask extends AsyncTask<String, Void, ArrayList<String>> {
        protected  ArrayList<String> doInBackground(String... strings) {
            // NOTE: only EVER called with one string (the username to search for)
            // returns an empty list if no users with that name found
            // may return more then one item if 2 usernames start with the string
            verifyClient();
            String string = "{\"query\": {\"match\": {\"name\": \"" + strings[0] + "\"}}}";
            Search search = new Search.Builder(string).addIndex(index).addType("user").build();

            ArrayList<String> returnedStrings = null;
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    Log.d("ESC", "GetUserByNameTask completed.");
                    returnedStrings = (ArrayList) result.getSourceAsStringList();
                } else {
                    throw new RuntimeException("search did not succeed");
                }
            } catch (IOException e) {
                throw new RuntimeException("search did not succeed");
            }
            return returnedStrings;
        }
    }

    /**
     * <code>GetUserByInstrumentIdTask</code> is meant to search for the <code>User</code> that owns the supplied
     * <code>Instrument</code>. Returns an <code>ArrayList</code> of JSON strings that can be translated
     * into <code>User</code>s using the <code>Deserializer</code> class.
     * The returned <code>ArrayList</code> should only contain 1 <code>User</code> (due to unique instrument ids).
     *
     * @see User
     * @see cmput301w16t08.scaling_pancake.models.Instrument
     * @see cmput301w16t08.scaling_pancake.util.Deserializer
     */
    public static class GetUserByInstrumentIdTask extends AsyncTask<String, Void, ArrayList<String>> {
        protected ArrayList<String> doInBackground(String... strings) {
            verifyClient();
            String string = "{\"query\": { \"nested\": {\"path\": \"ownedInstruments\", \"query\": {\"match\": {\"ownedInstruments.id\": \"" + strings[0] + "\"}}}}}";
            Search search = new Search.Builder(string).addIndex(index).addType("user").build();

            ArrayList<String> returnedStrings = null;
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    Log.d("ESC", "GetUserTask completed.");
                    returnedStrings = (ArrayList) result.getSourceAsStringList();
                } else {
                    throw new RuntimeException("search did not succeed");
                }
            } catch (IOException e) {
                throw new RuntimeException("search did not succeed");
            }
            return returnedStrings;
        }
    }

    /**
     * <code>DeleteUserTask</code> is meant to delete a <code>User</code>
     *
     * @see User
     */
    public static class DeleteUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();
            try {
                client.execute(new Delete.Builder(users[0].getId()).index(index).type("user").refresh(true).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * <code>DeleteUserByIdTask</code> is meant to delete the <code>User</code> with the supplied id
     *
     * @see User
     */
    public static class DeleteUserByIdTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            verifyClient();
            try {
                client.execute(new Delete.Builder(strings[0]).index(index).type("user").refresh(true).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * <code>UpdateUserTask</code> is meant to update the index of the <code>User</code> to reflect
     * any new changes
     *
     * @see User
     * @see Serializer
     */
    public static class UpdateUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            Serializer serializer = new Serializer();
            verifyClient();
            Index ind = new Index.Builder(serializer.serializeUser(users[0])).index(index).type("user").id(users[0].getId()).refresh(true).build();
            try {
                DocumentResult result = client.execute(ind);
                if (result.isSucceeded()) {
                    Log.d("ESC", "UpdateUserTask completed.");
                } else {
                    throw new RuntimeException("UpdateUserTask not completed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

 /*   *//**
     * <code>SearchInstrumentsTask</code> is meant to search all the <code>Instrument</code>s already saved
     * and find the ones containing the keywords that are separated by a space in the supplied string.
     * Returns an <code>ArrayList</code> of JSON strings, that can be translated using the <code>Deserializer</code>
     * class, corresponding to the <code>User</code>s that own the instruments.
     *
     * @see User
     * @see cmput301w16t08.scaling_pancake.models.Instrument
     *//*
    public static class SearchInstrumentsTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            // only one string containing all the keywords separated by spaces should be entered
            verifyClient();
            String string = "{\"query\": { \"nested\": {\"path\": \"ownedInstruments\", \"query\": \"bool\" : {\"should\" : [{\"match\": {\"ownedInstruments.description\": \"" + strings[0] + "\"}}, {\"match\": {\"ownedInstruments.name\": \"" + strings[0] + "\"}}]}}}}";
            Search search = new Search.Builder(string).addIndex(index).addType("user").build();

            ArrayList<String> returnedStrings = null;
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    Log.d("ESC", "GetUserTask completed.");
                    returnedStrings = (ArrayList) result.getSourceAsStringList();
                } else {
                    throw new RuntimeException("search did not succeed");
                }
            } catch (IOException e) {
                throw new RuntimeException("search did not succeed");
            }
            return returnedStrings;
        }
    }*/

    public static void verifyClient() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(url);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
