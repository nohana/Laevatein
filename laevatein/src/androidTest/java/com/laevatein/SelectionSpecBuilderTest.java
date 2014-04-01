package com.laevatein;

import com.laevatein.internal.ui.PhotoSelectionActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author keishin.yokomaku
 * @since 2014/03/31
 */
public class SelectionSpecBuilderTest extends AndroidTestCase {
    private static final int MOCK_REQUEST_CODE = 1;
    private SelectionSpecBuilder mBuilder;
    private CountDownLatch mLatch;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
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
                return getContext().getPackageName();
            }
        });
        assertNotNull(laevatein);

        mBuilder = laevatein.choose(MimeType.of(MimeType.JPEG));
        assertNotNull(mBuilder);
    }

    private void ensureAllExtrasExist(Intent intent) {
        assertNotNull(intent.getParcelableExtra(PhotoSelectionActivity.EXTRA_DIR_VIEW_RES));
        assertNotNull(intent.getParcelableExtra(PhotoSelectionActivity.EXTRA_ITEM_VIEW_RES));
        assertNotNull(intent.getParcelableExtra(PhotoSelectionActivity.EXTRA_SELECTION_SPEC));
        assertNotNull(intent.getParcelableArrayListExtra(PhotoSelectionActivity.EXTRA_RESUME_LIST));
    }

    public void testCountSpec() throws Exception {
        mBuilder = mBuilder.count(0, 10);
        assertNotNull(mBuilder);

        mBuilder.forResult(MOCK_REQUEST_CODE);
        mLatch.await();
    }

    public void testCaptureSpec() throws Exception {
        mBuilder = mBuilder.capture(true);
        assertNotNull(mBuilder);

        mBuilder.forResult(MOCK_REQUEST_CODE);
        mLatch.await();
    }

    public void testQualitySpec() throws Exception {
        mBuilder = mBuilder.quality(0, 100);
        assertNotNull(mBuilder);

        mBuilder.forResult(MOCK_REQUEST_CODE);
        mLatch.await();
    }

    public void testResumeSpec() throws Exception {
        mBuilder = mBuilder.resume(new ArrayList<Uri>());
        assertNotNull(mBuilder);

        mBuilder.forResult(MOCK_REQUEST_CODE);
        mLatch.await();
    }

    public void testBindItemViewSpec() throws Exception {
        mBuilder = mBuilder.bindEachImageWith(R.layout.l_grid_item_default_photo, R.id.l_default_grid_image, R.id.l_default_check_box);
        assertNotNull(mBuilder);

        mBuilder.forResult(MOCK_REQUEST_CODE);
        mLatch.await();
    }

    public void testBindAlbumViewSpec() throws Exception {
        mBuilder = mBuilder.bindEachAlbumWith(R.layout.l_list_item_default_album, R.id.l_default_list_image, R.id.l_default_directory_label);
        assertNotNull(mBuilder);

        mBuilder.forResult(MOCK_REQUEST_CODE);
        mLatch.await();
    }
}