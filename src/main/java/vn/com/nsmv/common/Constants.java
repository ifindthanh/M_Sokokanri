package vn.com.nsmv.common;

public final class Constants
{

	public static String OK = "0";
	public static String ERROR = "-1";
	public static int MAX_FILE = 50;
	public static long MAX_SIZE_A_FILE = 5242880;
	public static int MAX_IMAGE_PER_PAGE = 20;
	public static int MAX_RECORD_PER_TIME = 100;
	public static int MAX_RECORD = 50;
	public static int STATUS_INPUT = 3;
	public static int STATUS_CHECKER = 4;
	public static final String ROLE_I = "ROLE_I";
	public static final String ROLE_C = "ROLE_C";
	public static final String ROLE_B = "ROLE_B";
	public static final String ROLE_A = "ROLE_A";
	public static final String ROLE_U = "ROLE_U";
	public static final String ROLE_T1 = "ROLE_T1";
	public static final String ROLE_T2 = "ROLE_T2";
	public static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	public static String URL_PATTERN = "^(http://|https://)?(www.)?([a-zA-Z0-9-_]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";
	public static int MAX_PHONE_LENGTH = 50;
	public static String ID_WRONG = "ID_WRONG";
	public static String STATUS_WRONG = "STATUS_WRONG";

	public static String USER_CD_EMPTY = "userCd.required";
	public static String USER_CD_EXISTS = "userCd.exists";
	public static String STF_NO_NOT_EXISTS = "stfNo.not.exists";

	// Action
	public static String ACTION_UPDATE = "UPDATE";
	public static String ACTION_DELETE = "DELETE";
	public static String ACTION_ADD = "ADD";
	public static String ACTION_SHARE = "SHARE";
	public static String ACTION_RESTORE = "RESTORE";
	public static String ACTION_EMPTY = "EMPTY";

	//viettel
	public static String LINK_ACTIVE_ACCOUNT = "http://103.1.210.79:8080/sokokanri/register/active/";

	//amazon
	//public static String LINK_ACTIVE_ACCOUNT = "http://52.77.6.193/sokokanri/register/active/";

	public static String EMAIL_SUBJECT = "ã‚¢ã‚«ã‚¦ãƒ³ãƒˆç™»éŒ²ã�—ã�¾ã�—ã�Ÿ";

	public static class REQUEST_PARAMETER
	{
		public static final String SEARCH_BY_LINK = "searchByLink";
	}

	public static class UPLOAD_ORDER
	{
		public static final String INSERT = " ";
		public static final String DELETE = "C";
		public static final String UPDATE = "T";
	}

	public static final String BLANK = "";
	public static final String SPACE = " ";
	public static final String SPACE_68 = "                                                                    ";
	public static final String NON_SET_SUKKAYOTEIBI = "00000000";
	public static final String ROLE_BG = "BG";
	

	public static class PHONE_TYPE
	{
		public static String PHONE_TYPE_BUSINESS = "Business";
		public static String PHONE_TYPE_BUSINESS_FAX = "Business Fax";
		public static String PHONE_TYPE_COMPANY = "Company";
		public static String PHONE_TYPE_HOME = "Home";
		public static String PHONE_TYPE_HOME_FAX = "Home Fax";
		public static String PHONE_TYPE_MOBILE = "Mobile";
		public static String PHONE_TYPE_OTHER = "Other";
	}

	public static class SESSION_PARAM
	{
		public static String SOKO_CD = "sokoCd";
	}
}
