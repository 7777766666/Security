package boot.my.first.boot.controller.Model;

public enum Permission {

    DEVELOPERS_READ("developers:read"),     //Права разработчиков данного типа только просмотр
    DEVELOPERS_WRITE("developers:write");   //Права разработчиков данного типа запись

    private final String permission;

    Permission(String permission){
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }


}
