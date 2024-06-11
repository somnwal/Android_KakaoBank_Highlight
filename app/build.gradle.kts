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

    signingConfigs {
        create("release") {
            storeFile = file("keystore/KakaoBank.jks")
            storePassword = "KakaoBank"
            keyAlias = "KakaoBank"
            keyPassword = "KakaoBank"
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
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