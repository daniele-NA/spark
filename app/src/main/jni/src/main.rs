use jni::JNIEnv;
use jni::objects::{JClass, JString};
use jni::sys::jstring;

#[no_mangle]
pub extern "system" fn Java_com_crescenzi_spark_MainActivity_add(
    _env: *mut std::ffi::c_void,
    _class: *mut std::ffi::c_void,
    a: i64,
    b: i64,
) -> i64 {
    a+b
}