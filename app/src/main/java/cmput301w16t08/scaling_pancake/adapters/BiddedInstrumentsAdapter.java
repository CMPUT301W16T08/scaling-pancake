package cmput301w16t08.scaling_pancake.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.BidList;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.R;

/**
 * Provides a custom adapter for <code>Instruments</code> that have been bidded on by the
 * currently logged in user. Populates the <code>InstrumentListActivity</code> when the
 * "My Bids" option is chosen from the spinner.
 *
 * @author dan
 * @see cmput301w16t08.scaling_pancake.models.Instrument
 * @see Bid
 */
public class BiddedInstrumentsAdapter extends ArrayAdapter
{
    public BiddedInstrumentsAdapter(Controller controller, BidList bidList)
    {
        super(controller, 0, bidList.getArray());
    }

    /**
     * Handles assignment to different fields unique to this list type.
     * @param position
     * @param convertView
     * @param parent
     * @return the view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Bid bid = (Bid) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bidded_instrument_list_item, parent, false);
        }

        /* Get the thumbnail going */
//        try
//        {
//            final BitmapFactory.Options options = new BitmapFactory.Options();
//            /* In the future, the specific image will be retrieved. For now mothra is a placeholder */
//            final Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),
//                    R.drawable.mothra, options);
//            ImageView thumbnail = (ImageView) convertView.findViewById(R.id.bidded_instrument_list_item_thumbnail);
//            thumbnail.setImageBitmap(bitmap);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        /* Add the description and status */
        TextView description = (TextView) convertView.findViewById(R.id.bidded_instrument_list_item_description_tv);
        TextView username = (TextView) convertView.findViewById(R.id.bidded_instrument_list_item_username_tv);
        TextView bidAmount = (TextView) convertView.findViewById(R.id.bidded_instrument_list_item_bid_tv);

        description.setText(((Controller)getContext()).getInstrumentById(bid.getInstrumentId()).getDescription());
        username.setText(String.format("Owner: %s", ((Controller) getContext()).getUserById(bid.getOwnerId()).getName()));
        bidAmount.setText(String.format("Bid: %.2f/hr", bid.getBidAmount()));

        return convertView;
    }

}
