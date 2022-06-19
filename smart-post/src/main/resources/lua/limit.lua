local key = KEYS[1]
-- 窗口大小
local time = tostring(ARGV[1])
-- 当前时间
local now = tostring(ARGV[2])
-- 窗口数据写入
redis.call('zadd',key,tostring(now),tostring(now))
-- 窗口数据删除
redis.call('zremrangebyscore',key,0,tostring(now - time * 1000))
-- 窗口数据统计
local count = redis.call('zcard',key)
-- 设置过期时间
redis.call('expire',key, time)
-- 返回统计数据
return count
