Cluster Architecture:

Kubernetes Master:
ETCD: Stores cluster information and state
API Server: Responsible for all orchastration operations with the cluster. 
- Exposing Kube-API/Mgmt
- Monitors state of Cluster
- Gives work to worer nodes
- 
Controller Manager:
Kube Scheduler:


Worker nodes:
Kubelet:
Kube-Proxy:
Container Runtime:
