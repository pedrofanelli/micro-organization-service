# Organization Service Microservice

#### Zookeeper + Kafka

Zookeeper que se inicia antes que Kafka, inicializa un servidor Client en el puerto 2181 pero por default inicializa un servidor Admin en el 8080. Para modificar ingresar a /opt/homebrew/etc/zookeeper/zoo.cfg

Más info: https://stackoverflow.com/questions/62437233/zookeeper-starting-on-incorrect-port

Para ver que corre en cierto puerto: 'sudo lsof -i :8080' y luego usando el PID: 'ps aux | grep <PID>' 

Le agregamos a ese archivo de configuración que el servidor Admin esté cerrado, asi tenemos libre el puerto 8080.

Tenemos ejemplos de Consumers, Producers y Function en este microservicio. Pero también en Licensing Service tenemos un Consumer recibiendo el mensaje enviado desde aca. 

La idea es que el funcionamiento de Spring Cloud Stream con Kafka haya una cadena de servicios y se vayan pasando Streams de data. Nosotros creamos un Bean devolviendo una *Interfaz Funcional* es decir, interfaces con un solo método para implementar, entonces armamos funciones Lambda para implementar de forma automática la función. Nosotros implementamos esa interfaz y la guardamos como Bean. Si no agregamos ninguna propiedad se agregará de forma automática. Pero si nosotros tenemos otros Beans que forman Interfaces Funcionales del mismo tipo podemos diferenciarla específicamente usando: 'spring.cloud.function.definition=consumerBinding'. ConsumerBinding sería el nombre de la función que forma el Bean. 

Function sería también llamado en este contexto un **Procesador**, es decir, tiene un input y un output. Recibe información y expulsa data. Sirve para manipular data en medio de una cadena de Streams. Supplier no tiene parámetros pero devuelve el objeto que queremos enviar. Consumer tiene como parámetro el input y no devuelve nada. 

Entonces, luego de haber habilitado el servidor de Zookeeper y el de Kafka, podemos usar la conexión usando eventos entre microservicios.

Luego debemos configurar los **Topics**, que vendrían a ser como los canales conductores, es el nombre de identificación donde los Consumers/Producers/Functions estarán escuchando o teniendo como "destino". 
Por ejemplo: 'spring.cloud.stream.bindings.producerBinding-out-0.destination=processor-topic'
En el ejemplo, producerBinding es el nombre de la función que implementa el Bean; out significa que es un output, exporta data; 0 sería el index. Este nombre está estandarizado, para más info ver la documentación oficial de Spring Cloud Stream. Luego el nombre que se asigna será el Topic que se creará/usará. En este caso se envia la información a ese topic.

(DOCS: https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#_producing_and_consuming_messages)

Luego, si se quisiera recibir la info enviada se usa:
'spring.cloud.stream.bindings.processorBinding-in-0.destination=processor-topic'
Es el nombre de otro Bean, que recibe la data, y está escuchando en el mismo Topic. Este puede encontrarse en otro microservicio, siempre usando el mismo servidor de Kafka. 

Como plus se puede configurar el **Binder** que vendría a ser el cliente que se utiliza, en nuestro caso Kafka: 'spring.cloud.stream.kafka.binder.brokers=localhost:9092'
Esto por si tenemos varios al mismo tiempo.

Ahora, en este ejemplo, y el funcionamiento normal de estos Streams, es que sean usados para proveer información del estado de los servicios, por lo que funcionan constantemente enviando señales. The preceding Supplier bean produces a string whenever its get() method is invoked. However, who invokes this method and how often? The framework provides a default **polling mechanism** (answering the question of "Who?") that will trigger the invocation of the supplier and by default it will do so every second (answering the question of "How often?"). In other words, the above configuration produces a single message every second and each message is sent to an output destination that is exposed by the binder. To learn how to customize the polling mechanism, see Polling Configuration Properties section (https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#_polling_configuration_properties).

Es decir, podemos configurar el mecanismo de 'polling' indicando la cantidad de mensajes y el tiempo entre cada uno de ellos.

Sin embargo, supongamos que queremos enviar un evento por una señal externa. Es decir, que no se genere de forma constante por cierta cantidad de segundos, sino que dependa, por ejemplo, de un pedido REST. Para el envio a otro Microservicio nosotros no usamos el típico Supplier como el del ejemplo, usamos otra herramienta. 

Existe **streamBridge**! Con esta clase que nosotros incorporamos con @Autowired a nuestro REST Endpoint, nosotros indicamos el Topic al que queremos enviar la data, y el propio objeto, y listo! Para ver nuestro caso ir al controlador de este servicio. Lo que hace es, al recibir un pedido get, en el manejo del pedido, se envia el mensaje al topic, el cual será receptado por un Consumer, acá si será como los ejemplos iniciales, que estará escuchando en el mismo topic y lo recibirá.

Copado no? :D
