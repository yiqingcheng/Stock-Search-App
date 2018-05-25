package cs571.evehw9;

import android.graphics.drawable.Drawable;

/**
 * Created by yiqing on 11/21/17.
 */


public class CurrentCell {
    private String Label;
    private String Value;
    private Integer Flag = null;
    //private int a;

    public CurrentCell(String label, String value){
        Label = label;
        Value = value;

    }

    public CurrentCell(String value, int flag){
        Label = "Change";
        Value = value;
        Flag = flag;
    }

    public String getLabel() {
        return Label;
    }

    public String getValue() {
        return Value;
    }

    public int getFlag() {
        return Flag;
    }

}