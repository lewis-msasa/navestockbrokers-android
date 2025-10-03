plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version libs.versions.ksp.get()
}

android {
    namespace = "com.nafepay.main"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.room.ktx)
    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.swipe)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(project(":libraries:navigation"))
    implementation(project(":libraries:domain"))
    implementation(project(":libraries:core"))
    implementation(project(":libraries:navigation"))
    implementation(project(":libraries:ui:authentication"))
    implementation(project(":libraries:ui:common_ui"))
    implementation(project(":libraries:ui:portfolio"))
    implementation(project(":libraries:ui:stocks"))
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.paging)

    implementation(libs.androidx.lifecycle.liveData)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.coil)

    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.layout)
    implementation(libs.androidx.compose.animation)
    ksp(libs.room.compiler)
}