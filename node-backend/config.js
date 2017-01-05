const isDebug = process.env.NODE_ENV === 'development' || process.env.NODE_ENV === 'test';

const config = {
    debug: isDebug,
    hostname: '127.0.0.1',
    port: process.env.PORT || 3000,

    // mongodb 配置
    db: 'mongodb://127.0.0.1/org_dev',

    admin_name: 'admin', // 超级管理员，拥有所有的权限
};

module.exports = config;