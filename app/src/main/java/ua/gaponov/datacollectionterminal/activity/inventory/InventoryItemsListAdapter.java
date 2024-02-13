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
import ua.gaponov.datacollectionterminal.inventory.InventoryItem;

public class InventoryItemsListAdapter extends SimpleAdapter {

    List<? extends Map<String, ?>> data;
    private Context context;

    public InventoryItemsListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_inventory_detail, parent, false);

        TextView textViewRowNumber = view.findViewById(R.id.textViewRowNumber);
        TextView productName = view.findViewById(R.id.productName);
        TextView productQty = view.findViewById(R.id.productQty);
        TextView productPrice = view.findViewById(R.id.productPrice);

        Map<String, ?> v = data.get(position);
        InventoryItem item = (InventoryItem) v.get("item");
        int i = data.indexOf(position);

        textViewRowNumber.setText(String.valueOf(i + 1));
        productName.setText(item.getProductName());
        productQty.setText(String.valueOf(item.getQuantity()));
        productPrice.setText(String.valueOf(item.getPrice()));

        return view;
    }
}
