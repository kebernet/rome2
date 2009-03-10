package net.java.rome2.atom.spi;

import net.java.rome2.utils.DateUtils;

/**
 * @author tucu
 */
public class DateUtilsFactory {
    private String[] additionalMasks;

    public DateUtils createDateUtils() {
        return new DateUtils(additionalMasks);
    }

    public void setAdditionalMasks(String[] masks) {
        additionalMasks = masks;
    }

    public String[] getAdditionalMasks() {
        return additionalMasks.clone();
    }

}
