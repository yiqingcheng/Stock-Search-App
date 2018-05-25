package cs571.evehw9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lengyi on 11/22/17.
 */

public class NewsListAdapter extends BaseAdapter {

    public Context context;
    public List<NewsCell> newsData;

    public NewsListAdapter(Context context, List<NewsCell> cellList) {
        this.context = context;
        newsData = cellList;


    }

    @Override
    public int getCount() {
        return newsData.size();
    }

    @Override
    public NewsCell getItem(int position) {
        return newsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cell_news, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView author = (TextView) convertView.findViewById(R.id.author);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        title.setText(newsData.get(position).getTitle());
        author.setText("Author: " + newsData.get(position).getAuthor());
        date.setText("Date: " + newsData.get(position).getDate());
        return convertView;

    }
}
