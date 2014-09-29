package com.laevatein.internal.entity;

import android.database.MatrixCursor;
import android.provider.MediaStore;
import android.test.AndroidTestCase;

import com.laevatein.R;

/**
 * @author KeithYokoma
 * @since 2014/04/01
 */
public class AlbumTest extends AndroidTestCase {
    private static final String[] MOCK_PROJECTION = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

    public void testCamera() throws Exception {
        MatrixCursor cameraRow = new MatrixCursor(MOCK_PROJECTION);
        cameraRow.addRow(new String[]{"1", "1", "Camera"});
        assertTrue(cameraRow.moveToFirst());
        Album camera = Album.valueOf(cameraRow);
        assertNotNull(camera);
        assertFalse(camera.isAll());
        assertTrue(camera.isCamera());
        assertEquals(1L, camera.getCoverId());
        assertEquals("1", camera.getId());
        assertEquals(getContext().getString(R.string.l_album_name_camera), camera.getDisplayName(getContext()));
    }

    public void testAllSelect() throws Exception {
        MatrixCursor allRow = new MatrixCursor(MOCK_PROJECTION);
        allRow.addRow(new String[]{Album.ALBUM_ID_ALL, "-1", "All"});
        assertTrue(allRow.moveToFirst());
        Album all = Album.valueOf(allRow);
        assertNotNull(all);
        assertTrue(all.isAll());
        assertFalse(all.isCamera());
        assertEquals(-1L, all.getCoverId());
        assertEquals(Album.ALBUM_ID_ALL, all.getId());
        assertEquals(getContext().getString(R.string.l_album_name_all), all.getDisplayName(getContext()));
    }

    public void testDownload() throws Exception {
        MatrixCursor downloadRow = new MatrixCursor(MOCK_PROJECTION);
        downloadRow.addRow(new String[]{"2", "2", "Download"});
        assertTrue(downloadRow.moveToFirst());
        Album download = Album.valueOf(downloadRow);
        assertNotNull(download);
        assertFalse(download.isAll());
        assertFalse(download.isCamera());
        assertEquals(2L, download.getCoverId());
        assertEquals("2", download.getId());
        assertEquals(getContext().getString(R.string.l_album_name_download), download.getDisplayName(getContext()));
    }

    public void testScreenShot() throws Exception {
        MatrixCursor screenShotRow = new MatrixCursor(MOCK_PROJECTION);
        screenShotRow.addRow(new String[]{"3", "3", "Screenshots"});
        assertTrue(screenShotRow.moveToFirst());
        Album screenShot = Album.valueOf(screenShotRow);
        assertNotNull(screenShot);
        assertFalse(screenShot.isAll());
        assertFalse(screenShot.isCamera());
        assertEquals(3L, screenShot.getCoverId());
        assertEquals("3", screenShot.getId());
        assertEquals(getContext().getString(R.string.l_album_name_screen_shot), screenShot.getDisplayName(getContext()));
    }
}
