import com.somnwal.kakaobank.app.common.setNamespace

plugins {
    id("somnwal.kakaobank.android.library")
    id("somnwal.kakaobank.android.compose")
}

android {
    setNamespace("core.designsystem")
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.androidx.appcompat)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)
}