import java.io.*;

enum BasePosition {
    ST,
    LF,
    GK,
    LCB,
    RCB,
    MCB,
    RB,
    RWB,
    LB,
    LWB,
    RCDM,
    LCDM,
    MCDM,
    RCM,
    LCM,
    CM,
    MCM,
    LCAM,
    RCAM,
    MCAM,
    CAM,
    RW,
    RM,
    RF,
    LW,
    LM,
    CF,
    LST,
    RST,
    CDM,
    CB,
    INVALID //couldn't be bothered handling exception there rn
}

public class Position implements Serializable {

    private BasePosition pos;
    private String actual;

    public Position(String pos, String actual) {
        this.pos = translateStringPosToEnum(pos);
        this.actual = actual;
    }

    public Position(BasePosition pos, String actual) {
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

    public static BasePosition translateStringPosToEnum(String pos) {
        switch(pos.toLowerCase()) {
            case "st":
                return BasePosition.ST;
            case "lf":
                return BasePosition.LF;
            case "gk":
                return BasePosition.GK;
            case "cb":
                return BasePosition.CB;
            case "rb":
                return BasePosition.RB;
            case "lb":
                return BasePosition.LB;
            case "cm":
                return BasePosition.CM;
            case "cdm":
                return BasePosition.CDM;
            case "cam":
                return BasePosition.CAM;
            case "lw":
                return BasePosition.LW;
            case "rw":
                return BasePosition.RW;
            case "lm":
                return BasePosition.LM;
            case "rm":
                return BasePosition.RM;
            case "cf":
                return BasePosition.CF;
            case "rf":
                return BasePosition.RF;
            case "rwb":
                return BasePosition.RWB;
            case "lwb":
                return BasePosition.LWB;
            default:
                return BasePosition.INVALID;
        }
    }

    public Position(String pos)  {
        this.pos = translateStringPosToEnum(pos);
    }

    public Position(BasePosition pos)  {
        this.pos = pos;
    }

    public BasePosition getBasePos() {
        return pos;
    }

    public String getActual() {
        if (this.actual != null) {
            return actual;
        } else {
            return this.pos.toString();
        }
    }

    public void setPos(String pos) {
        this.pos = translateStringPosToEnum(pos);
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

//    GK, LCB, RCB, MCB, RB, RWB, LB, LWB, RCDM, LCDM, MCDM, RCM, LCM, CM, MCM, LCAM, RCAM, MCAM, CAM,
//    RW, RM, RF, LW, LM, LF, CF, LST, RST, CDM, CB
}
