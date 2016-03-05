package cmput301w16t08.scaling_pancake;

import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private static String index = "CMPUT301W16T08";

    public static class CreateUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();
            Index ind = new Index.Builder(users[0].toString()).index(index).type("user").build();
            try {
                DocumentResult result = client.execute(ind);
                if (result.isSucceeded()) {
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

    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<User>> {
         @Override
        protected ArrayList<User> doInBackground(String... strings) {
             // NOTE: only EVER called with one string (the username to search for)
             // returns an empty list if no users with that name found
             // can return more then 1 user if they both start with the search string
             verifyClient();

             ArrayList<User> user = new ArrayList<User>();
             String string = "{\"query\" : {\"filtered\" : {\"query\" : {\"match_all\" : {}}," +
                     "\"filter\" : {\"term\" : {\"name\" : " + strings[0] + "}}}}}";
             Search search = new Search.Builder(string).addIndex(index).addType("user").build();

             try {
                 SearchResult result = client.execute(search);
                 if (result.isSucceeded()) {
                     //user = ElasticsearchController.stringToUserArrayList((ArrayList<String>) result.getSourceAsStringList());
                 } else {
                     throw new RuntimeException("search did not succeed");
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
             return user;
         }
    }

    public static class GetInstrumentTask extends AsyncTask<Instrument, Void, Instrument> {
        @Override
        protected Instrument doInBackground(Instrument... instruments) {
            // NOTE: only EVER called with one string (the username to search for)
            // returns null if user cannot be found
            verifyClient();
            return null;
        }
    }

    public static class GetInstrumentsTask extends AsyncTask<User, Void, InstrumentList> {
        @Override
        protected InstrumentList doInBackground(User... users) {
            // each string is a separate keyword
            verifyClient();
            return null;
        }
    }

    public static class AddInstrumentTask extends AsyncTask<Instrument, Void, Void> {
        @Override
        protected Void doInBackground(Instrument... instruments) {
            verifyClient();
            return null;
        }
    }

    public static class EditInstrumentTask extends AsyncTask<Instrument, Void, Void> {
        @Override
        protected Void doInBackground(Instrument... instruments) {
            verifyClient();
            return null;
        }
    }

    public static class EditUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();
            return null;
        }
    }

    public static class DeleteUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();
            try {
                client.execute(new Delete.Builder(users[0].getId()).index(index).type("user").build());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class DeleteInstrumentTask extends AsyncTask<Instrument, Void, Void> {
        @Override
        protected Void doInBackground(Instrument... instruments) {
            verifyClient();
            return null;
        }
    }

    public static class SearchInstrumentsTask extends AsyncTask<String, Void, InstrumentList> {
        @Override
        protected InstrumentList doInBackground(String... strings) {
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
