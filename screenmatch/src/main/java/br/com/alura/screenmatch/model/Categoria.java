package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    AVENTURA("Adventure"),
    ROMANCE("Romance"),
    TERROR("Terror"),
    HORROR("Horror");

    private String omdbCategoria;

    Categoria(String omdbCategoria){
        this.omdbCategoria = omdbCategoria;
    }

    public static Categoria fromString(String txt){
        for (Categoria categoria:Categoria.values()){
            if (categoria.omdbCategoria.equalsIgnoreCase(txt)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + txt);
    }
}
