local key = KEYS[1]
local now = tonumber(ARGV[1])
local window = tonumber(ARGV[4])
local limit = tonumber(ARGV[3])
local cost = tonumber(ARGV[5])


local windowTimestamp=now-(now%1)

local windowKey=key .. ":" .. windowTimestamp

local current=tonumber(redis.call("GET",windowKey) or "0")

if current>=limit then
    return 0
else
    redis.call("INCR",windowKey)
    redis.call("EXPIRE",windowKey,2)
    return 1
end

