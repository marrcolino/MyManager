package com.example.mymanager;

public class Casi {

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCasiDiStudio() {
            return casiDiStudio;
        }

        public void setCasiDiStudio(String casiDiStudio) {
            this.casiDiStudio = casiDiStudio;
        }

        public String getCorso() {
            return corso;
        }

        public void setCorso(String corso) {
            this.corso = corso;
        }

        public String getNomeProf() {
            return nomeProf;
        }

        public void setNomeProf(String nomeProf) {
            this.nomeProf = nomeProf;
        }

        public Casi(String id, String casiDiStudio, String corso, String nomeProf) {
            this.id = id;
            this.casiDiStudio = casiDiStudio;
            this.corso = corso;
            this.nomeProf = nomeProf;
        }

        private String id;
        private String casiDiStudio;
        private String corso;
        private String nomeProf;
}
