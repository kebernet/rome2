package net.java.rome2.atom.io;

import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomFeed;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AtomReader {

    Map<String, String> getNamespacePrefixMap();

    List<String> getReportingList();

    void setReportingList(List<String> reportingList);

    String getEncoding();

    AtomFeed readFeed() throws IOException, AtomException;

    AtomEntry readEntry() throws IOException, AtomException;

    int getEntryCount();

    void close() throws IOException, AtomException;

}
