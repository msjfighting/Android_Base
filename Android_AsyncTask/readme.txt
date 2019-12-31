AsyncTask-异步任务
AsyncTask<Params,Progress,Result>是一个抽象类
通常用于被继承,继承AsyncTask需要制定如下三个泛型参数;
Params -> 启动任务时输入参数类型
Progress -> 后台任务执行中返回进度值的类型
Result -> 后台执行任务完成后返回结果的类型

回调方法

doInBackgrund(参数数组): 必须重写,异步执行后台线程将要完成的任务

onPreExecute: 执行后台耗时操作前被调用, 通常用户完成一些初始化操作

onPostExcute: 当doInBackgrund()完成后,系统会自动调用该方法,并将doInBackgrund方法返回的值传给该方法

onProgressUpdate: 在doInBackgrund()方法中调用 publishProgress()方法 更新任务的执行进度后,就会触发该方法



Bitmap - 加载





