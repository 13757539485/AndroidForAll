//
// Decompiled by Jadx - 809ms
//
package com.android.advancesettings.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.advancesettings.R;
import com.android.advancesettings.entity.AppBean;
import com.android.advancesettings.utils.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<AppBean> mList = new ArrayList<>();
    private ArrayList<AppBean> mCopyDatas = new ArrayList<>();
    private boolean isMultipleMode;
    private ArrayList<AppBean> mSelectItems = new ArrayList<>();
    private AppFilter mAppFilter;
    private OnAppAddFinishListener mOnAppAddFinishListener;
    private String mSearchText;

    public void setOnAppAddFinishListener(OnAppAddFinishListener onAppAddFinishListener) {
        mOnAppAddFinishListener = onAppAddFinishListener;
    }

    public AppAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void addAll(List<AppBean> list, boolean clear, String search) {
        if (clear) {
            mList.clear();
            mCopyDatas.clear();
        }
        if (mOnAppAddFinishListener != null) {
            mOnAppAddFinishListener.onSizeChange(list.size());
        }
        this.mList.addAll(list);
        mCopyDatas.addAll(list);
        getFilter().filter(search);
    }

    public boolean isMultipleMode() {
        return isMultipleMode;
    }

    public void setMultipleMode(boolean multipleMode, boolean needNotify) {
        isMultipleMode = multipleMode;
        if (!isMultipleMode) {
            mSelectItems.clear();
        }
        if (needNotify) notifyDataSetChanged();
    }

    public void addSelectItem(AppBean appBean) {
        if (mSelectItems.contains(appBean)) {
            mSelectItems.remove(appBean);
        } else {
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

    public boolean isSearchMode() {
        return !TextUtils.isEmpty(mSearchText);
    }

    public void setSearchText(String searchText) {
        mSearchText = searchText;
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
        String appPackage = item.getAppPackage();
        if (item.isSystem()) {
            holder.mTitleTextView.setTextColor(Color.RED);
        } else {
            holder.mTitleTextView.setTextColor(Color.GRAY);
        }
        if (isSearchMode()) {
            ColorGenerator.setViewTextByFilterColor(holder.mTitleTextView, title, mSearchText, disable);
            ColorGenerator.setViewTextByFilterColor(holder.mDescriptionTextView, appPackage, mSearchText, disable);
        } else {
            holder.mTitleTextView.setText(disable ? getDisableText(title) : title);
            holder.mDescriptionTextView.setText(disable ? getDisableText(appPackage) : appPackage);
        }
        if (isMultipleMode()) {
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mCheckBox.setChecked(mSelectItems.contains(item));
        } else {
            holder.mCheckBox.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return mAppFilter == null ? new AppFilter() : mAppFilter;
    }

    private class ViewHolder {
        TextView mDescriptionTextView;
        TextView mTitleTextView;
        CheckBox mCheckBox;
        ImageView mAppIcon;
    }

    public void remove(AppBean item) {
        this.mList.remove(item);
        this.mCopyDatas.remove(item);
        notifyDataSetChanged();
    }

    private SpannableString getDisableText(String text) {
        SpannableString spannableString = new SpannableString(text);
        StrikethroughSpan span = new StrikethroughSpan();
        spannableString.setSpan(span, 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private class AppFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            ArrayList<AppBean> tempList; // temp中间集合
            if (TextUtils.isEmpty(constraint)) {//当过滤的关键字为空的时候，我们则显示所有的数据
                tempList = mCopyDatas;
            } else {//否则把符合条件的数据对象添加到集合中
                tempList = new ArrayList<>();
                for (AppBean appBean : mCopyDatas) {
                    if (appBean.getTitle().contains(constraint) ||
                            appBean.getAppPackage().contains(constraint)) {
                        tempList.add(appBean);
                    }

                }
            }
            result.values = tempList; //符合搜索的结果的数据集合
            result.count = tempList.size();//符合搜索的结果的数据个数
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList.clear();
            mList.addAll((ArrayList<AppBean>) results.values);
            notifyDataSetChanged();//通知数据发生了改变
        }
    }

    public interface OnAppAddFinishListener {
        void onSizeChange(int size);
    }
}