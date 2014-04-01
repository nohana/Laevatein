# Laevatein

[![Build Status](https://travis-ci.org/nohana/Laevatein.svg?branch=master)](https://travis-ci.org/nohana/Laevatein)

Photo image selection activity set library.

## Screen Shot

![Selection Activity](https://raw.githubusercontent.com/nohana/Laevatein/master/documents/ss-1.png)
![Album List on DrawerMenu](https://raw.githubusercontent.com/nohana/Laevatein/master/documents/ss-2.png)
![Selected List](https://raw.githubusercontent.com/nohana/Laevatein/master/documents/ss-3.png)

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

## Features

Laevatein provides some APIs to customize selector behaviour for your spec.

### Selectable count limitation

Set selectable count with `count(int, int)`.
Default is `0 <= count <= 1`.

```java
Laevatein.from(this)
        .choose(MimeType.of(MimeType.JPEG))
        .count(0, 10)  // minimum = 0, max = 10, so 0 <= count <= 10;
        .forResult(REQUEST_CODE_CHOOSE);
```

### Selectable photo quality limitation

Set selectable photo quality by pixel count with `quality(int, int)`.
Default is `0 <= pixels <= Integer.MAX_VALUE`.

```java
Laevatein.from(this)
        .choose(MimeType.of(MimeType.JPEG))
        .quality(30000, Integer.MAX_VALUE)  // minimum = 30000px, max = Integer.MAX_VALUEpx, so 30000px <= count <= Integer.MAX_VALUEpx;
        .forResult(REQUEST_CODE_CHOOSE);
```

### Use custom cell layout

Set your layout and ids for the image cell with `bindEachImageWith(int, int, int)`.

```java
Laevatein.from(this)
        .choose(MimeType.of(MimeType.JPEG))
        .bindEachImageWith(R.layout.my_cell, R.id.my_cell_image_view, R.id.my_cell_check_box)
        .forResult(REQUEST_CODE_CHOOSE);
```

### Resume selection with previously selected photos

Set defaultly selected URIs with `resume(List<Uri>)`.

```java
List<Uri> mSelectedList;

Laevatein.from(this)
        .choose(MimeType.of(MimeType.JPEG))
        .resume(mSelectedList)
        .forResult(REQUEST_CODE_CHOOSE);
```

### Call camera from the selection activity

Set flag to enable camera capture from the selection activity with `capture(boolean)`.

```java
Laevatein.from(this)
        .choose(MimeType.of(MimeType.JPEG))
        .capture(true)
        .forResult(REQUEST_CODE_CHOOSE);
```

## Sample App

Sample application is available [here](https://deploygate.com/distributions/b43dc74fc4025bbb8587f179f5b8464418cca559).
[<img src="https://dply.me/orf0t9/button/large" alt="Try it on your device via DeployGate">](https://dply.me/orf0t9#install)

## Download

Via Gradle

```groovy
repositories {
    mavenCentral()
    maven { url 'https://raw.github.com/nohana/Laevatein/master/repository/' }
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
4. [CompoundContainers](https://github.com/KeithYokoma/CompoundContainers) by KeithYokoma
5. [AndroidDeviceCompatibility](https://github.com/mixi-inc/Android-Device-Compatibility) by mixi, Inc.

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
