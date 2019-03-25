package ru.jacklumers.utils;

import java.util.Map;

/**
 * Класс-утилита для генерации SQL SELECT запросов к определенной таблице.
 */
public final class SqlSelectQueryGenerator {

    /**
     * @param table              - таблица для обращения SELECT
     * @param columnsArgsHashMap - аргументы запроса в формате колонка-значение
     * @return Сгенерированный запрос, включающий только атрибуты указанной таблицы, без связанных таблиц.
     */
    public static String generateSelectQuery(String table, Map<String, String> columnsArgsHashMap) {
        StringBuilder whereColumnsBuilder = new StringBuilder();
        StringBuilder whereArgsBuilder = new StringBuilder();

        whereColumnsBuilder.append('(');
        whereArgsBuilder.append('(');
        //Ключи
        String[] keys = columnsArgsHashMap.keySet().toArray(new String[0]);
        for (int i = 0; i < keys.length - 1; i++) {
            whereColumnsBuilder.append(keys[i]).append(", ");
            whereArgsBuilder.append('\'')
                    .append(columnsArgsHashMap.get(keys[i]))
                    .append('\'').append(", ");
        }
        whereColumnsBuilder.append(keys[keys.length - 1]).append(')');
        whereArgsBuilder.append('\'')
                .append(columnsArgsHashMap.get(keys[keys.length - 1]))
                .append('\'').append(')');

        //language=SQL
        return "SELECT * FROM "
                + table
                + " WHERE "
                + whereColumnsBuilder.toString()
                + " = "
                + whereArgsBuilder.toString();

    }

    /**
     * Добавляет ORDER BY к составленному SQL запросу
     * с указанными колонками в качестве выражения сортировки.
     *
     * @param query - составленный SQL запрос
     * @param orderByColumns - колонки по которым проводится сортировка
     * @return SQL запрос с составленным ORDER BY
     */
    public static String addOrderToQuery(String query, String[] orderByColumns) {
        StringBuilder queryBuilder = new StringBuilder(query);
        queryBuilder.append(" ORDER BY ");
        for (int i = 0; i < orderByColumns.length - 1; i++) {
            queryBuilder.append(orderByColumns[i]);
            queryBuilder.append(", ");
        }
        queryBuilder.append(orderByColumns[orderByColumns.length - 1]);
        return queryBuilder.toString();
    }
}
