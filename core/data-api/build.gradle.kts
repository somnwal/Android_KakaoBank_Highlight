import com.somnwal.kakaobank.app.common.setNamespace

plugins {
    id("somnwal.kakaobank.android.library")
}

android {
    setNamespace("core.data.api")
}

dependencies {
    implementation(projects.core.model)
}