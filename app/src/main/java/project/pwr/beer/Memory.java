package project.pwr.beer;

/**
 * Created by Kamil on 2015-05-29.
 */
public class Memory
{
    public static final String DB_CREATED = "DB_CREATED";

    public static final String APLICATION_NAME = "xxxxxxxxx";

    private static Memory insance;


    private boolean databaseCreated;

    public static Memory getInstance()
    {
        if(insance == null)
        {
            insance = new Memory();
        }

        return insance;
    }

    private Memory()
    {}

    public boolean isDatabaseCreated() {
        return databaseCreated;
    }

    public void setDatabaseCreated(boolean value) {
        databaseCreated = value;
    }
}
