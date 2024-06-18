import com.somnwal.kakaobank.app.common.setNamespace

plugins {
    id("somnwal.kakaobank.android.library")
    id("somnwal.kakaobank.android.hilt")
    id("kotlinx-serialization")
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    setNamespace("core.datastore")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastoreProto)
    implementation(libs.androidx.datastore)

    implementation(libs.kotlinx.serialization.json)

}