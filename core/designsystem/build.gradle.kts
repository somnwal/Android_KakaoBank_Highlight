import com.somnwal.kakaobank.app.common.setNamespace

plugins {
    id("somnwal.kakaobank.android.library")
    id("somnwal.kakaobank.android.compose")
}

android {
    setNamespace("core.design")
}

dependencies {
    implementation(libs.androidx.appcompat)
}