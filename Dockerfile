FROM eclipse-temurin:21-jdk-jammy

RUN apt-get update && apt-get install -y \
    python3 \
    ffmpeg \
    curl \
    && rm -rf /var/lib/apt/lists/*

# yt-dlp 설치 (Debian PEP 668 회피)
RUN curl -sS https://bootstrap.pypa.io/get-pip.py | python3 -
RUN pip3 install --no-cache-dir yt-dlp --break-system-packages

WORKDIR /app

COPY build/libs/Remedy-Streaming-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]