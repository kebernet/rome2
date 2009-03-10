package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.spi.Reporter;

import java.text.MessageFormat;
import java.util.List;

public abstract class ReporterImpl implements Reporter {
    private MessageFormat FEED_ERROR = new MessageFormat("Feed      : {0}");
    private MessageFormat ENTRY_ERROR = new MessageFormat("Entry[{0}]: {1}");

    private List<String> reportingList;

    public List<String> getReportingList() {
        return reportingList;
    }

    public void setReportingList(List<String> errors) {
        this.reportingList = errors;
    }

    public abstract int getEntryCount();

    public boolean isEnabled() {
        return reportingList != null;
    }

    public void report(String msg) {
        if (reportingList != null) {
            if (getEntryCount() == 0) {
                reportingList.add(FEED_ERROR.format(new Object[]{msg}));
            }
            else {
                reportingList.add(ENTRY_ERROR.format(new Object[]{getEntryCount(), msg}));
            }
        }
    }

}
