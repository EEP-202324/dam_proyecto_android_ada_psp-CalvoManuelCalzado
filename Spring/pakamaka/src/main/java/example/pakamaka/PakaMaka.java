package example.pakamaka;

import org.springframework.data.annotation.Id;

record PakaMaka (@Id Long id, String name, String surname, String email, int number, String Country) {

}
             
