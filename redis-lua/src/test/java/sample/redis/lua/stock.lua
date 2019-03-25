-- 返回值
local RESULT_LOCK_FAIL      = -2 -- 获取锁失败
local RESULT_KEY_NOT_EXIST  = -1 -- 商品未维护
local RESULT_KEY_NOT_ENOUGH =  0 -- 库存为空
local RESULT_SUCCESS        =  1 -- 成功

-- 参数和值
local _key_product          = KEYS[1]
local _key_lock_product     = '_lock_'.._key_product  -- 库存的锁
local _value_lock_product   = 1                -- 库存的锁
local _expire_lock_product  = 50               -- 锁的有效时间

-- 1、获取商品共享锁
local success = redis.call('setnx', _key_lock_product, _value_lock_product)

-- 2、判断是否获取共享锁
if(success == 0) then
    return RESULT_LOCK_FAIL
end

-- 3、设置共享锁有效时间
redis.call('pexpire', _key_lock_product, _expire_lock_product)

-- 4、获取商品库存
local stock = redis.call('get', _key_product)

-- 5、库存未维护
if(not stock) then
    redis.call('del', _key_lock_product)
    return RESULT_KEY_NOT_EXIST
end

-- 6、库存为空
if(tonumber(stock) <= 0) then
    redis.call('del', _key_lock_product)
    return RESULT_KEY_NOT_ENOUGH
end

-- 7、扣减库存
redis.call('decr', _key_product)

-- 8、删除共享锁
redis.call('del', _key_lock_product)

-- 9、成功
return RESULT_SUCCESS