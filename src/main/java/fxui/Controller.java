package fxui;

import database.DatabaseAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import population.DBPokemon;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML public ComboBox<DBPokemon> pokeSelectDropDown;
    @FXML public Label currentPokeLabel;
    @FXML public ImageView pokemonImageView;
    @FXML public Label pokedexTextLabel;

    List<Node> typeControls;
    @FXML public Label typeLabel;
    @FXML public ImageView typePrimaryImageView;
    @FXML public ImageView typeSecondaryImageView;

    List<Node> abilityControls;
    @FXML public Label abilityLabel;
    @FXML public Hyperlink abilityMainHyperlink;
    @FXML public Label abilityOrLabel;
    @FXML public Hyperlink abilitySecondHyperlink;
    @FXML public Label abilityHiddenLabel;
    @FXML public Hyperlink abilityHiddenHyperlink;

    @FXML public ListView movesListView;
    @FXML public ScrollPane evolutionScrollPane;

    @FXML
    private void initialize() {

        System.out.println("gui init");

        // set up control collections to iterate over when necessary
        typeControls = new ArrayList<>();
        typeControls.add(typeLabel);
        typeControls.add(typePrimaryImageView);
        typeControls.add(typeSecondaryImageView);
        setControlsVisible(typeControls, false);

        abilityControls = new ArrayList<>();
        abilityControls.add(abilityLabel);
        abilityControls.add(abilityMainHyperlink);
        abilityControls.add(abilityOrLabel);
        abilityControls.add(abilitySecondHyperlink);
        abilityControls.add(abilityHiddenLabel);
        abilityControls.add(abilityHiddenHyperlink);
        setControlsVisible(abilityControls, false);

        // dropdown contains list of dbpokemon so display their id and name
        pokeSelectDropDown.setCellFactory(
                new Callback<>() {
                    @Override
                    public ListCell<DBPokemon> call(ListView<DBPokemon> dbPokemonListView) {
                        return new ListCell<>() {
                            { super.setPrefWidth(100);
                    }

                    @Override
                    public void updateItem(DBPokemon item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.toString());
                        }
                        else {
                            setText(null);
                        }
                    }
                };
            }
        });

        pokeSelectDropDown.getItems().addAll(DatabaseAccess.getDBPokeList());
        pokeSelectDropDown.setValue(pokeSelectDropDown.getItems().get(0));
    }

    public void displayPokemon(ActionEvent actionEvent) {
    }

    public void filterPokemonDropDown(KeyEvent keyEvent) {
    }

    public void displayAbilityTooltip(ActionEvent actionEvent) {
    }

    private void setControlsVisible(List<Node> controls, boolean visible) {
        for (Node n : controls) {
            n.setVisible(visible);
        }
    }
}
