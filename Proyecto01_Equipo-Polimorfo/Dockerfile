# Dockerfile para MonosChinos MX - Sistema de Ensamblaje de PC
# Utiliza Java 17 con soporte para JavaFX y VNC para acceso GUI universal

FROM openjdk:17-slim

# Instalacion de dependencias necesarias para JavaFX, GTK3, VNC y noVNC
RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-glx \
    libgtk-3-0 \
    libgtk-3-dev \
    libglib2.0-0 \
    libpango-1.0-0 \
    libpangocairo-1.0-0 \
    libcairo2 \
    libcairo-gobject2 \
    libgdk-pixbuf2.0-0 \
    libfontconfig1 \
    libfreetype6 \
    libasound2 \
    maven \
    tigervnc-standalone-server \
    tigervnc-common \
    fluxbox \
    xterm \
    wget \
    git \
    python3 \
    python3-numpy \
    net-tools \
    && rm -rf /var/lib/apt/lists/*

# Instalar noVNC para acceso web
RUN git clone https://github.com/novnc/noVNC.git /opt/noVNC && \
    git clone https://github.com/novnc/websockify /opt/noVNC/utils/websockify && \
    ln -s /opt/noVNC/vnc.html /opt/noVNC/index.html

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

# Crear directorio VNC y configurar
RUN mkdir -p /root/.vnc

# Copiar script de inicio
COPY start-vnc.sh /usr/local/bin/start-vnc.sh
RUN chmod +x /usr/local/bin/start-vnc.sh

# Exponer variables de entorno para JavaFX y VNC
ENV _JAVA_OPTIONS="-Djava.awt.headless=false"
ENV DISPLAY=:1
ENV USER=root

# Exponer puertos
# 80: noVNC (acceso web)
# 5901: VNC directo (opcional)
EXPOSE 80 5901

# Punto de entrada: script que inicia VNC + JavaFX
CMD ["/usr/local/bin/start-vnc.sh"]
