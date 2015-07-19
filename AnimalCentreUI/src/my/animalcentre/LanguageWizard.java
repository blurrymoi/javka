/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package my.animalcentre;


import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author blurry
 */
public class LanguageWizard {
    //private static final String DEF_SETTINGS = "Main/resources/default_locales_"+Locale.getDefault().toString();
    private static final String DEF_SETTINGS = "Main/resources/Bundle_"+Locale.getDefault().toString();

//private static final String DEF_SETTINGS = "Main/resources/default_locales_cs";

    static public String getString(String key) {
        return ResourceBundle.getBundle(DEF_SETTINGS).getString(key);
    }
}