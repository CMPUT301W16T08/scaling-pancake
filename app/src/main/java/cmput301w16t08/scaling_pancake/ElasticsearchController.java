package cmput301w16t08.scaling_pancake;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by William on 2016-02-25.
 */
public class ElasticsearchController {
    private static JestDroidClient client;
    private static String url = "http://cmput301.softwareprocess.es:8080";
    private static String index = "cmput301w16t08";

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

    public static class GetUserByInstrumentId extends AsyncTask<String, Void, ArrayList<String>> {
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

    public static class SearchInstrumentsTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            // each string is a separate keyword
            verifyClient();
            return null;
        }
    }

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
