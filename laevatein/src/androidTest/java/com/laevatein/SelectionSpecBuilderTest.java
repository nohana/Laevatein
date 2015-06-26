package com.laevatein;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.laevatein.ui.PhotoSelectionActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author keishin.yokomaku
 * @since 2014/03/31
 */
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class SelectionSpecBuilderTest {
    private static final int MOCK_REQUEST_CODE = 1;
    private SelectionSpecBuilder mBuilder;
    private CountDownLatch mLatch;

    @Before
    public void setUp() throws Exception {
        mLatch = new CountDownLatch(1);
        Laevatein laevatein = Laevatein.from(new Activity() {
            @Override
            public void startActivityForResult(Intent intent, int requestCode) {
                mLatch.countDown();
                assertEquals(MOCK_REQUEST_CODE, requestCode);
                ensureAllExtrasExist(intent);

                assertEquals(PhotoSelectionActivity.class.getCanonicalName(),
                        intent.getComponent().getClassName());
            }

            @Override
            public String getPackageName() {
                // XXX
                return "com.laevatein";
            }
        });
        assertNotNull(laevatein);

        mBuilder = laevatein.choose(MimeType.of(MimeType.JPEG));
        assertNotNull(mBuilder);
    }

    private void ensureAllExtrasExist(Intent intent) {
        assertNotNull(intent.getParcelableExtra(PhotoSelectionActivity.EXTRA_SELECTION_SPEC));
        assertNotNull(intent.getParcelableArrayListExtra(PhotoSelectionActivity.EXTRA_RESUME_LIST));
        assertNotNull(intent.getParcelableExtra(PhotoSelectionActivity.EXTRA_VIEW_SPEC));
        assertNotNull(intent.getParcelableExtra(PhotoSelectionActivity.EXTRA_ERROR_SPEC));
    }

    @Test
    public void count() throws Exception {
        mBuilder = mBuilder.count(0, 10);
        assertNotNull(mBuilder);

        mBuilder.forResult(MOCK_REQUEST_CODE);
        mLatch.await();
    }

    @Test
    public void capture() throws Exception {
        mBuilder = mBuilder.capture(true);
        assertNotNull(mBuilder);

        mBuilder.forResult(MOCK_REQUEST_CODE);
        mLatch.await();
    }

    @Test
    public void quality() throws Exception {
        mBuilder = mBuilder.quality(0, 100);
        assertNotNull(mBuilder);

        mBuilder.forResult(MOCK_REQUEST_CODE);
        mLatch.await();
    }

    @Test
    public void resume() throws Exception {
        mBuilder = mBuilder.resume(new ArrayList<Uri>());
        assertNotNull(mBuilder);

        mBuilder.forResult(MOCK_REQUEST_CODE);
        mLatch.await();
    }

    // FIXME cannot resolve R while using unit testing support for now
//    public void testBindItemViewSpec() throws Exception {
//        mBuilder = mBuilder.bindEachImageWith(R.layout.l_grid_item_default_photo, R.id.l_default_grid_image, R.id.l_default_check_box);
//        assertNotNull(mBuilder);
//
//        mBuilder.forResult(MOCK_REQUEST_CODE);
//        mLatch.await();
//    }
//
//    public void testBindAlbumViewSpec() throws Exception {
//        mBuilder = mBuilder.bindEachAlbumWith(R.layout.l_list_item_default_album, R.id.l_default_list_image, R.id.l_default_directory_label);
//        assertNotNull(mBuilder);
//
//        mBuilder.forResult(MOCK_REQUEST_CODE);
//        mLatch.await();
//    }
}