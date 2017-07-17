# JGroups Clustering on OpenShift with GossipRouter

This simple project shows how you can use the **TCPGOSSIP** discovery protocol of **JGroups** to discover members participating in a clustered setting.


### OpenShift Setup

Please use the variant of the instructions below that works for you (your environment). `sudo` is only needed if you need it to start `docker`.

```sh 
# Start the OSE environment
sudo oc cluster up --image="registry.access.redhat.com/openshift3/ose" --version="v3.5.5.31-2"

# Deploy the GossipRouter on OSE 
sudo oc new-app jboss/jgroups-gossip:3.6.13.Final --name=gossip-router -e LogLevel=trace  
```

### Deploy the clustered application

```sh
# Set the KUBERNETES_MASTER env variable
export KUBERNETES_MASTER=https://192.168.86.35:8443

# Deploy the application
sudo mvn clean fabric8:deploy
``` 

### Testing

Verify the logs of each pod or the logs of the GossipRouter after you have run the following command:

```sh 
# Scale the application to have 3 pods
sudo oc scale --replicas=3 dc simple-openshift-jgroups
```