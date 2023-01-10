// Empty constructor
function SeonPlugin() {}

// The function that passes work along to native shells
SeonPlugin.prototype.getDeviceFingerprint = function(sessionId, successCallback, errorCallback) {
  var options = {};
  options.sessionId = sessionId;
  cordova.exec(successCallback, errorCallback, 'SeonPlugin', 'getDeviceFingerprint', [options]);
}

// Installation constructor that binds SeonPlugin to window
SeonPlugin.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.seonPlugin = new SeonPlugin();
  return window.plugins.seonPlugin;
};
cordova.addConstructor(SeonPlugin.install);
