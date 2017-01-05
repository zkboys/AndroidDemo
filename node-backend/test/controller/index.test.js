const request = require('supertest');
const app = require('../../app');
const config = require('../../config');
const support = require('../support/support');

describe('test/controller/index.test.js', function () {
    it('should / get user count 10', function (done) {
        request(app).get('/')
            .end(function (err, res) {
                res.status.should.equal(200);
                res.body.age.should.equal(27);
                done();
            });
    });
});
