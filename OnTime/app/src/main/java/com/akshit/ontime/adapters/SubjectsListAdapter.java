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
import com.akshit.ontime.models.SubjectDetails;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SubjectsListAdapter extends RecyclerView.Adapter<SubjectsListAdapter.ViewHolder> {

    private final Context mContext;
    private  List<SubjectDetails> mSubjectDetails = new ArrayList<>();
    private OnSemesterClickListener mOnSemesterClickListener;

    public SubjectsListAdapter(final Context context) {
        mContext = context;
    }

    public void setOnSemesterClickListener(OnSemesterClickListener onSemesterClickListener) {
        mOnSemesterClickListener = onSemesterClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.subject_details_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsListAdapter.ViewHolder holder, int position) {
        final SubjectDetails semesterDetails = mSubjectDetails.get(position);
        holder.subjectName.setText(semesterDetails.getNameOfSubject());
        Glide.with(mContext).load(semesterDetails.getLogoUrl()).placeholder(R.drawable.ic_launcher_background).into(holder.subjectLogo);
    }


    @Override
    public int getItemCount() {
        return mSubjectDetails.size();
    }

    public void setSemesterDetails(List<SubjectDetails> subjectDetails) {
        this.mSubjectDetails = subjectDetails;
        notifyDataSetChanged();
    }


    public interface OnSemesterClickListener {
        public void onClick(SubjectDetails subjectDetails);
    }

    final class ViewHolder extends RecyclerView.ViewHolder {

        TextView subjectName;

        ImageView subjectLogo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name);
            subjectLogo = itemView.findViewById(R.id.subject_logoUrl);
            itemView.setOnClickListener(v -> {
                if (mOnSemesterClickListener != null) {
                    mOnSemesterClickListener.onClick(mSubjectDetails.get(getAdapterPosition()));
                }
            });
        }
    }
}
