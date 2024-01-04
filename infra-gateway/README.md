## Gateway

### Load balancing
```
Balancing method : least-connected
Default route : prod server (anime-1, anime-2, anime-3)
Backup : cloud server
```
- Balancing to prod server with the least number of active connections.
- Route to backup server when all the prod servers are marked as unavailable.

### Health check
```
Passive health checks
Fail timeout : 30
Max fails : 1
```
- NGINX fails to send a request to a server or does not receive a response from it 1 times in 30 seconds, it marks the server as unavailable for 30 seconds.

### Rate limit
```
Rate limit by user ip
Overflow response status : 429(Too Many Requests)
Rate=5r/s; with burst 5
Bucket size : 10mb
Admin, Dev ips are excluded
```
