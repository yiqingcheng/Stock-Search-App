package cs571.evehw9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yiqingcheng on 11/21/17.
 */


public class CurrentListAdapter extends BaseAdapter{
    public Context context;
    public List<CurrentCell> currentData;

    public CurrentListAdapter(Context context, List<CurrentCell> cellList) {
        this.context = context;
        currentData = cellList;
    }

    @Override
    public int getCount() {
        return currentData.size();
    }

    @Override
    public CurrentCell getItem(int position) {
        return currentData.get(position);
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
            convertView = inflater.inflate(R.layout.cell_current, parent, false);
        }

        TextView label = (TextView) convertView.findViewById(R.id.label);
        TextView value = (TextView) convertView.findViewById(R.id.value);
        ImageView image = (ImageView) convertView.findViewById(R.id.icon);
        CurrentCell curt = currentData.get(position);
        if(curt.getLabel().equals("Change")){
            image.setImageResource(curt.getFlag());
        }
        //image.setImageResource(R.drawable.down);
        label.setText(curt.getLabel());
        value.setText(curt.getValue());
        return convertView;
    }
}
