const controllerDecorator = require('../controller-decorator');
const userService = require('../../service/user');
const oauthTokenService = require('../../service/oauth_token');

exports.signIn = controllerDecorator(async function (req, res) {
    console.log(req.method);
    const userName = req.query.userName;
    const passWord = req.query.passWord;
    const scope = req.query.scope;
    const appKey = req.headers.appkey;

    const user = await userService.getUserByLoginNameAndPass(userName, passWord);
    const oauthToken = await oauthTokenService.createOauthToken(appKey, scope, user._id);
    res.send({
        accessToken: oauthToken.accessToken,
        refreshToken: oauthToken.refreshToken,
        scopes: oauthToken.scopes,
        expiresIn: oauthToken.expiresIn,
    });
});