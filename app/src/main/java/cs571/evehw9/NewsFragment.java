package cs571.evehw9;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private RequestQueue requestQueue = MainActivity.requestQueue;
    private List<NewsCell> newsData = new ArrayList<>();
    private NewsListAdapter newsListAdapter;
    private String url;
    private ProgressBar newsPro;

    public NewsFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getNews();
        View currentFragment = inflater.inflate(R.layout.fragment_news, container, false);
        newsPro = (ProgressBar)currentFragment.findViewById(R.id.newsPro);
        final ListView newsList = (ListView) currentFragment.findViewById(R.id.news_list);
        newsListAdapter = new NewsListAdapter(getContext(), newsData);
        newsList.setAdapter(newsListAdapter);
        newsListAdapter.notifyDataSetChanged();
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        return currentFragment;

    }




    class XMLRequest extends Request<XmlPullParser> {

        private final Response.Listener<XmlPullParser> mListener;

        public XMLRequest(int method, String url, Response.Listener<XmlPullParser> listener,
                          Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            mListener = listener;
        }

        public XMLRequest(String url, Response.Listener<XmlPullParser> listener, Response.ErrorListener errorListener) {
            this(Method.GET, url, listener, errorListener);
        }

        @Override
        protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
            try {
                String xmlString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = factory.newPullParser();
                xmlPullParser.setInput(new StringReader(xmlString));
                return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (XmlPullParserException e) {
                return Response.error(new ParseError(e));
            }
        }

        @Override
        protected void deliverResponse(XmlPullParser response) {
            mListener.onResponse(response);
        }

    }

    public void getNews(){
        String url = "http://evejs-env.us-west-2.elasticbeanstalk.com/?methodName=newsDetail&symbol=" + MainActivity.StockSymbol;
        XMLRequest xmlRequest = new XMLRequest(Request.Method.GET, url,
                new Response.Listener<XmlPullParser>(){
                    @Override
                    public void onResponse(XmlPullParser response){
                        try {
                            traverseXML(response);
                            newsListAdapter.notifyDataSetChanged();
                            //newsPro = (ProgressBar)this.getViewByid()
                            newsPro.setVisibility(View.INVISIBLE);
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

        requestQueue.add(xmlRequest);
    }

    public void traverseXML(XmlPullParser response) throws Exception{

        int eventType = response.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG && response.getName().equals("title")){
                response.next();
                String title = response.getText();
                response.next();
                response.next();
                response.next();
                eventType = response.next();

                if(response.getText() != null && response.getText().contains("article")){
                      url = response.getText();
                    String date = "";
                    String author = "";
                    while(true){
                        if(eventType == XmlPullParser.START_TAG && response.getName().equals("pubDate")){
                            eventType = response.next();
                            date = response.getText().substring(0,response.getText().length()-5);
                        }else if(eventType == XmlPullParser.START_TAG && response.getName().equals("sa:author_name")){
                            eventType = response.next();
                            author = response.getText();
                            break;
                        }else{
                            eventType = response.next();
                        }
                    }
                    System.out.println(url);
                    System.out.println(title);
                    System.out.println(date);
                    System.out.println(author);
                    newsData.add(new NewsCell(url, title, date, author));
                }
            }else {
                eventType = response.next();
            }
        }
        System.out.println("End document");
    }

}
