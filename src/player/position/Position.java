package player.position;

import java.io.*;

public class Position implements Serializable {

    private BasePosition pos;
    private ActualPosition actual;

    public Position(String pos, String actual) {
        this.pos = translateStringPosToBasePosEnum(pos);
        this.actual = translateStringPosToActualPosEnum(actual);
    }

    public Position(BasePosition pos, String actual) {
        this.pos = pos;
        this.actual = translateStringPosToActualPosEnum(actual);
    }

    public Position(BasePosition pos, ActualPosition actual) {
        this.pos = pos;
        this.actual = actual;
    }

    public Position(String pos, ActualPosition actual) {
        this.pos = translateStringPosToBasePosEnum(pos);
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

    public Position(String pos)  {
        this.pos = translateStringPosToBasePosEnum(pos);
    }

    public Position(BasePosition pos)  {
        this.pos = pos;
    }

    public BasePosition getBasePos() {
        return pos;
    }

    //unique value (i.e. if there are 2 CBs, will return RCB or LCB), so can uniquely identify player by position in a lineup with this func
    public ActualPosition getActualPosition() {
        if (this.actual != null) {
            return actual;
        } else {
            // BasePositions all exist in ActualPositions, but not vice versa
            return translateStringPosToActualPosEnum(this.pos.toString());
        }
    }

    public void setPos(String pos) {
        this.pos = translateStringPosToBasePosEnum(pos);
    }

    public void setPos(BasePosition pos) {
        this.pos = pos;
    }

    public void setActual(ActualPosition actual) {
        this.actual = actual;
    }

    public void setActual(String actual) {
        this.actual = translateStringPosToActualPosEnum(actual);
    }

    public static BasePosition translateStringPosToBasePosEnum(String pos) {
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

    public static ActualPosition translateStringPosToActualPosEnum(String pos) {
        switch(pos.toLowerCase()) {
            case "st":
                return ActualPosition.ST;
            case "rst":
                return ActualPosition.RST;
            case "lst":
                return ActualPosition.LST;
            case "lf":
                return ActualPosition.LF;
            case "gk":
                return ActualPosition.GK;
            case "cb":
                return ActualPosition.CB;
            case "rcb":
                return ActualPosition.RCB;
            case "lcb":
                return ActualPosition.LCB;
            case "mcb":
                return ActualPosition.MCB;
            case "rb":
                return ActualPosition.RB;
            case "lb":
                return ActualPosition.LB;
            case "cm":
                return ActualPosition.CM;
            case "rcm":
                return ActualPosition.RCM;
            case "lcm":
                return ActualPosition.LCM;
            case "mcm":
                return ActualPosition.MCM;
            case "cdm":
                return ActualPosition.CDM;
            case "rcdm":
                return ActualPosition.RCDM;
            case "lcdm":
                return ActualPosition.LCDM;
            case "mcdm":
                return ActualPosition.MCDM;
            case "cam":
                return ActualPosition.CAM;
            case "rcam":
                return ActualPosition.RCAM;
            case "lcam":
                return ActualPosition.LCAM;
            case "mcam":
                return ActualPosition.MCAM;
            case "lw":
                return ActualPosition.LW;
            case "rw":
                return ActualPosition.RW;
            case "lm":
                return ActualPosition.LM;
            case "rm":
                return ActualPosition.RM;
            case "cf":
                return ActualPosition.CF;
            case "rf":
                return ActualPosition.RF;
            case "rwb":
                return ActualPosition.RWB;
            case "lwb":
                return ActualPosition.LWB;
            default:
                return ActualPosition.INVALID;
        }
    }

}
