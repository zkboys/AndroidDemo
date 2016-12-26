const isDebug = process.env.NODE_ENV === 'development' || process.env.NODE_ENV === 'test';

const config = {
    debug: isDebug,
    hostname: '127.0.0.1',
    port: process.env.PORT || 8001,
};

module.exports = config;