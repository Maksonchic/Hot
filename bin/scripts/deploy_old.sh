#!/usr/bin/env bash
#would exec it from root folder!!!

mvn clean package

echo "Checking old version of project..."

# shellcheck disable=SC2087
ssh -i ~/.ssh/maks maks@45.133.18.174 << EOF
  DIR="/var/lib/tomcat9/webapps/HotelAlpha"
  count=2
  sleep_time_s=5

  echo "Deleting WAR file \$DIR.war ..."
  rm \$DIR.war
  echo "OK!"
  echo "Waiting for tomcat9 remove \$DIR deploed project folder..."

  while [ -d "\$DIR" ] && [[ \$count\>0 ]]
  do
    echo "The apps folder \$DIR is exists yet, waiting..."
    let count=\$count-1
    sleep \$sleep_time_s
  done

  if [ -d "\$DIR" ]; then
    echo "Could not remove folder ((("
  else
    echo "tomcat9 have remove \$DIR!"
  fi

  echo "Let's some sleep (for \$((sleep_time_s + 10)) seconds)..."
  sleep \$((sleep_time_s + 10))
  echo "ok! May continue!"
EOF

echo "Copy files..."
scp -i ~/.ssh/maks \
    target/HotelAlpha.war \
    maks@45.133.18.174:/var/lib/tomcat9/webapps/

echo "Bye!"
