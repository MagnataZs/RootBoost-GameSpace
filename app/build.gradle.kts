plugins { id("com.android.application") }

android {
    namespace = "br.com.rootboost.gamespace"
    compileSdk = 35
    defaultConfig {
        applicationId = "br.com.rootboost.gamespace"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "0.2.0-alpha"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
