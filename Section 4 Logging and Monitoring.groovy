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
