package com.laevatein.sample;

import com.laevatein.Laevatein;
import com.laevatein.MimeType;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * @author keishin.yokomaku
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
                .forResult(REQUEST_CODE_CHOOSE);
    }
}
