package cmput301w16t08.scaling_pancake.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.BidList;

/**
 * Created by dan on 24/03/16.
 */
public class BidsAdapter extends ArrayAdapter
{

    public BidsAdapter(Controller controller, BidList bidList)
    {
        super(controller, 0, bidList.getArray());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Bid bid = (Bid) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bid_list_item, parent, false);
        }

        return convertView;
    }
}
