package com.laevatein;

import android.net.Uri;
import android.test.mock.MockContentResolver;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MimeTypeTest {
    @Test
    public void typeCheck() throws Exception {
        MockContentResolver resolver = new MockContentResolver();

        {
            assertTrue(MimeType.JPEG.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.jpg")));
            assertTrue(MimeType.JPEG.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.jpeg")));
            assertFalse(MimeType.JPEG.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.png")));
            assertFalse(MimeType.JPEG.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.gif")));
            assertFalse(MimeType.JPEG.checkType(resolver, null));
        }

        {
            assertFalse(MimeType.PNG.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.jpg")));
            assertFalse(MimeType.PNG.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.jpeg")));
            assertTrue(MimeType.PNG.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.png")));
            assertFalse(MimeType.PNG.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.gif")));
            assertFalse(MimeType.PNG.checkType(resolver, null));
        }

        {
            assertFalse(MimeType.GIF.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.jpg")));
            assertFalse(MimeType.GIF.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.jpeg")));
            assertFalse(MimeType.GIF.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.png")));
            assertTrue(MimeType.GIF.checkType(resolver, Uri.parse("file://hogehoge/fugafuga/image.gif")));
            assertFalse(MimeType.GIF.checkType(resolver, null));
        }
    }
}
