select sum(l_quantity)
from lineitem
group by l_orderkey;