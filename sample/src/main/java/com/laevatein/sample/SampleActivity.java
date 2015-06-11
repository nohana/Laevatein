package com.laevatein.sample;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.laevatein.Laevatein;
import com.laevatein.MimeType;
import com.laevatein.internal.entity.ErrorViewResources;

import java.util.List;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 */
public class SampleActivity extends ActionBarActivity {
    public static final String TAG = SampleActivity.class.getSimpleName();
    public static final int REQUEST_CODE_CHOOSE = 1;
    private List<Uri> mSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.l_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Laevatein.obtainResult(data);
            Log.v(TAG, "selected: " + mSelected);
        }
    }

    public void onClickButton(View view) {
        Laevatein.from(this)
                .choose(MimeType.of(MimeType.JPEG))
                .count(0, 10)
                .quality(300000, Integer.MAX_VALUE)
                .resume(mSelected)
                .capture(true)
                .bindCountViewWith(android.R.color.white, R.color.l_background_count)
                .countOver(ErrorViewResources.ViewType.DIALOG, R.string.error_count_over)
                .enableSelectedView(true)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .forResult(REQUEST_CODE_CHOOSE);
    }
}
