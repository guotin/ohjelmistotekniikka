
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(500);
    }
    
    @Test
    public void luotuKassaOlemassa() {
        assertTrue(kassa!=null);      
    }
    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void rahatAlussaOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(500, kortti.saldo());
    }
    
    @Test
    public void lounaitaAlussaMyytyNolla() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void ostoKasvattaaRahaaEdullinen() {
        kassa.syoEdullisesti(400);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void ostoKasvattaaRahaaMaukas() {
        kassa.syoMaukkaasti(1000);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void ostoAntaaOikeinVaihtorahaaEdullinen() { 
        assertEquals(160, kassa.syoEdullisesti(400));
    }
    
    @Test
    public void ostoAntaaOikeinVaihtorahaaMaukas() { 
        assertEquals(100, kassa.syoMaukkaasti(500));
    }
    
    @Test
    public void ostoKasvattaaMyytyjaEdullinen() { 
        kassa.syoEdullisesti(300);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void ostoKasvattaaMyytyjaMaukas() { 
        kassa.syoMaukkaasti(500);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void ostoaEiTehdaAlihintaanEdullinen() { 
        kassa.syoEdullisesti(100);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void ostoaEiTehdaAlihintaanMaukas() { 
        kassa.syoMaukkaasti(100);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void ostoKortillaEdullinen() { 
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void ostoKortillaMaukas() { 
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void ostoKortillaOnnistuuEdullinen() { 
        assertEquals(true, kassa.syoEdullisesti(kortti));
        assertEquals(260, kortti.saldo());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void ostoKortillaOnnistuuMaukas() { 
        assertEquals(true, kassa.syoMaukkaasti(kortti));
        assertEquals(100, kortti.saldo());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassanRahatEiMuutuKortillaEdullinen() { 
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassanRahatEiMuutuKortillaMaukas() { 
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassanRahatKasvaaLatauksessa() {
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, kassa.kassassaRahaa());
    }
    
    @Test
    public void alihintainenPalauttaaKaikkiRahatMaukas() { 
        assertEquals(100, kassa.syoMaukkaasti(100));
    }
    
    @Test
    public void alihintainenPalauttaaKaikkiRahatEdullinen() { 
        assertEquals(100, kassa.syoEdullisesti(100));
    }
    
    @Test
    public void kortiltaEiOtetaRahaaJosEiRiitaMaukas() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void kortiltaEiOtetaRahaaJosEiRiitaEdullinen() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(20, kortti.saldo());
    }
    
    @Test
    public void ostoPalauttaaFalseJosEiRahaaEdullinen() {
        kortti.otaRahaa(500);
        assertEquals(false, kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void ostoPalauttaaFalseJosEiRahaaMaukas() {
        kortti.otaRahaa(500);
        assertEquals(false, kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kassanRahatEiKasvaNegatiivisellaLatauksella() {
        kassa.lataaRahaaKortille(kortti, -1000);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    
}
