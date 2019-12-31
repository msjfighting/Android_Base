package com.msj.android_project.bean;

import java.io.Serializable;

public class GoodsBean implements Serializable {
    /**
     自增ID
     */
    public  String product_id;
/**
 宝贝的id
 */
    public String itemid;
/**
 放单人ID
 */
    public String seller_id;


/**
 宝贝标题
 */
    public String itemtitle;


/**
 宝贝短标题
 */
    public String itemshorttitle;

/**
 宝贝推荐语
 */
    public String itemdesc;

/**
 在售价
 */
    public String itemprice;

/**
 宝贝月销量
 */
    public String itemsale;

/**
 宝贝近2小时跑单
 */
    public String itemsale2;

/**
 当天销量（选择昨日爆单榜没有该字段）
 */
    public String todaysale;

/**
 昨日销量（选择昨日爆单榜才有该字段）
 */
    public String yesterdaysale;

/**
 宝贝主图原始图像（由于图片原图过大影响加载速度，建议加上后缀_310x310.jpg，如https://img.alicdn.com/imgextra/i2/3412518427/TB26gs7bb7U5uJjSZFFXXaYHpXa_!!3412518427.jpg_310x310.jpg）
 */
    public String itempic;

/**
 推广长图（带http://img.haodanku.com/0_553757100845_1509175123.jpg-600进行访问）
 */
    public String itempic_copy;
/**
 商品类目：
 1女装，2男装，3内衣，4美妆，5配饰，6鞋品，7箱包，8儿童，9母婴，10居家，11美食，12数码，13家电，14其他，15车品，16文体，17宠物
 */
    public String fqcat;
/**
 宝贝券后价
 */
    public String itemendprice;
/**
 店铺类型：
 天猫（B）
 淘宝店（C）
 */
    public String shoptype;
/**
 佣金计划：
 隐藏
 营销
 */
    public String tktype;

/**
 佣金比例
 */
    public String tkrates;
/**
 预计可得（宝贝价格 * 佣金比例 / 100）
 */
    public String tkmoney;

/**
 优惠券链接
 */
    public String couponurl;
/**
 优惠券金额
 */
    public String couponmoney;
/**
 优惠券剩余量
 */
    public String couponsurplus;
/**
 优惠券领取量
 */
    public String couponreceive;
/**
 2小时内优惠券领取量
 */
    public String couponreceive2;
/**
 今日优惠券领取量
 */
    public String todaycouponreceive;
/**
 优惠券总数量
 */
    public String couponnum;
/**
 优惠券使用条件
 */
    public String couponexplain;
/**
 优惠券开始时间
 */
    public String couponstarttime;
/**
 优惠券结束时间
 */
    public String couponendtime;
/**
 活动开始时间
 */
    public String start_time;
/**
 活动结束时间
 */
    public String end_time;
/**
 发布时间
 */
    public String starttime;
/**
 是否为品牌产品（1是）
 */
    public String is_brand;
/**
 推广导购文案
 */
    public String guide_article;
/**
 商品视频ID（id大于0的为有视频单，视频拼接地址http://cloud.video.taobao.com/play/u/1/p/1/e/6/t/1/+videoid+.mp4）
 */
    public String videoid;
/**
 活动类型：
 普通活动
 聚划算
 淘抢购
 */
    public String activity_type;
/**
 好单指数
 */
    public String general_index;
/**
 营销计划链接
 */
    public String planlink;
/**
 放单人名号

 */
    public String seller_name;

/**
 店铺掌柜名
 */
    public String sellernick;
/**
 折扣力度
 */
    public String discount;
/**
 出单指数（选择出单指数榜才有该字段）
 */
    public String order_count;

/**
 是否村淘（1是）(首页数据)
 */
    public String cuntao;

/**
 举报处理条件
 0未举报
 1为待处理
 2为忽略
 3为下架
 */
    public String report_status;
/**
 是否为直播产品（1是）
 (首页数据)
 */
    public String is_live;
}
