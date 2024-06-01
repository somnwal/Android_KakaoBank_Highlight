import com.somnwal.kakaobank.app.common.setNamespace

plugins {
    id("somnwal.kakaobank.android.library")
    id("somnwal.kakaobank.android.hilt")
    id("kotlinx-serialization")
}

android {
    setNamespace("core.datastore")
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.androidx.datastore)
    implementation(libs.kotlinx.serialization.json)

}