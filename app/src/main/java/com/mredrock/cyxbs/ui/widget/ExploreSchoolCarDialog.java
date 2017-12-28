package com.mredrock.cyxbs.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mredrock.cyxbs.R;
import com.mredrock.cyxbs.ui.activity.explore.ExploreSchoolCar;

/**
 * Created by glossimar on 2017/12/26.
 */

public class ExploreSchoolCarDialog {
    AlertDialog dialog;

    public void show(Context context, Activity activity, int type) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.dialog_explore_school_car_notserve, null);
        ImageButton diasmissButton = (ImageButton) layout.findViewById(R.id.school_car_dialog_dismiss_button);
        ImageButton negativeButton = (ImageButton) layout.findViewById(R.id.school_car_dialog_negative_button);
        ImageButton positiveButton = (ImageButton) layout.findViewById(R.id.school_car_dialog_positive_button);

        dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(layout)
                .create();

        diasmissButton.setOnClickListener(v -> {
            if (v.getId() == R.id.school_car_dialog_dismiss_button) {
                dialog.dismiss();
                activity.finish();
            }
        });
        try {
            Window dialogWindow =  dialog.getWindow();
            dialog.show();
            dialogWindow.setLayout(845, 1070);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            switch (type) {
                case ExploreSchoolCar.LOST_SERVICES:
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_school_car_not_serve);
                    break;
                case ExploreSchoolCar.TIME_OUT:
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.ic_school_car_search_time_out);
                    break;
                case ExploreSchoolCar.NO_GPS:
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.ic_school_car_search_no_gps);
                    negativeButton.setOnClickListener(v -> {
                        dialog.cancel();
                    });
                    positiveButton.setOnClickListener(v -> {
                        dialog.cancel();
                    });
                    break;
            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void cancleDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            dialog = null;
        }
    }
}