## Infra services

### Services
```
NAMES    IMAGE                                    PORTS
anime-1  ghcr.io/giggle-projects/anime-kr:0.0.1   0.0.0.0:18081->8080/tcp
anime-2  ghcr.io/giggle-projects/anime-kr:0.0.1   0.0.0.0:18082->8080/tcp
anime-3  ghcr.io/giggle-projects/anime-kr:0.0.1   0.0.0.0:18083->8080/tcp
```

### Rolling update with health check
```
./deploy.sh
```

### Deployment output
```
=== INFO :: Update anime-1 ===
Restarting anime-1 ... done
Health check with localhost:18081/actuator/health ...
Health check with localhost:18081/actuator/health ...
Service anime-1 is healthy!

=== INFO :: Update anime-2 ===
Restarting anime-2 ... done
Health check with localhost:18082/actuator/health ...
Health check with localhost:18082/actuator/health ...
Service anime-2 is healthy!

=== INFO :: Update anime-3 ===
Restarting anime-3 ... done
Health check with localhost:18083/actuator/health ...
Health check with localhost:18083/actuator/health ...
Service anime-3 is healthy!
```

### Deployment for backup server
```
chmod +x deploy.sh
./deploy-backup-server.sh
```
