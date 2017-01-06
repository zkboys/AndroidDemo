const router = require('express').Router();
const oauth = require('./api/v1/oauth');
const app = require('./api/v1/app');
const signatured = require('./middlewares/signatured');

router.get('/v1/oauth/sign_in.json', signatured, oauth.signIn);
router.get('/v1/version.json', app.checkVersion);

module.exports = router;