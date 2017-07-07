package botalibrium.dta.input.bulk;

import botalibrium.entity.embedded.records.Record;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apolyakov on 6/16/2017.
 */
@Data
public class InsertRecordsInBulk {
    private Map<String, CountLogDto> tagsToCountLog = new HashMap<>();
    private boolean preview = true;
    private Record recordToBeInserted;

}
