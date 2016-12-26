/*
 * 路由映射文件,配置越靠前，优先级越高
 * */
var express = require('express');
var router = express.Router();
router.get('/', function (req, res, next) {
    res.render('index.html');
});
router.get('/test-ajax', function (req, res, next) {
    console.log(req.query.name);
    console.log(req.query.age);

    res.send([
        {'name': '王小水', age: 12},
        {'name': '李小水', age: 12}
    ]);
});

router.get('/oauth/sign_in.json', function (req, res, next) {
    console.log(req.query);
    // 根据appKey查询相应的OAuth对象
    // 判断OAuth.appSecret 是否等于 客户端传过来的appSecret
    // 根据用户名密码查询 用户id
    // 根据用户id、OAuth.scope创建 token对象，存储到数据库
    // 返回前端需要的token
    var appKey = req.headers.appKey;
    var timestamp = req.headers.timestamp;
    var ticket = req.headers.ticket;
    var sign = req.headers.sign;
    var version = req.headers.version;
    var accessToken = req.headers.accessToken;
    var url = req.url;
    var paramsSignString = "";
    var appSecret = "3b4fd56df5964909b45a2640a4317be0";

    for(var p in req.query){
        console.log(p);
    }

    var method =  req.method;

    res.send({
        accessToken: '123456',
        refreshToken: '654321',
        scope: 'a,b,c',
        expiresIn: 24 * 60 * 60 * 1000
    });
});

router.get('/version.json', function (req, res, next) {
    console.log(req.query);
    res.send({
        version: 6, // 版本号
        versionCode: '1.0.5', // 版本名
        promote: 0, // 是否更新 0 不更新 1 更新 2 强制更新
        appUrl: 'https://open-express.jiabangou.com/app/release_JBG_v1.0.5_201611141746.apk', // 更新下载地址
        upgradePrompt: '加班狗配送有新版本' // 更新提示
    });
});

module.exports = router;
