package com.laevatein;

import com.laevatein.internal.ui.PhotoSelectionActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keishin.yokomaku
 * @since 2014/04/01
 */
public class LaevateinTest extends AndroidTestCase {
    private Activity mActivity;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = new Activity();
    }

    public void testFrom() throws Exception {
        Laevatein laevatein = Laevatein.from(mActivity);
        assertNotNull(laevatein.getActivity());
    }

    public void testObtainResult() throws Exception {
        List<Uri> mock = new ArrayList<Uri>() {{ add(Uri.parse("content://hogehoge/fugafuga/1")); }};
        Intent data = new Intent();
        data.putParcelableArrayListExtra(PhotoSelectionActivity.EXTRA_RESULT_SELECTION, (ArrayList<? extends Parcelable>) mock);

        List<Uri> uris = Laevatein.obtainResult(data);
        assertNotNull(uris);
        assertSame(mock, uris);
        assertEquals(mock, uris);
    }
}
