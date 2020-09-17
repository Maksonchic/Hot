#!/usr/bin/env bash
#would exec it from root folder!!!

mvn clean package

echo "Checking old version of project..."

# shellcheck disable=SC2087
ssh -i ~/.ssh/maks maks@45.133.18.174 << EOF
  DIR="/var/lib/tomcat9/webapps/HotelAlpha"
  sleep_time_s=15

  echo "Deleting WAR file \$DIR.war ..."
  rm \$DIR.war
  echo "OK!"
  echo "Waiting for tomcat9 remove \$DIR deploed project folder..."
  sleep \$sleep_time_s
  echo "ok! May continue!"
EOF

echo "Copy files..."
scp -i ~/.ssh/maks \
    target/HotelAlpha.war \
    maks@45.133.18.174:/var/lib/tomcat9/webapps/

echo "Bye!"
