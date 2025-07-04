public class Account {
    @JsonField(name = "accountName")
    private String _name;
    @JsonField(name = "isActive")
    private Boolean _isActive;

    public Account(String name){
        _name = name;
        _isActive = true;
    }
}
