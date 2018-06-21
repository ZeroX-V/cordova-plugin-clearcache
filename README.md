# cordova-plugin-clearcache
Clear Application Cache

# Install
cordova plugin add https://github.com/lwz467666145/cordova-plugin-clearcache.git

# Use

### getCaches 获取应用缓存大小
```javascript
navigator.clearCache.getCaches(function (result) {
    // 获取成功回调方法
    // result - 缓存大小，单位Bytes
});
```

### clearCaches 清除应用缓存
```javascript
navigator.clearCache.clearCaches(function () {
    // 清除成功回调方法
});
```
