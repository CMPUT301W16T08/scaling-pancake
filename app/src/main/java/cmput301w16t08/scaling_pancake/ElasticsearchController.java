package cmput301w16t08.scaling_pancake;

import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.Index;

/**
 * Created by William on 2016-02-25.
 */
public class ElasticsearchController {
    private static JestDroidClient client;
    private static String url = "http://cmput301.softwareprocess.es:8080";

    public static class CreateUserTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            verifyClient();
            return null;
        }
    }

    public static class GetUserTask extends AsyncTask<String, Void, User> {
         @Override
        protected User doInBackground(String... strings) {
             // NOTE: only EVER called with one string (the username to search for)
             // returns null if user cannot be found
             verifyClient();
             return null;
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

    public static class AddInstrumentTask extends AsyncTask<Instrument, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Instrument... instruments) {
            verifyClient();
            return null;
        }
    }

    public static class EditInstrumentTask extends AsyncTask<Instrument, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Instrument... instruments) {
            verifyClient();
            return null;
        }
    }

    public static class EditUserTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            verifyClient();
            return null;
        }
    }

    public static class DeleteUserTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            verifyClient();
            return null;
        }
    }

    public static class DeleteInstrumentTask extends AsyncTask<Instrument, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Instrument... instruments) {
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
