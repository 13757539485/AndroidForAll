//
// Decompiled by Jadx - 809ms
//
package com.android.advancesettings.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.advancesettings.R;
import com.android.advancesettings.entity.AppBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppAdapter extends BaseAdapter {
    public static final int SORT_BY_DATE_ASC = 4;
    public static final int SORT_BY_DATE_DESC = 5;
    public static final int SORT_BY_NAME_ASC = 0;
    public static final int SORT_BY_NAME_DESC = 1;
    public static final int SORT_BY_SIZE_ASC = 2;
    public static final int SORT_BY_SIZE_DESC = 3;
    private Context mContext;
    private int mCurrentSortType = -1;
    private LayoutInflater mInflater;
    private List<AppBean> mList;

    public AppAdapter(Context context, List<AppBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mList = list;
    }

    /*private void sort() {
        switch (this.mCurrentSortType) {
            case SORT_BY_NAME_ASC *//*0*//*:
                Collections.sort(this.mList, new AppNameComparator(true));
                return;
            case SORT_BY_NAME_DESC *//*1*//*:
                Collections.sort(this.mList, new AppNameComparator(false));
                return;
            case SORT_BY_SIZE_ASC *//*2*//*:
                Collections.sort(this.mList, new AppSizeComparator(true));
                return;
            case SORT_BY_SIZE_DESC *//*3*//*:
                Collections.sort(this.mList, new AppSizeComparator(false));
                return;
            case SORT_BY_DATE_ASC *//*4*//*:
                Collections.sort(this.mList, new AppLastModifiedComparator(true));
                return;
            case SORT_BY_DATE_DESC *//*5*//*:
                Collections.sort(this.mList, new AppLastModifiedComparator(false));
                return;
            default:
                return;
        }
    }*/

    public void addAll(List<AppBean> list) {
        this.mList.addAll(list);
    }

    public int getCount() {
        return this.mList.size();
    }

    public int getCurrentSortType() {
        return this.mCurrentSortType;
    }

    public AppBean getItem(int i) {
        return mList.get(i);
    }

    public long getItemId(int id) {
        return id;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = this.mInflater.inflate(R.layout.app_item, viewGroup, false);
            holder.mAppIcon = view.findViewById(R.id.app_icon);
            holder.mTitleTextView = view.findViewById(R.id.app_title);
            holder.mDescriptionTextView = view.findViewById(R.id.app_package);
            holder.mTimeTextView = view.findViewById(R.id.app_last_modified);
            holder.mSizeTextView = view.findViewById(R.id.app_size);
            holder.mCheckBox = view.findViewById(R.id.app_select);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        AppBean item = getItem(position);
        holder.mAppIcon.setImageDrawable(item.getIcon());
        holder.mTitleTextView.setText(item.getTitle());
        if (item.isSystem()) {
            holder.mTitleTextView.setTextColor(Color.RED);
        } else {
            holder.mTitleTextView.setTextColor(Color.GRAY);
        }
        holder.mDescriptionTextView.setText(item.getDescription());
        holder.mSizeTextView.setText(Formatter.formatFileSize(this.mContext, item.getSize()));
        holder.mTimeTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(item.getLastModified())));
        holder.mCheckBox.setChecked(item.isChecked());
        return view;
    }

    private class ViewHolder{
        TextView mDescriptionTextView;
        TextView mTitleTextView;
        TextView mSizeTextView;
        TextView mTimeTextView;
        CheckBox mCheckBox;
        ImageView mAppIcon;
    }

    public void remove(AppBean item) {
        this.mList.remove(item);
    }

    public void sort(int i) {
        if (i != this.mCurrentSortType) {
            this.mCurrentSortType = i;
            //sort();
            notifyDataSetChanged();
        }
    }
}