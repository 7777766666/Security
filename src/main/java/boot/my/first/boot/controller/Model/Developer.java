package boot.my.first.boot.controller.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

    @Data   //для получения геттеров и сеттеров через ломбок (без него самому писать надо)
    @AllArgsConstructor
    public class Developer {

        private Long id;
        private String name;
        private String lastName;
}
