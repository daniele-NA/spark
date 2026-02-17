@file:Suppress("UnstableApiUsage","NewerVersionAvailable","UseTomlInstead","GradleDependency","DEPRECATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.crescenzi.spark"
    compileSdk = 36

    // == USE THIS VERSION OF NDK FOR 16 KB ALIGNMENT (REQUIRED FOR PLAY STORE) == //
    ndkVersion = "29.0.13846066"
    defaultConfig {
        applicationId = "com.crescenzi.spark"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildTypes{
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.3")
    implementation("androidx.core:core-splashscreen:1.0.1")
}

// == AUTOMATICALLY TRIGGERED == //
tasks.register<Exec>("build_rust") {
    group = "rust"

    // == WE DETECT RUST INTO OUR SYSTEM == //
    val cargoPath = "${System.getProperty("user.home")}/.cargo/bin/cargo"
    print(projectDir.path)

    val mainDirectory = "${projectDir.path}/src/main"

    // == IF CARGO-NDK IS NOT INSTALLED,WE REPAIR INSTALLING IT == //
    doFirst {
        val result = exec {
            commandLine(cargoPath, "ndk", "--help")
            isIgnoreExitValue = true
        }
        if (result.exitValue != 0) {
            println("cargo-ndk not found...installing it")
            exec {
                commandLine(cargoPath, "install", "cargo-ndk")
            }
        }
    }

    workingDir("$mainDirectory/rust")
    commandLine(
        cargoPath, "ndk",
        "-t", "arm64-v8a",
        "-t", "armeabi-v7a",
        "-t", "x86",
        "-t", "x86_64",
        "-o", "$mainDirectory/jniLibs",
        "build", "--release"
    )
}


tasks.named("preBuild") {
    dependsOn("build_rust")
}
