# Laevatein

Photo image selection activity set library.

## Usage

Call photo image selection activity by the following code snipet.

```java
public class SomeActivity extends Activity {
  public static final int REQUEST_CODE_CHOOSE = 1;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_some);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultcode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
    case REQUEST_CODE_CHOOSE:
      if (resultCode == RESULT_OK) {
        // Get result and proceed your work from here...
        List<Uri> selected = Laevatein.obtainResult(data);
      }
      break;
    default:
      break;
    }
  }

  public void onClickButton(View view) {
    // call chooser on click button like this
    Laevatein.from(this).choose(MimeType.of(MimeType.JPEG)).forResult(REQUEST_CODE_CHOOSE);
  }
}
```

And you'll get the selection result on `Activity#onActivityResult(int, int, Intent)`.

## Sample App

Sample application is available [here](https://deploygate.com/distributions/b43dc74fc4025bbb8587f179f5b8464418cca559).
[<img src="https://dply.me/orf0t9/button/large" alt="Try it on your device via DeployGate">](https://dply.me/orf0t9#install)

## Download

Via Gradle

```groovy
repositories {
    mavenCentral()
    maven { url 'https://raw.github.com/nohana/Laevatein/master/amalgam/repository/' }
}
android {
    dependencies {
        compile 'com.laevatein:Laevatein:1.0.0'
    }
}
```

## Acknowledgement

This library depends on the following libraries.

1. [Picasso](https://github.com/square/picasso) by Square Inc.
2. [ImageViewZoom](https://github.com/sephiroth74/ImageViewZoom) by Alessandro Crugnola
3. [Amalgam](https://github.com/nohana/Amalgam) by nohana, Inc.

## License

This library is licensed under Apache License v2.

```
Copyright (C) 2014 nohana, Inc. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
```
