# Account Management API
This is a simple POC for an Account Management application using docker with K8s deployment.

### Installation:
    Java
    Minikube (https://minikube.sigs.k8s.io/docs/start/)
    Docker (https://docs.docker.com/engine/install/ubuntu/)
      OR
    Docker Desktop


### Tech stack:
    IntelliJ
    SpringBoot 2.6.6
    Java 17
    Swagger
    Docker
    Kubernetes


### Project setup instructions
To start using this project use the following commands:

    Step 1: Clone this repo:
            git clone git@github.com:nj24/account-management.git

    Step 2: Start the minikube and evaluate:

            // To start minikube -> minikube start
            // To evaluate docker from minikube -> eval $(minikube docker-env)

    Step 3: Build the image using Dockerfile ( Use your choice name but do change in deployment file )
            $PATH_TO_PROJECT/ docker build -t fitness-api-image .

    Step 4: Apply all deployments inside deployment folder.

            kubectl apply -R -f ${Path_To_Project}/deployment

    Step 5: Verify the deployments applied successfully

            // To verify all
            kubectl get all 

            //To verify ingress
            kubectl get ingress 

    Step 6: Url using loadbalancer service : http://localhost:8080/swagger-ui.html

    Step 7: To set/forward your localhost using service host.
            Type the command below (May need sudo access):

            vim /etc/hosts
            #Add this for ingress host -> fitness-api-ingress
            127.0.0.1 account.managment.com


    Step 8: Add and save file. Verify using cat /etc/hosts

    Step 9: Open new terminal and type this command : 'minikube tunnel'

    Step 10: Using Ingress : http://account.managment.com/swagger-ui.html








