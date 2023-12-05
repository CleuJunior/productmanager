package br.com.cleonildo.productmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum CategoryType {
    NORMAL("Normal"),
    SPECIAL("Especial"),
    CUSTOM("Personalizado");

    private final String typeName;

}