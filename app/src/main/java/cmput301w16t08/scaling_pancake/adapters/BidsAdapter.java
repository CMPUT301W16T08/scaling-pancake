package cmput301w16t08.scaling_pancake.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.ViewBidsActivity;
import cmput301w16t08.scaling_pancake.activities.ViewInstrumentActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.BidList;

/**
 * Created by dan on 24/03/16.
 */
public class    BidsAdapter extends ArrayAdapter
{

    private Controller controller;
    private BidList bidList;

    public BidsAdapter(Controller controller, ViewBidsActivity activity, BidList bidList)
    {
        super(controller, 0, bidList.getArray());
        this.bidList = bidList;
        this.controller = controller;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Bid bid = (Bid) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bid_list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.bid_list_item_name_tv);
        TextView bidder = (TextView) convertView.findViewById(R.id.bid_list_item_bidder_tv);
        TextView rate = (TextView) convertView.findViewById(R.id.bid_list_item_rate_tv);

        convertView.findViewById(R.id.bid_list_item_accept_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!bid.getAccepted()) {
                    controller.acceptBidOnInstrument(bid);
                    Toast.makeText(controller, "Bid Accepted!", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            }
        });

        convertView.findViewById(R.id.bid_list_item_decline_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!bid.getAccepted()) {
                    controller.declineBidOnInstrument(bid);
                    bidList.removeBid(bid);
                    Toast.makeText(controller, "Bid Declined", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            }
        });

        name.setText(controller.getInstrumentById(bid.getInstrumentId()).getName());
        bidder.setText(controller.getUserById(bid.getBidderId()).getName());
        rate.setText(String.format("%.2f/hr", bid.getBidAmount()));

        return convertView;
    }


}
