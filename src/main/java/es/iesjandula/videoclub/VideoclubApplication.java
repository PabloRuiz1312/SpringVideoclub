package es.iesjandula.videoclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = "es.iesjandula.videoclub")
public class VideoclubApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(VideoclubApplication.class, args);
	}
}
