package com.akshit.ontime.adapters;


import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshit.ontime.R;
import com.akshit.ontime.models.SyllabusItem;

import java.util.ArrayList;
import java.util.List;

import io.noties.markwon.Markwon;
import io.noties.markwon.html.HtmlPlugin;

/**
 * Common adapter to hold any normal string list.
 */
public class SyllabusItemAdapter extends RecyclerView.Adapter<SyllabusItemAdapter.ViewHolder> {
    private static Markwon markwon;
    private final Context mContext;
    private List<SyllabusItem> mSyllabusItems = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public SyllabusItemAdapter(final Context context) {
        mContext = context;
        if (markwon == null) {
            markwon = Markwon.builder(context).
                    usePlugin(HtmlPlugin.create()).build();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.generic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SyllabusItem syllabusItem = mSyllabusItems.get(position);

        final StringBuilder markdownText = new StringBuilder();
        markdownText.append("**").append(syllabusItem.getTitle()).append("**");
        if (syllabusItem.getSubTopics() != null) {
            markdownText.append("<ol>");
            for (String subtopic : syllabusItem.getSubTopics()) {
                markdownText.append("<li>").append(subtopic).append("</li>");
            }
            markdownText.append("</ol>");
        }
        final Spanned markdown = markwon.render(markwon.parse(markdownText.toString()));

        markwon.setParsedMarkdown(holder.title, markdown);
    }

    public void setSyllabusItems(List<SyllabusItem> syllabusItems) {
        mSyllabusItems = syllabusItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mSyllabusItems.size();
    }


    public interface OnItemClickListener {
        public void onClick(SyllabusItem syllabusItem);
    }

    final class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.generic_item);
            itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(mSyllabusItems.get(getAdapterPosition()));
                }
            });
        }
    }

}