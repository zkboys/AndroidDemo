const controllerDecorator = require('../controller-decorator');

exports.checkVersion = controllerDecorator(async function (req, res) {
    const appType = req.query.appType;
    const clientVersion = req.query.clientVersion;

    console.log(appType, clientVersion);

    res.send({
        versionCode: 6, // 版本号
        versionName: '1.0.5', // 版本名
        promote: 0, // 是否更新 0:不更新 1:更新 2:强制更新
        appUrl: 'https://open-express.jiabangou.com/app/release_JBG_v1.0.5_201611141746.apk', // 更新下载地址
        upgradePrompt: '加班狗配送有新版本' // 更新提示
    });
});