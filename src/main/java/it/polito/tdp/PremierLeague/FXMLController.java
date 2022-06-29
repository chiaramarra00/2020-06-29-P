/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Integer> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	txtResult.clear();
    	if (!model.grafoCreato()) {
    		txtResult.setText("Creare un grafo\n");
    		return;
    	}
    	txtResult.appendText("Coppie con connessione massima:\n\n");
    	for (Adiacenza a : model.getConnessioneMassima()) {
    		txtResult.appendText(a.getM1()+" - "+a.getM2()+" ("+a.getPeso()+")\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	int min;
    	try {
    		min = Integer.parseInt(txtMinuti.getText());
    	} catch (NumberFormatException e) {
    		txtResult.setText("Inserire un valore di minuti intero\n");
    		e.printStackTrace();
    		return;
    	}
    	Integer mese = cmbMese.getValue();
    	if (mese==null) {
    		txtResult.setText("Selezionare un mese\n");
    		return;
    	}
    	model.creaGrafo(min,mese);
    	txtResult.appendText("Grafo creato\n");
    	txtResult.appendText("# VERTICI: " + this.model.nVertici() + "\n");
    	txtResult.appendText("# ARCHI: " + this.model.nArchi() + "\n");
    	cmbM1.getItems().setAll(model.getVertici());
    	cmbM2.getItems().setAll(model.getVertici());
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	txtResult.clear();
    	if (!model.grafoCreato()) {
    		txtResult.setText("Creare un grafo\n");
    		return;
    	}
    	Match m1 = cmbM1.getValue();
    	if (m1==null) {
    		txtResult.setText("Selezionare un match m1\n");
    		return;
    	}
    	Match m2 = cmbM2.getValue();
    	if (m2==null) {
    		txtResult.setText("Selezionare un match m2\n");
    		return;
    	}
    	if (m1.equals(m2)) {
    		txtResult.setText("Selezionare un match m2 diverso da m1\n");
    		return;
    	}
    	txtResult.setText("Percorso ottenuto:\n");
    	List<Match> percorso = model.cercaLista(m1, m2);
    	for (Match m : percorso) {
    		txtResult.appendText("    "+m+"\n");
    	}
    	txtResult.appendText("Peso relativo:\n    "+model.getPeso(percorso)+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for (int i=1;i<13;i++) {
    	    cmbMese.getItems().add(i);
    	}
    }
    
    
}
