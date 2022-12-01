package boot.my.first.boot.Rest;


import boot.my.first.boot.controller.Model.Developer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {

    private List<Developer> developerList= Stream.of(
            new Developer(1L,"Ann", "Good"),
            new Developer(2L,"Tom", "Jerry"),
            new Developer(3L,"Lol","SuperLol")
    ).collect(Collectors.toList());

    @GetMapping
    public List<Developer> getAll(){

        return developerList;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers.read')")
    public Developer getById(@PathVariable Long id){

        return developerList.stream().filter(developer -> developer.getId().equals(id))
                .findFirst()
                .orElse(null);

    }

    @PreAuthorize("hasAuthority('developers:write')")
    @PostMapping
    public Developer create(@RequestBody Developer newDeveloper){
        this.developerList.add(newDeveloper);
        return newDeveloper;
    }

    @PreAuthorize("hasAuthority('developers:write')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.developerList.removeIf(developer -> developer.getId().equals(id));

    }


}
