package com.itemsharing.zipkinserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ZipkinServerApplication.class, args);
	}
}
