package ru.jacklumers.utils;

import java.util.Map;

/**
 * Класс-утилита для генерации SQL SELECT запросов к определенной таблице.
 */
public final class SqlSelectQueryGenerator {

    public static String generateSelectQuery(String table, Map<String, String> columnsArgsHashMap) {
        StringBuilder whereColumnsBuilder = new StringBuilder();
        StringBuilder whereArgsBuilder = new StringBuilder();

        whereColumnsBuilder.append('(');
        whereArgsBuilder.append('(');
        //Ключи
        String[] keys = columnsArgsHashMap.keySet().toArray(new String[0]);
        for (String key: keys){
            System.out.println(key);
        }
        for (int i = 0; i<keys.length-1; i++) {
            whereColumnsBuilder.append(keys[i]).append(", ");
            whereArgsBuilder.append('\'')
                    .append(columnsArgsHashMap.get(keys[i]))
                    .append('\'').append(", ");
        }
        whereColumnsBuilder.append(keys[keys.length-1]).append(')');
        whereArgsBuilder.append('\'')
                .append(columnsArgsHashMap.get(keys[keys.length-1]))
                .append('\'').append(')');

        //language=SQL
        return "SELECT * FROM "
                + table
                + " WHERE "
                + whereColumnsBuilder.toString()
                + " = "
                + whereArgsBuilder.toString();

    }
}
