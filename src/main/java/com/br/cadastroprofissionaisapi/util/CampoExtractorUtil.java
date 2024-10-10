package com.br.cadastroprofissionaisapi.util;

import com.br.cadastroprofissionaisapi.exception.ValidacaoException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampoExtractorUtil {

    public static Map<String, Object> extrairCampos(Object objeto, List<String> campos) {
        Map<String, Object> mapa = new HashMap<>();
        for (String campo : campos) {
            try {
                String metodoGetter = campo.charAt(0) + campo.substring(1);
                Method metodo = objeto.getClass().getMethod(metodoGetter);
                Object valor = metodo.invoke(objeto);
                mapa.put(campo, valor);
            } catch (Exception e) {
                throw new ValidacaoException("Parâmetros de consulta inválidos");
            }
        }
        return mapa;
    }

    public static List<Map<String, Object>> filtrarCampos(List<?> objetos, List<String> campos) {
        return objetos.stream()
                .map(objeto -> extrairCampos(objeto, campos))
                .toList();
    }

    private CampoExtractorUtil() {
        throw new UnsupportedOperationException("Esta classe não pode ser instanciada.");
    }
}
