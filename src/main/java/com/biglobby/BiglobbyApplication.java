package com.biglobby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableWebSecurity
@EnableMongoRepositories
@EnableScheduling
@EnableWebSocket
@EnableWebSocketMessageBroker
public class BiglobbyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiglobbyApplication.class, args);
	}

}
