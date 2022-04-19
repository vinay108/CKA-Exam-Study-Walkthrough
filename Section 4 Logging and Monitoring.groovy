Monitoring K8s:
  - Node/Pod level metric
  - disk
  - cpu
  - Mem
  - Networking

- Open source solution avaiable as k8s does not come with one, have to use 3rd party ones such as:
  - Prometheus
  - Elastic Stack
  - DataDog 
  - Dynatrace
  - Heapster was used for monitoring however this was deprecated and now it is Metric Server
   
- Metric server, recieves metrics from each of the k8s nodes/pods and stores them in memory (in-memory) and does not store metrics on disk and cannot see historical 
  data. 

- In the kubelet it has something called a 'cAdvisor' which is used to retrieve metrics from pods exposing them via kubelet API to make them avaiable. 
- Kubectl top node
- kubeclt top pod

Pracice test 1 monitoring:
  
1. We have deployed a few PODs running workloads. Inspect them
   kubectl get pods --all-namespaces

2. Let us deploy metrics-server to monitor the PODs and Nodes. Pull the git repository for the deployment files.
   Run: git clone https://github.com/kodekloudhub/kubernetes-metrics-server.git
  
3. Deploy the metrics-server by creating all the components downloaded.
   Run the kubectl create -f . command from within the downloaded repository.
  
4. It takes a few minutes for the metrics server to start gathering data.
   Run the kubectl top node command and wait for a valid output.
     
5. Identify the node that consumes the most CPU. = ControlPlane   
   kubectl top node  
  
6. Identify the node that consumes the most Memory.
   = ControlPlane
     
7. Identify the POD that consumes the most Memory
   = Rabbit

8. Identify the POD that consumes the least CPU.
   = lion
     
     
     
     
