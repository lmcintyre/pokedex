package fxui;

import database.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.util.Callback;
import population.DBPokemon;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML public ComboBox<DBPokemon> pokeSelectDropDown;
    @FXML public Label currentPokeLabel;
    @FXML public ImageView pokemonImageView;
    @FXML public TextArea pokedexTextLabel;

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
    @FXML public Tooltip abilityPrimaryTooltip;
    @FXML public Tooltip abilitySecondaryTooltip;
    @FXML public Tooltip abilityHiddenTooltip;

    @FXML public TableView<Move> movesTableView;
    public TableColumn<Move, String> levelColumn;
    public TableColumn<Move, ImageView> typeColumn;
    public TableColumn<Move, ImageView> categoryColumn;
    public TableColumn<Move, String> nameColumn;
    public TableColumn<Move, Integer> powerColumn;
    public TableColumn<Move, Integer> accuracyColumn;
    public TableColumn<Move, String> descriptionColumn;

    @FXML public TableView<Evolution> evolutionTableView;
    public TableColumn<Evolution, String> evolvesIntoColumn;
    public TableColumn<Evolution, ImageView> evolvesIconColumn;
    public TableColumn<Evolution, String> evolvesMethodColumn;
    public TableColumn<Evolution, String> evolvesCriteriaColumn;

    @FXML
    private void initialize() {

        // set up control collections to iterate over when necessary
        typeControls = new ArrayList<>();
        typeControls.add(typeLabel);
        typeControls.add(typePrimaryImageView);
        typeControls.add(typeSecondaryImageView);

        abilityControls = new ArrayList<>();
        abilityControls.add(abilityLabel);
        abilityControls.add(abilityMainHyperlink);
        abilityControls.add(abilityOrLabel);
        abilityControls.add(abilitySecondHyperlink);
        abilityControls.add(abilityHiddenLabel);
        abilityControls.add(abilityHiddenHyperlink);

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
                            setItem(item);
                        }
                        else {
                            setText(null);
                        }
                    }
                };
            }
        });

        //Moves tableview
        levelColumn.setCellValueFactory(moveStringCellDataFeatures -> {
            int level = moveStringCellDataFeatures.getValue().getLevelLearned();
            if (level == 0)
                return Bindings.createStringBinding(() -> "Egg");
            else
                return Bindings.createStringBinding(() -> Integer.toString(level));
        });

        typeColumn.setCellValueFactory(moveTypeCellDataFeatures ->
                Bindings.createObjectBinding(() -> new ImageView(moveTypeCellDataFeatures.getValue().getType().getTypeIcon()))
        );

        categoryColumn.setCellValueFactory(moveCategoryCellDataFeatures ->
                Bindings.createObjectBinding(() -> new ImageView(moveCategoryCellDataFeatures.getValue().getCategory().getIcon()))
        );

        powerColumn.setCellValueFactory(new PropertyValueFactory<>("power"));
        accuracyColumn.setCellValueFactory(new PropertyValueFactory<>("accuracy"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("moveName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("moveText"));

        movesTableView.setPlaceholder(new Label("Select a Pokemon above to view learned moves here."));

        //Evolution listview
        evolvesIntoColumn.setCellValueFactory(new PropertyValueFactory<>("evolvesTo"));
        evolvesIconColumn.setCellValueFactory(evolutionImageViewCellDataFeatures ->
                    Bindings.createObjectBinding(() ->
                        new ImageView(evolutionImageViewCellDataFeatures.getValue().getEvolvedIcon())
                    )
        );
        evolvesMethodColumn.setCellValueFactory(new PropertyValueFactory<>("evolutionMethod"));
        evolvesCriteriaColumn.setCellValueFactory(new PropertyValueFactory<>("evolutionCriteria"));

        evolutionTableView.setPlaceholder(new Label("Select a Pokemon above to view evolutions here."));

        evolutionTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                Evolution selected = evolutionTableView.getSelectionModel().getSelectedItem();
                displayPokemon(selected.getEvolvesId());
            }
        });

        /*  Do this if criteria or methods are too cryptic
        evolvesCriteriaColumn.setCellValueFactory(evolutionStringCellDataFeatures -> {
            String something = evolutionStringCellDataFeatures.getValue() ....stuff;
            if (level == 0)
                return Bindings.createStringBinding(() -> "Egg");
            else
                return Bindings.createStringBinding(() -> Integer.toString(level));
        });*/

        pokeSelectDropDown.getItems().addAll(DatabaseAccess.getDBPokeList());
        pokeSelectDropDown.setValue(pokeSelectDropDown.getItems().get(0));
        displayPokemon(null);
    }

    private void clearControls() {
        currentPokeLabel.setText("");
        pokemonImageView.setImage(null);
        pokedexTextLabel.setText("");

        typePrimaryImageView.setImage(null);
        typeSecondaryImageView.setImage(null);

        abilityMainHyperlink.setText("");
        abilitySecondHyperlink.setText("");
        abilityHiddenHyperlink.setText("");

        setControlsVisible(typeControls, false);
        setControlsVisible(abilityControls, false);

        movesTableView.getItems().clear();
        evolutionTableView.getItems().clear();
    }

    public void displayPokemon(ActionEvent actionEvent) {
        int id = pokeSelectDropDown.getValue().getId();
        displayPokemon(id);
    }

    public void displayPokemon(int id) {
        clearControls();

        if (pokeSelectDropDown.getValue().getId() != id)
        {
            for (DBPokemon poke : pokeSelectDropDown.getItems()) {
                if (poke.getId() == id) {
                    pokeSelectDropDown.setValue(poke);
                }
            }
        }

        Pokemon poke = DatabaseAccess.getPokemon(id);

        currentPokeLabel.setText(poke.getName());
        pokemonImageView.setImage(poke.getIcon());
        pokedexTextLabel.setText(poke.getPokedexText());

        Type tPrimary = null;
        Type tSecondary = null;

        for (Type t : poke.getTypes()) {
            if (t.getKind() == Type.Kind.PRIMARY)
                tPrimary = t;
            else
                tSecondary = t;
        }

        typeLabel.setVisible(true);
        if (tPrimary != null) {
            typePrimaryImageView.setImage(tPrimary.getTypeIcon());
            typePrimaryImageView.setVisible(true);
        }

        if (tSecondary != null) {
            typeSecondaryImageView.setImage(tSecondary.getTypeIcon());
            typeSecondaryImageView.setVisible(true);
        }

        Ability aPrimary = null;
        Ability aSecondary = null;
        Ability aHidden = null;

        for (Ability a : poke.getAbilities()) {
            if (a.isHidden())
                aHidden = a;
            else if (aPrimary == null)
                aPrimary = a;
            else if (aSecondary == null)
                aSecondary = a;
        }

        if (aPrimary != null) {
            abilityLabel.setVisible(true);
            abilityMainHyperlink.setText(aPrimary.getAbilityName());
            abilityMainHyperlink.getTooltip().setText(aPrimary.getAbilityText());
            abilityMainHyperlink.setVisible(true);
        }
        if (aSecondary != null) {
            abilitySecondHyperlink.setText(aSecondary.getAbilityName());
            abilitySecondHyperlink.setVisible(true);
            abilitySecondHyperlink.getTooltip().setText(aSecondary.getAbilityText());
            abilityOrLabel.setVisible(true);
        }
        if (aHidden != null) {
            abilityHiddenHyperlink.setText(aHidden.getAbilityName());
            abilityHiddenHyperlink.getTooltip().setText(aHidden.getAbilityText());
            abilityHiddenLabel.setVisible(true);
            abilityHiddenHyperlink.setVisible(true);
        }

        movesTableView.setItems(FXCollections.observableList(poke.getMoves()));

        List<Evolution> evolutions = poke.getEvolutions();
        if (evolutions.size() == 0) {
            evolutionTableView.setPlaceholder(new Label("This Pokemon has no evolutions."));
        } else {
            evolutionTableView.setItems(FXCollections.observableList(evolutions));
        }
    }

    //i dont like your style
    public void hLinkClicked(ActionEvent actionEvent) {
        Hyperlink source = (Hyperlink) actionEvent.getSource();
        source.setVisited(false);
    }

    private void setControlsVisible(List<Node> controls, boolean visible) {
        for (Node n : controls) {
            n.setVisible(visible);
        }
    }


}
