package com.akshit.ontime;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    int res;
    Context context;
    String response;
    ItemAdapter(Context context, int res,
                List<Item> items)
    {
        super(context,res,items);
        this.res=res;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout linearLayout;
        Item item=getItem(position);

//        View view=super.getView(position,convertView,parent);
        if(convertView==null)
        {
            linearLayout=new LinearLayout(getContext());
            String inflator=Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi=(LayoutInflater)getContext().getSystemService(inflator);
            vi.inflate(res,linearLayout,true);
        }
        else
        {
            linearLayout=(LinearLayout) convertView;
        }

        TextView object=linearLayout.findViewById(R.id.object);
//        object.setTypeface(null, Typeface.BOLD);
        object.setTextSize(TypedValue.COMPLEX_UNIT_DIP,32);
        TextView refe=linearLayout.findViewById(R.id.member);
        refe.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        object.setText(item.getObject());
        refe.setText(item.getRef());
//        if(position%2==0)
//        {
//            object.setTextColor(Color.WHITE);
//            refe.setTextColor(Color.WHITE);
//        }
//        else
//        {
//            object.setTextColor(Color.argb(255,27,131,255));
//            refe.setTextColor(Color.argb(255,27,131,255));
//        }
//        layoutParams = view.getLayoutParams();
//        layoutParams.height=200;
        return linearLayout;
    }
}
