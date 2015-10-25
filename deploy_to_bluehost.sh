#!/usr/bin/env bash
echo "copying jar locally"
cp target/bling-0.0.1-SNAPSHOT.jar target/bling.jar
echo "stopping process on server"
ssh -i ~/.ssh/id_rsa.pub bluecycl@cmikeb.net << EOF
  /etc/init.d/bling stop
EOF
echo "copying jar to server"
scp -i ~/.ssh/id_rsa.pub target/bling.jar bluecycl@cmikeb.net:~/bling
echo "updating permissions and starting app on server"
ssh -i ~/.ssh/id_rsa.pub bluecycl@cmikeb.net << EOF
  chmod 777 ~/bling/bling.jar
  /etc/init.d/bling start
EOF
echo "DONE!"
