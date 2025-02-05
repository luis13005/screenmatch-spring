package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action","Ação"),
    COMEDIA("Comedy","Comédia"),
    DRAMA("Drama","Drama"),
    AVENTURA("Adventure","Aventura"),
    ROMANCE("Romance","Romance"),
    TERROR("Terror","Terror"),
    HORROR("Horror","Horror");

    private String omdbCategoria;
    private String generoPortugues;

    Categoria(String omdbCategoria, String generoPortugues){
        this.omdbCategoria = omdbCategoria;
        this.generoPortugues = generoPortugues;
    }

    public static Categoria fromString(String txt){
        for (Categoria categoria:Categoria.values()){
            if (categoria.omdbCategoria.equalsIgnoreCase(txt)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + txt);
    }

    public static Categoria fromPortugues(String txt){
        for (Categoria categoria:Categoria.values()){
            if (categoria.generoPortugues.equalsIgnoreCase(txt)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + txt);
    }
}
