package co.edu.uniquindio.marketplace.marketplaceapp.model.builder;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Comentario;

public class ComentarioBuilder {
    private String texto;
    private String autor;

    public ComentarioBuilder setTexto(String texto) {
        this.texto = texto;
        return this;
    }

    public ComentarioBuilder setAutor(String autor) {
        this.autor = autor;
        return this;
    }

    public Comentario build() {
        return new Comentario(texto, autor);
    }
}
