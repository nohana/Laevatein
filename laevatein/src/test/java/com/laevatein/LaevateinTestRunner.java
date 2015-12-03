package com.laevatein;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

/**
 * @author KeishinYokomaku
 */
public class LaevateinTestRunner extends RobolectricGradleTestRunner {
	public LaevateinTestRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	protected AndroidManifest getAppManifest(Config config) {
		return new AndroidManifest(
				Fs.fileFromPath("../laevatein/src/main/AndroidManifest.xml"),
				Fs.fileFromPath("../laevatein/build/intermediates/res/merged/" + BuildConfig.FLAVOR + "/debug"),
				Fs.fileFromPath("../laevatein/build/intermediates/assets/" + BuildConfig.FLAVOR + "/debug/"));
	}
}
