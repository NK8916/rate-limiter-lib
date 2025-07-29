local key = KEYS[1]
local now = tonumber(ARGV[1])
local refill_rate = tonumber(ARGV[2])
local capacity = tonumber(ARGV[3])
local cost = tonumber(ARGV[5])

local bucket=redis.call("HMGET",key,"tokens","timestamp")
local tokens=tonumber(bucket[1]) or capacity
local last_ts=tonumber(bucket[2]) or now

local delta=math.max(0,now-last_ts)
tokens=math.min(capacity,tokens+delta*refill_rate)

if tokens>=cost then
    tokens=tokens-cost
    redis.call("HSET",key,"tokens",tokens,"timestamp",now)
    redis.call("EXPIRE",key,60)
    return 1
else
    redis.call("HSET",key,"tokens",tokens,"timestamp",now)
    redis.call("EXPIRE",key,60)
    return 0
end
