package com.laevatein;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.laevatein.ui.PhotoSelectionActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * @author keishin.yokomaku
 * @since 2014/04/01
 */
@RunWith(RobolectricTestRunner.class)
public class LaevateinTest {
    private Activity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = new Activity();
    }

    @Test
    public void from_returnsLaevatein() throws Exception {
        Laevatein laevatein = Laevatein.from(mActivity);
        assertNotNull(laevatein.getActivity());
    }

    @Test
    public void obtainResult_returnsSomeUri() throws Exception {
        List<Uri> mock = new ArrayList<Uri>() {{ add(Uri.parse("content://hogehoge/fugafuga/1")); }};
        Intent data = new Intent();
        data.putParcelableArrayListExtra(PhotoSelectionActivity.EXTRA_RESULT_SELECTION, (ArrayList<? extends Parcelable>) mock);

        List<Uri> uris = Laevatein.obtainResult(data);
        assertNotNull(uris);
        assertSame(mock, uris);
        assertEquals(mock, uris);
    }
}
