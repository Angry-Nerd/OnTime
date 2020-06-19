package com.akshit.ontime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshit.ontime.R;
import com.akshit.ontime.models.ResourceItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ResourceListAdapter extends RecyclerView.Adapter<ResourceListAdapter.ViewHolder> {

    private final Context mContext;
    private List<ResourceItem> mResourceItems = new ArrayList<>();
    private OnResourceItemClickListener mOnResourceItemClickListener;

    public ResourceListAdapter(final Context context) {
        mContext = context;
    }

    public void setOnResourceItemClickListener(OnResourceItemClickListener onResourceItemClickListener) {
        mOnResourceItemClickListener = onResourceItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.resource_details_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ResourceItem semesterDetails = mResourceItems.get(position);
        holder.resourceName.setText(semesterDetails.getName());
        Glide.with(mContext).load(semesterDetails.getLogoUrl()).placeholder(R.drawable.ic_launcher_background).into(holder.resourceLogo);
    }


    @Override
    public int getItemCount() {
        return mResourceItems.size();
    }

    public void setSemesterDetails(List<ResourceItem> subjectDetails) {
        this.mResourceItems = subjectDetails;
        notifyDataSetChanged();
    }


    public interface OnResourceItemClickListener {
        public void onClick(ResourceItem resourceItem);
    }

    final class ViewHolder extends RecyclerView.ViewHolder {

        TextView resourceName;


        ImageView resourceLogo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            resourceName = itemView.findViewById(R.id.resource_name);
            resourceLogo = itemView.findViewById(R.id.resource_logoUrl);
            itemView.setOnClickListener(v -> {
                if (mOnResourceItemClickListener != null) {
                    mOnResourceItemClickListener.onClick(mResourceItems.get(getAdapterPosition()));
                }
            });
        }
    }
}
