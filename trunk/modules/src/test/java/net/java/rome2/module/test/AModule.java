/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.java.rome2.module.test;

import net.java.rome2.modules.Module;


/**
 *
 * @author kebernet
 */
public class AModule implements Module {
    private String testValue;
    private String categoryValue;

    /**
     * Get the value of testValue
     *
     * @return the value of testValue
     */
    public String getTestValue() {
        return this.testValue;
    }

    /**
     * Set the value of testValue
     *
     * @param newtestValue new value of testValue
     */
    public void setTestValue(String newtestValue) {
        this.testValue = newtestValue;
    }

    /**
     * Get the value of categoryValue
     *
     * @return the value of categoryValue
     */
    public String getCategoryValue() {
        return this.categoryValue;
    }

    /**
     * Set the value of categoryValue
     *
     * @param newcategoryValue new value of categoryValue
     */
    public void setCategoryValue(String newcategoryValue) {
        this.categoryValue = newcategoryValue;
    }
}
