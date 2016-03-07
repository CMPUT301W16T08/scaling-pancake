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
 * Created by dan on 02/03/16.
 */
public class OwnedInstrumentAdapter extends ArrayAdapter
{

    public OwnedInstrumentAdapter(Context context, InstrumentList instrumentList)
    {
        super(context, 0, instrumentList.getArray());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Instrument instrument = (Instrument) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.owned_instrument_list_item, parent, false);
        }

        /* Get the thumbnail going */
        try
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            /* In the future, the specific image will be retrieved. For now mothra is a placeholder */
            final Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.mothra, options);
            ImageView thumbnail = (ImageView) convertView.findViewById(R.id.owned_instrument_list_item_thumbnail);
            thumbnail.setImageBitmap(bitmap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        /* Add the description and status */
        TextView description = (TextView) convertView.findViewById(R.id.owned_instrument_list_item_description_tv);
        TextView status = (TextView) convertView.findViewById(R.id.owned_instrument_list_item_status_tv);

        description.setText(instrument.getDescription());
        status.setText(String.format("Status: %s", instrument.getStatus()));

        return convertView;
    }
}
