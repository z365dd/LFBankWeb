package com.adtec.para.common.constants;

public interface ParaConst {
    interface RunStat {
        String RUN = "01";
        String STOP = "00";
    }

    interface SyncFlg {
        String SYNC = "Y";
        String UNSYNC = "N";
    }

    interface DelFlg {
        String DEL = "Y";
        String NODEL = "N";
    }

    interface ReadAuthLvl {
        String AUTH_PUBLIC = "00";
        String AUTH_TENANT = "01";
        String AUTH_PART = "02";
        String AUTH_USER_TENANT = "03";
    }

    interface ReadAuthTp {
        String AUTH_READ = "00";
        String AUTH_WRITE = "01";
    }

    interface DataTp {
        String DATA_TP_OBJECT = "Objcet";
        String DATA_TP_STRING = "String";
        String DATA_TP_LONG = "Long";
        String DATA_TP_BYTE = "Byte";
    }

    interface StorgRuleTp {
        String STORG_RULE_TP_PARAM = "00";
        String STORG_RULE_TP_CACHE = "01";
    }

    interface CustomType {
        String AND = "AND";
        String OR = "OR";
    }
}
