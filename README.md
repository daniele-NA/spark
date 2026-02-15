# ⚡ Spark

A minimal Android app with native Rust code via JNI.

## 🔧 Stack

- **Kotlin** + Jetpack Compose — UI & app logic
- **Rust** (via `cargo-ndk`) — native `.so` libraries loaded through JNI
- **Gradle KTS** — build system with a custom `build_rust` task

## 📦 How It Works

Rust code lives in `app/src/main/jni/` and compiles into shared libraries (`.so`) for four Android architectures:
`arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`.

The Gradle task `build_rust` runs automatically before every build via `preBuild`, placing the compiled `.so` files into `app/src/main/jniLibs/`. On the Kotlin side, the library is loaded with `System.loadLibrary("spark_rust")` and native functions are declared as `external fun`.

## 🚀 Setup

1. Install Rust Android targets:
   ```
   rustup target add aarch64-linux-android armv7-linux-androideabi i686-linux-android x86_64-linux-android
   ```
2. Make sure `cargo-ndk` is installed (the build task auto-installs it if missing).
3. Open the project in Android Studio and hit **Run**.

## 📁 Project Structure

```
app/
├── src/main/
│   ├── jni/              # Rust crate
│   │   ├── Cargo.toml
│   │   └── src/lib.rs
│   ├── jniLibs/          # compiled .so output (auto-generated)
│   ├── kotlin/           # Kotlin sources
│   └── res/              # Android resources
└── build.gradle.kts      # includes build_rust task
```
