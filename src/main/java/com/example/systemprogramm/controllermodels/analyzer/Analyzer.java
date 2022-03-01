package com.example.systemprogramm.controllermodels.analyzer;

import java.util.*;

/**
 * Класс для проверки синтаксической конструции на принадлежность к циклу while или if
 * и проверки выполнения их логических условий
 */
public class Analyzer {

    public static HashMap<String, Integer> intMap = new HashMap<>();
    public static HashMap<String, Double> doubleMap = new HashMap<>();
    public static HashMap<String, String> stringMap = new HashMap<>();
    public static HashMap<String, Boolean> booleanMap = new HashMap<>();

    /**
     * Метод для проверки строки  строки на принадлежность к циклу while и проверки того выполнится ли он хотя бы раз
     * @param mainString строка для анализа
     * @return в случии если строка верна будет возвращено true для случая если код в цикле while выполнится хотя бы раз
     *  и else в другом случае. Если строка не верна будет возвращен MyException с текстом ошибки
     */
    public static boolean analyze(String mainString) {
        int i = 0;
        try {
            mainString.replaceAll("[\\s]{2,}", " ").trim();
            if(mainString.equals("")) throw new AnalyzeException("Данная конструкция не содержит цикл while");
            String[] mas = mainString.split("while[\\s]*\\(");
            if (mas.length != 2) throw new AnalyzeException("Неккорректная структура цикла while");
            mas[1] = "("+mas[1];
            if (mas[0].trim().lastIndexOf(";") != mas[0].trim().length() - 1) throw new AnalyzeException("Отсутствует ;");
            String[] initMas = mas[0].trim().split(";");
            for (; i < initMas.length; i++) {
                if (initMas[i].trim().equals("")) continue;
                analyzeInit(initMas[i]);
            }
            toStringMap();
            return analyzeWhile(mas[1].trim());

        } catch (Throwable e) {
            throw new AnalyzeException(e.getMessage());
        } finally {
            clearMaps();
        }
    }

    /**
     * Метод для проверки строки  строки на принадлежность к конструкции if и проверки того какой блок выполнится
     * @param mainString строка для анализа
     * @return в случии если строка верна будет возвращена строка "if" если выполнится блок if
     * else если выполнится блок else и null если ни один из блоков не будет выполнен. Если строка не верна будет возвращен MyException с текстом ошибки
     */
    public static String analyze2(String mainString) {
        try {
            int i = 0;
            try {
                String[] masRes = new String[3];
                mainString.replaceAll("[\\s]{2,}", " ").trim();
                String[] mas = mainString.split("if[\\s]*\\(");
                if (mas.length != 2) throw new AnalyzeException("Данная конструкция либо не содержит if, либо содержит больше одного");
                masRes[0] = mas[0].trim();
                try {
                    String[] mas2 = mas[1].split("else[\\s]*\\{");
                    masRes[1] = "("+mas2[0];
                    masRes[2] = mas2[1].trim().substring(0, mas2[1].trim().length()-1);
                }
                catch (Throwable e){
                    masRes[1] = "(" + mas[1];
                }
                if (masRes[0].trim().lastIndexOf(";") != masRes[0].trim().length() - 1) throw new AnalyzeException("Отсутствует ;");
                String[] initMas = masRes[0].trim().split(";");
                for (; i < initMas.length; i++) {
                    if (initMas[i].trim().equals("")) continue;
                    analyzeInit(initMas[i]);
                }
                toStringMap();
                boolean b = analyzeWhile(masRes[1].trim());
                if(masRes[2] != null){
                    masRes[2] = masRes[2].trim();
                    if (masRes[2].lastIndexOf(";") != masRes[2].length() - 1) throw new AnalyzeException("Отсутствует ; в теле оператора");
                    String[] elseStr = masRes[2].split(";");
                    for (String s : elseStr){
                        if (s.equals("")) continue;
                        analyzeInit(s.trim());
                    }
                }
                if(b) return "if";
                if(masRes[2] == null){return "null";}
                return "else";

            } catch (Throwable e) {
                throw new AnalyzeException(e.getMessage());
            }

        } catch (Throwable e) {
            throw new AnalyzeException(e.getMessage());
        }
        finally {
            clearMaps();
        }
    }

    private static boolean analyzeWhile(String whileString) {
        try {
            if (whileString.charAt(0) != '(' || whileString.charAt(whileString.length() - 1) != '}')
                throw new AnalyzeException("Ошибка в расстановке скобок");
            String[] str = whileString.split("\\)");
            if (str.length != 2) throw new AnalyzeException("Ошибка с количеством )");
            str[1] = str[1].trim();
            if (str[0].length() > 1)
                str[0] = str[0].substring(1);
            else str[0] = "";
            if (str[1].charAt(0) != '{') throw new AnalyzeException("Ошибка в расстановке скобок");
            str[1] = str[1].substring(1, str[1].length() - 1);
            if (str[0].trim().equals("")) throw new AnalyzeException("Условие не может быть пустым");
            String[] a = str[0].replaceAll("[|]{2,}", "|").replaceAll("[\\&]{2,}", "&").split("[&|]");
            List<String> list = getRazd(str[0]);
            if (list.size() != a.length - 1) throw new AnalyzeException("Ошибка в условии");
            boolean res = false;
            for (int i = 0; i < a.length; i++) {
                boolean newbool = analyzeWhileCriterion(a[i].trim());
                if (i == 0) res = newbool;
                else if (list.get(i - 1).equals("|") && !res && newbool) {
                    res = true;
                } else if (list.get(i - 1).equals("&") && res && !newbool) {
                    res = false;
                }
            }
            str[1] = str[1].trim();
            if(str[1].equals("")){ return res;}
            else if (str[1].lastIndexOf(";") != str[1].length() - 1) throw new AnalyzeException("Отсутствует ; в теле оператора");
            String[] bodyWhile = str[1].split(";");
            for (String s : bodyWhile) analyzeInit(s.trim());
            return res;
        } catch (Throwable e) {
            throw new AnalyzeException(e.getMessage());
        }

    }

    private static List<String> getRazd(String str) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == '&' || str.charAt(i) == '|') {
                list.add(String.valueOf(str.charAt(i)));
                i++;
            }
        return list;
    }

    private static String getZnak(String s) {
        String res = "";
        char[] znaks = {'<', '>', '=', '!'};
        for (int i = 0; i < s.length(); i++) {
            for (char znak : znaks)
                if (s.charAt(i) == znak) {
                    res = res + znak;
                }
        }
        return res;
    }


    private static boolean analyzeWhileCriterion(String init) {
        String znak = getZnak(init);
        String[] analiz = null;
        if (!znak.equals("")) {
            analiz = init.split(znak);
            analiz[0] = analiz[0].trim();
            analiz[1] = analiz[1].trim();
        }
        double a, b;
        try {
            switch (znak) {
                case "<": {
                    if (intMap.containsKey(analiz[0])) a = intMap.get(analiz[0]);
                    else if (doubleMap.containsKey(analiz[0])) a = doubleMap.get(analiz[0]);
                    else a = Double.parseDouble(analiz[0]);
                    if (intMap.containsKey(analiz[1])) b = intMap.get(analiz[1]);
                    else if (doubleMap.containsKey(analiz[1])) b = doubleMap.get(analiz[1]);
                    else b = Double.parseDouble(analiz[1]);
                    return a < b;
                }
                case ">": {
                    if (intMap.containsKey(analiz[0])) a = intMap.get(analiz[0]);
                    else if (doubleMap.containsKey(analiz[0])) a = doubleMap.get(analiz[0]);
                    else a = Double.parseDouble(analiz[0]);
                    if (intMap.containsKey(analiz[1])) b = intMap.get(analiz[1]);
                    else if (doubleMap.containsKey(analiz[1])) b = doubleMap.get(analiz[1]);
                    else b = Double.parseDouble(analiz[1]);
                    System.out.println(a);
                    System.out.println(b);
                    return a > b;
                }
                case ">=": {
                    if (intMap.containsKey(analiz[0])) a = intMap.get(analiz[0]);
                    else if (doubleMap.containsKey(analiz[0])) a = doubleMap.get(analiz[0]);
                    else a = Double.parseDouble(analiz[0]);
                    if (intMap.containsKey(analiz[1])) b = intMap.get(analiz[1]);
                    else if (doubleMap.containsKey(analiz[1])) b = doubleMap.get(analiz[1]);
                    else b = Double.parseDouble(analiz[1]);
                    return a >= b;
                }
                case "<=": {
                    if (intMap.containsKey(analiz[0])) a = intMap.get(analiz[0]);
                    else if (doubleMap.containsKey(analiz[0])) a = doubleMap.get(analiz[0]);
                    else a = Double.parseDouble(analiz[0]);
                    if (intMap.containsKey(analiz[1])) b = intMap.get(analiz[1]);
                    else if (doubleMap.containsKey(analiz[1])) b = doubleMap.get(analiz[1]);
                    else b = Double.parseDouble(analiz[1]);
                    return a <= b;
                }
                case "==": {
                    String[] n1 = getInfo(analiz[0]);
                    String[] n2 = getInfo(analiz[1]);
                    String type1 = n1[0];
                    String type2 = n2[0];
                    if (type1.equals("null")) {
                        if (isDigit(analiz[0]))
                            type1 = "int";
                        else if (analiz[0].equals("true") || analiz[0].equals("false"))
                            type1 = "boolean";
                        else if (analiz[0].charAt(0) == '"' && analiz[0].charAt(analiz[0].length() - 1) == '"')
                            type1 = "string";
                    }
                    if (type2.equals("null")) {
                        if (isDigit(analiz[1]))
                            type2 = "int";
                        else if (analiz[1].equals("true") || analiz[1].equals("false"))
                            type2 = "boolean";
                        else if (analiz[1].charAt(0) == '"' && analiz[1].charAt(analiz[1].length() - 1) == '"')
                            type2 = "string";
                    }
                    if (type1 != type2 || type1.equals("null"))
                        throw new AnalyzeException("Ошибка в условии (не совпадают типы)");
                    if (type1 != "int" && type1 != "double")
                        return n1[1].equals(n2[1]);
                    return Double.parseDouble(n1[1]) == Double.parseDouble(n2[1]);
                }
                case "!=": {
                    String[] n1 = getInfo(analiz[0]);
                    String[] n2 = getInfo(analiz[1]);
                    String type1 = n1[0];
                    String type2 = n2[0];
                    if (type1.equals("null")) {
                        if (isDigit(analiz[0]))
                            type1 = "int";
                        else if (analiz[0].equals("true") || analiz[0].equals("false"))
                            type1 = "boolean";
                        else if (analiz[0].charAt(0) == '"' && analiz[0].charAt(analiz[0].length() - 1) == '"')
                            type1 = "string";
                    }
                    if (type2.equals("null")) {
                        if (isDigit(analiz[1]))
                            type2 = "int";
                        else if (analiz[1].equals("true") || analiz[1].equals("false"))
                            type2 = "boolean";
                        else if (analiz[1].charAt(0) == '"' && analiz[1].charAt(analiz[1].length() - 1) == '"')
                            type2 = "string";
                    }
                    if (!type1.equals(type2) || type1.equals("null"))
                        throw new AnalyzeException("Ошибка в условии (не совпадают типы)");
                    if (!type1.equals("int") && type1 != "double")
                        return !n1[1].equals(n2[1]);
                    return Double.parseDouble(n1[1]) != Double.parseDouble(n2[1]);
                }
                case "": {
                    if (init.equals("true") || init.equals("false"))
                        return Boolean.parseBoolean(init);
                    if (booleanMap.containsKey(init))
                        return booleanMap.get(init);
                    throw new AnalyzeException("Ошибка в условии");
                }
                default:
                    throw new AnalyzeException("Ошибка со знакам(и)");
            }
        } catch (Throwable ex) {
            throw new AnalyzeException("Ошибка в условии");
        }
    }

    private static String[] getInfo(String name) {
        if (!intMap.containsKey(name))
            if (!doubleMap.containsKey(name))
                if (!stringMap.containsKey(name))
                    if (!booleanMap.containsKey(name)) {
                        return new String[]{"null", name};
                    } else return new String[]{"bool", String.valueOf(booleanMap.get(name))};
                else return new String[]{"string", stringMap.get(name)};
            else return new String[]{"int", String.valueOf(doubleMap.get(name))};
        else return new String[]{"int", String.valueOf(intMap.get(name))};
    }

    private static boolean isDigit(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }

    private static boolean plusplusChek(String param) {
        if (param.contains(" ")) return false;
        String paramName = param.substring(0, param.length() - 2);
        if (param.startsWith("++", param.length() - 2)) {
            if (doubleMap.containsKey(paramName)) {
                doubleMap.replace(paramName, doubleMap.get(paramName) + 1);
                return true;
            } else if (intMap.containsKey(paramName)) {
                intMap.replace(paramName, intMap.get(paramName) + 1);
                return true;
            }
        } else if (param.startsWith("--", param.length() - 2)) {
            if (doubleMap.containsKey(paramName)) {
                doubleMap.replace(paramName, doubleMap.get(paramName) - 1);
                return true;
            } else if (intMap.containsKey(paramName)) {
                intMap.replace(paramName, intMap.get(paramName) - 1);
                return true;
            }
        }
        return false;
    }

    private static void analyzeInit(String init) {
        String[] param = init.trim().split("=");
        if (param.length != 2)
            if (param.length == 1)
                if (!plusplusChek(param[0].trim())) throw new AnalyzeException("Ошибка в операции присваивания: " + init);
                else return;
        String[] firstParam = param[0].trim().split(" ");
        if (firstParam.length != 2) throw new AnalyzeException("Ошибка в синтаксисе :" + param[0]);
        if (mapContainsName(firstParam[1])) throw new AnalyzeException("Имя уже занято: " + firstParam[1]);
        param[1] = param[1].trim();
        if (povtorCheck(firstParam[1]))
            throw new AnalyzeException("Невозможно назвать переменную зарезервированным словом" + firstParam[1]);
        try {
            switch (firstParam[0]) {
                case "int": {
                    try {
                        intMap.put(firstParam[1], Integer.parseInt(param[1].trim()));
                    } catch (Throwable e) {
                        if (intMap.containsKey(param[1]))
                            intMap.put(firstParam[1], intMap.get(param[1]));
                        else throw new AnalyzeException("Неверное значение параметра: " + param[1]);
                    }
                    break;
                }
                case "double": {
                    try {
                        doubleMap.put(firstParam[1], Double.parseDouble(param[1].trim()));
                    } catch (Throwable e) {
                        if (doubleMap.containsKey(param[1]))
                            doubleMap.put(firstParam[1], doubleMap.get(param[1]));
                        else throw new AnalyzeException("Неверное значение параметра: " + param[1]);
                    }
                    break;
                }
                case "string": {
                    if (param[1].charAt(0) != '"' || param[1].charAt(param[1].trim().length() - 1) != '"') {
                        if (stringMap.containsKey(param[1]))
                            stringMap.put(firstParam[1], stringMap.get(param[1]));
                        else throw new AnalyzeException();
                    } else stringMap.put(firstParam[1], param[1].substring(1, param[1].length() - 1));
                    break;
                }
                case "bool": {
                    try {
                        if (param[1].trim().equals("true") || param[1].trim().equals("false"))
                            booleanMap.put(firstParam[1], Boolean.valueOf(param[1].trim()));
                        else throw new AnalyzeException("Неверное значение параметра: " + param[1]);
                    } catch (Throwable e) {
                        if (booleanMap.containsKey(param[1]))
                            booleanMap.put(firstParam[1], booleanMap.get(param[1]));
                        else throw new AnalyzeException("Неверное значение параметра: " + param[1]);
                    }
                    break;
                }
                default:
                    throw new AnalyzeException("Ошибка");
            }
        } catch (Throwable ex) {
            throw new AnalyzeException("Ошибка");
        }
    }

    public static boolean mapContainsName(String name) {
        return intMap.containsKey(name) || booleanMap.containsKey(name) || stringMap.containsKey(name) || doubleMap.containsKey(name);
    }

    public static boolean povtorCheck(String name) {
        String[] error = {"string", "bool", "int", "double", "true", "false", "if", "for", "while", "else"};
        String[] error2 = {"+", "-", ")", "(", "*", "&", "^", "%", "$", "#", "@", "!", ":", "?", "~", "="};
        for (int i = 0; i < error.length; i++) {
            if (Objects.equals(name, error[i])) return true;
        }
        for (int i = 0; i < error2.length; i++) {
            if (name.contains(error2[i])) return true;
        }
        return false;
    }

    private static void clearMaps(){
        intMap.clear();
        booleanMap.clear();
        stringMap.clear();
        doubleMap.clear();
    }

    public static void toStringMap() {
        System.out.println("ИНТЫ");
        for (String s : intMap.keySet()) {
            System.out.println("Ключ - " + s + " Значение - " + intMap.get(s));
        }
        System.out.println("Дублики");
        for (String s : doubleMap.keySet()) {
            System.out.println("Ключ - " + s + " Значение - " + doubleMap.get(s));
        }
        System.out.println("СТРИНГИ");
        for (String s : stringMap.keySet()) {
            System.out.println("Ключ - " + s + " Значение - " + stringMap.get(s));
        }
        System.out.println("Булевы>");
        for (String s : booleanMap.keySet()) {
            System.out.println("Ключ - " + s + " Значение - " + booleanMap.get(s));
        }
    }


}
