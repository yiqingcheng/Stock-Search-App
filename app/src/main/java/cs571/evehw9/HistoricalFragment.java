package cs571.evehw9;

/**
 * Created by yiqingcheng on 2017/11/24.
 */

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricalFragment extends Fragment {

    private WebView historical;
    private ProgressBar his_pro;
    public HistoricalFragment() {
        // Required empty public constructor

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        his_pro.setVisibility(View.VISIBLE);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_historical, container, false);
        historical = (WebView) v.findViewById(R.id.search);
        his_pro = (ProgressBar)v.findViewById(R.id.his_pro);
        String url = "file:///android_asset/historical.html";
        // Enable Javascript
        WebSettings webSettings = historical.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            historical.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
        historical.loadUrl(url);

        historical.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView webview, String url){
                System.out.print(MainActivity.StockSymbol);

                    historical.loadUrl("javascript:loadHistorical('" + MainActivity.StockSymbol + "')");


            }});

        return v;
    }

}
