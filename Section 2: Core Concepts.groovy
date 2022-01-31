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


Pods with YAML:

Pod yml files always contain the following field, this is mandatory:

- apiVersion: version of k8s api used to create the object, must use the correct version for now using v1
- Kind: type of object for example pod, replica set, service etc
- Metadata: data about the object, names, labels etc.
- Spec: depending on the object additional information abouyt the object, spec is a dictionary 

Practice test:

How to check default pods:
kubectl get pods -n default

how to create new nginx pod:
kubectl run nginx --image=nginx

How to check which image is running on pod?
kubectl desribe pods -n default xxxx 

which node is the pods placed on?
this will tell you via the describe pods commands, you than type in kubectl get nodes 

how many containers are running inside that pod?
describe command again however there maybe an easier command?

What images are used in the new webapp pod?
again describe command

What is the state of the container agentx in the pod webapp?
again describe command

Why do you think the container agentx in pod webapp is in error?
in describe by checking events 

What does the READY column in the output of the kubectl get pods command indicate?
Answer: Running Containers in POD/Total Containers in POD
  
Delete the webapp Pod.
kubectl delete pods -n default webapp

Create a new pod with the name redis and with the image redis123
1.  kubectl run redis --image=redis123 --dry-run=client -o yaml > redis-definition.yaml
2.  kubectl create -f redis-definition.yaml
3.  kubectl get pods

apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: redis
  name: redis
spec:
  containers:
  - image: redis
    name: redis
    resources: {}
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}
  
Replicasets:
  
Replication controller:
  
- If you have a single pod which crashed, to prevent users from loosing access their application we can create a replica set of the pod.
- Runs multiple instances of the pod
- ensures specifies ammount of pods are running at time
- to create multiple pods to share load it allows this. 
- if numbers of users increase you can increase pods, deploy additional pods on other nodes. 
- replications controller can span on different nodes 
- replication controller vs replica set, replication controller is the old technology and it is being replaced by replicaset. 
- When creating a replicaset.yml, this is the layout:
- Under the spec field, all the pods yml config in which you are tring to replicacate goes in there. 
 
  apiVersion: v1 
  kind: ReplicationController
  metadata: 
    name: myapp-rc
    labels:
        app: myapp
        type: front-end
spec:
  template:
    metadata:
      name: myapp-pod
      labels:
          app: myapp
          type: front-end
  spec:
    containers:
    - name: nginx-containers
      image: nginx

replcas: 3
  
- kubectl create -f rc-definition.yml 
- kubectl get replicationcontroller
- kubectl get pods 

Replica-sets:
  
  apiVersion: apps/v1 
  kind: ReplicationController
  metadata: 
    name: myapp-rc
    labels:
        app: myapp
        type: front-end
spec:
  template:
    metadata:
      name: myapp-pod
      labels:
          app: myapp
          type: front-end
  spec:
    containers:
    - name: nginx-containers
      image: nginx

replcas: 3
selector: 
   matchLabels:
      type: front-end
 #specifies what pods come under it, can also manage pods which were not created part of the replication configuraiton for example, pods created before -
 # the creation of replica set matched label for selector, main differences between replication controller. 
   
Labels and Selectors:
  
- for example If we deploy 3 instances of our front end app as 3 pods, we like to create replication-controllers/replicasets
- replicset monitor pods and if they fail, it will deploy new ones
- it knows how to monitor pods via labelling as labels are referenced for the pods. 
- can use the kubectl replace -f replicaset-definition.yml command to update what was done. 
- or can also run this commanmd as imperitive
           - kubectl scale --replica=6 -f replicaset-definition.yml

  Replica-set practice test:
    
 -How many PODs exist on the system? 0
- How many ReplicaSets exist on the system? kubectl get replicasets -n default 0
- How about now? How many ReplicaSets do you see? kubectl get replicasets -n default 1
- How many PODs are DESIRED in the new-replica-set? kubectl get replicasets -n default 4
- What is the image used to create the pods in the new-replica-set? kubectl describe  replicasets -n default new-replica-set Image: busybox777
- How many PODs are READY in the new-replica-set? 0
- Why do you think the PODs are not ready? kubectl describe  pods -n default  new-replica-set-jf7jv = kubectl describe  pods -n default  new-replica-set-jf7jv Image does not exist
- Delete any one of the 4 PODs. = kubectl delete  pods -n default  new-replica-set-jf7jv
- How many PODs exist now? = kubectl get pods -n default still 4 as replicaset
- Why are there still 4 PODs, even after you deleted one? = ReplicaSet ensures that desired number of PODs always run
- Create a ReplicaSet using the replicaset-definition-1.yaml file located at /root/. There is an issue with the file, so try to fix it. Missing apps/v1, need to do this also kubectl create -f replicaset-definition-1.yaml
- Fix the issue in the replicaset-definition-2.yaml file and create a ReplicaSet using it. the matchlabel should match with labels under spec
- Delete the two newly created ReplicaSets - replicaset-1 and replicaset-2 = kubectl delete replicasets -n default replicaset-1
- Fix the original replica set new-replica-set to use the correct busybox image = kubectl edit replicaset -n default new-replica-set followed by deleting the pods and allowing it to recreate.
- Scale the ReplicaSet to 5 PODs. Use kubectl scale command or edit the replicaset using kubectl edit replicaset. kubectl edit replicaset -n default new-replica-set, delete pods and let it start recreate agaib

  

 



