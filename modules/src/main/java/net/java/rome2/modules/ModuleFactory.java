/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.java.rome2.modules;


/**
 *
 * @author kebernet
 */
public interface ModuleFactory<T extends Module> {
    T create();
}
