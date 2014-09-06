package me.currencies.common;

public enum CommonVariables {
    CURRENCY_SERVER_URL("http://www.boi.org.il/currency.xml"),
    LOCAL_FILE_PATH("CURRENCIES.XML"),
    SYNC_INTERVAL("5");

    private final String text;

    /**
     * set common variable
     *
     * @param text value of variable
     */
    private CommonVariables(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}