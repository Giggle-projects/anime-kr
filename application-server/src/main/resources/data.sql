# use animekr;
#
SELECT * FROM daily_count;

INSERT INTO daily_count (id, today_count, total_count, yesterday_count, last_recorded)
SELECT * FROM (SELECT 1, 0, 2, 3, NOW()) AS tmp
WHERE NOT EXISTS (
    SELECT id FROM daily_count WHERE id = 1
) LIMIT 1;
