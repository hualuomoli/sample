var request = require('request')
var Mock = require('mockjs')
var expect = require('expect')
var Log = require('./log')

// mockjs随机数使用
// http://mockjs.com/examples.html

describe('mockjs使用', function() {

  it('创建json', function() {
    var id = Mock.mock('@character("number")')
    var username = Mock.mock('@word(3, 5)')
    var nickname = Mock.mock('@cword(2, 8)')
    Log.log('id=%s, username=%s, nickname=%s', id, username, nickname)
  })

  it('中文文章', function() {
    var cparagraph = Mock.mock('@cparagraph(20, 50)')
    Log.log('cparagraph=%s', cparagraph)
  })

})