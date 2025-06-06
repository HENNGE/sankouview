# sankouview
[<img src="https://github.com/user-attachments/assets/f60a5cec-1741-485f-a74b-75d167443920" width="250"/>](https://github.com/user-attachments/assets/f60a5cec-1741-485f-a74b-75d167443920)
[<img src="https://github.com/user-attachments/assets/68052e23-6a4f-45e5-8765-16eb5a72e29f" width="250"/>](https://github.com/user-attachments/assets/68052e23-6a4f-45e5-8765-16eb5a72e29f)

A simple Kotlin based viewer for displaying Open Source Software (OSS) license data for Android. The purpose of this tool is a drop-in replacement for previous (now deprecated) license listing tools. The hope of this library is to eliminate the extra work of showing library information while being flexiable enought to fit into various layouts and navigation schemes.

# Design
Currently this tool relies on he [licensee library](https://github.com/cashapp/licensee) for parsing data. 
This library depends on output from the licensee library and provides multiple ways to display the contents based on app needs:
- As a stand alone activity (least effort required)
- As a full screen compose view to be hosted where you please (includes top bar)
- As a compose components that you can place in your layout as you see fit

# Including This Project
This project requires the [licensee library](https://github.com/cashapp/licensee) and is dependant on version 1.13.0 at a minimum to function. Before using this project its recommended to go read up on the licensee library directly.

Required Includes:
Add to your project gradle file:
```gradle
//Add to the dependencies section (with classpath entries)
classpath "app.cash.licensee:licensee-gradle-plugin:1.13.0"
```

Add to your app/gradle file:
```gradle
//Near the top of the file to handle SpdxId includes
import app.cash.licensee.SpdxId

//Add this whereever you are adding plugins
apply plugin: "app.cash.licensee"

//Place this in your 'android' block
licensee {
    bundleAndroidAsset.set(true) //Required or this library will not work

    //Add allowUrl/allowDependency as needed. See licensee documentation.

    allow(SpdxId.Apache_20)
    allow(SpdxId.MIT)
}

//Add to Dependencies (this library!)
implementation "com.hennge.oss.sankou:sankouview:0.1.3"
```


Other Minimum requirements to function with this library:
- This library supports Android 11 and greater.
- If you use Kotlin in your project, you must be on version 2.0 or greater or it will conflict with this project

# Usage
### Call as an activity
From somewhere in your app where you have access to application context, use the following line:
```kotlin
SankouView.launchLicenseActivity(this@MainActivity)
```

### Call as a Compose View (Full Screen)
If you want to have your own activity or fragment hosting a compose view, and want more control over the theming of the view, use the following code to add the compose view directly into your app. The callback here is used to handle up navigation from the displayed view. 

This view *must* be called inside of a compose context (in a @Composeable method or a setContent block).

Warning: This view contains a top bar and will look weird if you place it inside of a navigation view or have your own navigation bar displayed at the time.
```kotlin
SankouView.GenerateLicenseList(callback = object : LicenseScreenCallback {
    override fun onNavigateUpCalled() {
        // Handle "up" navigation here
    }
})
```

### Call as a Compose View (Integrated)
Depending on your design, you may want to have more direct control over the placement of compose views in your layout. In this case the following methods have been made available. Use the "Accessing List Items Directly" section below to get direct access to the list data. As with the other compose views, this must be hosted inside of a compose context (in a @Composeable method or a setContent block).
```kotlin
// This will display the list view and give a click listener that lets you know which index and item was selected
SankouView.LicenseListPage(items = licenseItems, listener = object : OnListInteraction {
    override fun onListItemClick(clickedItem: LicenseItem, index: Int) {
        //Handle navigation to the detail view here
    }
})

...

// This will generate a compose license detail view based on a single licence item
SankouView.LicenseItemPage(licenseItems[index])
```

### Accessing List Items Directly
Your design may require you to have direct access to the license item list data generated by this library (using Licensee) this data can be accessed using the following method with any form of application or view context:
```kotlin
// Generate just a list of license items
val items = SankouView.licenseItems(context=LocalContext.current)
```

## Authors

Module managed by [HENNGE](https://github.com/HENNGE).

## License
```
Copyright 2025 HENNGE, K.K.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

sankouview is licensed by HENNGE K.K. under the Apache License, Version 2.0. See [LICENSE](LICENSE) for the full license text.
