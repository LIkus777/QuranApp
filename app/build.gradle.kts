plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.zaur.quranapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zaur.quranapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.activityCompose.toString()
    }

    android {
        sourceSets {
            getByName("main") {
                jniLibs.srcDirs(
                    "src/main/jniLibs",
                    "../unityLibrary/src/main/jniLibs"
                )
            }
        }
    }
}

dependencies {

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.lifecycle.runtime)
    implementation(libs.material)
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.runtime:runtime:1.6.0")
    implementation(files("C:\\Users\\zm393\\Masjid\\android\\unityLibrary\\libs\\classes.jar"))
    implementation(files("C:\\Users\\zm393\\Masjid\\android\\unityLibrary\\libs\\unity-classes.jar"))

    // Debugging tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation(libs.navigation)

    //LeakCanary
    debugImplementation (libs.leakcanary.android)
    //releaseImplementation (libs.leakcanary.android.no.op)

    /*implementation (files("libs/classes.jar"))
    implementation (files("libs/unity-classes.jar"))*/

    implementation(project(":unityLibrary"))
    implementation(project(":di"))
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":features"))
    implementation(project(":navigation"))
    implementation(project(":presentation"))
}