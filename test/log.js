var util = require('util');

var Log = {};

Log.log = function(format, ...args) {
  var str = null

  if (!args) {
    str = format
  } else if (args.length == 1) {
    str = util.format(format, args[0])
  } else if (args.length == 2) {
    str = util.format(format, args[0], args[1])
  } else if (args.length == 3) {
    str = util.format(format, args[0], args[1], args[2])
  } else if (args.length == 4) {
    str = util.format(format, args[0], args[1], args[3])
  } else {
    str = '';
  }
  Log.show(str)
}

Log.show = function(str) {
  // console.log(str)
}

module.exports = Log