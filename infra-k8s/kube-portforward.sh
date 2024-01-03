# ps -ef | grep "kubectl port"

# kong ingress proxy
nohup kubectl port-forward -n kong service/kong-gateway-proxy 52080:80 --address 0.0.0.0 &