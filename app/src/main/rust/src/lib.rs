
use serde::Deserialize;
use jni::sys::{JNI_VERSION_1_6 ,jint,JavaVM};

use android_logger::Config;

pub fn init_logging(){
    android_logger::init_once(
        Config::default()
            .with_max_level(log::LevelFilter::Trace)
            .with_tag("MY-LOG")
    );
}

use log::{info};

//                              INIT //


#[no_mangle]
pub extern "C" fn JNI_OnLoad(
    _vm: *mut JavaVM,
    _reserved: *mut std::ffi::c_void,
) -> jint {
    init_logging();
    JNI_VERSION_1_6
}


//                              MATH //


pub fn make_sum(a:i32,b:i32)->i32{
    a+b
}

#[no_mangle]
pub extern "system" fn Java_com_crescenzi_spark_JavaActivity_sum(
    _env: *mut std::ffi::c_void,
    _class: *mut std::ffi::c_void,
    a: i32,
    b: i32,
) -> i32 {
    make_sum(a,b)
}



                                // NETWORK //



// == USED FOR API CALL AS RESPONSE == //
#[derive(Deserialize,Debug)]
struct Todo{
    #[allow(non_snake_case)]userId: u32, // unsigned integer
    id:u32,
    title:String,
    completed:bool,
}
#[no_mangle]
pub extern "C" fn Java_com_crescenzi_spark_KotlinActivity_callApi(
    _env: *mut std::ffi::c_void,
    _class: *mut std::ffi::c_void,
) -> bool {
    let rt = tokio::runtime::Runtime::new().unwrap();
    info!("init api runtime");

    rt.block_on(async {
        match reqwest::get("https://jsonplaceholder.typicode.com/todos/1").await {
            Ok(resp) => match resp.json::<Todo>().await {
                Ok(todo) => {
                    info!("{:?}", todo);
                    true
                }
                Err(_) => false,
            },
            Err(_) => false,
        }
    })
}