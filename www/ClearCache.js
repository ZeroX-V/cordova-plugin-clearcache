var exec = require('cordova/exec');

exports.getCaches = function (success) {
    exec(success, null, 'ClearCache', 'getCaches', []);
};

exports.clearCaches = function (success) {
    exec(success, null, 'ClearCache', 'clearCaches', []);
};