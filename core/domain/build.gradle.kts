import com.somnwal.kakaobank.app.common.setNamespace

plugins {
    id("somnwal.kakaobank.android.library")
}

android {
    setNamespace("core.domain")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.dataApi)
}