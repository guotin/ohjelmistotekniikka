package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void lisaysToimii() {
        kortti.lataaRahaa(50);
        kortti.lataaRahaa(-150);
        assertEquals(1050, kortti.saldo());
    }
    
    @Test
    public void rahanOttoVahentaaRahaa() {
        kortti.otaRahaa(50);
        assertEquals(950, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuJosOtetaanLiikaa() {
        kortti.otaRahaa(10000);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void palauttaaTrueJosRahatRiitti() {
        assertEquals(true, kortti.otaRahaa(7));
    }
    @Test
    public void palauttaaFalseJosRahatEiRiittanyt() {
        assertEquals(false, kortti.otaRahaa(7000));
    }
}
