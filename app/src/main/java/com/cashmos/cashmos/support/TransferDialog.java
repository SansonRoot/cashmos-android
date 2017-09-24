package com.cashmos.cashmos.support;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cashmos.cashmos.MobileMoneyTransferConfirmationActivity;
import com.cashmos.cashmos.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by brightantwiboasiako on 9/7/17.
 */

public class TransferDialog {


    private AlertDialog mAlert;
    private ProgressBar mProcessProgress;
    private ImageView mProcessSuccess;
    private ImageView mProcessFailure;
    private Button mProcessButton;
    private TextView mProcessSuccessMessage;
    private Context context;
    private View mAlertView;

    public TransferDialog(Context context, View alertView){
        this.context = context;
        mAlertView = alertView;
    }


    public Callback<Void> afterTransferRequest(){

        return new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                mProcessProgress.setVisibility(ProgressBar.INVISIBLE);

                if(response.isSuccessful()){

                    // Transfer was successful
                    // We inform the user about it

                    // We set the success message
                    mProcessSuccessMessage.setText(
                            context.getString(R.string.transfer_complete)
                    );
                    mProcessSuccessMessage.setVisibility(TextView.VISIBLE);

                    // Show success icon
                    mProcessSuccess.setImageResource(R.drawable.check);
                    mProcessSuccess.setVisibility(ImageView.VISIBLE);

                    // We activate the action button
                    mProcessButton.setText(context.getString(
                            R.string.transfer_action_done
                    ));
                    mProcessButton.setVisibility(Button.VISIBLE);
                    mProcessButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            TransferDialog.this.toDashboard();

                        }
                    });

                }else{

                    Log.d("ERROR", response.message());

                    Toast.makeText(
                            context,
                            response.code() + "",
                            Toast.LENGTH_LONG
                    ).show();

                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        };

    }


    public void toDashboard(){

        // Destroy the alert box
        mAlert.dismiss();

        ActivityManager.toDashboard(context);
    }


    public void initProcessing(){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        mProcessProgress = (ProgressBar) mAlertView.findViewById(R.id.processProgress);
        mProcessSuccess = (ImageView) mAlertView.findViewById(R.id.processOnSuccess);
        mProcessFailure = (ImageView) mAlertView.findViewById(R.id.processOnFailure);
        mProcessButton = (Button) mAlertView.findViewById(R.id.processAction);
        mProcessSuccessMessage = (TextView) mAlertView.findViewById(R.id.processSuccessMessage);

        mProcessProgress.setVisibility(ProgressBar.VISIBLE);

        builder.setView(mAlertView);

        mAlert = builder.create();
        mAlert.setCancelable(false);
        mAlert.show();

    }

}
