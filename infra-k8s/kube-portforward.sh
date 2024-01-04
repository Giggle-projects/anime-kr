# ps -ef | grep "kubectl port"

# kong ingress proxy
nohup kubectl port-forward -n kong service/kong-gateway-proxy 52080:80 --address 0.0.0.0 &
nohup kubectl port-forward -n animekr service/animekr-svc 18080:8080 --address 0.0.0.0 &
#nohup kubectl port-forward -n animekr animekr-api-844bfbdd84-pdr54 18080:8080 --address 0.0.0.0 &
nohup kubectl port-forward -n test test-nginx-deploy-658dc494b9-97xq7 18080:8080 --address 0.0.0.0 &