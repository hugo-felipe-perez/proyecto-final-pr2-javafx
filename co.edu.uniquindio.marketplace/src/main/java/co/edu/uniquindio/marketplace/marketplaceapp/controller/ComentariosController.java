package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Comentario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class ComentariosController {

    @FXML
    private ListView<String> comentariosListView;

    private ObservableList<String> comentariosList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        comentariosListView.setItems(comentariosList);
    }

    public void setComentarios(List<Comentario> comentarios) {
        comentariosList.clear();
        for (Comentario comentario : comentarios) {
            comentariosList.add(comentario.getAutor() + ": " + comentario.getTexto());
        }
    }
    @FXML
    public void cerrarVentana() {
        Stage stage = (Stage) comentariosListView.getScene().getWindow();
        stage.close();
    }

}
