SELECT o_custkey, SUM(q9_annot2) FROM lineitem
INNER JOIN orders ON lineitem.l_orderkey = orders.o_orderkey
GROUP BY o_custkey;