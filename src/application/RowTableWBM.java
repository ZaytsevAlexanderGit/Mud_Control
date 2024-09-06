package application;

public class RowTableWBM {
    
    
    String location;
    String fieldWell;
    String date;
    String mudLost;
    String mudLeft;
    String mudMade;
    String caCO3;
    String lubric;
    String kCl;
    String krakhmal;
    String barite;
    String naCl;
    String ingib1;
    String ingib2;
    String centrifuge;
    
    public RowTableWBM(String date,String mudLost,String mudMade,String caCO3,String lubric,String kCl,
             String krakhmal,String barite,String naCl,String ingib1,String ingib2, String centrifuge,String mudLeft){
        this.date=date;
        this.mudLost=mudLost;
        this.mudLeft=mudLeft;
        this.mudMade=mudMade;
        this.caCO3=caCO3;
        this.lubric=lubric;
        this.kCl=kCl;
        this.krakhmal=krakhmal;
        this.barite=barite;
        this.naCl=naCl;
        this.ingib1=ingib1;
        this.ingib2=ingib2;
        this.centrifuge=centrifuge;
}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMudLost() {
        return mudLost;
    }

    public void setMudLost(String mudLost) {
        this.mudLost = mudLost;
    }

    public String getMudLeft() {
        return mudLeft;
    }

    public void setMudLeft(String mudLeft) {
        this.mudLeft = mudLeft;
    }

    public String getMudMade() {
        return mudMade;
    }

    public void setMudMade(String mudMade) {
        this.mudMade = mudMade;
    }

    public String getCaCO3() {
        return caCO3;
    }

    public void setCaCO3(String caCO3) {
        this.caCO3 = caCO3;
    }

    public String getLubric() {
        return lubric;
    }

    public void setLubric(String lubric) {
        this.lubric = lubric;
    }

    public String getkCl() {
        return kCl;
    }

    public void setkCl(String kCl) {
        this.kCl = kCl;
    }

    public String getCentrifuge() {
        return centrifuge;
    }

    public void setCentrifuge(String centrifuge) {
        this.centrifuge = centrifuge;
    }

    public String getKrakhmal() {
        return krakhmal;
    }

    public void setKrakhmal(String krakhmal) {
        this.krakhmal = krakhmal;
    }

    public String getBarite() {
        return barite;
    }

    public void setBarite(String barite) {
        this.barite = barite;
    }

    public String getNaCl() {
        return naCl;
    }

    public void setNaCl(String naCl) {
        this.naCl = naCl;
    }
    
    public String getIngib1() {
        return ingib1;
    }

    public void setIngib1(String ingib1) {
        this.ingib1 = ingib1;
    }

    public String getIngib2() {
        return ingib2;
    }

    public void setIngib2(String ingib2) {
        this.ingib2 = ingib2;
    }
  
   }
