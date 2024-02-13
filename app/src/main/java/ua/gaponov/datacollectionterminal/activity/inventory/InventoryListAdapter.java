package ua.gaponov.datacollectionterminal.activity.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ua.gaponov.datacollectionterminal.R;
import ua.gaponov.datacollectionterminal.inventory.Inventory;

public class InventoryListAdapter extends SimpleAdapter {

    List<? extends Map<String, ?>> data;
    private Context context;

    public InventoryListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_inventory, parent, false);

        TextView inventoryDate = view.findViewById(R.id.inventoryDate);
        TextView inventoryComment = view.findViewById(R.id.inventoryComment);
        TextView textViewId = view.findViewById(R.id.textViewRowNumber);

        Map<String, ?> v = data.get(position);
        Inventory inventory = (Inventory) v.get("doc");

        inventoryDate.setText(inventory.getDate());
        inventoryComment.setText(inventory.getComment());
        textViewId.setText(String.valueOf(inventory.getDocId()));

        return view;
    }
}
