
pub fn make_rand_sum(a:i32,b:i32)->i32{
    a+b
}

#[no_mangle]
pub extern "system" fn Java_com_crescenzi_spark_MainActivity_sum(
    _env: *mut std::ffi::c_void,
    _class: *mut std::ffi::c_void,
    a: i32,
    b: i32,
) -> i32 {
    a+b+b
}