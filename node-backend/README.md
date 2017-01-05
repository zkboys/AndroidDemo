# node-backend
使用nodejs实现的后端，此目录后期可以单独重android项目分离出来，前期为了方便管理，放到一起。


## 安装&启动
1. [node > 7.x](https://nodejs.org/en/)
2. [pm2](http://pm2.keymetrics.io/) 
```
npm install

npm install pm2@latest -g

npm run pro 

访问 http://localhost:3000
```
## 开发
```
npm run dev
```

## 查看端口占用
```
lsof -i:3000
```
