local key = KEYS[1]
local now = tonumber(ARGV[1])
local window = tonumber(ARGV[4])
local limit = tonumber(ARGV[3])
local cost = tonumber(ARGV[5])

-- remove timestamps older than (now - window)
redis.call("LTRIM", key, 0, limit - 1) -- optional: keep size trimmed
local entries = redis.call("LRANGE", key, 0, -1)

local valid_entries = {}
for i = 1, #entries do
    local ts = tonumber(entries[i])
    if ts > now - window then
        table.insert(valid_entries, ts)
    end
end

if #valid_entries<limit then
    redis.call("LPUSH",key,now)
    redis.call("EXPIRE",key,math.ceil(window/1000))
    return 1
else
    return 0
end

