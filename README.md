# PortScanner
a port scan tool
## usage
java -jar PortScanner.jar ip1 ip2 portRange [connectTimeout] [executorPoolSize]  
connectTimeout(milliseconds): default 200, executorPoolSize: default 5000  
java -jar PortScanner.jar 192.168.1.1 192.168.2.255 80-1000 300 5000
