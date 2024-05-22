import com.somnwal.kakaobank.app.configureHiltAndroid
import com.somnwal.kakaobank.app.configureKotlinAndroid

// Application 전반적으로 적용됨
plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()