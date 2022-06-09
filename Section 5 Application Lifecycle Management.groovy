Rolling updates and Rollbacks:
  - When you first configure a deployment it triggers a rollout
  - creates new deployment revision for example revision 1 
  - when the new application is updated a new deployment or revision is created named revision 2 
  - by having this, it allows you roll back if required. 
  - kubectl rollout status deployment/myapp-deployment (tells you rollout/history)
    
2 types of deployment strategies:

- for example if you have 5 replicas of your applications deployed
    - 1 way to upgrade these is too destroy them and re-create them, problem with this is. the application will be down and application will be inaccessible. 
      not best practice. 
  
    - second strategy and preffered, is to not destroy the application at once, take down older version and bring up new version 1 by 1. 
      
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
  labels:
    app: nginx
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.14.2
        ports:
        - containerPort: 80

- when a new deplyment is created to create say 5 replicas it first creates 5 replicas creating 5 pods 
  - k8s deploymenmt object creates new replicaset under the hood and starts creating the containers there. 
  - at the same time starts taking down the old pods 1 by 1 upon rolling update. 
  - when you upgrade your application and realise something isnt right i.e something is wrong with new version of build for example ---
    you can roll back your update and rollback to previous revision destroying new pods and bringing old ones back. 
    
   commands:
    
   create ---------- kubectl create -f deployment-definition.yml
   get    ---------- kubectl get deployments
   update ---------- Kubectl apply -f deployment-definition.yml
                     kubectl set image deployment/my-app-deployment nginx=nginx:1.9.1
   status ---------- kubectl rollout status deployment/myapp-deployment
                     kubectl rollout history deployment/myapp-deployment
   rollback -------- kubectl rollout undo deployment/myapp-deploy
 
- Container only run if process runs, if the process stops so does the container. 
  
  
Practice test - Rolling updates and Rollbacks:

3. Run the script named curl-test.sh to send multiple requests to test the web application. Take a note of the output.
   Execute the script at /root/curl-test.sh. = Hello, Application Version: v1 ; Color: blue OK
 
4. Inspect the deployment and identify the number of PODs deployed by it = 4

5. What container image is used to deploy the applications? = kodekloud/webapp-color:v1

6. Inspect the deployment and identify the current strategy = RollingUpdate
  
7. If you were to upgrade the application now what would happen? =  Pods are upgraded few at a time

8. Let us try that. Upgrade the application by setting the image on the deployment to kodekloud/webapp-color:v2
   Do not delete and re-create the deployment. Only set the new image name for the existing deployment.
  
apiVersion: apps/v1
kind: Deployment
metadata:
  annotations:
    deployment.kubernetes.io/revision: "1"
  creationTimestamp: "2022-05-23T20:07:08Z"
  generation: 1
  name: frontend
  namespace: default
  resourceVersion: "856"
  uid: f6fbe6bf-7ef7-4cb1-8f86-ddf35dd6672b
spec:
  minReadySeconds: 20
  progressDeadlineSeconds: 600
  replicas: 4
  revisionHistoryLimit: 10
  selector:
    matchLabels:
      name: webapp
  strategy:
    rollingUpdate:
      maxSurge: 25%
      maxUnavailable: 25%
    type: RollingUpdate
  template:
    metadata:
      creationTimestamp: null
      labels:
        name: webapp
    spec:
      containers:
      - image: kodekloud/webapp-color:v2 <<<<<-------------------------------------------------Here needs to be updated
        imagePullPolicy: IfNotPresent
        name: simple-webapp
 
10. Up to how many PODs can be down for upgrade at a time
    Consider the current strategy settings and number of PODs - 4 
  strategy:
    rollingUpdate:
      maxSurge: 25%
      maxUnavailable: 25%
    type: RollingUpdate
    = Since it says 25% so this equals 1 pod is allowed to be down at the time of upgrade. 

11. Change the deployment strategy to Recreate
    Delete and re-create the deployment if necessary. Only update the strategy type for the existing deployment.
    = Run the command kubectl edit deployment frontend and modify the required field. Make sure to delete the properties of rollingUpdate as well, set at strategy.rollingUpdate.
    
12. Upgrade the application by setting the image on the deployment to kodekloud/webapp-color:v3
    Do not delete and re-create the deployment. Only set the new image name for the existing deployment.
    = Run the command: kubectl edit deployment frontend and modify the required field
  
Commands and arguments:
      
docker run --name ubuntu-sleeper ubuntu-sleeper
docker run --name ubuntu-sleeper ubuntu-sleeper 10 

FROM Ubuntu
ENTRYPOINT ["sleep"] (This relates to Command)
CMD ["5"] (This relates to Args)

apiVersion:v1
kind: Pod
metadata: 
 namee: ubuntu-sleeper-pod
spec:
   containers: 
      - name: ubuntu-sleeper
        image: ubuntu-sleeper
        command:["sleep2.0"]
        args: ["10"]

Practice test commands and arguments:
  
1. How many PODs exist on the system? = 1
2. What is the command used to run the pod ubuntu-sleeper? = Run the command kubectl describe pod and look for command option = sleep 4800
3. Create a pod with the ubuntu image to run a container to sleep for 5000 seconds. Modify the file ubuntu-sleeper-2.yaml.
   Note: Only make the necessary changes. Do not modify the name. = kubectl run ubuntu-sleeper-2 --image=ubuntu --dry-run=client -o yaml > ubuntu-sleeper-2.yaml

---
apiVersion: v1 
kind: Pod 
metadata:
  name: ubuntu-sleeper-2 
spec:
  containers:
  - name: ubuntu
    image: ubuntu
    command: <<<<<<<<<<------------------------------These commands below
      - "sleep"
      - "5000"

4. Create a pod using the file named ubuntu-sleeper-3.yaml. There is something wrong with it. Try to fix it!
   Note: Only make the necessary changes. Do not modify the name.

apiVersion: v1
kind: Pod 
metadata:
  name: ubuntu-sleeper-3
spec:
  containers:
  - name: ubuntu
    image: ubuntu
    command:
      - "sleep"
      - "1200" <<<<<<-------- This was without approphe
  
  
5. Update pod ubuntu-sleeper-3 to sleep for 2000 seconds.
   Note: Only make the necessary changes. Do not modify the name of the pod. Delete and recreate the pod if necessary.  
   = update command above to 2000
  
6. Inspect the file Dockerfile given at /root/webapp-color directory. What command is run at container startup? 
   = python.app.py
  
7. Inspect the file Dockerfile2 given at /root/webapp-color directory. What command is run at container startup?
   = python.app.py --color-red

8. Inspect the two files under directory webapp-color-2. What command is run at container startup?
   Assume the image was created from the Dockerfile in this folder. = The ENTRYPOINT in the Dockerfile is overridden by the command in the pod definition
   = - color green

9. Inspect the two files under directory webapp-color-3. What command is run at container startup?
   Assume the image was created from the Dockerfile in this folder.
   =  python app.py --color pink

10. Create a pod with the given specifications. By default it displays a blue background. Set the given command line arguments to change it to green

---
apiVersion: v1 
kind: Pod 
metadata:
  name: webapp-green
  labels:
      name: webapp-green 
spec:
  containers:
  - name: simple-webapp
    image: kodekloud/webapp-color
    args: ["--color", "green"]


Enviroment variable in applications:
  
To set up an enviroment variable. set up a env property (array)

---
apiVersion: v1 
kind: Pod 
metadata:
  name: webapp-green
  labels:
      name: webapp-green 
spec:
  containers:
  - name: simple-webapp
    image: kodekloud/webapp-color
    
    env: 
      - name: APP_COLOR
        value: Pink

(There are other ways in setting up enviroment variables such as config maps and secrets etc).
  
ConfigMap:

---
apiVersion: v1 
kind: Pod 
metadata:
  name: webapp-green
  labels:
      name: webapp-green 
spec:
  containers:
  - name: simple-webapp
    image: kodekloud/webapp-color
    
    env: 
      - name: APP_COLOR
        valueFrom:
            configMapKeyRef:
          
Secrets: 
          
---
apiVersion: v1 
kind: Pod 
metadata:
  name: webapp-green
  labels:
      name: webapp-green 
spec:
  containers:
  - name: simple-webapp
    image: kodekloud/webapp-color
    
    env: 
      - name: APP_COLOR
        valueFrom:
           secretKeyRef:
          
ConfigMaps:
  
1. If  you have a lot of pod definition files, it will become difficult to manage data.
2. ConfigMaps are used to pass configuration data in a form of key value pair. 
3. When a pod is created inject the configMap into the pod so the key value pairs are available as enviroment variables 
   for the application hosted in the enviroment. 
4. Can be configured imperative or declarative 
      - kubectl create configmap
      - app-config --from-literal=APP_COLOR-blue \
      - app-config --from-file=app_config.properties

Declarative:
      
apiVersion: v1
kind: ConfigMap
metadata:
  name: game-demo
data:
  # property-like keys; each key maps to a simple value
  player_initial_lives: "3"
  ui_properties_file_name: "user-interface.properties"

followed by kubectl create -f .....yaml
     
5. To view configMaps, run this command:
      - kubectl get configmaps

6. The most important step is to configure it with a pod now, 
   below is a simple pod yaml which runs an application:
  
---
apiVersion: v1 
kind: Pod 
metadata:
  name: webapp-green
  labels:
      name: webapp-green 
spec:
  containers:
  - name: simple-webapp
    image: kodekloud/webapp-color 
    envFrom: 
      - configMapRef:
           name: app-config <<<-----------This is a new propert called 'envFrom' property, specify and inject configmap from one created earlier before. 

7. There are other ways in injecting configuration data into pod above is using config maps however other ways are, single enviroment variable or injecting as a file volume. 


Practice test: Enviroment variables:
      
1. 
2.
3. What is the value set on the environment variable APP_COLOR on the container in the pod? = kubectl describe pod webapp-color = ping
4. Update the environment variable on the POD to display a green background
   Note: Delete and recreate the POD. Only make the necessary changes. Do not modify the name of the Pod.
   = kubectl delete pod webapp-color / followed by / kubectl edit pod webapp-color / 
5.
6.
7.
8.
9.
10.
      
      
      
      
      
      
      
