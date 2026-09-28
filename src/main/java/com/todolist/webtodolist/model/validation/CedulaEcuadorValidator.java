package com.todolist.webtodolist.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CedulaEcuadorValidator implements ConstraintValidator<CedulaEcuador, String> {
    @Override
    public boolean isValid(String cedula, ConstraintValidatorContext context) {

        if (cedula == null || cedula.isBlank()) {
            return true;
        }

        if (!cedula.matches("\\d{10}")) {
            return false;
        }

        int provincia = Integer.parseInt(
                cedula.substring(0, 2)
        );

        if (provincia < 1 || provincia > 24) {
            return false;
        }

        int tercerDigito = Integer.parseInt(
                cedula.substring(2, 3)
        );

        if (tercerDigito > 5) {
            return false;
        }

        int suma = 0;

        for (int i = 0; i < 9; i++) {

            int digito = Character.getNumericValue(
                    cedula.charAt(i)
            );

            if (i % 2 == 0) {

                int resultado = digito * 2;

                if (resultado >= 10) {
                    resultado -= 9;
                }

                suma += resultado;

            } else {

                suma += digito;
            }
        }

        int siguienteDecena =
                ((suma / 10) + 1) * 10;

        int digitoVerificador =
                siguienteDecena - suma;

        if (digitoVerificador == 10) {
            digitoVerificador = 0;
        }

        int ultimoDigito =
                Character.getNumericValue(
                        cedula.charAt(9)
                );

        return digitoVerificador == ultimoDigito;
    }

}
