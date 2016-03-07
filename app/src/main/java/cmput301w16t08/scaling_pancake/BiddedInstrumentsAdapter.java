package cmput301w16t08.scaling_pancake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dan on 06/03/16.
 */
public class BiddedInstrumentsAdapter extends ArrayAdapter
{
    public BiddedInstrumentsAdapter(Context context, BidList bidList)
    {
        super(context, 0, bidList.getArray());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Bid bid = (Bid) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bidded_instrument_list_item, parent, false);
        }

        /* Get the thumbnail going */
        try
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            /* In the future, the specific image will be retrieved. For now mothra is a placeholder */
            final Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.mothra, options);
            ImageView thumbnail = (ImageView) convertView.findViewById(R.id.bidded_instrument_list_item_thumbnail);
            thumbnail.setImageBitmap(bitmap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        /* Add the description and status */
        TextView description = (TextView) convertView.findViewById(R.id.bidded_instrument_list_item_description_tv);
        TextView username = (TextView) convertView.findViewById(R.id.bidded_instrument_list_item_username_tv);
        TextView bidAmount = (TextView) convertView.findViewById(R.id.bidded_instrument_list_item_bid_tv);

        description.setText(bid.getInstrument().getDescription());
        username.setText(String.format("Owner: %s", bid.getInstrument().getOwner().getName()));
        bidAmount.setText(String.format("Bid: %.2f/hr", bid.getBidAmount()));

        return convertView;
    }

}
