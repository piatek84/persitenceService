docker run -d -p 27017:27017 --name mongodb mongo

docker run -d --name kafka \
-p 9092:9092 -p 9093:9093 \
-e ALLOW_PLAINTEXT_LISTENER=yes \
-e KAFKA_ENABLE_KRAFT=yes \
-e KAFKA_CFG_NODE_ID=1 \
-e KAFKA_CFG_PROCESS_ROLES=broker,controller \
-e KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=1@127.0.0.1:9093 \
-e KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 \
-e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
-e KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER \
bitnami/kafka:latest


{"name": "Iga Świątek","country": "Poland","type": "PERSON","urlIcon": "http://icon.com"}
{"participantId1":"67a730dcae1e04614ed13b61","participantId2":"67a730dcae1e04614ed13b62","winDrawLostParticipant1":"WIN","winDrawLostParticipant2":"LOST","result":"6-4,7-5","date":"07102024","championship":"Australian Open"}
