package cmput301w16t08.scaling_pancake.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.InstrumentList;
import cmput301w16t08.scaling_pancake.R;

/**
 * Provides a custom adapter for <code>Instruments</code> that are owned by the
 * currently logged in user. Populates the <code>InstrumentListActivity</code> when the
 * "Owned" option is chosen from the spinner.
 *
 * @author dan
 * @see Instrument
 */
public class OwnedInstrumentAdapter extends ArrayAdapter
{

    public OwnedInstrumentAdapter(Context context, InstrumentList instrumentList)
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.owned_instrument_list_item, parent, false);
        }

        /* Add the description and status */
        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.owned_instrument_list_item_thumbnail_iv);
        TextView name = (TextView) convertView.findViewById(R.id.owned_instrument_list_item_name_tv);
        TextView description = (TextView) convertView.findViewById(R.id.owned_instrument_list_item_description_tv);
        TextView status = (TextView) convertView.findViewById(R.id.owned_instrument_list_item_status_tv);

        if(instrument.hasThumbnail())
        {
            thumbnail.setImageBitmap(instrument.getThumbnail());
        }
        name.setText(instrument.getName());
        description.setText(instrument.getDescription());
        status.setText(String.format("Status: %s", instrument.getStatus()));

        return convertView;
    }
}
