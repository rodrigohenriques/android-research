package com.github.android.research.infrastructure.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.github.android.research.R;

public class DialogHelper {

    private DialogHelper() {
    }

    public static AlertDialog showErrorDialog(Context context, String code, String detail) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(String.format("ERRO: %s", code))
                .setMessage(String.format("%s", detail))
                .setPositiveButton(R.string.text_close, null)
                .create();

        dialog.show();

        customizeDialog(context, dialog);

        return dialog;
    }

    public static AlertDialog showAlertDialogWithTwoButtons(Activity activity,
                                                            String title, String message, String positiveButtonText,
                                                            String negativeButtonText,
                                                            DialogInterface.OnClickListener positiveListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveButtonText, positiveListener);
        builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        customizeDialog(activity, alert);

        return alert;
    }

    public static void customizeDialog(Context context, AlertDialog alert) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            Resources res = context.getResources();

            int idDivider = res.getIdentifier("titleDivider", "id", "android");
            View titleDivider = alert.findViewById(idDivider);
            if (titleDivider != null) {
                titleDivider.setBackgroundColor(context.getResources()
                        .getColor(R.color.accent));
            }

            int idTitle = res.getIdentifier("alertTitle", "id", "android");
            TextView title = (TextView) alert.findViewById(idTitle);
            if (title != null) {
                title.setTextColor(context.getResources().getColor(
                        R.color.primary));
            }

            TextView message = (TextView) alert.findViewById(android.R.id.message);
            if (message != null) {
                message.setTextColor(context.getResources().getColor(
                        R.color.secondary_text));
            }
        }
    }

}
