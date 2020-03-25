package com.msj.android_project.base;

import com.msj.android_project.view.PrimaryScreeningDialog;

public class DialogManager {

    private  DialogAttrs dialogAttrs = new DialogAttrs();


   private static BaseDialog dialog = new PrimaryScreeningDialog();


   public static BaseDialog getDialog(){
       return dialog;
   }

   public static DialogManager getInstance(){
       return new DialogManager();
   }

   public DialogManager setDialogAttrs(){
       dialog.dialogAttrs = this.dialogAttrs;
       return this;
   }


    /**
     * 点击外部是否消失  默认true 消失
     * @param dismissOnTouchOutside
     * @return
     */
    public DialogManager dismissOnTouchOutside(Boolean dismissOnTouchOutside) {
        this.dialogAttrs.isDismissOnTouchOutside = dismissOnTouchOutside;
        return this;
    }

    /**
     * 按返回键是否消失  默认true 消失
     * @param dismissOnBackPressed
     * @return
     */
    public DialogManager dismissOnBackPressed(Boolean dismissOnBackPressed) {
        this.dialogAttrs.isDismissOnBackPressed = dismissOnBackPressed;
        return this;
    }


}
