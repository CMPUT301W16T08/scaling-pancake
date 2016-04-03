package cmput301w16t08.scaling_pancake.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.InstrumentList;

/**
 * Provides a custom adapter for <code>Instruments</code> that are obtained by a
 * search query. Populates the <code>DisplaySearchResultsActivity</code>.
 *
 * @author dan
 * @see Instrument
 */
public class SearchResultsAdapter extends ArrayAdapter
{
    private Controller controller;

    public SearchResultsAdapter(Controller controller, InstrumentList instrumentList)
    {
        super(controller, 0, instrumentList.getArray());
        this.controller = controller;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_instrument_list_item, parent, false);
        }

        /* Add the description and status */
        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.displaysearchresults_list_item_thumbnail_iv);
        TextView name = (TextView) convertView.findViewById(R.id.displaysearchresults_list_item_name_tv);
        TextView description = (TextView) convertView.findViewById(R.id.displaysearchresults_list_item_description_tv);
        TextView username = (TextView) convertView.findViewById(R.id.displaysearchresults_list_item_username_tv);
        TextView status = (TextView) convertView.findViewById(R.id.displaysearchresults_list_item_status_tv);

        if(instrument.hasThumbnail())
        {
            thumbnail.setImageBitmap(instrument.getThumbnail());
        }

        name.setText(instrument.getName());
        description.setText(instrument.getDescription());
        username.setText(String.format("Owner: %s", controller.getUserById(instrument.getOwnerId()).getName()));
        status.setText(String.format("Status: %s", instrument.getStatus()));

        return convertView;
    }
}
