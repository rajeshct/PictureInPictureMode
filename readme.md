### Welcome to the PictureInPictureMode
One of the useful feature on API greater than 23 is picture-in-picture mode where app can open over other app meaning two apps running in same time.

![Picture in Picture mode](https://github.com/rajeshct/PictureInPictureMode/blob/master/picture%20in%20picture.gif)

**1) Setting minimum SDK in app build.gradle**
```
android {
    compileSdkVersion 26
    defaultConfig {
        applicationId "com.example.rtiwari.pictureinpicturemode"
        minSdkVersion 24
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}
```
**2) For setting activity in Picture-in-picture mode write down this in activity tag in `manifest.xml`**

```
<activity
            android:name=".MainActivity"
            android:configChanges="screenSize|smallestScreenSize|screenLayout|orientation"
            android:resizeableActivity="true"
            android:supportsPictureInPicture="true">
```

**3) Start activity in picture-in-picture mode**

```
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PictureInPictureParams pictureInPictureParams = new PictureInPictureParams.Builder().build();
            enterPictureInPictureMode(pictureInPictureParams);
        } else {
            enterPictureInPictureMode();
        }
``` 

**4) Callback entered in picture-in-picture mode**
```
 @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode) {
            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.
            toolbar.setVisibility(View.GONE);
        } else {
            // Restore the full-screen UI.
            toolbar.setVisibility(View.VISIBLE);
        }
    }
```
