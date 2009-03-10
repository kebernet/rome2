/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.modules;

/**
 * This is the SPI for a Rome module. It will have init() calls to
 * register itself with a ParserContext or a GeneratorContext.
 *
 * @author kebernet
 */
public interface ModuleProvider {

    void init(ParserContext context);
    
    void init(GeneratorContext context);

}
