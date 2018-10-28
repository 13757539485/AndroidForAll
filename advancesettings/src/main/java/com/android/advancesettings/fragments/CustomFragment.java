package com.android.advancesettings.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.advancesettings.R;
import com.android.advancesettings.adapter.ShellAdapter;
import com.android.advancesettings.database.DbUtil;
import com.android.advancesettings.view.ShellView;

import java.util.ArrayList;

public class CustomFragment extends Fragment implements View.OnClickListener {

    private ShellAdapter mShellAdapter;
    private EditText mEditName;
    private EditText mEditInstruction;
    private Button mBtnSelectFileOff, mBtnSelectFileOn;
    private TextView mShowFilePathOff, mShowFilePathOn;
    private RadioGroup mRadioGroup;
    private AlertDialog mAlertDialog;
    private View mLayoutOff;
    private View mDialogView;
    private DbUtil mDbUtil;
    private boolean isAdd = true;
    private int mCurrentId;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mShellAdapter = new ShellAdapter(getActivity());
        mDbUtil = new DbUtil(getActivity());
        mDialogView = View.inflate(getActivity(), R.layout.dialog_shell_view, null);
        initView(mDialogView);
        ArrayList<ShellView> all = mDbUtil.findAll();
        mShellAdapter.addShellCommond(all);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_fragment, null);
        ListView listView = view.findViewById(R.id.custom_list);
        View addExecuteView = view.findViewById(R.id.title_add);
        listView.setAdapter(mShellAdapter);
        addExecuteView.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShellView item = mShellAdapter.getItem(position);
                mCurrentId = item.getId();
                showModifyDialog(item);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ShellView item = mShellAdapter.getItem(position);
                showDeleteDialog(item.getId());
                return true;
            }
        });
        return view;
    }

    private void showAddDialog() {
        isAdd = true;
        createShellViewDialog();
        if (mAlertDialog != null && !mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
    }

    private void showModifyDialog(ShellView shellView) {
        isAdd = false;
        createShellViewDialog();
        mEditName.setText(shellView.getName());
        mEditInstruction.setText(shellView.getInstruction());
        int type = shellView.getType();
        boolean isExec = type == ShellView.TYPE_EXECUTE;
        int id = isExec ? R.id.check_type_exec : R.id.check_type_ins;
        mRadioGroup.check(id);
        if (isExec) {
            mLayoutOff.setVisibility(View.GONE);
            mShowFilePathOn.setText(shellView.getPath());
            mShowFilePathOff.setText("");
        } else {
            mLayoutOff.setVisibility(View.VISIBLE);
            mShowFilePathOff.setText(shellView.getCheckOff());
            mShowFilePathOn.setText(shellView.getCheckOn());
        }
        mLayoutOff.setVisibility(isExec ? View.GONE : View.VISIBLE);
        if (mAlertDialog != null && !mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
    }

    private void showDeleteDialog(final int id) {
        new AlertDialog.Builder(getActivity())
                .setTitle("警告")
                .setMessage("确定删除此脚本")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDbUtil.delete(id);
                        mShellAdapter.addShellCommond(mDbUtil.findAll());
                    }
                }).setNegativeButton("取消", null)
                .show();
    }
    private void createShellViewDialog() {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(getActivity())
                    .setView(mDialogView)
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", null)
                    .create();
            mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button positionButton = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positionButton.setOnClickListener(CustomFragment.this);
                }
            });
        }
        mEditName.setText("");
        mEditInstruction.setText("");
        mLayoutOff.setVisibility(View.GONE);
        mRadioGroup.check(R.id.check_type_exec);
        mShowFilePathOn.setText("");
        mShowFilePathOff.setText("");
    }

    public void initView(View view) {
        mEditName = view.findViewById(R.id.edit_name);
        mEditInstruction = view.findViewById(R.id.edit_instruction);
        mBtnSelectFileOn = view.findViewById(R.id.btn_select_file_on);
        mShowFilePathOn = view.findViewById(R.id.show_file_path_on);
        mBtnSelectFileOff = view.findViewById(R.id.btn_select_file_off);
        mShowFilePathOff = view.findViewById(R.id.show_file_path_off);
        mLayoutOff = view.findViewById(R.id.layout_off);
        mRadioGroup = view.findViewById(R.id.radio_group);
        mBtnSelectFileOn.setOnClickListener(this);
        mBtnSelectFileOff.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.check_type_exec:
                        mLayoutOff.setVisibility(View.GONE);
                        break;
                    case R.id.check_type_ins:
                        mLayoutOff.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.title_add:
                showAddDialog();
                break;
            case android.R.id.button1:
                String name = mEditName.getText().toString();
                String instruction = mEditInstruction.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "必须填写命令名称", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(instruction)) {
                    Toast.makeText(getActivity(), "必须填写命令说明", Toast.LENGTH_SHORT).show();
                    return;
                }
                ShellView shellView = new ShellView();
                shellView.setName(name);
                shellView.setInstruction(instruction);
                int checkedRadioButtonId = mRadioGroup.getCheckedRadioButtonId();
                switch (checkedRadioButtonId) {
                    case R.id.check_type_exec:
                        mLayoutOff.setVisibility(View.GONE);
                        shellView.setType(ShellView.TYPE_EXECUTE);
                        shellView.setPath("");
                        String pathOn = mShowFilePathOn.getText().toString();
                        if (TextUtils.isEmpty(pathOn)) {
                            Toast.makeText(getActivity(), "请选择脚本", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            shellView.setPath(pathOn);
                        }
                        break;
                    case R.id.check_type_ins:
                        mLayoutOff.setVisibility(View.VISIBLE);
                        shellView.setType(ShellView.TYPE_CHECK);
                        shellView.setCheckOn("");
                        shellView.setCheckOff("");
                        pathOn = mShowFilePathOn.getText().toString();
                        String pathOff = mShowFilePathOff.getText().toString();
                        if (TextUtils.isEmpty(pathOn) || TextUtils.isEmpty(pathOff)) {
                            Toast.makeText(getActivity(), "请选择脚本", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            shellView.setCheckOn(pathOn);
                            shellView.setCheckOff(pathOn);
                        }
                        break;
                }
                if (isAdd) {
                    mDbUtil.insert(shellView);
                } else {
                    shellView.setId(mCurrentId);
                    mDbUtil.update(shellView);
                }
                mShellAdapter.addShellCommond(mDbUtil.findAll());
                if (mAlertDialog != null) {
                    mAlertDialog.dismiss();
                }
                break;
            case R.id.btn_select_file_on:
                showFileChooser(0);
                break;
            case R.id.btn_select_file_off:
                showFileChooser(1);
                break;
        }
    }

    private void showFileChooser(int type) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), type);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String path = getPath(getActivity(), uri);
            if (requestCode == 0) {
                mShowFilePathOn.setText(path);
            } else {
                mShowFilePathOff.setText(path);
            }
        }
    }

    public String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it  Or Log it.
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
}
