# AndroidDemo
android项目例子

## 项目结构
```
app：安卓相关目录，存放页面相关文件

zkboys-sdk：service相关目录，存放model service http封装 oauth封装，主要与后端交互，为view提供数据；
            封装了一些请求和一些service，开发时，主要关心的文件为：model service exception ZKBoysSDK.java，其他的基本是通用的，已经封装好了。
            model：定义一些javabean，模型
            service：发送请求，获取数据，会加入到ZKBoysSDK.java工厂类中，供android 的 view层从ZKBoysSDK.java中调用
            exception：扩展ServiceException中的异常类型，以及自定义其他异常等
            ZKBoysSDK.java：这个是个service工厂，新建service要在这个类中新增方法
```

## 请求加签算法
```java
private Map<String, String> sign(boolean authenticated, String url, String body) throws ServiceException, NetworkException {
    Map<String, String> signParams = new HashMap<>();

    /*
    * timestamp 时间戳
    * ticket随机字符串 与时间戳一起，对加签字符串进行混淆，保证同样的请求，每次加签字符串都不同
    * version 版本号 可以根据这个版本号确定加签算法，可以对加签算法进行过渡升级
    * appKey 后端根据这个appKey，读取后端存储的appSecret
    * accessToken 登录验证
    *
    * url
    * body
    * appSecret，前端的secret、、 这三个参数不会在signParams中，只是用来生成sign字符串
    *
    * sign，基于一定算法，计算出来的字符串，后端会根据以上参数，同样的算法计算出一个后端的sign字符串，与客户端传送过来的sign进行比较，如果相等，说明此次请求有效
    *
    * 加签之后，一个请求会携带额外的数据为：timestamp ticket version appKey accessToken sign
    *                还有正常要发的数据：body
    *
    * */
    signParams.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
    signParams.put("ticket", UUID.randomUUID().toString());
    signParams.put("version", VERSION);
    signParams.put("appKey", this.oauthProvider.getOAuthClient().appKey);
    if (authenticated) {
        signParams.put("accessToken", this.oauthProvider.getAccessToken(this));
    }
    List<String> list = new ArrayList<>(signParams.values());

    list.add(url);
    list.add(body);
    list.add(this.oauthProvider.getOAuthClient().appSecret);

    Collections.sort(list);
    String str = "";

    for (int i = 0, size = list.size(); i < size; i++) {
        str += list.get(i);
        if (i + 1 < size) {
            str += "&";
        }
    }
    signParams.put("sign", MD5Util.md5(str).toUpperCase());

    return signParams;
}
```

## MVP 模式

### M：model
1. 数据模型的定义
1. 后端等交互，进行数据的请求，持久化等操作

### V：Activity xml等视图相关
1. 定义获取页面数据方法，供P使用
1. 定义设置页面数据，改变页面等方法，供P使用

### P: Presenter
1. 调用V中的方法，获取数据，进行数据的处理
1. 调用M中的方法，将数据传给后端，或者本地存储等
1. 调用V中的方法，将处理结果返回给V，V可以做一些更新页面的操作

## 重新登录
项目中通过 oAuthLifeCycleListener 判断是否需要重新登录，并跳转

## 启动一个activity
Activity中定义一个静态启动方法，其他Activity如果要启动另外一个Activity，调用这个方法即可
```java
/**
 * 启动当前Activity，如果需要参数，在这里定义，其他Activity能够明确知道，启动这个Activity需要什么参数
 */
public static void actionStart(Context context) {
    Intent intent = new Intent(context, HomeActivity.class);
    context.startActivity(intent);
}
```

## Activity 启动之后，设置输入框不自动获取焦点
```xml
    // 在 不想获取焦点的输入框父级，加入如下属性
    android:focusable="true"
    android:focusableInTouchMode="true"
```
## android原生提供了很多图标可以使用
```
@android:drawable/ic_menu_send
```

## icon 图标库

[http://joanzapata.com/android-iconify/](http://joanzapata.com/android-iconify/)

使用自定义字体图标方法 [http://www.iconfont.cn/plus/help/detail?helptype=code](http://www.iconfont.cn/plus/help/detail?helptype=code)

## 自定义按钮背景颜色
[可绘制对象资源](https://developer.android.com/guide/topics/resources/drawable-resource.html#StateList)

## 矢量图标生成方式（需要api level > 21）
右键-> new -> Vector Asset 可以选择本地SVG图片进行生成，SVG图片可以在[网上](http://www.iconfont.cn/plus)下载，或者自己制作

可以通过 设置 alpha值，改变图标透明度，将纯黑色图标改成灰色

## 图片的展示方式

## toolbar的使用

## mchId storeId
HttpJsonServiceClient 中统一为所有的请求添加了mchId 和 storeId参数，后端可以获取到，并决定是否使用这两个参数

## TODO
1.[x] 返回的数据怎么加签的？加签了吗？有双向加签的，但是这个项目没用用双向加签
1.[x] 底层封装的请求，用的都是post，不基于RestFul进行封装，提供其他方法吗？比如常用的get post put delete， 做封装时候偷懒了，用的都是post
1.[x] OAuth 中的scopes 客户端这边好像没用到，这个是用来确定，token的可访问资源，这个限制怎么能客户端传scope呢？不能根据appKey 或者 appSecret确定这个token的权限吗？
      这个是预留功能，典型的场景是第三方登录，用户会选几个权限，确认之后，服务端会根据这几个权限进行创建accessToken
1.[x] 底层请求封装，按照RestFull规范进行重新封装
1.[x] 完整的node后端支持
1.[ ] 颜色设置
1.[ ] 数据缓存，底层封装。先显示老数据，背后请求新数据，完成之后，显示新数据，
1.[ ] 自定义组件如何获取原始属性
1.[ ] 点餐页面有可能给顾客用，这个页面添加用户锁功能，用户返回需要输入提前设置的密码，才能返回，界面按钮权限限制，防止用户乱操作。
1.[ ] 点餐页面添加菜品图片，点击弹出详情，这个页面有可能给客户用。
1.[ ] 正餐的电话号码没用，预订才有用。正餐点餐电话号码去掉。
1.[ ] 自定义dialog组件样式，对系统dialog进行封装
