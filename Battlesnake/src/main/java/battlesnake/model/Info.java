package battlesnake.model;

public record Info(String apiversion,
            String author,
            String color,
            String head,
            String tail) {
    public Info() {
        this("1",
                "schmalzlm",
                "#EC3AAA",
                "default",
                "curled");
    }
}
