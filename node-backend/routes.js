const router = require('express').Router();
const oauth = require('./api/v1/oauth');
const app = require('./api/v1/app');

router.get('/v1/oauth/sign_in.json', oauth.signIn);
router.get('/v1/version.json', app.checkVersion);

module.exports = router;