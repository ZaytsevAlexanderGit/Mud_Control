package application;

public class RowTableOBM {
        
    String location;
    String fieldWell;
    String date;
    String mudLost;
    String mudLeft;
    String mudMade;
    String caCO3;
    String baseOil;
    String caCl2;
    String emilsifier;
    String versaTrol;
    String barite;
    String weigthingAgent;
    String clay;
    String lime;
    
    public RowTableOBM(String date,String mudLost,String mudMade,String caCO3,String baseOil,String caCl2,
             String emilsifier,String versaTrol,String barite,String weigthingAgent, String clay, String lime,String mudLeft){
        this.date=date;
        this.mudLost=mudLost;
        this.mudLeft=mudLeft;
        this.mudMade=mudMade;
        this.caCO3=caCO3;
        this.baseOil=baseOil;
        this.caCl2=caCl2;
        this.emilsifier=emilsifier;
        this.versaTrol=versaTrol;
        this.barite=barite;

        this.weigthingAgent=weigthingAgent;
        this.clay=clay;
        this.lime=lime;
}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFieldWell() {
        return fieldWell;
    }

    public void setFieldWell(String fieldWell) {
        this.fieldWell = fieldWell;
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

    public String getBaseOil() {
        return baseOil;
    }

    public void setBaseOil(String baseOil) {
        this.baseOil = baseOil;
    }

    public String getCaCl2() {
        return caCl2;
    }

    public void setCaCl2(String caCl2) {
        this.caCl2 = caCl2;
    }

    public String getEmilsifier() {
        return emilsifier;
    }

    public void setEmilsifier(String emilsifier) {
        this.emilsifier = emilsifier;
    }

    public String getVersaTrol() {
        return versaTrol;
    }

    public void setVersaTrol(String versaTrol) {
        this.versaTrol = versaTrol;
    }

    public String getBarite() {
        return barite;
    }

    public void setBarite(String barite) {
        this.barite = barite;
    }

    public String getWeigthingAgent() {
        return weigthingAgent;
    }

    public void setWeigthingAgent(String weigthingAgent) {
        this.weigthingAgent = weigthingAgent;
    }

    public String getClay() {
        return clay;
    }

    public void setClay(String clay) {
        this.clay = clay;
    }

    public String getLime() {
        return lime;
    }

    public void setLime(String lime) {
        this.lime = lime;
    }

}
