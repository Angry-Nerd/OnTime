package com.akshit.ontime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
//import made by @kSh!t B@ns@l N@bha
import java.util.List;

public class TodoItemAdapter extends ArrayAdapter<TodoItem>{
    private int res;
    Context context;
    String response;
    TodoItemAdapter(Context context, int res,
                    List<TodoItem> items)
    {
        super(context,res,items);
        this.res=res;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout linearLayout;
        TodoItem item=getItem(position);
        if(convertView==null)
        {
            linearLayout=new LinearLayout(getContext());
            String inflator=Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi=(LayoutInflater)getContext().getSystemService(inflator);
            vi.inflate(res,linearLayout,true);
        }
        else
            linearLayout=(LinearLayout) convertView;

        TextView object=linearLayout.findViewById(R.id.object);
        object.setTextSize(TypedValue.COMPLEX_UNIT_DIP,32);
        TextView refe=linearLayout.findViewById(R.id.member);
        refe.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        TextView member1=linearLayout.findViewById(R.id.member1);
        refe.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        object.setText(item.getObject());
        refe.setText(item.getRef());
        member1.setText(item.getRef1());
        return linearLayout;
    }
}
