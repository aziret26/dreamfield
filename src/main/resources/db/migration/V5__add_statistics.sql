CREATE VIEW player_statistics AS
SELECT player_id                                    as id,
       SUM(attempts_success)                        as attempts_success,
       SUM(attempts_failed)                         as attempts_failed,
       SUM(attempts_success) + SUM(attempts_failed) as attempts_total,
       count(*)                                     as unique_words,
       SUM(scores_achieved)                         as total_score,
       player_id
FROM player_word_statistics
GROUP BY player_id;

CREATE VIEW word_statistics AS
SELECT word_id                                      as id,
       SUM(attempts_success)                        as attempts_success,
       SUM(attempts_failed)                         as attempts_failed,
       SUM(attempts_success) + SUM(attempts_failed) as attempts_total,
       count(*)                                     as unique_players,
       MAX(scores_achieved)                         as max_score_achieved,
       word_id
FROM player_word_statistics
GROUP BY word_id;

UPDATE words
SET value='амеба'
WHERE value = 'амёба'
  AND description = 'одноклеточное'