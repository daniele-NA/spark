
use serde::Deserialize;

pub fn make_sum(a:i32,b:i32)->i32{
    a+b
}

#[no_mangle]
pub extern "system" fn Java_com_crescenzi_spark_MainActivity_sum(
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
pub extern "C" fn Java_com_crescenzi_spark_MainActivity_callApi(
    _env: *mut std::ffi::c_void,
    _class: *mut std::ffi::c_void,
) -> bool {
    let rt = tokio::runtime::Runtime::new().unwrap();
    rt.block_on(async {
        match reqwest::get("https://jsonplaceholder.typicode.com/todos/1").await {
            Ok(resp) => match resp.json::<Todo>().await {
                Ok(todo) => {
                    println!("{:?}", todo);
                    true
                }
                Err(_) => false,
            },
            Err(_) => false,
        }
    })
}