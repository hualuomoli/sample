var request = require('request')
var Mock = require('mockjs')
var expect = require('expect')
var Log = require('./log')

var server = 'http://localhost:3000'

function getUrl(uri) {
  return server + uri
}

describe('user', function() {

  it('使用get方式请求', function(done) {

    request({
      url: getUrl('/user'),
      json: true
    }, function(err, res, data) {

      expect(err).toBeNull()
      expect(data.length).toEqual(2)
      done()
    })

  })

  it('使用post表单方式提交(默认header)', function(done) {

    var id = Mock.mock('@character("number")')
    var username = Mock.mock('@word(3, 5)')
    var nickname = Mock.mock('@cword(2, 8)')
    var remark = '使用post表单方式提交(默认header)';
    Log.log('[%s] id=%s, username=%s, nickname=%s', remark, id, username, nickname)

    request({
      url: getUrl('/user/add'),
      method: 'POST',
      form: {
        id: id,
        username: username,
        nickname: nickname,
        remark: remark
      }
    }, function(err, res, data) {
      data = JSON.parse(data)

      expect(err).toBeNull()
      expect(data.success).toEqual(true)
      expect(data.message).toEqual('添加成功')
      expect(data.content.id).toEqual(id)
      expect(data.content.username).toEqual(username)
      expect(data.content.nickname).toEqual(nickname)

      done()
    })
  })

  it('使用post表单方式提交(指定header)', function(done) {

    var id = Mock.mock('@character("number")')
    var username = Mock.mock('@word(3, 5)')
    var nickname = Mock.mock('@cword(2, 8)')
    var remark = '使用post表单方式提交(指定header)';
    Log.log('[%s] id=%s, username=%s, nickname=%s', remark, id, username, nickname)

    request({
      url: getUrl('/user/add'),
      method: 'POST',
      headers: {
        'Content-Type': 'applicaiton/x-www-form-urlencoded'
      },
      form: {
        id: id,
        username: username,
        nickname: nickname,
        remark: remark
      }
    }, function(err, res, data) {
      data = JSON.parse(data)

      expect(err).toBeNull()
      expect(data.success).toEqual(true)
      expect(data.message).toEqual('添加成功')
      expect(data.content.id).toEqual(id)
      expect(data.content.username).toEqual(username)
      expect(data.content.nickname).toEqual(nickname)

      done()
    })
  })

  it('使用post表单方式提交(默认header/form方法)', function(done) {

    var id = Mock.mock('@character("number")')
    var username = Mock.mock('@word(3, 5)')
    var nickname = Mock.mock('@cword(2, 8)')
    var remark = '使用post表单方式提交(默认header/form方法)';
    Log.log('[%s] id=%s, username=%s, nickname=%s', remark, id, username, nickname)

    request({
        url: getUrl('/user/add'),
        method: 'POST'
      }, function(err, res, data) {
        data = JSON.parse(data)

        expect(err).toBeNull()
        expect(data.success).toEqual(true)
        expect(data.message).toEqual('添加成功')
        expect(data.content.id).toEqual(id)
        expect(data.content.username).toEqual(username)
        expect(data.content.nickname).toEqual(nickname)

        done()
      })
      .form({
        id: id,
        username: username,
        nickname: nickname,
        remark: remark
      })
  })

  it('使用post表单方式提交(指定header/form方法)', function(done) {

    var id = Mock.mock('@character("number")')
    var username = Mock.mock('@word(3, 5)')
    var nickname = Mock.mock('@cword(2, 8)')
    var remark = '使用post表单方式提交(指定header/form方法)';
    Log.log('[%s] id=%s, username=%s, nickname=%s', remark, id, username, nickname)

    request({
        url: getUrl('/user/add'),
        method: 'POST',
        headers: {
          'Content-Type': 'applicaiton/x-www-form-urlencoded'
        }
      }, function(err, res, data) {
        data = JSON.parse(data)

        expect(err).toBeNull()
        expect(data.success).toEqual(true)
        expect(data.message).toEqual('添加成功')
        expect(data.content.id).toEqual(id)
        expect(data.content.username).toEqual(username)
        expect(data.content.nickname).toEqual(nickname)

        done()
      })
      .form({
        id: id,
        username: username,
        nickname: nickname,
        remark: remark
      })
  })

  it('使用payload方式提交', function(done) {

    var id = Mock.mock('@character("number")')
    var username = Mock.mock('@word(3, 5)')
    var nickname = Mock.mock('@cword(2, 8)')
    var remark = '使用payload方式提交';
    Log.log('[%s] id=%s, username=%s, nickname=%s', remark, id, username, nickname)

    var req = request({
      url: getUrl('/user/add'),
      method: 'POST',
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        id: id,
        username: username,
        nickname: nickname,
        remark: remark
      })
    }, function(err, res, data) {
      data = JSON.parse(data)

      expect(err).toBeNull()
      expect(data.success).toEqual(true)
      expect(data.message).toEqual('添加成功')
      expect(data.content.id).toEqual(id)
      expect(data.content.username).toEqual(username)
      expect(data.content.nickname).toEqual(nickname)

      done()
    })
  })

})