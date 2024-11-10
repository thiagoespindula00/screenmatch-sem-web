package br.com.alura.ScreenMatch.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class ConsultaMyMemoryAPI {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TextoTraduzido(@JsonAlias("translatedText") String texto) {
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TraducaoResponse(@JsonAlias("responseData") TextoTraduzido textoTraduzido) {
    }
    public static String traduzir(String texto) {
        if (texto.isEmpty())
            return "";

        String json = ConsumoAPI.getDados("https://api.mymemory.translated.net/get?q=" + texto.replace(" ", "+").trim() + "&langpair=en%7Cpt-br");
        TraducaoResponse traducaoResponse = ConverteDados.getDados(json, TraducaoResponse.class);

        return traducaoResponse.textoTraduzido().texto();
    }
}
