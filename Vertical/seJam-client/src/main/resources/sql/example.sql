SELECT gender, MEDIAN(deposit)
FROM police INNER JOIN bank
    ON bank.id = police.id
GROUP BY gender;