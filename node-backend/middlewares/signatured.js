const config = require('../config');
const AppService = require('../service/app');
const crypto = require('crypto');
const md5 = crypto.createHash('md5');

exports = module.exports = async function (req, res, next) {
    const appKey = req.headers.appkey;
    const timestamp = req.headers.timestamp;
    const ticket = req.headers.ticket;
    const sign = req.headers.sign;
    let version = req.headers.version;
    const accessToken = req.headers.accesstoken;
    const url = req.url;
    const method = req.method;
    let params = req.body;
    if (method === 'GET') {
        params = req.query;
    }
    const authenticated = false; // TODO
    const checkResult = await AppService.checkSignature({
        url,
        sign,
        ticket,
        appKey,
        params,
        version,
        timestamp,
        accessToken,
        authenticated
    });
    if (checkResult) {
        next();
    } else {
        res.sendError('无效的签名');
    }
}