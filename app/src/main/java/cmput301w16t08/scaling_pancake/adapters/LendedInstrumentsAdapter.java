package cmput301w16t08.scaling_pancake.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.InstrumentList;

/**
 * Created by dan on 24/03/16.
 */
public class LendedInstrumentsAdapter extends ArrayAdapter
{
    private Controller controller;

    public LendedInstrumentsAdapter(Controller controller, InstrumentList instrumentList)
    {
        super(controller, 0, instrumentList.getArray());

        this.controller = controller;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Instrument lendedInstrument = (Instrument) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lended_instrument_list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.lended_instrument_list_item_name_tv);
        TextView borrower = (TextView) convertView.findViewById(R.id.lended_instrument_list_item_borrower_tv);
        TextView rate = (TextView) convertView.findViewById(R.id.lended_instrument_list_item_rate_tv);

        name.append(lendedInstrument.getName());
        borrower.append(controller.getUserById(lendedInstrument.getBorrowedById()).getName());
        rate.append(String.format("%.2f/hr", lendedInstrument.getBids().getBid(0).getBidAmount()));

        return convertView;
    }
}
