-- ========================== 处理结果 ==========================
local _dealing           = {}
_dealing.hasError        = false -- 是否有错误
_dealing.errorMessage    = {}    -- 错误提示信息


-- ========================== 全局参数 ==========================
local _global = {}

-- 日志
_global.logEnable = true

-- JSON数据
_global.datas    = {} -- 数据
_global.dataSize = 0  -- 数据长度

-- ========================== 变量定义 ==========================
local _redis_call_success = 1 -- redis call 执行成功返回值

-- ========================== 函数定义 ==========================
-- 日志
local function log(message) 
    if(_global.logEnable) then
        print(message)
    end
end

-- 抛出异常
local function errorResult(message) 
    return redis.error_reply(message)
end

-- 状态结果
local function statusResult(number, message) 
    return number .. "_" .. message
end

-- 加锁
local function lock(key, value, expire) 

    -- 获取共享锁
    local _success = redis.call('setnx', key, value)
    
    -- 判断是否加锁成功
    if(_success ~= _redis_call_success) then
        _dealing.errorMessage  = 'can not get distribute lock ' .. key
        return false
    end
    
    -- 设置共享锁有效时间,单位为毫秒
    _success = redis.call('pexpire', key, expire)
    if(_success ~= _redis_call_success) then
        _dealing.hasError      = true
        _dealing.errorMessage  = 'can not expire lock ' .. key
        return false
    end
    
    return true
    -- end
end


-- 解锁
local function unlock(key, value)
    
    -- 获取当前锁的key对应的值
    local _value = redis.call('get', key)
    
    -- 未获取到(1: 未设置, 2:已超时)
    if(not _value) then
        _dealing.hasError      = true
        _dealing.errorMessage  = "can not lock key " .. key .. " or key has over time"
        return false
    end

    -- 锁定的值与当前值不同
    if(_value ~= value) then
        _dealing.hasError      = true
        _dealing.errorMessage  = "redis lock value " .. _value .. " not match " .. value
        return false
    end

    -- delete key
    local _success = redis.call('del', key)
    if(_success ~= _redis_call_success) then
        _dealing.hasError      = true
        _dealing.errorMessage  = "can not unlock key " .. key
        return false
    end
    
    return true
    -- end
end

-- JSON转换 
-- 转换json数据为 {"key": "操作的键", "number": 操作的值, "operator": 增加还是减少}
local function tranfer(jsonStr, name_key, name_number, name_operator, value_increment, value_decrement) 
    -- parse to json
    local json = cjson.decode(jsonStr)

    for _,obj in ipairs(json) do
        local _obj = {}
        for key,value in pairs(obj) do
            if(key == name_key) then
                _obj.key = value;
            elseif(key == name_number) then
                _obj.number = value
            elseif(key == name_operator) then
                _obj.operator = value
            end
        end
        
        -- 判断是否已经获取 key, number, operator
        if(not _obj.key) then
            _dealing.hasError      = true
            _dealing.errorMessage  = "json con not contain " .. name_key
            return false
        end
        if(not _obj.number) then
            _dealing.hasError      = true
            _dealing.errorMessage  = "json con not contain " .. name_number
            return false
        end
        if(not _obj.operator) then
            _dealing.hasError      = true
            _dealing.errorMessage  = "json con not contain " .. name_operator
            return false
        end
        
        -- 验证操作符
        if(_obj.operator ~= value_increment and _obj.operator ~= value_decrement) then
            _dealing.hasError      = true
            _dealing.errorMessage  = "invalid operator " .. _obj.operator .. ". current support increment with " .. value_increment .. ", decrement with " .. value_decrement
            return false
        end
        
        -- 设置到全局
        _global.dataSize = _global.dataSize + 1
        _global.datas[_global.dataSize] = _obj
    end

    return true
    -- end
end

-- 验证是否满足扣减
local function check(json, value_decrement) 
    for _,obj in ipairs(json) do
        if(obj.operator == value_decrement) then
            local _value = redis.call('get', obj.key)
            if(not _value) then
                -- 不存在
                _dealing.errorMessage = 'can not found key ' .. obj.key
                return false
            elseif(tonumber(_value) < obj.number) then
                -- 不满足扣减
                _dealing.errorMessage = 'value not enough for ' .. obj.key .. '. cuurent is ' .. _value .. ', need ' .. obj.number
                return false
            end
        end
    end
    
    return true
    -- end
end

-- 执行
local function exec(json, value_increment, value_decrement) 
    for _,obj in ipairs(json) do
        if(obj.operator == value_increment) then
            redis.call('incrBy', obj.key, obj.number)
        elseif(obj.operator == value_decrement) then
            redis.call('decrBy', obj.key, obj.number)
        end
    end
end

-- redis-cli --eval test.lua _lock_test , 1 1000 id num ops incr decr '[{"id": "1234", "num": 20, "ops": "incr"}, {"id": "5678", "num": 10, "ops": "decr"}]'

local param_lock_key            = KEYS[1] -- 锁KEY
local param_lock_value          = ARGV[1] -- 锁值
local param_lock_expire         = ARGV[2] -- 锁有效时间,单位毫秒
local param_json_key            = ARGV[3] -- JSON的key名称
local param_json_number         = ARGV[4] -- JSON的number名称
local param_json_operator       = ARGV[5] -- JSON的operator名称
local param_operator_increment  = ARGV[6] -- 增加的操作值
local param_operator_decrement  = ARGV[7] -- 减少的操作值
local param_json_data           = ARGV[8] -- 需要操作的JSON数据

-- 1、转换请求参数
if(not tranfer(param_json_data, param_json_key, param_json_number, param_json_operator, param_operator_increment, param_operator_decrement)) then
    if(_dealing.hasError) then
        return errorResult(_dealing.errorMessage)
    else
        return statusResult(0, _dealing.errorMessage)
    end
end

-- 锁定
if(not lock(param_lock_key, param_lock_value, param_lock_expire)) then
    if(_dealing.hasError) then
        return errorResult(_dealing.errorMessage)
    else
        return statusResult(0, _dealing.errorMessage)
    end
end


-- 验证是否满足扣减, 如果满足扣减执行扣减
local checked = check(_global.datas, param_operator_decrement)
if(checked) then
    exec(_global.datas, param_operator_increment, param_operator_decrement)
end

-- 释放锁
if(not unlock(param_lock_key, param_lock_value)) then
    if(_dealing.hasError) then
        return errorResult(_dealing.errorMessage)
    else
        return statusResult(0, _dealing.errorMessage)
    end
end

if(checked) then
    return statusResult(1, "deal success.")
else
    return statusResult(0, _dealing.errorMessage)
end
