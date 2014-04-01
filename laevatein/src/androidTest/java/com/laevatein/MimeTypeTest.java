package com.laevatein;

import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.mock.MockContentResolver;

import java.util.Set;

/**
 * @author keishin.yokomaku
 * @since 2014/03/31
 */
public class MimeTypeTest extends AndroidTestCase {
    public void testOneOf() throws Exception {
        {
            Set<MimeType> set = MimeType.of(MimeType.JPEG);
            assertEquals(1, set.size());
            assertTrue(set.contains(MimeType.JPEG));
            assertFalse(set.contains(MimeType.PNG));
            assertFalse(set.contains(MimeType.GIF));
        }

        {
            Set<MimeType> set = MimeType.of(MimeType.PNG);
            assertEquals(1, set.size());
            assertFalse(set.contains(MimeType.JPEG));
            assertTrue(set.contains(MimeType.PNG));
            assertFalse(set.contains(MimeType.GIF));
        }

        {
            Set<MimeType> set = MimeType.of(MimeType.GIF);
            assertEquals(1, set.size());
            assertFalse(set.contains(MimeType.JPEG));
            assertFalse(set.contains(MimeType.PNG));
            assertTrue(set.contains(MimeType.GIF));
        }
    }

    public void testSomeOf() throws Exception {
        {
            Set<MimeType> set = MimeType.of(MimeType.JPEG, MimeType.PNG);
            assertEquals(2, set.size());
            assertTrue(set.contains(MimeType.JPEG));
            assertTrue(set.contains(MimeType.PNG));
            assertFalse(set.contains(MimeType.GIF));
        }

        {
            Set<MimeType> set = MimeType.of(MimeType.JPEG, MimeType.GIF);
            assertEquals(2, set.size());
            assertTrue(set.contains(MimeType.JPEG));
            assertFalse(set.contains(MimeType.PNG));
            assertTrue(set.contains(MimeType.GIF));
        }

        {
            Set<MimeType> set = MimeType.of(MimeType.PNG, MimeType.GIF);
            assertEquals(2, set.size());
            assertFalse(set.contains(MimeType.JPEG));
            assertTrue(set.contains(MimeType.PNG));
            assertTrue(set.contains(MimeType.GIF));
        }

        {
            Set<MimeType> set = MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF);
            assertEquals(3, set.size());
            assertTrue(set.contains(MimeType.JPEG));
            assertTrue(set.contains(MimeType.PNG));
            assertTrue(set.contains(MimeType.GIF));
        }
    }

    public void testAllOf() throws Exception {
        Set<MimeType> set = MimeType.allOf();
        assertEquals(3, set.size());
        assertTrue(set.contains(MimeType.JPEG));
        assertTrue(set.contains(MimeType.PNG));
        assertTrue(set.contains(MimeType.GIF));
    }

    public void testToString() throws Exception {
        assertEquals("image/jpeg", MimeType.JPEG.toString());
        assertEquals("image/png", MimeType.PNG.toString());
        assertEquals("image/gif", MimeType.GIF.toString());
    }

    public void testCheckType() throws Exception {
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
