package com.alfalahsoftech.alframe.factory;

// This class is typically used to defined final keywords, for mutable use FNConstants
public class FNC {

	/*private FNC() {
	}
*/
	public static final String HTTP = "http://";
	public static final String HTTPS = "https://";

	public static final String ENTITYNAME = "entityName";
	public static final String HDRID = "headerID";
	public static final String SERIALID = "serialID";
	public static final String ACTIONID = "actionID";
	public static final String API_KEY = "apiKey";
	public static final String ID = "id";
	public static final String VALUE = "value";
	public static final String OBJECTHASH = "objectHash";
	public static final String OBJARRAY = "objectArray";
	public static final String RESPARRAY = "responseArray";

	public static final String EN = "en";
	public static final String DEFAULTLC = "default";
	public static final String LANG_ID_0 = "langId";
	public static final String LANGID = "langID";

	public static final String ERR_CODE = "errorCode";
	public static final String DATA = "data";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";

	public static final String SING_SPACE = " ";
	public static final String EMPTYSTR = "";
	public static final String CURLY_BRACE_ST = "{";
	public static final String SELF = "self";
	public static final String TILDE = "~";
	public static final String FWD_SLASH = "/";
	public static final String COMMA = ",";
	public static final String HYPHEN = "-";
	public static final String UNDERSCORE = "_";
	public static final String DOT = "\\.";
	public static final String PERC = "%";

	public static final String DELETED = "_DELETED";
	public static final String EQ = "_EQ";
	public static final String ACTIVE = "Active";
	public static final String INACTIVE = "Inactive";

	public static final String AUTH_AJAX_URL = "/rest/auth/ajax";
	public static final String UNAUTH_AJAX_URL = "/rest/unauth/ajax";
	public static final String UTF8 = "UTF-8";
	public static final String NO = "NO";
	public static final String YES = "YES";
	public static final String TRUE = "TRUE";
	public static final String NULL_LC = "null";
	public static final String UN_DEFINED = "undefined";
	public static final String FALSE_LC = "false";
	public static final String UTC = "UTC";
	public static final String STRING = "STRING";
	public static final String ISACTIVE = "isActive";
	public static final String CACHE = "Cache-Control";
	public static final String EXP = "Expires";
	public static final String PRAGMA = "Pragma";
	public static final String NO_CACHE = "no-cache";
	public static final String CACHE_VALUE = "no-cache, post-check=0, pre-check=0, must-revalidate, no-store";
	public static final String TXT_HTML = "text/html";
	public static final String APPJSON = "application/json";

	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String FORMULAE = "FORMULAE";
	public static final String UNKNOWN = "UNKNOWN";

	//Moved from FNConstants
	public static final String IAMALIVE = "IAMALIVE";
	public static final String KEYS_WITH_NULL_VALUE = "==keysWithNullValue==";
	public static final Double ZERO_DOUBLE = Double.valueOf(0.0);
	public static final Double ONE_DOUBLE = Double.valueOf(1.0);
	public static final Integer ZERO_INTEGER = Integer.valueOf(0);
	public static final Integer ONE_INTEGER = Integer.valueOf(1);
	public static String INTG_DATE_FORMAT = "MM/dd/yyyy";//YYYYDDMM
	public static final String RSS_DATE_FORMAT = "EEE, MMM dd, yyyy";
	public static String INTG_DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm";
	public static final String DATE_TIME_FORMAT_WITH_ZONE = "MM/dd/yyyy HH:mm z";
	public static final String DATE_TIME_STR_FORMAT_WITH_ZONE = "MMM dd, yyyy HH:mm z";
	public static final String INTG_TIME_FORMAT = "HH:mm";
	public static final String AMPM_TIME_FORMAT = "h:mm a";
	public static final String SYSTEM = "System";
	public static final String DB_DATE_PATTERN = "yyyy-MM-dd";

	//MQ COnstants
	public static final String RECIEVED_FRM_Q = "RECIEVED_FRM_Q";
	public static final String RECIEVED_FRM_REST = "RECIEVED_FRM_REST";
	public static final String TRANSMITTED_TO_QUEUE = "TRANSMITTED_TO_QUEUE";
	public static final String ADAPTER_RUN = "ADAPTER_RUN";
}
