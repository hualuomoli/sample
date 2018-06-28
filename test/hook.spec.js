var Mock = require('mockjs')
var Log = require('./log')

// 测试用例钩子
// https://mochajs.org/#hooks

// it方法按照编码顺序执行,如果上一个it没有使用done，后面的it测试不等待
// before和beforeEach方法应该使用done方法指定已经完成预处理，否则后面的测试可能不等待预处理结果
// before、beforeEach、after和afterEach方法执行严格按照before -> beforeEach -> it -> afterEach -> afeter

var sleepMills = 666

// 异步操作，前面的方法未使用done方法，后面的方法不等待前面的方法返回就执行
describe('异步未使用done', function() {

  var result = null

  it('业务1', function() {

    setTimeout(function() {
      result = '业务1'
    }, sleepMills)

  })

  it('业务2', function() {
    Log.log('业务1结果', result)
  })

})

// 异步操作，前面的方法使用done方法，后面的方法等待前面的方法调用done后才会执行
describe('异步使用done', function() {

  var result = null

  it('业务1', function(done) {

    setTimeout(function() {
      result = '业务1'

      // 只有done后该测试才完成
      done()
    }, sleepMills)

  })

  it('业务2', function() {
    Log.log('业务1结果', result)
  })

})

// 一个describe在执行所有it测试用例前只执行一次
describe('before', function() {

  var token = null

  before('登录', function(done) {
    Log.log('业务处理前先登录获取token')

    // 模拟登录操作
    setTimeout(function() {
      token = Mock.mock('@guid()')
      Log.log('登录成功');

      // 业务处理完成后调用done方法,否则it方法可能在改方法未返回时调用
      done();
    }, sleepMills);
  });

  it('获取用户信息', function() {
    Log.log('获取用户信息，使用token', token)
  })

  it('查询推荐商品', function() {
    Log.log('查询推荐商品，使用token', token)
  })

})

// 一个describe在执行所有it测试用例后只执行一次
describe('after', function() {

  var token = Mock.mock('@guid()')

  it('获取用户信息', function() {
    Log.log('获取用户信息，使用token', token)
  })

  it('查询推荐商品', function() {
    Log.log('查询推荐商品，使用token', token)
  })

  after('退出', function(done) {
    Log.log('业务处理后退出系统')

    setTimeout(function() {
      token = null
      Log.log('登出成功')

      done();
    }, sleepMills);
  });

})


// 所有it方法执行前执行
describe('beforeEach', function() {

  var usercode = null

  beforeEach('获取用户信息', function(done) {
    Log.log('正在获取用户信息')

    // 模拟登录操作
    setTimeout(function() {
      usercode = Mock.mock('@name(5, 15)')
      Log.log('获取用户信息成功');

      done();
    }, sleepMills);
  });

  it('查询用户区域', function() {
    Log.log('查询用户区域，使用usercode', usercode)
  })

  it('获取购物车商品', function() {
    Log.log('获取购物车商品，使用usercode', usercode)
  })

})

// 所有it方法执行后执行
describe('afterEach', function() {

  var result = null

  it('查询用户区域', function() {
    result = '查询用户区域'
  })

  it('获取购物车商品', function() {
    result = '获取购物车商品'
  })

  afterEach('打印操作日志', function(done) {
    Log.log('正在打印操作日志')

    // 模拟登录操作
    setTimeout(function() {
      Log.log('业务执行结果', result);
      result = null

      done();
    }, sleepMills);
  });

})



describe('before和after是否有顺序', function() {

  var commonParams = null
  var params = null
  var result = null

  after('业务处理后调用', function(done) {
    Log.log('业务处理结果', result)

    done()
  })

  afterEach('每个业务处理后调用', function(done) {
    Log.log('每个业务处理结果', result)

    done()
  })


  it('业务处理', function() {
    Log.log('业务处理', commonParams, params)
    result = '66666'
  });

  it('业务处理2', function() {
    Log.log('业务处理2', commonParams, params)

    result = '888888'
  })

  before('业务处理前调用', function(done) {
    commonParams = {
      username: 'jack'
    }

    Log.log('业务处理前调用')

    done()

  })

  beforeEach('每个业务处理前调用', function(done) {
    params = {
      nickname: '杰克'
    }

    Log.log('每个业务处理前调用')

    done()
  })

})