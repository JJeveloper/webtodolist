package com.todolist.webtodolist.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CedulaEcuadorValidator.class)//En la clase CedulaEcuadorValidator esta la logica de la anotacion @CedulaEcuador()
@Target(ElementType.FIELD)//Indica que la logica de sebe aplicar a un campo
@Retention(RetentionPolicy.RUNTIME)//Significa que la anotación debe estar disponible durante la ejecución del programa.
public @interface CedulaEcuador {

    String message() default "La cédula ingresada no es válida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
