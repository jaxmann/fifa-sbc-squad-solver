import java.io.*;

public class Position implements Serializable {

    private String pos;
    private String actual;

    public Position(String pos, String actual) {
        this.pos = pos;
        this.actual = actual;
    }

    public Position deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Position) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Position(String pos) {
        this.pos = pos;
    }

    public String getPos() {
        return pos;
    }

    public String getActual() {
        if (this.actual != null) {
            return actual;
        } else {
            return this.pos;
        }
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

//    GK, LCB, RCB, MCB, RB, RWB, LB, LWB, RCDM, LCDM, MCDM, RCM, LCM, CM, MCM, LCAM, RCAM, MCAM, CAM,
//    RW, RM, RF, LW, LM, LF, CF, LST, RST, CDM, CB
}
