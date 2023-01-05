// Empty constructor
function SeonPlugin() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
SeonPlugin.prototype.show = function(message, duration, successCallback, errorCallback) {
  var options = {};
  options.message = message;
  options.duration = duration;
  cordova.exec(successCallback, errorCallback, 'SeonPlugin', 'show', [options]);
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
