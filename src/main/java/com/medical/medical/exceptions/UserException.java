package com.medical.medical.exceptions;

public class UserException extends Exception {
    public static final int DATE_DE_NAISSANCE_INVALIDE = 1;
    public static final int NOM_INCORRECT = 2;
    public static final int PRENOM_INCORRECT = 3;

    public UserException(int numero) {
        super(getErrorMessage(numero));
    }

    private static String getErrorMessage(int numero) {
        switch (numero) {
            case DATE_DE_NAISSANCE_INVALIDE:
                return "Date de naissance invalide";
            case NOM_INCORRECT:
                return "Votre nom est incorrect";
            case PRENOM_INCORRECT:
                return "Votre prénom est incorrect";
            default:
                return "Erreur inconnue";
        }
    }
}
