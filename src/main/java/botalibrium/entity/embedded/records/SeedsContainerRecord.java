package botalibrium.entity.embedded.records;

import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

@Data
@Entity
@Embedded
public class SeedsContainerRecord extends ContainerRecord {
    protected int germinated = 0;

}
