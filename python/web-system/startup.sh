# *********** Async Worker ***********

async_worker() {
  PS3="Start or stop async_worker? "
  options=(start stop)
  select menu in "${options[@]}"; do
    case $REPLY in
      1) start_async_worker; break ;;
      2) kill_async_worker; break ;;
    esac
  done
}

start_async_worker() {
  cd async_worker
  python3 consumer.py 2>&1 &
  echo "Async Worker Started...."
  cd ..
}

kill_async_worker() {
  ps -ef | grep consumer.py | grep -v grep | awk '{print $2}' | xargs kill -9
  echo "Async Worker Stopped...."
}

# *********** Database  ***********

database() {
  PS3="Start or stop database? "
  options=(start stop)
  select menu in "${options[@]}"; do
    case $REPLY in
      1) start_database; break ;;
      2) kill_database; break ;;
    esac
  done
}

start_database() {
  source ws_env/bin/activate
  cd database
  python manage.py runserver 8020 &
  echo "Database Started...."
  cd ..
}

kill_database() {
  ps -ef | grep 'runserver 8020' | grep -v grep | awk '{print $2}' | xargs kill -9
  echo "Database Stopped...."
}

# *********** Database Cache ***********

database_cache() {
  PS3="Start or stop database_cache? "
  options=(start stop)
  select menu in "${options[@]}"; do
    case $REPLY in
      1) start_database_cache; break ;;
      2) kill_database_cache; break ;;
    esac
  done
}

start_database_cache() {
  source ws_env/bin/activate
  cd database_cache
  python manage.py runserver 8030 &
  echo "Database Cache Started...."
  cd ..
}

kill_database_cache() {
  ps -ef | grep 'runserver 8030' | grep -v grep | awk '{print $2}' | xargs kill -9
  echo "Database Cache Stopped...."
}

# *********** Load Balancer ***********

load_balancer() {
  PS3="Start or stop load_balancer? "
  options=(start stop)
  select menu in "${options[@]}"; do
    case $REPLY in
      1) start_load_balancer; break ;;
      2) kill_load_balancer; break ;;
    esac
  done
}

start_load_balancer() {
  source ws_env/bin/activate
  cd load_balancer
  python manage.py runserver 8010 &
  echo "Load Balancer Started...."
  cd ..
}

kill_load_balancer() {
  ps -ef | grep 'runserver 8010' | grep -v grep | awk '{print $2}' | xargs kill -9
  echo "Load Balancer Stopped...."
}

# *********** Web Service ***********

web_service() {
  PS3="Start or stop web services? "
  options=(start stop)
  select menu in "${options[@]}"; do
    case $REPLY in
      1) start_web_service; break ;;
      2) kill_web_service; break ;;
    esac
  done
}

start_web_service() {
  source ws_env/bin/activate
  cd web_service
  for i in {0..9}
  do
     python manage.py runserver 800$i &
     echo "Web Service Started...."
  done
  cd ..
}

kill_web_service() {
  ps -ef | grep 'runserver 800' | grep -v grep | awk '{print $2}' | xargs kill -9
  echo "Web Service Stopped...."
}

start_kafka() {
  zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & 
  kafka-server-start /usr/local/etc/kafka/server.properties &
}

kill_kafka() {
  ps -ef | grep 'zookeeper.properties' | grep -v grep | awk '{print $2}' | xargs kill -9
  ps -ef | grep 'kafka.Kafka' | grep -v grep | awk '{print $2}' | xargs kill -9
}

everything() {
  PS3="Start or stop everything? "
  options=(start stop)
  select menu in "${options[@]}"; do
    case $REPLY in
      1) start_everything; break ;;
      2) kill_everything; break ;;
    esac
  done 
}

start_everything() {
  start_async_worker
  start_database
  start_database_cache
  start_load_balancer
  start_web_service
  start_kafka
}

kill_everything() {
  kill_async_worker
  kill_database
  kill_database_cache
  kill_load_balancer
  kill_web_service
  kill_kafka
}

while true; do
  PS3="Choose a service to start: "
  options=(async_worker database database_cache load_balancer session_store web_service kafka everything)
  select menu in "${options[@]}"; do
    case $REPLY in
      1) async_worker; break ;;
      2) database; break ;;
      3) database_cache; break ;;
      4) load_balancer; break ;;
      5) echo "session_store"; break ;;
      6) web_service; break ;;
      7) start_kafka; break ;;
      8) everything; break ;;
      *) echo "Wrong Answer!!!"; break ;;
    esac
  done
done

source ws_env/bin/activate
cd web_service
python manage.py runserver $1
cd .. 
