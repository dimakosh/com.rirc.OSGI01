package com.rirc.OSGI01;

import com.rirc.OSGI01.DataViewStructure.DetailCollection;
import com.rirc.OSGI01.DataViewStructure.DetailFlags;
import com.rirc.OSGI01.DataViewStructure.FieldType;
import com.rirc.OSGI01.DataViewStructure.Detail;
import com.rirc.OSGI01.DataViewStructure.Orientation;
import com.rirc.OSGI01.DataViewStructure.TextLabelCollection;

public class SoftRep {
    public Orientation orientation = Orientation.Portrait;

    final public TextLabelCollection heads = new TextLabelCollection();
    final public DetailCollection details = new DetailCollection();
    final public TextLabelCollection footers = new TextLabelCollection();
    
    public double[] repSums;

	final public ConnPrms connPrms;
	final public String repName;
	final public String repSmallHead;
	final public String repFileName;
	final public KDResultSetable dataCmd;

    public SoftRep(ConnPrms _connPrms, String _repName, String _repSmallHead, String _repFileName, KDResultSetable _dataCmd) {
    	connPrms= _connPrms;
        repName = _repName;
        repSmallHead = _repSmallHead;
        dataCmd = _dataCmd;
        repFileName = _repFileName;
    }
    public SoftRep(ConnPrms _connPrms, String _repName, String _repSmallHead, KDResultSetable _dataCmd) {
    	this(_connPrms, _repName, _repSmallHead, _repName, _dataCmd);
    }
    public SoftRep(ConnPrms _connPrms, String _repName, KDResultSetable _dataCmd) {
    	this(_connPrms, _repName, null, _repName, _dataCmd);
    }
    public SoftRep(String _repName, KDResultSetable _dataCmd) {
    	this(null, _repName, null, _repName, _dataCmd);
    }

    public int DetailN(String _Field, String _Title, int _Width, int _Dec) {
        return details.Add(new Detail(FieldType.N, _Field, _Title, _Width, _Dec));
    }
    public int DetailN(String _Field, String _Title, int _Width, int _Dec, DetailFlags _Df) {
        return details.Add(new Detail(FieldType.N, _Field, _Title, _Width, _Dec, _Df));
    }
    public int DetailN(int _ColOrd, String _Title, int _Width, int _Dec) {
        return details.Add(new Detail(FieldType.N, _ColOrd, _Title, _Width, _Dec));
    }
    public int DetailN(int _ColOrd, String _Title, int _Width, int _Dec, DetailFlags _Df) {
        return details.Add(new Detail(FieldType.N, _ColOrd, _Title, _Width, _Dec, _Df));
    }
    public int DetailN(String _Title, int _Width, int _Dec) {
        return details.Add(new Detail(FieldType.N, -1, _Title, _Width, _Dec));
    }
    public int DetailN(String _Title, int _Width, int _Dec, DetailFlags _Df) {
        return details.Add(new Detail(FieldType.N, -1, _Title, _Width, _Dec, _Df));
    }

    public int DetailN(String _Field, String _Title, int _Width) {
        return details.Add(new Detail(FieldType.N, _Field, _Title, _Width, 0));
    }
    public int DetailN(int _ColOrd, String _Title, int _Width) {
        return details.Add(new Detail(FieldType.N, _ColOrd, _Title, _Width, 0));
    }
    public int DetailN(String _Title, int _Width) {
        return details.Add(new Detail(FieldType.N, -1, _Title, _Width, 0));
    }

    public int DetailC(String _Field, String _Title, int _Width) {
        return details.Add(new Detail(FieldType.C, _Field, _Title, _Width, 0));
    }
    public int DetailC(int _ColOrd, String _Title, int _Width) {
        return details.Add(new Detail(FieldType.C, _ColOrd, _Title, _Width, 0));
    }
    public int DetailC(String _Title, int _Width) {
        return details.Add(new Detail(FieldType.C, -1, _Title, _Width, 0));
    }

    public int DetailD(String _Field, String _Title, int _Width) {
        return details.Add(new Detail(FieldType.D, _Field, _Title, _Width, 0));
    }
    public int DetailD(int _ColOrd, String _Title, int _Width) {
        return details.Add(new Detail(FieldType.D, _ColOrd, _Title, _Width, 0));
    }
    public int DetailD(String _Title, int _Width) {
        return details.Add(new Detail(FieldType.D, -1, _Title, _Width, 0));
    }

    public int DetailD(String _Field, String _Title) {
        return details.Add(new Detail(FieldType.D, _Field, _Title, 10, 0));
    }
    public int DetailD(int _ColOrd, String _Title) {
        return details.Add(new Detail(FieldType.D, _ColOrd, _Title, 10, 0));
    }
    public int DetailD(String _Title) {
        return details.Add(new Detail(FieldType.D, -1, _Title, 10, 0));
    }

    public int DetailDT(String _Field, String _Title, int _Width) {
        return details.Add(new Detail(FieldType.DT, _Field, _Title, _Width, 0));
    }

    public int DetailDT(int _ColOrd, String _Title, int _Width) {
        return details.Add(new Detail(FieldType.DT, _ColOrd, _Title, _Width, 0));
    }
    public int DetailDT(String _Title, int _Width) {
        return details.Add(new Detail(FieldType.DT, -1, _Title, _Width, 0));
    }

    public int DetailDT(String _Field, String _Title) {
        return details.Add(new Detail(FieldType.DT, _Field, _Title, 16, 0));
    }
    public int DetailDT(int _ColOrd, String _Title) {
        return details.Add(new Detail(FieldType.DT, _ColOrd, _Title, 16, 0));
    }
    public int DetailDT(String _Title) {
        return details.Add(new Detail(FieldType.DT, -1, _Title, 16, 0));
    }

    public int DetailMn(String _Field, String _Title, int _Width) {
        return details.Add(new Detail(FieldType.Mn, _Field, _Title, _Width, 0));
    }
    public int DetailMn(int _ColOrd, String _Title, int _Width) {
        return details.Add(new Detail(FieldType.Mn, _ColOrd, _Title, _Width, 0));
    }
    public int DetailMn(String _Title, int _Width) {
        return details.Add(new Detail(FieldType.Mn, -1, _Title, _Width, 0));
    }

    public int DetailMn(String _Field, String _Title) {
        return details.Add(new Detail(FieldType.Mn, _Field, _Title, 16, 0));
    }
    public int DetailMn(int _ColOrd, String _Title) {
        return details.Add(new Detail(FieldType.Mn, _ColOrd, _Title, 16, 0));
    }
    public int DetailMn(String _Title) {
        return details.Add(new Detail(FieldType.Mn, -1, _Title, 16, 0));
    }
}
