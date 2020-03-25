package com.msj.android_project.base;



import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.msj.android_project.view.PrimaryScreeningDialog;

import butterknife.ButterKnife;

public abstract class BaseDialog<T> extends DialogFragment {

    public static DialogAttrs dialogAttrs;

    private Dialog dialog;

    public abstract int getDialogLayout();

    public abstract BaseDialog show(FragmentActivity activity, T resultInfo, @NonNull OnClicked onClicked);

    protected abstract void bindEvents(View view);



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = getDialog();
        if (dialog != null){
            if (dialog.getWindow() != null){
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.setCanceledOnTouchOutside(this.dialogAttrs.isDismissOnTouchOutside);
            dialog.setCancelable(this.dialogAttrs.isDismissOnTouchOutside);
            onKeylistenter();
        }

        View view = inflater.inflate(getDialogLayout(), container, false);
        bindEvents(view);
        return view;
    }

    private void onKeylistenter(){
        if (dialog != null){
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) return !dialogAttrs.isDismissOnBackPressed;
                    return false;
                }
            });
        }
    }

}
