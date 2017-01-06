require('mochawait');
const versionUpgradeProxy = require('../../proxy/version_upgrade');

const oauthProxy = require('../../proxy/oauth');

describe('test/proxy/version_upgrade.test.js', function () {
    describe('#addVersionUpgrade()', function () {
        // this.slow(1);
        // this.timeout(100);'
        // it.skip(...)
        // it.only(...)

        it.skip('should return a versionUpgrade and versionUpgrade.version_code==6', async() => {
            const vu = {
                app_type: 'android', // 客户端设备类型(android,ios)
                version_name: '1.0.5', // 可读性版本号，给用户看的 '1.0.5'
                version_code: 6, // 版本号，程序使用 6
                promote: 0, // 是否更新 0:不更新 1:更新 2:强制更新
                app_url: 'https://www.baidu.com', // 新app下载地址
                upgrade_prompt: '测试更新', // app更新内容
                size: 1024, // app大小
                state: 'enable', //
            }

            const versionUpgrade = await versionUpgradeProxy.addVersionUpgrade(vu);
            versionUpgrade.version_code.should.equal(6);
        });

        it.skip('should return a oauth and oauth.app_key==1000', async() => {
            const vu = {
                app_key: '1000',
                app_secret: '3b4fd56df5964909b45a2640a4317be0',
                scope: 'base',
                expires_in: 30 * 60, // 过期时间，单位秒
            }

            const oauth = await oauthProxy.addOauth(vu);
            oauth.app_key.should.equal('1000');
        });
    });
});
