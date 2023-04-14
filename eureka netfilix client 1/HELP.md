zookeeper-server-start.bat ../../config/zookeeper.properties
kafka-server-start.bat ../../config/server.properties
kafka-topics --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 2
kafka-topics.bat --bootstrap-server localhost:9092 --list
kafka-topics --bootstrap-server localhost:9092 --topic firstTopic --describe
kafka-console-producer --broker-list alai.behsazan.net:9092 --topic secondTopic --producer-property acks=all