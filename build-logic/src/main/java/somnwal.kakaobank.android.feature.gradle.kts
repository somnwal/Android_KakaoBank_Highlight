import com.somnwal.kakaobank.app.common.libs
import com.somnwal.kakaobank.app.configureHiltAndroid

plugins {
    id("somnwal.kakaobank.android.library")
    id("somnwal.kakaobank.android.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

configureHiltAndroid()

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
//    implementation(project(":core:designsystem"))
//    implementation(project(":core:domain"))
//    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))

    val libs = extensions.libs

    implementation(libs.findLibrary("hilt.navigation.compose").get())
    implementation(libs.findLibrary("androidx.compose.navigation").get())

    implementation(libs.findLibrary("androidx.lifecycle.runtime.compose").get())
    implementation(libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
}