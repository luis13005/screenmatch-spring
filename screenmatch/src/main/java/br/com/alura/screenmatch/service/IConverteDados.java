package br.com.alura.screenmatch.service;

public interface IConverteDados {
    <T> T ObterDados(String json,Class<T> classe);
}
