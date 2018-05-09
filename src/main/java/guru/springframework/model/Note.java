package guru.springframework.model;

public class Note {

    private Long id;
    private Recipe recipe;
    private String note;

    public Note() {
    }

    public Note(Recipe recipe, String note) {
        this.recipe = recipe;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
