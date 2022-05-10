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
  
Practice test: Managing application logs:
  
-  We have deployed a POD hosting an application. Inspect it. Wait for it to start.
- A user - USER5 - has expressed concerns accessing the application. Identify the cause of the issue.
  Inspect the logs of the POD = kubectl logs webapp-1
- We have deployed a new POD - webapp-2 - hosting an application. Inspect it. Wait for it to start.
- A user is reporting issues while trying to purchase an item. Identify the user and the cause of the issue.
  Inspect the logs of the webapp in the POD = Since there is more then 1 container, need to specify the container name = kubectl logs webapp-2 -c simple-webapp
