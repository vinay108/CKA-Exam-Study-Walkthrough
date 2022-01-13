Cluster Architecture:

Kubernetes Master Componenets:

- Master node has a list of control plan componenets. 
ETCD: Stores cluster information and state, the ETCD is a key and value store. 

API Server: Responsible for all orchastration operations with the cluster. 
- Exposing Kube-API/Mgmt
- Monitors state of Cluster
- Gives work to worker nodes

Controller Manager:
Node-controller = Takes care of nodes, onboarding new nodes to cluster and handling situation where nodes become unavailable and gets destroyed. 

Replication-Controller = Ensures rights ammounts of containers are running at all times in replication group. 

Kube Scheduler: Identfies the right node to plane the container based on container resource requirements, resource requirements, taints and tolerations etc. 


Worker nodes:

Kubelet: Agents run on each node of the cluser, responsible for liasing with the master node (api-server), sending reports back to master on status of cluster etc. The API -Server Periodically fetches status reports from the kubelet.

Kube-Proxy: Ensures network rules are in place, rules are in place to reach services within the cluster. 

Container Runtime: Software which runs the conainerised application. 

ETCD For Beginners:

- ETCD is a Key Value store
- Used to store and restore 

ETCD In Kubernetes:

List the following componenets
- Nodes
- Pods
- Configs
- Secrets
- Accounts
- Roles
- Bindings
- Others

- All information is updated on the ETCD Cluster

- Uses port 2379

- ETCD in HA Enviroment, can have multiple ETCD in a Cluster. 


Kube-API Server:

- Primary mgmt component in Kubernetes

1. When you run a Kubectl command it reaches the Kube-API Server. The Kube-API Server first validates the request.
2. The Kube-API retrieves the data from the ETCD Cluster and responds back. 
3. Or you can simply run a post request. 

The Kube-Scheduler continously monitors the API Server and if a new pod with no node assigned to it, the schedulre identifies a node to put the pod on-to
and communicats that back to the API Server. tje API Server updates the information in the ETCD Cluster. The API Server then passes information to the Kubelet to create the pod on the new node, the kublet instructs the container runtime to deploy the image, The Kubelet then updates the API-Server and then the ETCD Is updates the API-Server. 

Kube-Api Server: Does the following,

- Authenticates users
- Validates request
- Retrieves data
- Updates ETCD
- Scheduler
- Kubelet

Kube-Controller Manager:

Node-Controller:

- Checks status on nodes every 5 sec
- waits 40 sec before marking down as unreachable
- POD Eviction 5 min


Kube-Scheduler:

- Right containers ends in right node
- Kube-Scheduler looks at resource contraints etc to determine which node the pod goes to.
- It will filter out the nodes which are not relevent to the specific node
- It then calculates if the nodes left are tie, it will rank them based on up-to 10, it will look how resources will be free after pod is placed on it. 


Kubelet:

- Load / Unload containers 
- Captain of the ship (Worker nodes)
- Send back report to API-Server 
- Tells Container run-time engine to docker to pull the required image


Kube-Proxy:

- By default every pod in a cluster can reach another pod 
- Pod can reahc another pod based on its IP by networking
- creating a service to expose the service 
- Service has an ip assigned 
- when a pod tries to reach the service it forward traffic to backend pod through the pod
- Kube-Proxy is a process that runs on each node, looks for new services 
- creates appropiate rules when new services come in 
- creates ip tables rules on each node to forward traffic to the new service


POD Recap:

- Pods are the smallest objects which run on nodes, single instance of an application
- Can have multiple containers in a pod however not really good practice
- can have helped containers in the same pod or part of it. 
- If destroying container will also destroy same helper container
