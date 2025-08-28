# Explicación de la Propuesta de Implementación
## Simulación de Servicios de Streaming

Al analizar los requerimientos de esta práctica, me di cuenta de que el problema central gira en torno a dos aspectos fundamentales: la necesidad de un sistema de notificaciones flexible entre servicios y usuarios, y la gestión dinámica de diferentes estrategias de cobro para cada servicio.

## Visión General de la Arquitectura

Mi propuesta se estructura alrededor de una simulación centralizada que orquesta las interacciones entre usuarios y servicios durante un período de doce meses. La clase `Simulacion` actúa como el director de esta orquesta, controlando el flujo temporal y coordinando todas las operaciones mensuales. Esta decisión centralizada me permite mantener un control preciso sobre el comportamiento específico de cada usuario en cada mes, tal como lo requiere la práctica.

La arquitectura separa claramente las responsabilidades: los servicios se encargan de generar notificaciones y procesar cobros, los usuarios mantienen su estado financiero y de suscripciones, y el sistema de simulación coordina todas estas interacciones. Esta separación me permite modificar comportamientos individuales sin afectar el resto del sistema.

## Aplicación del Patrón Observer

El patrón Observer resulta fundamental para resolver el sistema de notificaciones que la práctica requiere. Los servicios actúan como sujetos observables, mientras que los usuarios implementan la interfaz `Observer`. Esta relación me permite que cada servicio notifique automáticamente a todos sus suscriptores sobre eventos relevantes: mensajes de bienvenida, despedidas, confirmaciones de cobro, fallos en pagos, recomendaciones mensuales y el conteo de meses de uso.

La interfaz `Observer` define métodos específicos para cada tipo de notificación, lo que me da la flexibilidad de manejar cada evento de manera diferente si es necesario. Por ejemplo, una notificación de cobro exitoso se maneja de forma distinta a una recomendación mensual. Los usuarios, al implementar esta interfaz, pueden recibir y procesar todas estas notificaciones de manera uniforme, independientemente del servicio que las envíe.

Una ventaja importante de esta implementación es que permite agregar nuevos tipos de notificaciones en el futuro sin modificar las clases existentes. Si necesitáramos añadir notificaciones de promociones especiales, por ejemplo, solo requeriríamos agregar un nuevo método a la interfaz `Observer` y implementarlo en la clase `Usuario`.

## Aplicación del Patrón Strategy

El patrón Strategy resuelve eficientemente la complejidad de los diferentes esquemas de cobro que maneja cada servicio. Cada plan de suscripción se representa como una estrategia concreta que implementa la interfaz `EstrategiaCobro`. Esta interface define métodos para calcular costos, obtener descripciones de planes y determinar si un plan es gratuito en función del número de meses contratados.

Esta aproximación me permite encapsular la lógica específica de cada plan de cobro. Por ejemplo, `ThisneyPlan` implementa la lógica compleja de cobrar $130 los primeros tres meses y $160 a partir del cuarto mes, mientras que `SpootifyGratis` siempre retorna costo cero. La flexibilidad del patrón me permite que los usuarios cambien dinámicamente entre diferentes planes del mismo servicio sin afectar el resto de la lógica.

La implementación de cada estrategia encapsula no solo el cálculo del costo, sino también la descripción textual del plan y la determinación de períodos gratuitos. Esto resulta especialmente útil para servicios como HVO Max que ofrecen meses gratuitos iniciales, ya que toda esta lógica queda contenida en la estrategia correspondiente.

## Diseño de la Clase Usuario

La clase `Usuario` actúa como el núcleo de las interacciones del sistema. Implementa la interfaz `Observer` para recibir notificaciones y mantiene una relación de composición con `CuentaBanco` para manejar las transacciones financieras. Además, utiliza un `Map` de objetos `HistorialServicio` para rastrear el estado de cada suscripción.

El `HistorialServicio` fue una decisión de diseño clave, ya que encapsula toda la información relevante sobre la relación del usuario con un servicio específico: los meses totales contratados, el estado activo de la suscripción, la estrategia de cobro actual y la fecha del último pago. Esta clase me permite cumplir con el requerimiento crucial de que los meses contratados se mantengan aunque el usuario cancele y renueve su suscripción.

La gestión del dinero través de `CuentaBanco` me permite simular transacciones reales con un sistema de saldos y un historial completo de transacciones. Esta clase verifica que el usuario tenga fondos suficientes antes de procesar cualquier pago, y mantiene un registro detallado que será útil para los reportes finales.

## Arquitectura de Servicios

La clase abstracta `Servicio` implementa la interfaz `Subject` del patrón Observer y proporciona la funcionalidad común a todos los servicios de streaming. Esta clase maneja la lista de observadores, las notificaciones estándar y el procesamiento de cobros mensuales. Los servicios concretos como `Memeflix`, `MomazonPrime`, `Spootify`, `Thisney` y `HVOMax` heredan de esta clase base e implementan métodos específicos para obtener sus estrategias disponibles y generar recomendaciones únicas.

Cada servicio concreto define sus propias recomendaciones mensuales y especifica qué estrategias de cobro están disponibles. Esta separación me permite que cada servicio tenga personalidad propia en términos de contenido recomendado, mientras mantiene un comportamiento uniforme en cuanto a notificaciones y cobros.

El método `procesarCobroMensual` en la clase base `Servicio` coordina todo el proceso de cobro: identifica a los usuarios suscritos, calcula los montos usando las estrategias correspondientes, intenta procesar los pagos, y genera las notificaciones apropiadas según el resultado. Esta centralización asegura que todos los servicios manejen los cobros de manera consistente.

## Sistema de Simulación

La clase `Simulacion` orchestar toda la experiencia temporal de la práctica. Inicializa los usuarios con sus saldos específicos y los servicios con sus recomendaciones. Luego, para cada uno de los doce meses, ejecuta los comportamientos específicos de cada usuario según las especificaciones de la práctica, procesa todos los cobros mensuales y registra todas las transacciones.

Los métodos `aplicarComportamientoX` para cada usuario encapsulan la lógica específica de suscripciones, cancelaciones y cambios de plan que cada usuario debe realizar en meses específicos. Esta separación me permite modificar fácilmente el comportamiento de cualquier usuario sin afectar el resto de la simulación.

El `GestorArchivos` se encarga de generar los reportes requeridos por la práctica. Esta clase separada me permite concentrar toda la lógica de formateo y escritura de archivos en un solo lugar, facilitando el mantenimiento y permitiendo generar diferentes tipos de reportes según sea necesario.

## Manejo de Estados y Persistencia

Una consideración importante en mi diseño fue el manejo correcto de los estados de suscripción y el conteo acumulativo de meses. El requerimiento de que los meses se mantengan incluso después de cancelaciones me llevó a diseñar el `HistorialServicio` como una entidad persistente que nunca se elimina, solo cambia su estado de activo/inactivo.

Esta persistencia es crucial para servicios como Thisney+ y HVO Max que tienen períodos gratuitos iniciales, ya que evita que un usuario pueda aprovecharse cancelando y renovando repetidamente para obtener meses gratuitos adicionales. El sistema siempre recuerda cuántos meses ha contratado cada usuario con cada servicio.

## Integración de Componentes

Todo el sistema se integra a través de interfaces bien definidas que permiten la comunicación fluida entre componentes. Los usuarios se suscriben a servicios proporcionando una estrategia de cobro específica. Los servicios notifican a los usuarios sobre eventos relevantes. La simulación coordina los comportamientos temporales y procesa los cobros mensuales. El gestor de archivos documenta todas las transacciones.

Esta arquitectura me permite cumplir con todos los requerimientos de la práctica: implementa correctamente ambos patrones de diseño, maneja la complejidad de múltiples servicios con diferentes esquemas de cobro, procesa comportamientos específicos de usuarios, mantiene persistencia de datos crucial, y genera los reportes necesarios. Además, la separación clara de responsabilidades hace que el código sea mantenible y extensible para futuras modificaciones.

La flexibilidad del diseño también me permitirá agregar fácilmente nuevos servicios o usuarios en el futuro, ya que solo requeriría implementar las interfaces existentes sin modificar el código base. Esta escalabilidad es una ventaja importante de la arquitectura basada en patrones de diseño.
