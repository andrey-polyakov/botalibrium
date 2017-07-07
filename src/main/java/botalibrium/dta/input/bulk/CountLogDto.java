package botalibrium.dta.input.bulk;

import java.util.Date;

/**
 * Created by apolyakov on 7/6/2017.
 */
public class CountLogDto {

    private int died = 0;
    private int sold = 0;
    private int germinated = 0;
    private Date date = new Date();

    public int getDied() {
        return died;
    }

    public void setDied(int died) {
        this.died = died;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getGerminated() {
        return germinated;
    }

    public void setGerminated(int germinated) {
        this.germinated = germinated;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
