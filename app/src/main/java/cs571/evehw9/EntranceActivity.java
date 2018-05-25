package cs571.evehw9;

/**
 * Created by yiqingcheng on 2017/11/24.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Collections;
import android.os.Handler;

//class ListItem {
//
//    public String Symbol;
//    public double change;
//    public double changePercent;
//    public double price;
//
//    public ListItem(String ) {
//
//    }
//}


public class EntranceActivity extends AppCompatActivity {

    private AutoCompleteTextView stockQuote;
    private ArrayAdapter<String> aAdapter;
    private List<String> completeList;
    private RequestQueue requestQueue;
    //private ProgressBar probar;

    public ArrayList<String> Symbols;
    public ArrayList<String> Prices;
    public ArrayList<String> Changes;
    private ListView favorite_list;
    private ImageButton ref_button;
    private FavoriteListAdapter mAdapter;
    //private View activity_entrance;
    private Switch autorefresh;
    private TextView Error;
    public String selectedOrder;
    //private static TreeSet<String> favoritesSet;
    private final Handler handler = new Handler();

    private Spinner sortby;
    private Spinner order;
    public void clickGetQuote(View view){
        String stockName = stockQuote.getText().toString().trim();
        if(stockName.equals("")){
            Toast.makeText(EntranceActivity.this, "Please enter a stock name or symbol",
                    Toast.LENGTH_SHORT).show();
        }else{
            getCurrentStock(stockName);
        }
    }

    public void clickClear(View view) {
        stockQuote.setText("");
    }

    private void generateIntent(JSONObject response, Intent intent) throws JSONException{

            JSONObject metaData = response.getJSONObject("Meta Data");
            JSONObject historicalData = response.getJSONObject("Time Series (Daily)");

            Iterator iter = historicalData.keys();
            JSONObject theLastDay = historicalData.getJSONObject(iter.next().toString());
            JSONObject theSecondLast = historicalData.getJSONObject(iter.next().toString());
            intent.putExtra("symbol", metaData.getString("2. Symbol"));
            intent.putExtra("date", metaData.getString("3. Last Refreshed"));
            intent.putExtra("open", theLastDay.getString("1. open"));
            intent.putExtra("high", theLastDay.getString("2. high"));
            intent.putExtra("low", theLastDay.getString("3. low"));
            intent.putExtra("the_last_close", theLastDay.getString("4. close"));
            intent.putExtra("volume", theLastDay.getString("5. volume"));
            intent.putExtra("the_second_last_close", theSecondLast.getString("4. close"));

        }


    public void getCurrentStock(String quote){

        final String symbol = quote.split("-")[0].trim();

        String url = "http://evejs-env.us-west-2.elasticbeanstalk.com/?methodName=detail&symbol=" + symbol;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            Intent toMainActivity = new Intent(EntranceActivity.this, MainActivity.class);

                            if(response.keys().next().equals("Error Message")){
                                System.out.println("error message");
                                Toast.makeText(EntranceActivity.this, "Failed to Load Data",
                            Toast.LENGTH_SHORT).show();
                                return;
                            }
                                generateIntent(response, toMainActivity);
                                EntranceActivity.this.startActivity(toMainActivity);

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("Volley error");
                        Intent toMainActivity = new Intent(EntranceActivity.this, MainActivity.class);
                        EntranceActivity.this.startActivity(toMainActivity);
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


    private void initFavoriteList() {
        this.favorite_list = (ListView) findViewById(R.id.favorite_list);
        TreeSet<String> favoritesSet = new TreeSet<>(getSharedPreferences(getString(R.string.default_shared_preferences_key), MODE_PRIVATE)
                .getStringSet(getString(R.string.favorites_key), new TreeSet<String>()));
        this.Symbols = new ArrayList<>();
        this.Prices = new ArrayList<>();
        this.Changes = new ArrayList<>();

        Iterator<String> iterator = favoritesSet.iterator();
        while (iterator.hasNext()) {
            Symbols.add(iterator.next());
        }

        for (int i = 0; i < Symbols.size(); i++) {
            Prices.add("");
            Changes.add("");
        }

        //System.out.println(mFavoriteListSymbols);
        this.mAdapter = new FavoriteListAdapter(this,
                Symbols,
                Prices,
               Changes);
                favorite_list.setAdapter(mAdapter);
                favorite_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getCurrentStock(Symbols.get(position));
            }
        });
        //delete after long click
        favorite_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long arg3) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        EntranceActivity.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        which = position;
                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.default_shared_preferences_key), MODE_PRIVATE);
                        TreeSet<String> favorites = new TreeSet<>(sharedPreferences
                                .getStringSet(getString(R.string.favorites_key), new TreeSet<String>()));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        favorites.remove(Symbols.get(which));
                       Changes.remove(which);
                        Prices.remove(which);
                       Symbols.remove(which);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.notifyDataSetInvalidated();
                        dialog.dismiss();
                        editor.putStringSet(getString(R.string.favorites_key), favorites);
                        editor.apply();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();
                return true;
            }

        });

    }
    private void refresh() {
        for (int i = 0; i < Symbols.size(); i++) {
            String url = "http://evejs-env.us-west-2.elasticbeanstalk.com/?methodName=detail&symbol=" + Symbols.get(i);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response){
                            try {
                                JSONObject metaData = response.getJSONObject("Meta Data");
                                JSONObject historicalData = response.getJSONObject("Time Series (Daily)");
                                Iterator iter = historicalData.keys();
                                JSONObject theLastDay = historicalData.getJSONObject(iter.next().toString());
                                JSONObject theSecondLast = historicalData.getJSONObject(iter.next().toString());
                                int index = Symbols.indexOf(metaData.getString("2. Symbol"));

                                System.out.println(theLastDay.getString("4. close"));
                                String lastprice = theLastDay.getString("4. close").substring(0,theLastDay.getString("4. close").length()-2);
                                String lastlastprice = theSecondLast.getString("4. close").substring(0,theLastDay.getString("4. close").length()-2);
                                Float num1 = Float.parseFloat(lastprice);
                                Float num2 = Float.parseFloat(lastlastprice);
                                String Change = String.format("%.2f",num1 - num2);
                                String ChangePercent = String.format("%.2f",((num1 - num2)/num2 * 100)) + "%";
                                if (index >= 0) {
                                    Prices.set(index, theLastDay.getString("4. close").substring(0,theLastDay.getString("4. close").length()-2));
                                    Changes.set(index, Change
                                            + " (" + ChangePercent + ")");
                                    mAdapter.refreshData(
                                            Prices,
                                            Changes);
                                }

                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            System.out.println("Volley error");
                        }
                    });

            requestQueue.add(jsonObjectRequest);
        }
    }

    public void getAutoComplete(String hint){
        final String symbol = hint.split("-")[0].trim();
        String url = "http://evejs-env.us-west-2.elasticbeanstalk.com/?methodName=auto&symbol=" + symbol;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response){
                        try {
                            completeList = new ArrayList<>();
                            for(int i = 0; i < response.length(); i++){
                                JSONObject curt = response.getJSONObject(i);
                                completeList.add(curt.getString("Symbol") + " - " +
                                        curt.getString("Name") + " (" + curt.getString("Exchange") +")");

                            }
                            aAdapter = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_dropdown_item_1line, completeList);
                            stockQuote.setAdapter(aAdapter);
                            aAdapter.notifyDataSetChanged();
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        requestQueue = Volley.newRequestQueue(EntranceActivity.this);
        stockQuote = (AutoCompleteTextView) findViewById(R.id.stock_name);
        stockQuote.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                getAutoComplete(newText);
            }

        });
        ref_button = (ImageButton)findViewById(R.id.ref_button);
        ref_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                ***Do what you want with the click here***
                try {
                    initFavoriteList();
                    refresh();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        autorefresh = (Switch)findViewById(R.id.autorefresh);
        autorefresh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                doTheAutoRefresh();
            }
        });


        //spinner change arraylist
        sortby = (Spinner)findViewById(R.id.sortby);
        order = (Spinner)findViewById(R.id.order);

        order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOrder = parent.getItemAtPosition(position).toString();
//                Toast.makeText(EntranceActivity.this, selectedOrder,
//                            Toast.LENGTH_SHORT).show();

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        sortby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedOrder != null && selectedOrder.equals("Ascending")) {
                    EntranceActivity.this.sortFavoriteListBy(selectedItem);
                } else if(selectedOrder != null && selectedOrder.equals("Descending")) {
                    EntranceActivity.this.sortReverse(selectedItem);
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    class StockItem {
        public String symbol;
        public double price;
        public double change;
        public double changePercent;

        public StockItem(String symbol, String price, String changeStr) {
            try {
                this.symbol = symbol;
                this.price = Double.parseDouble(price);

                int idx = changeStr.indexOf(' ');
                this.change = Double.parseDouble(changeStr.substring(0, idx));

                int idx1 = changeStr.indexOf('(');
                int idx2 = changeStr.indexOf('%');
                this.changePercent = Double.parseDouble(changeStr.substring(idx1+1, idx2));
            } catch (Exception e) {
            }
        }
    }

    public void sortFavoriteListBy(String sortItem) {

        ArrayList<StockItem> itemList = new ArrayList<>();

        for (int idx = 0; idx < this.Symbols.size(); idx++) {
            String symbol = this.Symbols.get(idx);
            String price = this.Prices.get(idx);
            String changeStr = this.Changes.get(idx);
            StockItem item = new StockItem(symbol, price, changeStr);
            itemList.add(item);
        }

        if (sortItem.equals("Change")) {
            Collections.sort(itemList, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem a, StockItem b) {
                    if (a.change-b.change < 0) {
                        return -1;
                    } else if (a.change == b.change) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        } else if (sortItem.equals("Price")) {
            Collections.sort(itemList, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem a, StockItem b) {
                    if (a.price-b.price < 0) {
                        return -1;
                    } else if (a.price == b.price) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        } else if (sortItem.equals("Symbol")) {
            Collections.sort(itemList, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem a, StockItem b) {
                    return a.symbol.compareTo(b.symbol);
                }
            });
        }

        for (int idx = 0; idx < itemList.size(); idx++) {
            StockItem item = itemList.get(idx);
            String symbol = item.symbol;
            String price = String.valueOf(item.price);
            String change = item.change + " (" + item.changePercent + "%)";
            this.Symbols.set(idx, symbol);
            this.Changes.set(idx, change);
            this.Prices.set(idx, price);
        }

        this.mAdapter.notifyDataSetChanged();
    }
    public void sortReverse(String sortItem) {

        ArrayList<StockItem> itemList = new ArrayList<>();

        for (int idx = 0; idx < this.Symbols.size(); idx++) {
            String symbol = this.Symbols.get(idx);
            String price = this.Prices.get(idx);
            String changeStr = this.Changes.get(idx);
            StockItem item = new StockItem(symbol, price, changeStr);
            itemList.add(item);
        }

        if (sortItem.equals("Change")) {
            Collections.sort(itemList, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem a, StockItem b) {
                    if (a.change-b.change < 0) {
                        return -1;
                    } else if (a.change == b.change) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        } else if (sortItem.equals("Price")) {
            Collections.sort(itemList, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem a, StockItem b) {
                    if (a.price-b.price < 0) {
                        return -1;
                    } else if (a.price == b.price) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        } else if (sortItem.equals("Symbol")) {
            Collections.sort(itemList, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem a, StockItem b) {
                    return a.symbol.compareTo(b.symbol);
                }
            });
        }

        for (int idx = 0; idx < itemList.size(); idx++) {
            StockItem item = itemList.get(idx);
            String symbol = item.symbol;
            String price = String.valueOf(item.price);
            String change = item.change + " (" + item.changePercent + "%)";
            this.Symbols.set(idx, symbol);
            this.Changes.set(idx, change);
            this.Prices.set(idx, price);
        }
        Collections.reverse(EntranceActivity.this.Symbols);
        Collections.reverse(EntranceActivity.this.Prices);
        Collections.reverse(EntranceActivity.this.Changes);
        this.mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFavoriteList();
        refresh();
    }
    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initFavoriteList();
                refresh();
            }
        }, 5000);
    }

}
