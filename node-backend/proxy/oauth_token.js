const OauthTokenModel = require('../models').OauthToken;

exports.getOauthTokenByUserId = function (userId) {
    return OauthTokenModel.findOne({user_id: userId}).lean().lean();
};

exports.createOauthToken = function (oauthToken) {
    return new OauthTokenModel(oauthToken).save();
};