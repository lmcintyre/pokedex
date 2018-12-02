package population;

public class DBAbility {

    private int id;
    private String name;
    private String intName;
    private String text;

    public DBAbility() {}

    public DBAbility(int id, String name, String intName, String text) {
        this.id = id;
        this.name = name;
        this.intName = intName;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntName() {
        return intName;
    }

    public void setIntName(String intName) {
        this.intName = intName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
