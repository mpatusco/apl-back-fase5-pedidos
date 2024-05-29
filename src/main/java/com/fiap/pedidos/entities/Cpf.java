package com.fiap.pedidos.entities;

import com.fiap.pedidos.exceptions.entities.CpfInvalidoException;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Cpf {

    public static final Pattern REGEX_CPF_NUMERO_REPETIDO = Pattern.compile("/([0-9])\1{10}/g");
    private String cpf;
    Cpf() {}

    public Cpf(String cpf) {
        this.cpf = Objects.nonNull(cpf) ? cpf : "";
        if (!isValid()) {
            throw new CpfInvalidoException();
        }
    }

    public String getCpf() {
        return this.cpf;
    }

    @Override
    public String toString() {
        return getCpf();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cpf other = (Cpf) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        return true;
    }

    public boolean isValid() {
        return Objects.nonNull(this.cpf) && (!this.cpf.isEmpty()) && (!this.cpf.isEmpty()) && (this.cpf.length() == 14 || this.cpf.length() == 11);
    }

}
