 ps aux|grep 'hanfu-product-center'|grep -v grep | awk '{print $2}'|xargs kill -9
