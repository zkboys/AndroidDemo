const express = require('express');
const router = express.Router();

router.get('/', function (req, res) {
    res.send({
        name: '张三',
        age: '27'
    });
});

module.exports = router;