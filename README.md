# EasyPreferences

```kotlin
//regular persisted properties declarations
var userName by PersistedProperty("pref_user_name","USD")
var userAge by PersistedProperty("pref_user_age",0)
var userWeight by PersistedProperty("pref_user_weight",75.8f)

//live persisted properties declaration
var usingDarkThemeLive = LivePersistedProperty("pref_use_dark_theme",false)

//regular persisted properties usage
userName = "XUser"
println(userName)

//live persisted properties usage
usingDarkThemeLive.setValue(true)
usingDarkThemeLive.observe(this, androidx.lifecycle.Observer {
    //Act accordigly 
})
```
