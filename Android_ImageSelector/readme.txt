目标:
1, 尽可能的去避免内存溢出
  a, 根据图片的显示大小去压缩图片
  b, 使用缓存对我们的图片进行管理(LruCache)

2, 用户操作UI控件必须充分的流畅
 getView里面尽可能的不去做耗时操作(异步加载 + 回调显示)
3, 用户预期显示的图片尽可能的快(图片的加载策略的选择) LIFO FIFO

ImageLoader

getView(){
  url -> Bitmap
  url -> LruCache 查找
       ->找到返回
       -> 找不到 url-Task -> TaskQueue且发送一个通知去提醒后台轮询线程
       Task -> run(){ 根据url加载图片
                    1, 获取图片显示的大小
                    2, 使用Options对图片进行压缩
                    3, 加载图片并且放入LruCache
       }
}

后台轮询线程
TaskQueue -> Task -> 线程池去执行

