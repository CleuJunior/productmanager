package br.com.cleonildo.productmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum CategoryType {
    NORMAL("Normal"),
    SPECIAL("Special"),
    CUSTOM("Personalizado");

    private final String translatedName;

}