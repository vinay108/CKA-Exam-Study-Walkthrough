Cluster Architecture:

Kubernetes Master:
- Master node has a list of control plan componenets. 
ETCD: Stores cluster information and state, the ETCD is a key and value store. 

API Server: Responsible for all orchastration operations with the cluster. 
- Exposing Kube-API/Mgmt
- Monitors state of Cluster
- Gives work to worker nodes
- 
Controller Manager:
Kube Scheduler:


Worker nodes:
Kubelet:
Kube-Proxy:
Container Runtime:
