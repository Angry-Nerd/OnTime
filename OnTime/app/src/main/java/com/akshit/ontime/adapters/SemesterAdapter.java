package com.akshit.ontime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshit.ontime.R;
import com.akshit.ontime.models.SemesterDetails;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.ViewHolder> {
    private final Context mContext;
    private List<SemesterDetails> mSemesterDetails = new ArrayList<>();
    private OnSemesterClickListener mOnSemesterClickListener;

    public SemesterAdapter(final Context context) {
        mContext = context;
    }

    public void setOnSemesterClickListener(OnSemesterClickListener onSemesterClickListener) {
        mOnSemesterClickListener = onSemesterClickListener;
    }

    @NonNull
    @Override
    public SemesterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.semester_grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SemesterDetails semesterDetails = mSemesterDetails.get(position);
        holder.semesterName.setText(convertNumberToString(semesterDetails.getSemesterNumber()));
        Glide.with(mContext).load(semesterDetails.getLogoUrl()).placeholder(R.drawable.ic_launcher_background).into(holder.semesterLogo);
    }

    public void setSemesterDetails(List<SemesterDetails> semesterDetails) {
        mSemesterDetails = semesterDetails;
        notifyDataSetChanged();
    }

    private String convertNumberToString(int semesterNumber) {
        return "First";
    }

    @Override
    public int getItemCount() {
        return mSemesterDetails.size();
    }


    public interface OnSemesterClickListener {
        public void onClick(SemesterDetails semesterDetails);
    }

    final class ViewHolder extends RecyclerView.ViewHolder {

        TextView semesterName;

        Button exploreSemesterBtn;

        ImageView semesterLogo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            semesterName = itemView.findViewById(R.id.semester_name);
            exploreSemesterBtn = itemView.findViewById(R.id.explore_semester);
            semesterLogo = itemView.findViewById(R.id.semester_logo);
            exploreSemesterBtn.setOnClickListener(v -> {
                if (mOnSemesterClickListener != null) {
                    mOnSemesterClickListener.onClick(mSemesterDetails.get(getAdapterPosition()));
                }
            });
        }
    }

}