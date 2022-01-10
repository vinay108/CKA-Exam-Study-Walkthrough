Cluster Architecture:

Kubernetes Master Componenets:

- Master node has a list of control plan componenets. 
ETCD: Stores cluster information and state, the ETCD is a key and value store. 

API Server: Responsible for all orchastration operations with the cluster. 
- Exposing Kube-API/Mgmt
- Monitors state of Cluster
- Gives work to worker nodes
- 
Controller Manager:
Node-controller = Takes care of nodes, onboarding new nodes to cluster and handling situation where nodes become unavailable and gets destroyed. 
Replication-Controller = Ensures rights ammounts of containers are running at all times in replication group. 

Kube Scheduler: Identfies the right node to plane the container based on container resource requirements, resource requirements, taints and tolerations etc. 


Worker nodes:
Kubelet:
Kube-Proxy:
Container Runtime:
