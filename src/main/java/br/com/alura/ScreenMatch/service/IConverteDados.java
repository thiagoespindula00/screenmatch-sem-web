package br.com.alura.ScreenMatch.service;

public interface IConverteDados {
    public <T> T getDados(String json, Class<T> classe);
}
