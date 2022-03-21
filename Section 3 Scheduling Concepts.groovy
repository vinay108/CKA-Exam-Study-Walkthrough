Manually scheduling:
     
- Kube Scheduler, schedule pods to nodes
- every pod has a field called node name which is not set when you create pod menifest file, k8s adds this automatically.
- Kube schedule looks at all the pods which do not have the 'node name' property set.
- If a pod does not have a node name set in the property, it schedule pods to a node. 
- if there is no scheduler you would need to manually assign pods to node yourself
     - so in the pod manifest, you can simply add the node name field and assign the pod to the node. 
- You can only specify the node name in creation time and not once created. 
- kubernetes wont allow you to modify the node name property of the pod after creation 
- Instead you can create a binding object and send post request to pod api. 
- if there is no scheduler, the pods will continue to be in a pending state. 

  
Manually schedule the pod on node01.
Delete and recreate the POD if necessary.
---
apiVersion: v1
kind: Pod
metadata:
  name: nginx
spec:
  containers:
  -  image: nginx
     name: nginx
  nodeName: node01 ------------------Adding this allows you to manually specify the pod to a node, however do need to delete the pod and create file again.
    
Now schedule the same pod on the controlplane node.
Delete and recreate the POD if necessary.
  
---
apiVersion: v1
kind: Pod
metadata:
  name: nginx
spec:
  containers:
  -  image: nginx
     name: nginx
  nodeName: controlplane
~                                                                                                                                                               
~               
     
Labels and Selectors:
     
- Are used to identify where objects belong to
- for each object attach labels as per your needs i.e app / function
- specify condition to filer out objects
- In pod definition file, you can specify under metadata section called labels and add as many labels as you like. 
- once pod is created you can select pod with specific labels using this command kubectl get pods --selector app=App1
- For example to create a replicaset consisting of 3 different pods you can labels the pod definition and use label the pod definition - 
  and group the pods by adding the spec.
- Need to be aware that there are 2 sections with labels
     - the top part where it specifies the label, is the label of the replicaset, the labels under the template section is the labels of the pods. 

  apiVersion: v1 
  kind: ReplicationSet
  metadata: 
    name: myapp-rc
    labels:
        app: myapp
        type: front-end
    annotations:
         buildversion: 1.34 -------------------------------Annotations can provide input and information. 
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

- In order the replicaset to the pod, we configure the selector field to match the labels defined on the pod. so the matchlabels label need to match the label on the metadata. 
- Annotation can be used to describe or provide information 

1. We have deployed a number of PODs. They are labelled with tier, env and bu. How many PODs exist in the dev environment?
Use selectors to filter the output = kubectl get pods --selector env=dev


2. How many PODs are in the finance business unit (bu)? kubectl get pods --selector bu==finance
    
3. How many objects are in the prod environment including PODs, ReplicaSets and any other objects? kubectl get all --selector env=prod

4. Identify the POD which is part of the prod environment, the finance BU and of frontend tier? kubectl get pods --selector env=prod --selector bu=finance --selector tier=frontend 

5. A ReplicaSet definition file is given replicaset-definition-1.yaml. Try to create the replicaset. There is an issue with the file. Try to fix it.
Need to set the pod label to frontend

---
apiVersion: apps/v1
kind: ReplicaSet
metadata:
   name: replicaset-1
spec:
   replicas: 2
   selector:
      matchLabels:
        tier: frontend
   template:
     metadata:
       labels:
        tier: frontend
     spec:
       containers:
       - name: nginx
         image: nginx 




Taints and Tolerations:
  
are used to set restrictions on what pods can be sceduled on nodes. 
     
Taints: Prevents pods joining the node
     
Tolerations: attracts the pod to the node, so enforces that pod to that node. 
     
- if you have pods A B C D and Nodes 1 2 3
     - Placing a Taint to Node 1 called Taint =blue
     - None of the pods can tolerate a taint so they be placed on Node 1 
     - If we place toleration on Pod D to tolerate to go to Node 1 it works, the kube scheduler does this. #
     - Taints are set on Nodes and tolerations are set on Pods 

command:
    - Kubectl taint nodes node-name key=value:taint-effect
    - 3 types of options
        - NoSchedule = Pods not be scheduled on the node
        - PreferNoSchedule = the system will try to not schedule a pod to a node but this isnt guranteed
        - NoExecute = new pods will not be scheduled on the node and existing pods will be evivted if they do not tolerate the taint, - 
          may have been scheduled before taint was applied on the node.  
    - Example configuration:
           - kubectl taint nodes node1 app=blue:NoSchedule 

    - Toleration - Pods 
           - kubectl taint nodes node1 node1 app= blue:NoSchedule
---
apiVersion:
kind: Pod
metadata:
  name: nginx
spec:
  containers:
  -  image: nginx
     name: nginx

toleration
- key: "app"
  operator: "Equal"
  value: "blue"
  effect: "NoSchedule"

- If you have multiple nodes, and have taint and toleration on pod and taint on node, wheras if the other nodes dont have anything on them as in no taint or no toleration -
     on other pods, the pod with the pod with toleration doesnt even have to be selected to go to pod with the correct taint, if it happens to land on another node it can go there - 
     the taint chooses what it accepts and what it doesnt, it doesnt restrict pod joining other nodes with no restrictions.   
          
- Taint is set on the master node automatically to prevent any pods being set on the Masternode. 
          to see this taint run , kubectl describe node kubemaster | grep Taint


 - 3. Create a taint on node01 with key of spray, value of mortein and effect of NoSchedule = ubectl taint node node01 spray=mortein:NoSchedule
 - 4. Create a new pod with the nginx image and pod name as mosquito. = kubectl run mosquito --image=nginx
 - 10 Remove the taint on controlplane, which currently has the taint effect of NoSchedule = kubectl edit node controlplane remove taint
 - 7. Create another pod named bee with the nginx image, which has a toleration set to the taint mortein = 
      
---
apiVersion: v1
kind: Pod
metadata:
  name: bee
spec:
  containers:
  - image: nginx
    name: bee
  tolerations:
  - key: spray
    value: mortein
    effect: NoSchedule
    operator: Equal

- Taints can only be configured on a node and it prevents pods joining node
- toleration can only be configuered on a pod and it grants a policy allowing you to join a node 




