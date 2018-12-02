package database;

public class Move {

    private String moveName;
    private String moveInternalName;
    private Type type;
    private int power;
    private int accuracy;
    private String moveText;
    private Category category;
    private int levelLearned;   //0 is egg move

    public Move() {}

    public Move(String moveName, String moveInternalName, Type type, int power, int accuracy, Category category, int levelLearned) {
        this.moveName = moveName;
        this.moveInternalName = moveInternalName;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.category = category;
        this.levelLearned = levelLearned;
    }

    public String getMoveName() {
        return moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public String getMoveInternalName() {
        return moveInternalName;
    }

    public void setMoveInternalName(String moveInternalName) {
        this.moveInternalName = moveInternalName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public String getMoveText() {
        return moveText;
    }

    public void setMoveText(String moveText) {
        this.moveText = moveText;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getLevelLearned() {
        return levelLearned;
    }

    public void setLevelLearned(int levelLearned) {
        this.levelLearned = levelLearned;
    }
}
