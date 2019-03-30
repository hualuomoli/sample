-- 返回值
local RESULT_FAIL_KEY_NOT_EXISTS = -2 -- KEY不存在
local RESULT_FAIL_KEY_NOT_ENOUGH = -1 -- 数量不满足扣减
local RESULT_FAIL_LOCK           =  0 -- 获取共享锁失败
local RESULT_SUCCESS             =  1 -- 成功

-- local jsonStr = '[{"key": "jack", "num": 20}, {"key": "pitter", "num": -10}]'

-- 参数和值
local _key_lock          = KEYS[1]   -- 分布式锁key
local _json_str          = ARGV[1]   -- 操作的数据个数
local _key_lock_value    = 1         -- 分布式锁的值
local _key_lock_expire   = 50        -- 分布式锁的有效时间

-- 1、获取商品共享锁
local success = redis.call('setnx', _key_lock, _key_lock_value)

-- 2、判断是否获取共享锁
if(success == 0) then
    return RESULT_FAIL_LOCK
end

-- 3、设置共享锁有效时间
redis.call('pexpire', _key_lock, _key_lock_expire)

local _json_parse_key = 'key'
local _json_parse_num = 'num'

-- 4、转换成json
local _index = 1
local _array = {}
local json = cjson.decode(_json_str)
for _,obj in ipairs(json) do
    local _obj = {}
    for key,value in pairs(obj) do
        if(key == _json_parse_key) then
            _obj.key = value;
        elseif(key == _json_parse_num) then
            _obj.num = value
        end
    end
    _array[_index] = _obj
    _index = _index + 1
end

-- 5、判断key是否都存在,数量是否满足扣减
for _,obj in ipairs(_array) do

    if(obj.num < 0) then
        local redis_value = redis.call('get', obj.key)
        if(not redis_value) then
            return RESULT_FAIL_KEY_NOT_EXISTS
        elseif(tonumber(redis_value) <= -1 * obj.num) then
            return RESULT_FAIL_KEY_NOT_ENOUGH
        end
    end
end

-- 6、执行加减
for _,obj in ipairs(_array) do
    redis.call('incrBy', obj.key, obj.num)
end

-- 7、删除锁
redis.call('del', _key_lock)

return RESULT_SUCCESS
