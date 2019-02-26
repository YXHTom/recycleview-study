package com.chinamall21.mobile.study.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.chinamall21.mobile.study.R;


/**
 * desc：
 * author：Created by xusong on 2018/12/26 16:52.
 */


public class NormalDialogFragment extends DialogFragment {
    private DialogClickListener mDialogClickListener;

    public static NormalDialogFragment newInstance() {
        NormalDialogFragment fragment = new NormalDialogFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_award, null);
        final ImageView iv = view.findViewById(R.id.iv);
        iv.setImageBitmap(mDialogClickListener.getBitmap());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogClickListener != null) {
                    mDialogClickListener.onDialogClick();
                }
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置dialog的 进出 动画
        dialog.getWindow().setWindowAnimations(R.style.animate_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mDialogClickListener != null)
                        mDialogClickListener.onCancelClick();
                    return true;
                }
                return false;
            }
        });
        return dialog;

    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss();
    }

    public interface DialogClickListener {
        void onDialogClick();

        void onCancelClick();

        Bitmap getBitmap();
    }


    public void setDialogClickListener(DialogClickListener dialogClickListener) {
        mDialogClickListener = dialogClickListener;
    }


}
