package cs571.evehw9;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import static android.content.Context.MODE_PRIVATE;
import static cs571.evehw9.R.layout.fragment_current;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentFragment extends Fragment {

    private CurrentListAdapter currentListAdapter;
    private Button change;
    private ImageButton star;
    private Spinner spinner;
    private String init;
    private String selectedItem;
    private View currentFragment;
    private WebView indicator;
    private String symbol;
    private TreeSet<String> favorites;
    private ProgressBar detail_bar;
    private ImageButton facebook;
    public CurrentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        currentFragment = inflater.inflate(fragment_current, container, false);
        ListView currentList = (ListView) currentFragment.findViewById(R.id.current_list);
        change = (Button)currentFragment.findViewById(R.id.change);
        TextView error = (TextView) currentFragment.findViewById(R.id.message);

            spinner = (Spinner) currentFragment.findViewById(R.id.indicator);
            init = "Price";
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedItem = parent.getItemAtPosition(position).toString();
                    System.out.println(selectedItem);
                    if (selectedItem.equals(init)) {
                        change.setEnabled(false);
                    } else {
                        change.setEnabled(true);
                    }
                } // to close the onItemSelected

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        indicator = (WebView) currentFragment.findViewById(R.id.indicateWebview);
            String url = "file:///android_asset/historical.html";
            // Enable Javascript
            WebSettings webSettings = indicator.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            webSettings.setDomStorageEnabled(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webSettings.setLoadsImagesAutomatically(true);
            } else {
                webSettings.setLoadsImagesAutomatically(false);
            }
            indicator.loadUrl(url);
            // Force links and redirects to open in the WebView instead of in a browser
            indicator.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView webview, String url) {
                    indicator.loadUrl("javascript:loadPrice('" + MainActivity.StockSymbol + "')");
//                hisProbar.setVisibility(View.INVISIBLE);
                }
            });
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                //On click function
                public void onClick(View view) {
                    change.setEnabled(false);
                    init = selectedItem;
                    //Create the intent to start another activity
                    if (selectedItem.equals("SMA")) {
                        indicator = (WebView) currentFragment.findViewById(R.id.indicateWebview);
                        String url = "file:///android_asset/historical.html";
                        // Enable Javascript
                        WebSettings webSettings = indicator.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                        webSettings.setDomStorageEnabled(true);
                        webSettings.setAllowFileAccess(true);
                        webSettings.setAppCacheEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webSettings.setLoadsImagesAutomatically(true);
                        } else {
                            webSettings.setLoadsImagesAutomatically(false);
                        }
                        indicator.loadUrl(url);
                        // Force links and redirects to open in the WebView instead of in a browser
                        indicator.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView webview, String url) {

                                indicator.loadUrl("javascript:loadSMA('" + MainActivity.StockSymbol + "')");
//                hisProbar.setVisibility(View.INVISIBLE);
                            }
                        });

                    } else if (selectedItem.equals("EMA")) {
                        indicator = (WebView) currentFragment.findViewById(R.id.indicateWebview);
                        String url = "file:///android_asset/historical.html";

                        // Enable Javascript
                        WebSettings webSettings = indicator.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                        webSettings.setDomStorageEnabled(true);
                        webSettings.setAllowFileAccess(true);
                        webSettings.setAppCacheEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webSettings.setLoadsImagesAutomatically(true);
                        } else {
                            webSettings.setLoadsImagesAutomatically(false);
                        }
                        indicator.loadUrl(url);
                        // Force links and redirects to open in the WebView instead of in a browser
                        indicator.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView webview, String url) {

                                indicator.loadUrl("javascript:loadEMA('" + MainActivity.StockSymbol + "')");
//                hisProbar.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else if (selectedItem.equals("STOCH")) {
                        indicator = (WebView) currentFragment.findViewById(R.id.indicateWebview);
                        String url = "file:///android_asset/historical.html";

                        // Enable Javascript
                        WebSettings webSettings = indicator.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                        webSettings.setDomStorageEnabled(true);
                        webSettings.setAllowFileAccess(true);
                        webSettings.setAppCacheEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webSettings.setLoadsImagesAutomatically(true);
                        } else {
                            webSettings.setLoadsImagesAutomatically(false);
                        }
                        indicator.loadUrl(url);
                        // Force links and redirects to open in the WebView instead of in a browser
                        indicator.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView webview, String url) {

                                indicator.loadUrl("javascript:loadSTOCH('" + MainActivity.StockSymbol + "')");
//                hisProbar.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else if (selectedItem.equals("RSI")) {
                        indicator = (WebView) currentFragment.findViewById(R.id.indicateWebview);
                        String url = "file:///android_asset/historical.html";

                        // Enable Javascript
                        WebSettings webSettings = indicator.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                        webSettings.setDomStorageEnabled(true);
                        webSettings.setAllowFileAccess(true);
                        webSettings.setAppCacheEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webSettings.setLoadsImagesAutomatically(true);
                        } else {
                            webSettings.setLoadsImagesAutomatically(false);
                        }
                        indicator.loadUrl(url);
                        // Force links and redirects to open in the WebView instead of in a browser
                        indicator.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView webview, String url) {

                                indicator.loadUrl("javascript:loadRSI('" + MainActivity.StockSymbol + "')");
//                hisProbar.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else if (selectedItem.equals("ADX")) {
                        indicator = (WebView) currentFragment.findViewById(R.id.indicateWebview);
                        String url = "file:///android_asset/historical.html";

                        // Enable Javascript
                        WebSettings webSettings = indicator.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                        webSettings.setDomStorageEnabled(true);
                        webSettings.setAllowFileAccess(true);
                        webSettings.setAppCacheEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webSettings.setLoadsImagesAutomatically(true);
                        } else {
                            webSettings.setLoadsImagesAutomatically(false);
                        }
                        indicator.loadUrl(url);
                        // Force links and redirects to open in the WebView instead of in a browser
                        indicator.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView webview, String url) {

                                indicator.loadUrl("javascript:loadADX('" + MainActivity.StockSymbol + "')");
//                hisProbar.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else if (selectedItem.equals("CCI")) {
                        indicator = (WebView) currentFragment.findViewById(R.id.indicateWebview);
                        String url = "file:///android_asset/historical.html";

                        // Enable Javascript
                        WebSettings webSettings = indicator.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                        webSettings.setDomStorageEnabled(true);
                        webSettings.setAllowFileAccess(true);
                        webSettings.setAppCacheEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webSettings.setLoadsImagesAutomatically(true);
                        } else {
                            webSettings.setLoadsImagesAutomatically(false);
                        }
                        indicator.loadUrl(url);
                        // Force links and redirects to open in the WebView instead of in a browser
                        indicator.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView webview, String url) {

                                indicator.loadUrl("javascript:loadCCI('" + MainActivity.StockSymbol + "')");
//                hisProbar.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else if (selectedItem.equals("BBANDS")) {
                        indicator = (WebView) currentFragment.findViewById(R.id.indicateWebview);
                        String url = "file:///android_asset/historical.html";

                        // Enable Javascript
                        WebSettings webSettings = indicator.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                        webSettings.setDomStorageEnabled(true);
                        webSettings.setAllowFileAccess(true);
                        webSettings.setAppCacheEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webSettings.setLoadsImagesAutomatically(true);
                        } else {
                            webSettings.setLoadsImagesAutomatically(false);
                        }
                        indicator.loadUrl(url);
                        // Force links and redirects to open in the WebView instead of in a browser
                        indicator.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView webview, String url) {

                                indicator.loadUrl("javascript:loadBBANDS('" + MainActivity.StockSymbol + "')");
//                hisProbar.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else if (selectedItem.equals("MACD")) {
                        indicator = (WebView) currentFragment.findViewById(R.id.indicateWebview);
                        String url = "file:///android_asset/historical.html";

                        // Enable Javascript
                        WebSettings webSettings = indicator.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                        webSettings.setDomStorageEnabled(true);
                        webSettings.setAllowFileAccess(true);
                        webSettings.setAppCacheEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webSettings.setLoadsImagesAutomatically(true);
                        } else {
                            webSettings.setLoadsImagesAutomatically(false);
                        }
                        indicator.loadUrl(url);
                        // Force links and redirects to open in the WebView instead of in a browser
                        indicator.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView webview, String url) {

                                indicator.loadUrl("javascript:loadMACD('" + MainActivity.StockSymbol + "')");
//                hisProbar.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else if (selectedItem.equals("Price")) {
                        indicator = (WebView) currentFragment.findViewById(R.id.indicateWebview);
                        String url = "file:///android_asset/historical.html";
                        // Enable Javascript
                        WebSettings webSettings = indicator.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                        webSettings.setDomStorageEnabled(true);
                        webSettings.setAllowFileAccess(true);
                        webSettings.setAppCacheEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webSettings.setLoadsImagesAutomatically(true);
                        } else {
                            webSettings.setLoadsImagesAutomatically(false);
                        }
                        indicator.loadUrl(url);
                        // Force links and redirects to open in the WebView instead of in a browser
                        indicator.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView webview, String url) {

                                indicator.loadUrl("javascript:loadPrice('" + MainActivity.StockSymbol + "')");
//                hisProbar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }
            });

            detail_bar = (ProgressBar) currentFragment.findViewById(R.id.detail_bar);
            List<CurrentCell> curtCells = generateCells();
            if (curtCells != null) {
                detail_bar.setVisibility(View.INVISIBLE);
            } else {
                detail_bar.setVisibility(View.VISIBLE);
            }
            currentListAdapter = new CurrentListAdapter(getContext(), curtCells);
            currentList.setAdapter(currentListAdapter);


            //favorite list
            symbol = MainActivity.StockSymbol;
            star = (ImageButton) currentFragment.findViewById(R.id.fav_button);
//        if(favorites.contains(symbol)){
//            star.setBackgroundResource(R.drawable.filled);
//        }
            setFavorite();

            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
//                ***Do what you want with the click here***
                    if (symbol != null && !symbol.equals("")) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.default_shared_preferences_key), MODE_PRIVATE);
                        favorites = new TreeSet<>(sharedPreferences
                                .getStringSet(getString(R.string.favorites_key), new TreeSet<String>()));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (favorites.contains(symbol)) {
                            favorites.remove(symbol);
                            star.setBackgroundResource(R.drawable.empty);
                        } else {
                            favorites.add(symbol);
                            star.setBackgroundResource(R.drawable.filled);
                        }
                        System.out.println(favorites);
                        System.out.println(favorites.size());
                        editor.putStringSet(getString(R.string.favorites_key), favorites);
                        editor.apply();
                    }
                }
            });

        return currentFragment;

    }
    private void setFavorite(){
        if (symbol != null && !symbol.equals("")) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.default_shared_preferences_key), MODE_PRIVATE);
            favorites = new TreeSet<>(sharedPreferences
                    .getStringSet(getString(R.string.favorites_key), new HashSet<String>()));
            if (favorites.contains(symbol)) {
                star.setBackgroundResource(R.drawable.filled);
            }
        }
    }

    public List<CurrentCell> generateCells(){
        List<CurrentCell> result = new ArrayList<>();
        result.add(new CurrentCell(" Stock Symbol", MainActivity.StockSymbol));
        result.add(new CurrentCell(" Last Price", MainActivity.LastPrice));
        result.add(new CurrentCell(MainActivity.ChangeResult, MainActivity.Sign));
        result.add(new CurrentCell(" Timestamp", MainActivity.Timestamp));
        result.add(new CurrentCell(" Open", MainActivity.Open));
        result.add(new CurrentCell(" Close", MainActivity.Close));
        result.add(new CurrentCell(" Day's Range", MainActivity.Range));
        result.add(new CurrentCell(" Volume", MainActivity.Volume));
        return result;
    }
}
