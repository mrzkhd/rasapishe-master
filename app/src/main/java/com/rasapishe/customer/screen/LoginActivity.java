package com.rasapishe.customer.screen;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rasapishe.boom.customer.R;
import com.rasapishe.customer.service.TosanServices;
import com.rasapishe.customer.service.dto.DepositTransferReq;
import com.rasapishe.customer.service.dto.DepositTransferResp;
import com.rasapishe.customer.service.dto.LoginReqDto;
import com.rasapishe.customer.service.dto.ServiceCallBack;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.support.v4.app.NavUtils.navigateUpFromSameTask;

public class LoginActivity extends AppCompatActivity {

    final private  String user_name = "samiminsh";
    final private  String password = "54141158";
    final private  String source_deposit = "4610-801-215128-13";
    final private  String dest_deposit = "1871-701-10272702-1";
    final private  String amount = "11000";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tranfer();
            }
        });
        ((AppCompatEditText)findViewById(R.id.password)).setText(user_name);
        ((AppCompatEditText)findViewById(R.id.username)).setText(password);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Sample for transfer call
    public void tranfer() {
        final ProgressDialog _progressDialog= new ProgressDialog(this);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage(getResources().getString(R.string.loading_text));
        _progressDialog.setCancelable(false);
        _progressDialog.show();

        DepositTransferReq req = new DepositTransferReq();
        req.setAmount(amount);
        req.setSource_deposit(source_deposit);
        req.setDestination_deposit(dest_deposit);

        LoginReqDto loginReq = new LoginReqDto();
        loginReq.setUsername(user_name);
        loginReq.setPassword(password);

        TosanServices tosanServices = new TosanServices();

        tosanServices.depositeTransfer(loginReq, req, new ServiceCallBack() {

            @Override
            public void onLoginFailed() {
                //toast user
                _progressDialog.dismiss();
                MaterialDialog.Builder builder = new MaterialDialog.Builder(LoginActivity.this)
                        .positiveText(R.string.ok_button_text)
                        .title(R.string.login_failed_dialog_title)
                        .titleGravity(GravityEnum.END)
                        .buttonsGravity(GravityEnum.END)
                        .contentGravity(GravityEnum.END)
                        .content(R.string.login_failed_dialog_text);
                builder.build().show();
            }

            @Override
            public void onLoginBoomFailed() {
                //toast user
                _progressDialog.dismiss();
                MaterialDialog.Builder builder = new MaterialDialog.Builder(LoginActivity.this)
                        .positiveText(R.string.ok_button_text)
                        .title(R.string.login_boom_failed_dialog_title)
                        .titleGravity(GravityEnum.END)
                        .buttonsGravity(GravityEnum.END)
                        .contentGravity(GravityEnum.END)
                        .content(R.string.login_failed_dialog_text);
                builder.build().show();
            }

            @Override
            public void onDepositTransferFailed() {
                //toast user
                _progressDialog.dismiss();
                MaterialDialog.Builder builder = new MaterialDialog.Builder(LoginActivity.this)
                        .positiveText(R.string.ok_button_text)
                        .title(R.string.transfer_failed_dialog_title)
                        .titleGravity(GravityEnum.END)
                        .buttonsGravity(GravityEnum.END)
                        .contentGravity(GravityEnum.END)
                        .content(R.string.login_failed_dialog_text);
                builder.build().show();
            }

            @Override
            public void onDepositTransferSuccess(DepositTransferResp depositTransferResp) {
                _progressDialog.dismiss();
                String track_no = "شماره پیگیری تراکنش: " + depositTransferResp.getTracking_code();
                //toast success, new result screen
                MaterialDialog dialog = new MaterialDialog.Builder(LoginActivity.this)
                        .positiveText(R.string.ok_button_text)
                        .titleGravity(GravityEnum.END)
                        .buttonsGravity(GravityEnum.END)
                        .contentGravity(GravityEnum.END)
                        .customView(R.layout.dlg_success_transfer, true)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                finish();
                            }
                        })
                        .build();
                dialog.show();
                final View dialogView = dialog.getCustomView();
                ((TextView)dialogView.findViewById(R.id.track_no)).setText(track_no);
            }
        });

    }
}
