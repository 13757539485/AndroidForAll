package com.android.advancesettings.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.advancesettings.R;
import com.android.advancesettings.entity.ShellResult;
import com.android.advancesettings.utils.ShellUtils;
import com.android.advancesettings.view.ShellView;

import java.util.ArrayList;

public class ShellAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ShellView> mShellViews = new ArrayList<>();
    private LayoutInflater mInflater;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 100:
                    ShellResult shellResult = (ShellResult) msg.obj;
                    if (shellResult.errorMsg == null) {
                        showDialog("应用成功");
                    } else {
                        showDialog(shellResult.errorMsg);
                    }
                    break;
            }
            return false;
        }
    });

    public ShellAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void addShellCommond(ShellView shellView) {
        mShellViews.add(shellView);
        notifyDataSetChanged();
    }

    public void addShellCommond(ArrayList<ShellView> shellViews) {
        mShellViews.clear();
        mShellViews.addAll(shellViews);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mShellViews.size();
    }

    @Override
    public ShellView getItem(int position) {
        return mShellViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.shell_item, parent, false);
            holder.mShellName = convertView.findViewById(R.id.shell_name);
            holder.mShellInstruction = convertView.findViewById(R.id.shell_instruction);
            holder.mExecute = convertView.findViewById(R.id.shell_execute);
            holder.mCheckBox = convertView.findViewById(R.id.shell_check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ShellView item = getItem(position);
        holder.mShellName.setText(item.getName());
        String instruction = item.getInstruction();
        holder.mShellInstruction.setText(instruction);
        int type = item.getType();
        switch (type) {
            case ShellView.TYPE_EXECUTE:
                holder.mExecute.setVisibility(View.VISIBLE);
                holder.mCheckBox.setVisibility(View.GONE);
                holder.mExecute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String path = item.getPath();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ShellResult shellResult = ShellUtils.execSh(path);
                                Message message = mHandler.obtainMessage();
                                message.obj = shellResult;
                                message.what = 100;
                                mHandler.sendMessage(message);
                            }
                        }).start();

                    }
                });
                break;
            case ShellView.TYPE_CHECK:
                holder.mExecute.setVisibility(View.GONE);
                holder.mCheckBox.setVisibility(View.VISIBLE);
                holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        final String str;
                        if (isChecked) {
                            str = item.getCheckOn();
                        } else {
                            str = item.getCheckOff();
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ShellResult shellResult = ShellUtils.execSh(str);
                                Message message = mHandler.obtainMessage();
                                message.obj = shellResult;
                                message.what = 100;
                                mHandler.sendMessage(message);
                            }
                        }).start();
                    }
                });
                break;
        }
        return convertView;
    }

    private class ViewHolder {
        TextView mShellName;
        TextView mShellInstruction;
        Button mExecute;
        CheckBox mCheckBox;
    }

    private AlertDialog showDialog(String result) {
        return new AlertDialog.Builder(mContext).setTitle("脚本执行")
                .setMessage(result)
                .setPositiveButton("确定", null)
                .show();
    }
}
