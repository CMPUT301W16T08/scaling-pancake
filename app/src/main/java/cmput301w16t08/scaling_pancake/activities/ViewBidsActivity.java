package cmput301w16t08.scaling_pancake.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.adapters.BidsAdapter;
import cmput301w16t08.scaling_pancake.controllers.Controller;

public class ViewBidsActivity extends ListActivity
{

    private static Controller controller;
    private BidsAdapter bidsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids);

        Intent intent = getIntent();

        controller = (Controller) getApplicationContext();
        bidsAdapter = new BidsAdapter(controller,
                controller.getInstrumentById(intent.getStringExtra("instrument_id")).getBids());

        setListAdapter(bidsAdapter);
    }
}
