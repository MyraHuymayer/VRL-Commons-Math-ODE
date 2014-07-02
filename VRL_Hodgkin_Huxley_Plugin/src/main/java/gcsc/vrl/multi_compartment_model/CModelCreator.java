/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gcsc.vrl.multi_compartment_model;

import java.util.*;

/**
 *
 * @author myra
 */
public class CModelCreator {

    //eventuell moechte man die Anzahl der Compartments selber angeben - Waere das denn sinnvoll??
    //dann muesste es Default values fuer die laenge, den radius, r_L usw geben! oder will user 
    //das immer selbst angeben? -natuerlich NICHT!!!, wenn man sehr viele Compartments hat
    // eventuell moechte man aber nur eine gruppe veraendern etc.
    private int totalNum;
    
    private final ArrayList<Compartment> compartmentList = new ArrayList();
    
    private final ArrayList<Edge> edgeList = new ArrayList(); 
    
    /*-----------------------------------------------------------------------------------------------------------------------------*/
    public CModelCreator() {
        
    }
    
     public CModelCreator(int totalNum) {
        this.totalNum = totalNum;
        
        /*hier muessen noch mehrere spezifikationen stattfinden - dh hier werden dann schon alle 
        compartments erstellt! ist das so auch wirklich sauber?
        */
    }
    /* hier werden die Strukturen gebaut! 
        d.h.: alle Compartments werden in ein Array geschrieben
        hier werden die Hogdkin Huxley Formeln benoetigt um im zu berechnen, oder?
        Wobei es dabei vielleicht doch sinnvoller ist dafuer erst mal eine neue
        Funktion zu erstellen, nach Vorlage der HHequs() 
        --> DAS PASSIERT NICHT HIER! DAFUER BRAUCHEN WIR NOCH EINE NEUE KLASSE A LA HHEQUS.JAVA
        -   Note bleibt erst mal hier stehen, bis wir eine neue Klasse erstellt haben
    */
    
    
    /*
        Eventuell kann man es so machen: der user gibt die Edges an --> das sind 
        dann zwei int, die dann jeweils ein compartment kreieren, und die jeweiligen 
        integer = compartment id 
        DAS IST ZIEMLICHER BLOEDSINN, ABER TROTZDEM SOLLTE MAN DEN GEDANKEN NOCH NICHT 
        GANZ VERWERFEN - EVTL KANN MAN DA NOCH WAS DRAUSS MACHEN
    */

   
    
}
