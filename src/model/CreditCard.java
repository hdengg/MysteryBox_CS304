package model;


import java.lang.reflect.Field;
import java.sql.Date;

/**
 * Created by janicelee on 2018-03-24.
 */

public class CreditCard {
    private int cid;
    private Date expDate;
    private String token;
    private String type;
    private int lastDigits;

    public CreditCard(int cid, Date expDate, String token, String type, int lastDigits) {
        this.cid = cid;
        this.expDate = expDate;
        this.token = token;
        this.type = type;
        this.lastDigits = lastDigits;
    }

    public int getCid() {
        return cid;
    }

    public Date getExpDate() {
        return expDate;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public int getLastDigits() {
        return lastDigits;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }
}
