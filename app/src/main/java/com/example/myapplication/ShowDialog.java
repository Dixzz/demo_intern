package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;

public class ShowDialog {
    private static String a, b, c;
    private final Intent intent = new Intent(Intent.ACTION_VIEW);

    private ShowDialog(Context context) {
        SwipeDismissDialog swipeDismissDialog = new SwipeDismissDialog.Builder(context).setLayoutResId(R.layout.activity_main).build();
        LinearLayout linearLayout = (LinearLayout) ((CardView) swipeDismissDialog.getChildAt(0)).getChildAt(0);
        ((TextView) linearLayout.getChildAt(0)).setText(a);
        ((SimpleDraweeView) linearLayout.getChildAt(1)).setImageURI(b);
        linearLayout.getChildAt(2).setOnClickListener(view -> {
            context.startActivity(intent.setData(Uri.parse(c)));
        });
        swipeDismissDialog.show();
    }

    public static void getShowDialogInstance(String title, String imageUrl, String intentUrl, Context context) {
        if (a == null && b == null && c == null) {
            a = title;
            b = imageUrl;
            c = intentUrl;
        }
        new ShowDialog(context);
    }
}
