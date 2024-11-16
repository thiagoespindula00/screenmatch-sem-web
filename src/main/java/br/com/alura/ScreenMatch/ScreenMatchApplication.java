package br.com.alura.ScreenMatch;

import br.com.alura.ScreenMatch.model.*;
import br.com.alura.ScreenMatch.repository.SerieRepository;
import br.com.alura.ScreenMatch.service.ConsultaOMDBAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.*;

@SpringBootApplication
public class ScreenMatchApplication  {
    public static void main(String[] args) {
        SpringApplication.run(ScreenMatchApplication.class, args);
    }
}
