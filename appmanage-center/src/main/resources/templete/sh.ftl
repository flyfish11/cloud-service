#!/bin/bash
echo '================package finished================'
#cd /var/lib/jenkins/workspace/${service_name}
echo '================stop  ${service_name}================'
sudo  docker stop rgs.hlxd.com/library/${service_name}
echo '===============remove contaion ${service_name}================'
sudo  docker rm rgs.hlxd.com/library/${service_name}
echo '================remove image ${service_name}:latest================'
sudo  docker rmi rgs.hlxd.com/library/${service_name}:latest
echo '================build  ${service_name}:latest================'
sudo docker build -t rgs.hlxd.com/library/${service_name}:latest -f ./Dockerfile . --network host --build-arg HTTP_PROXY=http://itanms0818:init1234@10.130.14.28:8080 --build-arg HTTPS_PROXY=http://itanms0818:init1234@10.130.14.28:8080
echo '================pull ${service_name}================'
sudo docker login -u admin -p Harbor12345 rgs.hlxd.com
sudo docker push rgs.hlxd.com/library/${service_name}:latest
echo '================install k8s================'
sudo kubectl apply -f ./deployment.yaml

echo "finished!"
exit  ###exit machine
remotessh  ###end
echo '================stop finished================'