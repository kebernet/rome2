/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.java.rome2.modules.impl;

import net.java.rome2.modules.ModuleProvider;
import net.java.rome2.modules.ParserContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This is a default implementation of a ROME ParserContext that will attempt to
 * load ModuleProvider implementations based on the JAR SPI system.
 *
 * @author kebernet
 */
public class DefaultParserContext extends ParserContext {
    public static final String UNEXPECTED_EXCEPTION = "Unexpected exception";
    private static final Set<ModuleProvider> PROVIDERS = new HashSet<ModuleProvider>();

    static {
        try {
            Enumeration<URL> serviceDefs = Thread.currentThread()
                                                 .getContextClassLoader()
                                                 .getResources("META-INF/services/" +
                    ModuleProvider.class.getCanonicalName());

            while (serviceDefs.hasMoreElements()) {
                InputStream is = serviceDefs.nextElement().openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                            is));
                String line = reader.readLine();

                while (line != null) {
                    int commentStart = line.indexOf("#");
                    String className = line.substring(0,
                            (commentStart == -1) ? line.length() : commentStart)
                                           .trim();
                    Logger.getLogger(DefaultParserContext.class.getName())
                          .log(Level.FINEST,
                        "Read: " + line);

                    if (className.length() == 0) {
                        line = reader.readLine();
                        continue;
                    }

                    Logger.getLogger(DefaultParserContext.class.getName())
                          .log(Level.INFO,
                        "Rome found " + ModuleProvider.class.getSimpleName() +
                        ": " + className);

                    try {
                        PROVIDERS.add((ModuleProvider) Class.forName(className)
                                                            .newInstance());
                    } catch (InstantiationException ex) {
                        Logger.getLogger(DefaultParserContext.class.getName())
                              .log(Level.SEVERE, UNEXPECTED_EXCEPTION, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(DefaultParserContext.class.getName())
                              .log(Level.SEVERE, UNEXPECTED_EXCEPTION, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(DefaultParserContext.class.getName())
                              .log(Level.SEVERE, UNEXPECTED_EXCEPTION, ex);
                    } catch (ClassCastException ex) {
                        Logger.getLogger(DefaultParserContext.class.getName())
                              .log(Level.SEVERE,
                            "Unable to cast " + className + " to " +
                            ModuleProvider.class.getCanonicalName(), ex);
                    }
                    line = reader.readLine();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DefaultParserContext.class.getName())
                  .log(Level.SEVERE,
                "Exception reading service provider files", ex);
        }
    }

    public DefaultParserContext() {
        if (PROVIDERS.size() == 0) {
            Logger.getLogger(DefaultParserContext.class.getName())
                  .log(Level.WARNING, "No Rome modules found.");
        }

        this.setModuleProviders(PROVIDERS);
    }
}
