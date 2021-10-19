package com.example.mymanager;

public class Casi {

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDescrizione() {
            return descrizione;
        }

        public void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }

        public String getEsame() {
            return esame;
        }

        public void setEsame(String esame) {
            this.esame = esame;
        }

        public String getMatricolaProfessore() {
            return matricolaProfessore;
        }

        public void setMatricolaProfesssore(String matricolaProfessore) {
            this.matricolaProfessore = matricolaProfessore;
        }

        public Casi(String id, String nome, String descrizione, String esame, String matricolaProfessore) {
            this.id = id;
            this.nome = nome;
            this.descrizione = descrizione;
            this.esame = esame;
            this.matricolaProfessore = matricolaProfessore;
        }

        private static String id;
        private static String nome;
        private static String descrizione;
        private static String esame;
        private static String matricolaProfessore;
}
