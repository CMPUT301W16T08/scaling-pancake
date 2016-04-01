package cmput301w16t08.scaling_pancake.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.InstrumentList;
import cmput301w16t08.scaling_pancake.R;

/**
 * Provides a custom adapter for <code>Instruments</code> that have been borrowed by the
 * currently logged in user. Populates the <code>InstrumentListActivity</code> when the
 * "Borrowed" option is chosen from the spinner.
 *
 * @author dan
 *
 * @see Instrument
 *
 */
public class BorrowedInstrumentAdapter extends ArrayAdapter
{

    public BorrowedInstrumentAdapter(Context context, InstrumentList instrumentList)
    {
        super(context, 0, instrumentList.getArray());
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
        Instrument instrument = (Instrument) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.borrowed_instrument_list_item, parent, false);
        }

        /* Add the description and status */
        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.borrowed_instrument_list_item_thumbnail_iv);
        TextView name = (TextView) convertView.findViewById(R.id.borrowed_instrument_list_item_name_tv);
        TextView rate = (TextView) convertView.findViewById(R.id.borrowed_instrument_list_item_rate_tv);
        TextView description = (TextView) convertView.findViewById(R.id.borrowed_instrument_list_item_description_tv);
        TextView username = (TextView) convertView.findViewById(R.id.borrowed_instrument_list_item_owner_tv);

        if(instrument.hasThumbnail())
        {
            thumbnail.setImageBitmap(instrument.getThumbnail());
        }

        name.setText(instrument.getName());
        rate.setText(String.format("%.2f/hr", instrument.getBids().getBid(0).getBidAmount()));
        description.setText(instrument.getDescription());
        username.setText(String.format("Username: %s", ((Controller) getContext()).getUserById(instrument.getOwnerId()).getName()));

        return convertView;
    }
}
