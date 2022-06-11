package it.polito.tdp.genes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnStatistiche;

    @FXML
    private Button btnRicerca;

    @FXML
    private ComboBox<String> boxLocalizzazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRicerca(ActionEvent event) {

    	txtResult.setText("\n\n\n");
    	String partenza= boxLocalizzazione.getValue();
    	List<String> result= new ArrayList<String>();
    	result=this.model.cercaLista(partenza);
    	
    	for (String s: result) {
    		txtResult.appendText(s+"\n");
    	}
    }

    @FXML
    void doStatistiche(ActionEvent event) {
    String vicini= boxLocalizzazione.getValue();
    List<String> adiacenti= model.getAdiacenza(vicini);
   int peso=0;
   txtResult.setText("Adiacenti a "+vicini+"\n\n");
    for (String s: adiacenti) {
    	peso=this.model.pesoGrafo(vicini, s);
    	txtResult.appendText(s+"\t\t"+peso+"\n");
    }
    }

    @FXML
    void initialize() {
        assert btnStatistiche != null : "fx:id=\"btnStatistiche\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
       // assert boxLocalizzazione != null : "fx:id=\"boxLocalizzazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		txtResult.clear();
		model.creaGrafo();
		txtResult.appendText("Grafo creato!\n");
		txtResult.appendText("VERTICI: "+this.model.nVertici()+"\n");
    	txtResult.appendText("ARCHI: "+this.model.nArchi()+"\n");
    	
    	boxLocalizzazione.getItems().clear();
    	boxLocalizzazione.getItems().addAll(this.model.getVertici());
		
	}
}
