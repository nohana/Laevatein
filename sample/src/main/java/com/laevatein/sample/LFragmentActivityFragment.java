package com.laevatein.sample;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.laevatein.Laevatein;
import com.laevatein.MimeType;
import com.laevatein.internal.entity.ErrorViewResources;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class LFragmentActivityFragment extends Fragment {

    public static final int REQUEST_CODE_CHOOSE = 1;
    private List<Uri> mSelected;

    public LFragmentActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_l, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button = (Button) getActivity().findViewById(R.id.button);
        final Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Laevatein.from(fragment)
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
}
