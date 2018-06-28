var Log = require('./log')

// 测试用例嵌套
// 执行顺序:先执行外层测试再执行内层测试
describe('外层模块', function() {

  it('外层模块功能', function() {
    Log.log('外层模块功能测试中......');
  })

  describe('内层模块', function() {

    describe('叶子模块', function() {

      it('叶子模块功能', function() {
        Log.log('叶子模块功能测试中......')
      })

    })

    it('内层模块功能', function() {
      Log.log('内层模块功能测试中......')
    })

  })

})