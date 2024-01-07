# Organization Service Microservice

#### Zookeeper + Kafka

Zookeeper que se inicia antes que Kafka, inicializa un servidor Client en el puerto 2181 pero por default inicializa un servidor Admin en el 8080. Para modificar ingresar a /opt/homebrew/etc/zookeeper/zoo.cfg

Más info: https://stackoverflow.com/questions/62437233/zookeeper-starting-on-incorrect-port

Para ver que corre en cierto puerto: 'sudo lsof -i :8080' y luego usando el PID: 'ps aux | grep <PID>' 

Tenemos ejemplos de Consumers, Producers y Function en este microservicio. Pero también en Licensing Service tenemos un Consumer recibiendo el mensaje enviado desde aca. 

