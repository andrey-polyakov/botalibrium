package botalibrium.dta.input.bulk;

import lombok.Data;

import java.util.Date;

@Data
public class PopulationLogDto {
    private int died = 0;
    private int removed = 0;
    private int germinated = 0;
    private Date date = new Date();

}
