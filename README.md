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