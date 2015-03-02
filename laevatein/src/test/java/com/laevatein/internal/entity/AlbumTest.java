package com.laevatein.internal.entity;

import android.app.Activity;
import android.content.ContextWrapper;
import android.database.MatrixCursor;
import android.provider.MediaStore;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author KeithYokoma
 * @since 2014/04/01
 */
public class AlbumTest {
    private static final String[] MOCK_PROJECTION = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

    @Test
    public void camera() throws Exception {
        MatrixCursor cameraRow = new MatrixCursor(MOCK_PROJECTION);
        cameraRow.addRow(new String[]{"1", "1", "Camera"});
        assertTrue(cameraRow.moveToFirst());
        Album camera = Album.valueOf(cameraRow);
        assertNotNull(camera);
        assertFalse(camera.isAll());
        assertTrue(camera.isCamera());
        assertEquals(1L, camera.getCoverId());
        assertEquals("1", camera.getId());
        assertEquals("Camera", camera.getDisplayName(new ContextWrapper(new Activity())));
    }

    @Test
    public void all() throws Exception {
        MatrixCursor allRow = new MatrixCursor(MOCK_PROJECTION);
        allRow.addRow(new String[]{Album.ALBUM_ID_ALL, "-1", "All"});
        assertTrue(allRow.moveToFirst());
        Album all = Album.valueOf(allRow);
        assertNotNull(all);
        assertTrue(all.isAll());
        assertFalse(all.isCamera());
        assertEquals(-1L, all.getCoverId());
        assertEquals(Album.ALBUM_ID_ALL, all.getId());
        assertEquals("All Photos", all.getDisplayName(new ContextWrapper(new Activity())));
    }

    @Test
    public void download() throws Exception {
        MatrixCursor downloadRow = new MatrixCursor(MOCK_PROJECTION);
        downloadRow.addRow(new String[]{"2", "2", "Download"});
        assertTrue(downloadRow.moveToFirst());
        Album download = Album.valueOf(downloadRow);
        assertNotNull(download);
        assertFalse(download.isAll());
        assertFalse(download.isCamera());
        assertEquals(2L, download.getCoverId());
        assertEquals("2", download.getId());
        assertEquals("Download", download.getDisplayName(new ContextWrapper(new Activity())));
    }

    @Test
    public void screenshot() throws Exception {
        MatrixCursor screenShotRow = new MatrixCursor(MOCK_PROJECTION);
        screenShotRow.addRow(new String[]{"3", "3", "Screenshots"});
        assertTrue(screenShotRow.moveToFirst());
        Album screenShot = Album.valueOf(screenShotRow);
        assertNotNull(screenShot);
        assertFalse(screenShot.isAll());
        assertFalse(screenShot.isCamera());
        assertEquals(3L, screenShot.getCoverId());
        assertEquals("3", screenShot.getId());
        assertEquals("Screenshot", screenShot.getDisplayName(new ContextWrapper(new Activity())));
    }
}
