package com.laevatein.sample;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.laevatein.Laevatein;
import com.laevatein.MimeType;
import com.laevatein.internal.entity.ErrorViewResources;

import java.util.List;


public class LToolbarActivity extends ActionBarActivity {

    public static final int REQUEST_CODE_CHOOSE = 1;
    private List<Uri> mSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltoolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Laevatein.from(LToolbarActivity.this)
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
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ltoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
