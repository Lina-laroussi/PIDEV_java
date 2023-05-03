package entities;


public class Consultation {  
    private int idConslt, idRdv;
    private float poids, taille, imc, temperature, prix, pression_arterielle, frequence_cardiaque, taux_glycemie;
    private String maladie, traitement, observation, patientName;
    private RendezVous rendezVous;
    private FicheMedicale ficheMedicale;
    private Ordonnance ordonnance;

    public Consultation() {
        
    }

    public Consultation(int idConslt, String patientName, float poids, float taille, float imc, float temperature, float prix, float pression_arterielle, float frequence_cardiaque, float taux_glycemie, String maladie, String traitement, String observation, FicheMedicale ficheMedicale, Ordonnance ordonnance) {
        this.idConslt = idConslt;
        this.patientName = patientName;
        this.poids = poids;
        this.taille = taille;
        this.imc = imc;
        this.temperature = temperature;
        this.prix = prix;
        this.pression_arterielle = pression_arterielle;
        this.frequence_cardiaque = frequence_cardiaque;
        this.taux_glycemie = taux_glycemie;
        this.maladie = maladie;
        this.traitement = traitement;
        this.observation = observation;
        this.ficheMedicale = ficheMedicale;
        this.ordonnance = ordonnance;
    }
    public Consultation(int idConslt, float poids, float taille, float prix, String maladie, String traitement) {
        this.idConslt = idConslt;
        this.poids = poids;
        this.taille = taille;
        this.prix = prix;
        this.maladie = maladie;
        this.traitement = traitement;
    }

    public Consultation(int idConslt,float poids, float taille, float imc, float temperature, float prix, float pression_arterielle, float frequence_cardiaque, float taux_glycemie, String maladie, String traitement, String observation) {
        this.idConslt = idConslt;
        this.poids = poids;
        this.taille = taille;
        this.imc = imc;
        this.temperature = temperature;
        this.prix = prix;
        this.pression_arterielle = pression_arterielle;
        this.frequence_cardiaque = frequence_cardiaque;
        this.taux_glycemie = taux_glycemie;
        this.maladie = maladie;
        this.traitement = traitement;
        this.observation = observation;
    }
    public Consultation(float poids, float taille, float imc, float temperature, float prix, float pression_arterielle, float frequence_cardiaque, float taux_glycemie, String maladie, String traitement, String observation) {
        this.poids = poids;
        this.taille = taille;
        this.imc = imc;
        this.temperature = temperature;
        this.prix = prix;
        this.pression_arterielle = pression_arterielle;
        this.frequence_cardiaque = frequence_cardiaque;
        this.taux_glycemie = taux_glycemie;
        this.maladie = maladie;
        this.traitement = traitement;
        this.observation = observation;
    }

    public Consultation(int idConslt, String idPatient, int idRdv, RendezVous rendezVous) {
        this.idConslt = idConslt;
        this.patientName = patientName;
        this.idRdv = idRdv;
        this.rendezVous = rendezVous;
    }
//********************** GETTERS AND SETTERS ****************************

    public FicheMedicale getFicheMedicale() {
        return ficheMedicale;
    }

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }

    public void setFicheMedicale(FicheMedicale ficheMedicale) {
        this.ficheMedicale = ficheMedicale;
    }

    public void setOrdonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
    }
    
    
    
    public String getPatientName() {
        return patientName;
    }

    public int getIdRdv() {
        return idRdv;
    }

    public RendezVous getRendezVous() {
        return rendezVous;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setIdRdv(int idRdv) {
        this.idRdv = idRdv;
    }

    public void setRendezVous(RendezVous rendezVous) {
        this.rendezVous = rendezVous;
    }
    

    
    public int getIdConslt() {
        return idConslt;
    }

    public float getPoids() {
        return poids;
    }

    public float getTaille() {
        return taille;
    }

    public float getImc() {
        return imc;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPression_arterielle() {
        return pression_arterielle;
    }

    public float getFrequence_cardiaque() {
        return frequence_cardiaque;
    }

    public float getTaux_glycemie() {
        return taux_glycemie;
    }

    public float getPrix() {
        return prix;
    }

    public String getMaladie() {
        return maladie;
    }

    public String getTraitement() {
        return traitement;
    }

    public String getObservation() {
        return observation;
    }

    public void setIdConslt(int idConslt) {
        this.idConslt = idConslt;
    }
    
    public void setPoids(float poids) {
        this.poids = poids;
    }

    public void setTaille(float taille) {
        this.taille = taille;
    }

    public void setImc(float imc) {
        this.imc = imc;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setPression_arterielle(float pression_arterielle) {
        this.pression_arterielle = pression_arterielle;
    }

    public void setFrequence_cardiaque(float frequence_cardiaque) {
        this.frequence_cardiaque = frequence_cardiaque;
    }

    public void setTaux_glycemie(float taux_glycemie) {
        this.taux_glycemie = taux_glycemie;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }


       
}
