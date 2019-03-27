-- 返回值
local RESULT_FAIL_KEY_NOT_EXISTS = -2 -- KEY不存在
local RESULT_FAIL_KEY_NOT_ENOUGH = -1 -- 数量不满足扣减
local RESULT_FAIL_LOCK           =  0 -- 获取共享锁失败
local RESULT_SUCCESS             =  1 -- 成功

-- 参数和值
local _key_lock          = KEYS[1]   -- 分布式锁key
local _data_len          = ARGV[1]   -- 操作的数据个数
local _key_lock_value    = 1         -- 分布式锁的值
local _key_lock_expire   = 50        -- 分布式锁的有效时间
local _key_offset         = 1        -- key的偏移量
local _value_offset       = 1        -- value的偏移量

-- 1、获取商品共享锁
local success = redis.call('setnx', _key_lock, _key_lock_value)

-- 2、判断是否获取共享锁
if(success == 0) then
    return RESULT_FAIL_LOCK
end

-- 3、设置共享锁有效时间
redis.call('pexpire', _key_lock, _key_lock_expire)

-- 4、判断key是否都存在,数量是否满足扣减
for i=1,_data_len,1 do
    local key = KEYS[i + _key_offset]
    local value = ARGV[i + _value_offset]

    if(tonumber(value) < 0) then
        local redis_value = redis.call('get', KEYS[i + _key_offset])
        if(not redis_value) then
            return RESULT_FAIL_KEY_NOT_EXISTS
        elseif(tonumber(redis_value) <= -1 * tonumber(value)) then
            return RESULT_FAIL_KEY_NOT_ENOUGH
        end
    end
end

-- 5、执行加减
for i=1,_data_len,1 do
    local key = KEYS[i + _key_offset]
    local value = ARGV[i + _value_offset]

    redis.call('incrBy', key, value)
end

-- 6、删除锁
redis.call('del', _key_lock)

return RESULT_SUCCESS
