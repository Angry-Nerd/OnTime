package com.akshit.ontime.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshit.ontime.R;
import com.akshit.ontime.managers.DownloadManager;
import com.akshit.ontime.models.ResourceMaterialItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Common adapter to hold any normal string list.
 */
public class ResourceMaterialAdapter extends RecyclerView.Adapter<ResourceMaterialAdapter.ViewHolder> {
    private final Context mContext;
    private List<ResourceMaterialItem> mResourceMaterialItems = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public ResourceMaterialAdapter(final Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_material, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ResourceMaterialItem resourceMaterialItem = mResourceMaterialItems.get(position);
        holder.titleOfFile.setText(resourceMaterialItem.getTitleOfFile());
        holder.downloadButton.setOnClickListener(v -> {
            DownloadManager.getInstance().downloadFile(resourceMaterialItem.getDownloadUrl(), resourceMaterialItem.getTitleOfFile(), resourceMaterialItem.getFileExtension());
            Toast.makeText(mContext, resourceMaterialItem.getDownloadUrl(), Toast.LENGTH_LONG).show();
        });
    }

    public void setResourceMaterialItems(List<ResourceMaterialItem> resourceMaterialItems) {
        mResourceMaterialItems = resourceMaterialItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mResourceMaterialItems.size();
    }


    public interface OnItemClickListener {
        public void onClick(ResourceMaterialItem resourceMaterialItem);
    }

    final class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleOfFile;
        ImageButton downloadButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            downloadButton = itemView.findViewById(R.id.download_resource_file);
            titleOfFile = itemView.findViewById(R.id.resource_material_name);
            itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(mResourceMaterialItems.get(getAdapterPosition()));
                }
            });
        }
    }

}