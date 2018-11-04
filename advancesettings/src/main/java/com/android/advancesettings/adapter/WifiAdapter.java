package com.android.advancesettings.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.advancesettings.R;

import java.util.ArrayList;
import java.util.Map;

public class WifiAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Map<String, String>> mWifiLists = new ArrayList<>();

    public WifiAdapter(Context context) {
        this.context = context;
    }

    public void addAllItem(ArrayList<Map<String, String>> list) {
        mWifiLists.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mWifiLists.size();
    }

    @Override
    public Map<String, String> getItem(int position) {
        return mWifiLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        Map map = this.mWifiLists.get(position);
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = View.inflate(this.context, R.layout.wifi_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mSSID = view.findViewById(R.id.wifi_ssid);
            viewHolder.mPassword = view.findViewById(R.id.wifi_pwd);
            view.setTag(viewHolder);
        }
        viewHolder.mSSID.setText((String) map.get("ssid"));
        viewHolder.mPassword.setText((String) map.get("psk"));
        return view;
    }

    private class ViewHolder {
        TextView mSSID;
        TextView mPassword;
    }
}
