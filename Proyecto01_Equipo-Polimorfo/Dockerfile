# Dockerfile para MonosChinos MX - Sistema de Ensamblaje de PC
# Utiliza Java 17 con soporte para JavaFX

FROM openjdk:17-slim

# Instalacion de dependencias necesarias para JavaFX
RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-glx \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copiar archivos de configuracion de Maven
COPY pom.xml .

# Descargar dependencias de Maven (para aprovechar cache de Docker)
RUN mvn dependency:go-offline -B

# Copiar el codigo fuente
COPY src ./src

# Compilar el proyecto
RUN mvn clean package -DskipTests

# Crear directorio para persistencia
RUN mkdir -p /.monoschinos

# Exponer variables de entorno para JavaFX
ENV _JAVA_OPTIONS="-Djava.awt.headless=false"
ENV DISPLAY=:0

# Punto de entrada por defecto: modo consola
CMD ["java", "-cp", "target/sistema-ensamblaje-pc-1.0.0.jar:target/lib/*", "Main"]

# Para ejecutar en modo GUI, usar:
# CMD ["java", "-cp", "target/sistema-ensamblaje-pc-1.0.0.jar:target/lib/*", "MainJavaFX"]
