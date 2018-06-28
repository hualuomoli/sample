var request = require('request')
var Mock = require('mockjs')
var expect = require('expect')
var Log = require('./log')

// mocha 默认支持promise,并且可以在then方法验证业务结果

var server = 'http://localhost:3000'

function getUrl(uri) {
  return server + uri
}

describe('user Promise', function() {

  it('使用get方式请求', function() {

    new Promise(function(resolve, reject) {
        request({
          url: getUrl('/user?id=1'),
          json: true
        }, function(err, res, data) {
          expect(err).toBeNull()
          resolve(data)
        })
      })
      .then(function(data) {
        expect(data.length).toEqual(2)
      })
  })

  it('使用post表单方式提交(默认header/form方法)', function() {

    // 组装数据
    new Promise(function(resolve, reject) {
        var id = Mock.mock('@character("number")')
        var username = Mock.mock('@word(3, 5)')
        var nickname = Mock.mock('@cword(2, 8)')
        var remark = '使用post表单方式提交(默认header/form方法)';

        resolve({
          id: id,
          username: username,
          nickname: nickname,
          remark: remark
        })
      })
      // 执行HTTP请求
      .then(function(params) {
        return new Promise(function(resolve, reject) {
          request({
              url: getUrl('/user/add'),
              method: 'POST'
            }, function(err, res, data) {
              expect(err).toBeNull()
              data = JSON.parse(data)
              resolve({
                data: data,
                params: params
              })
            })
            .form(params)
        })
      })
      // 验证执行结果
      .then(function(result) {
        var data = result.data
        var params = result.params

        expect(data.success).toEqual(true)
        expect(data.message).toEqual('添加成功')
        expect(data.content.id).toEqual(params.id)
        expect(data.content.username).toEqual(params.username)
        expect(data.content.nickname).toEqual(params.nickname)

        return result
      })
  })

  it('使用payload方式提交', function() {

    // 组装数据
    new Promise(function(resolve, reject) {
        var id = Mock.mock('@character("number")')
        var username = Mock.mock('@word(3, 5)')
        var nickname = Mock.mock('@cword(2, 8)')
        var remark = '使用payload方式提交';

        resolve({
          id: id,
          username: username,
          nickname: nickname,
          remark: remark
        })
      })
      // 发起HTTP请求
      .then(function(params) {
        return new Promise(function(resolve, reject) {
          request({
            url: getUrl('/user/add'),
            method: 'POST',
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify(params)
          }, function(err, res, data) {
            expect(err).toBeNull()

            data = JSON.parse(data)
            resolve({
              data: data,
              params: params
            })
          })
        })
      })
      // 验证处理结果
      .then(function(result) {
        var data = result.data
        var params = result.params

        expect(data.success).toEqual(true)
        expect(data.message).toEqual('添加成功')
        expect(data.content.id).toEqual(params.id)
        expect(data.content.username).toEqual(params.username)
        expect(data.content.nickname).toEqual(params.nickname)
      })
  })

})