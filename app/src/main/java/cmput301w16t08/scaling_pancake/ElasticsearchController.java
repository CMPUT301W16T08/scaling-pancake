package cmput301w16t08.scaling_pancake;

import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

/**
 * Created by William on 2016-02-25.
 */
public class ElasticsearchController {
    private static JestDroidClient client;

    public static class CreateUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... strings) {
            // NOTE: only EVER called with one string (the username to create a profile for)
            // returns null if username already in use
            verifyClient();
            return null;
        }
    }
    public static class GetUserTask extends AsyncTask<String, Void, User> {
        // also used for logging in (check that the user exists)
         @Override
        protected User doInBackground(String... strings) {
             // NOTE: only EVER called with one string (the username to search for)
             // returns null if user cannot be found
             verifyClient();
             return null;
         }
    }

    public static class GetInstrumentsTask extends AsyncTask<String, Void, InstrumentList> {
        @Override
        protected InstrumentList doInBackground(String... strings) {
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

    public static class EditUserTask extends AsyncTask<Instrument, Void, Void> {
        @Override
        protected Void doInBackground(Instrument... instruments) {
            verifyClient();
            return null;
        }
    }

    public static void verifyClient() {
        if(client == null) {
            // TODO: Put this URL somewhere it makes sense (e.g. class variable?)
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
