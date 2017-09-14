package Reports;

/**
 * An Entry is a node which contains a key-value pairing.
 * This is used in replacement of a HashMap, so we can use an ArrayList<Entry>
 * and keep the same structure of the HashMap but while maintaining insertion order.
 *
 * @author Sean Zimmerman
 */
public class Entry {
    private String key;

    private int value;

    /**
     * The constructor for the Entry
     * @param key the String used to identify the Entry
     * @param value the value corresponding to the key
     */
    public Entry(String key, int value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Accessor for key
     * @return the Entry's key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Accessor for value
     * @return the Entry's value
     */
    public int getValue() {
        return this.value;
    }
}
