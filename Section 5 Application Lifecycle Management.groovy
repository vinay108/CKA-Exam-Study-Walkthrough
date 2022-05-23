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




