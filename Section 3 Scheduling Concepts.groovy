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


A ReplicaSet definition file is given replicaset-definition-1.yaml. Try to create the replicaset. There is an issue with the file. Try to fix it.
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
