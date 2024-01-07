# Organization Service Microservice

#### Zookeeper + Kafka

Zookeeper que se inicia antes que Kafka, inicializa un servidor Client en el puerto 2181 pero por default inicializa un servidor Admin en el 8080. Para modificar ingresar a /opt/homebrew/etc/zookeeper/zoo.cfg

Más info: https://stackoverflow.com/questions/62437233/zookeeper-starting-on-incorrect-port

Para ver que corre en cierto puerto: 'sudo lsof -i :8080' y luego usando el PID: 'ps aux | grep <PID>' 

Tenemos ejemplos de Consumers, Producers y Function en este microservicio. Pero también en Licensing Service tenemos un Consumer recibiendo el mensaje enviado desde aca. 

La idea es que el funcionamiento de Spring Cloud Stream con Kafka haya una cadena de servicios y se vayan pasando Streams de data. Nosotros creamos un Bean devolviendo una *Interfaz Funcional* es decir, interfaces con un solo método para implementar, entonces armamos funciones Lambda para implementar de forma automática la función. Nosotros implementamos esa interfaz y la guardamos como Bean. Si no agregamos ninguna propiedad se agregará de forma automática. Pero si nosotros tenemos otros Beans que forman Interfaces Funcionales del mismo tipo podemos diferenciarla específicamente usando: 'spring.cloud.function.definition=consumerBinding'. ConsumerBinding sería el nombre de la función que forma el Bean. 

Function sería también llamado en este contexto un **Procesador**, es decir, tiene un input y un output. Recibe información y expulsa data. Sirve para manipular data en medio de una cadena de Streams. Supplier no tiene parámetros pero devuelve el objeto que queremos enviar. Consumer tiene como parámetro el input y no devuelve nada. 

Entonces, luego de haber habilitado el servidor de Zookeeper y el de Kafka, podemos usar la conexión usando eventos entre microservicios.

Luego debemos configurar los **Topics**, que vendrían a ser como los canales conductores, es el nombre de identificación donde los Consumers/Producers/Functions estarán escuchando o teniendo como "destino". 
Por ejemplo: 'spring.cloud.stream.bindings.producerBinding-out-0.destination=processor-topic'
En el ejemplo, producerBinding es el nombre de la función que implementa el Bean; out significa que es un output, exporta data; 0 sería el index. Este nombre está estandarizado, para más info ver la documentación oficial de Spring Cloud Stream. Luego el nombre que se asigna será el Topic que se creará/usará. En este caso se envia la información a ese topic.

Luego, si se quisiera recibir la info enviada se usa:
'spring.cloud.stream.bindings.processorBinding-in-0.destination=processor-topic'
Es el nombre de otro Bean, que recibe la data, y está escuchando en el mismo Topic. Este puede encontrarse en otro microservicio, siempre usando el mismo servidor de Kafka. 

Como plus se puede configurar el **Binder** que vendría a ser el cliente que se utiliza, en nuestro caso Kafka: 'spring.cloud.stream.kafka.binder.brokers=localhost:9092'
Esto por si tenemos varios al mismo tiempo.

