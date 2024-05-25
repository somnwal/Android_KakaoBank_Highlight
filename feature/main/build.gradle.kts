import com.somnwal.kakaobank.app.common.setNamespace

plugins {
    id("somnwal.kakaobank.android.feature")
}

android {
    setNamespace("feature.main")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

    implementation(libs.android.material)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}