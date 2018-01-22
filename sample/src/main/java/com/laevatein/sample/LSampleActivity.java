package com.laevatein.sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.laevatein.Laevatein;
import com.laevatein.MimeType;
import com.laevatein.internal.entity.ErrorViewResources;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 */
@RuntimePermissions
public class LSampleActivity extends AppCompatActivity {
    public static final String TAG = LSampleActivity.class.getSimpleName();
    public static final int REQUEST_CODE_CHOOSE = 1;
    private List<Uri> mSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Toolbar toolbar = findViewById(R.id.l_toolbar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.choose);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LSampleActivityPermissionsDispatcher.startPhotoSelectWithCheck(LSampleActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Laevatein.obtainResult(data);
            Log.v(TAG, "selected: " + mSelected);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LSampleActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void startPhotoSelect() {
        Laevatein.from(this)
                .choose(MimeType.of(MimeType.JPEG))
                .count(10, 10)
                .quality(300000, Integer.MAX_VALUE)
                .size(300, 300)
                .resume(mSelected)
                .capture(true)
                .countUnder(ErrorViewResources.ViewType.SNACKBAR, R.string.error_count_under)
                .countOver(ErrorViewResources.ViewType.DIALOG, R.string.error_count_over)
                .enableSelectedView(true)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForCamera() {
        Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForCamera() {
        Button button = findViewById(R.id.choose);
        button.setText(R.string.permission_nerver_ask);
    }
}
