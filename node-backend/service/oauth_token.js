const OauthTokenProxy = require('../proxy/oauth_token');
const OauthService = require('./oauth');
const ServiceError = require('./service-error');
const message = require('../properties').errorMessages;
const uuidV4 = require('uuid/v4');

const REFRESH_TOKEN_EXPIRES_ID = 90 * (24 * 60 * 60); // refresh token过期时间，单位分，90天

exports.createOauthToken = async function (appKey, scope, userId) {
    const oauth = await OauthService.getOauthByAppKey(appKey);

    // 检查scope
    const scopes = oauth.scope.split(',');
    const paramScopes = scope.split(',');

    // 取交集
    const realScopes = paramScopes.filter(s => scopes.indexOf(s) != -1);
    if (!realScopes || !realScopes.length) {
        throw new ServiceError(message.scopeInvalid);
    }
    const realScopeStr = realScopes.join(',');
    const refreshToken = uuidV4();
    const nowTime = (new Date()).getTime();

    const oauthToken = {
        oauth_id: oauth._id,
        user_id: userId,
        app_key: appKey,
        scope: realScopeStr,
        version_id: null, // TODO 这个字段是干嘛的？
        access_token: uuidV4(),
        expired_at: nowTime + oauth.expires_in * 1000,
        refresh_token: refreshToken,
        old_refresh_token: refreshToken,
        refresh_token_expired_at: nowTime + REFRESH_TOKEN_EXPIRES_ID * 1000,
    }

    await OauthTokenProxy.createOauthToken(oauthToken);

    return {
        accessToken: oauthToken.access_token,
        refreshToken: oauthToken.refresh_token,
        scopes: oauthToken.scope,
        expiresIn: oauth.expires_in,
    };
};





















