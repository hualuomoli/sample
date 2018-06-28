var express = require('express');
var users = require('../json/user.json')


var router = express.Router();

router.get('/', function(req, res) {
  console.log(req.query)
  res.json(users)
})

router.all('/find', function(req, res) {
  res.json(users)
})

router.post('/add', function(req, res) {
  console.log(req.body);
  res.json({
    content: req.body,
    success: true,
    message: '添加成功'
  })
})

module.exports = router;