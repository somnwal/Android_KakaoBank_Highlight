plugins {
    id("somnwal.kakaobank.android.application")
}

android {
    namespace = "com.somnwal.kakaobank.app"

    defaultConfig {
        applicationId = "com.somnwal.kakaobank.app"
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            //
        }
    }
}

dependencies {
    implementation(projects.feature.main)
}