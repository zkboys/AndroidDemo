const controllerDecorator = require('../controller-decorator');
const userService = require('../../service/user');

exports.signIn = controllerDecorator(async function (req, res) {

    const userName = req.query.userName;
    const passWord = req.query.passWord;

    const user = await userService.getUserByLoginNameAndPass(userName, passWord);

    console.log(user);

    // 根据appKey查询相应的OAuth对象
    // 判断OAuth.appSecret 是否等于 客户端传过来的appSecret
    // 根据用户名密码查询 用户id
    // 根据用户id、OAuth.scope创建 token对象，存储到数据库
    // 返回前端需要的token
    const appKey = req.headers.appKey;
    const timestamp = req.headers.timestamp;
    const ticket = req.headers.ticket;
    const sign = req.headers.sign;
    const version = req.headers.version;
    const accessToken = req.headers.accessToken;
    const url = req.url;
    const paramsSignString = "";
    const appSecret = "3b4fd56df5964909b45a2640a4317be0";

    for (var p in req.query) {
        console.log(p);
    }

    res.send({
        accessToken: '123456',
        refreshToken: '654321',
        scope: 'a,b,c',
        expiresIn: 24 * 60 * 60 * 1000
    });
});