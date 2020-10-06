# Simple ZXing Scanner View

Simple QR & Barcode scanning library for Android, based on the [ZXing](https://github.com/zxing/zxing/), [ZXing Android Embedded](https://github.com/journeyapps/zxing-android-embedded), [Barcode Scanner](https://github.com/dm77/barcodescanner) for decoding.

## Installation

- Add the following repositories to your `project/build.gradle` file.

```groovy
repositories {
   maven { url 'https://jitpack.io' }
}
```

- Add the following dependency to your `project/app/build.gradle` file.

```groovy
dependencies {
    implementation 'com.github.prongbang:scannerview:1.0.0'
}
```

## Usage

- Add camera permission to your AndroidManifest.xml file:

```xml
<uses-permission android:name="android.permission.CAMERA" />
```

- Add ScannerView to xxx_layout.xml file:

```xml
<com.prongbang.scanview.ScannerView
    android:id="@+id/scannerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

- Add on Activity file:

```kotlin
private fun startScanner() {
    scannerView?.apply {
        addObserver(this)
        onResultListener {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}

private fun restartScanner() {
    scannerView?.resumeScannerView()
}
```