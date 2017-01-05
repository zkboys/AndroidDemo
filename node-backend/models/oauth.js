const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const RoleSchema = new Schema({
    appKey: String,
    appSecret: String,
    scope: {type: String},
    expiresIn: Number,
});

mongoose.model('oauth', RoleSchema);
