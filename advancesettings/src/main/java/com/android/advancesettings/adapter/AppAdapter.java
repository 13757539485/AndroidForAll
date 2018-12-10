//
// Decompiled by Jadx - 809ms
//
package com.android.advancesettings.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.advancesettings.R;
import com.android.advancesettings.entity.AppBean;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<AppBean> mList = new ArrayList<>();
    private boolean isMultipleMode;
    private ArrayList<AppBean> mSelectItems = new ArrayList<>();

    public AppAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void addAll(List<AppBean> list, boolean clear) {
        if (clear) {
            mList.clear();
        }
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void removeSelectItems() {
        mSelectItems.clear();
        notifyDataSetChanged();
    }

    public boolean isMultipleMode() {
        return isMultipleMode;
    }

    public void setMultipleMode(boolean multipleMode) {
        isMultipleMode = multipleMode;
        if (!isMultipleMode) {
            mSelectItems.clear();
        }
        notifyDataSetChanged();
    }

    public void addSelectItem(AppBean appBean) {
        if (mSelectItems.contains(appBean)) {
            mSelectItems.remove(appBean);
        }else {
            mSelectItems.add(appBean);
        }
        notifyDataSetChanged();
    }

    public ArrayList<AppBean> getSelectItems() {
        return mSelectItems;
    }

    public boolean hasSelectItem() {
        return mSelectItems.size() > 0;
    }

    public int getCount() {
        return this.mList.size();
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
            holder.mCheckBox = view.findViewById(R.id.app_select);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        AppBean item = getItem(position);
        holder.mAppIcon.setImageDrawable(item.getIcon());
        boolean disable = item.isDisable();
        String title = item.getTitle();
        holder.mTitleTextView.setText(disable ? getDisableText(title) : title);
        if (item.isSystem()) {
            holder.mTitleTextView.setTextColor(Color.RED);
        } else {
            holder.mTitleTextView.setTextColor(Color.GRAY);
        }
        String appPackage = item.getAppPackage();
        holder.mDescriptionTextView.setText(disable ? getDisableText(appPackage) : appPackage);
        if (isMultipleMode()) {
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mCheckBox.setChecked(mSelectItems.contains(item));
        } else {
            holder.mCheckBox.setVisibility(View.GONE);
        }
        return view;
    }

    private class ViewHolder{
        TextView mDescriptionTextView;
        TextView mTitleTextView;
        CheckBox mCheckBox;
        ImageView mAppIcon;
    }

    public void remove(AppBean item) {
        this.mList.remove(item);
        notifyDataSetChanged();
    }

    private SpannableString getDisableText(String text) {
        SpannableString spannableString = new SpannableString(text);
        StrikethroughSpan span = new StrikethroughSpan();
        spannableString.setSpan(span, 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}