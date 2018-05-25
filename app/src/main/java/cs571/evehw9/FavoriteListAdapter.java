package cs571.evehw9;

/**
 * Created by yiqingcheng on 2017/11/28.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteListAdapter extends BaseAdapter {

    private ArrayList<String> Symbols;
    private ArrayList<String> Prices;
    private ArrayList<String> Changes;

    private static LayoutInflater inflater;

    public FavoriteListAdapter(Context context,
                               ArrayList<String> favoriteListSymbols,
                               ArrayList<String> favoriteListPrices,
                               ArrayList<String> favoriteListChanges) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.Symbols = favoriteListSymbols;
        this.Prices = favoriteListPrices;
        this.Changes = favoriteListChanges;
    }

    @Override
    public int getCount() {
        return Symbols.size();
    }

    @Override
    public Object getItem(int position) {
        return Symbols.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class StockNewsCellHolder {
        TextView favoriteSymbol;
        TextView favoritePrice;
        TextView favoriteChange;
    }
    @Override
    public View getView (final int position, View View, ViewGroup parent) {
        StockNewsCellHolder stock;
        if (View == null) {
            stock = new StockNewsCellHolder();
            View = inflater.inflate(R.layout.cell_favorite, parent, false);
            stock.favoriteSymbol = (TextView) View.findViewById(R.id.fav_name);
            stock.favoritePrice = (TextView) View.findViewById(R.id.fav_price);
            stock.favoriteChange = (TextView) View.findViewById(R.id.fav_change);
            View.setTag(stock);
        } else {
            stock = (StockNewsCellHolder) View.getTag();
        }

        stock.favoriteSymbol.setText(Symbols.get(position));
        stock.favoriteSymbol.setBackgroundColor(Color.WHITE);
        stock.favoritePrice.setText(Prices.get(position));
        stock.favoritePrice.setBackgroundColor(Color.WHITE);
        stock.favoriteChange.setBackgroundColor(Color.WHITE);
        String change = Changes.get(position);
        stock.favoriteChange.setText(change);
        if (change.startsWith("-")) {
            stock.favoriteChange.setTextColor(Color.RED);
        }else {
            stock.favoriteChange.setTextColor(Color.GREEN);
        }
        return View;
    }

    public void refreshData (ArrayList<String> newPrices,
                             ArrayList<String> newChanges) {
        this.Prices = newPrices;
        this.Changes = newChanges;
        notifyDataSetChanged();
    }
}