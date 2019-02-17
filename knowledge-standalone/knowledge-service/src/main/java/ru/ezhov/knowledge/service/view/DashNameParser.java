package ru.ezhov.knowledge.service.view;

public class DashNameParser implements NameParser {

    @Override
    public String name(String originalName) {
        if (originalName == null || originalName.isEmpty()) return "";
        if (!originalName.contains("-")) return "";
        String[] strings = originalName.split("-");
        String text = strings[1];
        return "".equals(text) ? originalName : text;
    }
}
