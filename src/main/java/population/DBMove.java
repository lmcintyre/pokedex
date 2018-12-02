package population;

public class DBMove {

    private int id;
    private String name;
    private String intName;
    private int type;
    private int power;
    private int accuracy;
    private int category;
    private String text;

    public DBMove() {}

    public DBMove(int id, String name, String intName, int type, int power, int accuracy, int category, String text) {
        this.id = id;
        this.name = name;
        this.intName = intName;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.category = category;
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntName(String intName) {
        this.intName = intName;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIntName() {
        return intName;
    }

    public int getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getCategory() {
        return category;
    }

    public String getText() {
        return text;
    }
}
