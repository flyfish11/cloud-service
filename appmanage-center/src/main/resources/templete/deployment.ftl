apiVersion: extensions/v1beta1
kind: Deployment
metadata:
name: ${service_name}
spec:
replicas: 1
template:
metadata:
labels:
app: ${service_name}
spec:
containers:
- name: ${service_name}
image: rgs.hlxd.com/library/${service_name}:latest
env:
- name: AUTHOR
value: ${service_name}
ports:
- containerPort: ${port}
---
apiVersion: v1
kind: Service
metadata:
name: ${service_name}
spec:
ports:
- port: ${port}
protocol: TCP
targetPort: ${port}
nodePort: ${port}
type: NodePort
selector:
app: ${service_name}