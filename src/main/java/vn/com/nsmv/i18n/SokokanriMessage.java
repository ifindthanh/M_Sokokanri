package vn.com.nsmv.i18n;

import java.util.Locale;
public class SokokanriMessage {
	private static final ExposedResourceBundleMessageSource messageSource = new ExposedResourceBundleMessageSource();

	//The items had been exported successfully.
	public static String getItemExportSuccessfully( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("item.export.successfully", args, locale);
}

	//この製造番号 {0} はすでにインポートされています。
	public static String getItemAlreadyExist(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("item.already.exist", args, locale);
}

	//この製造番号 {0} は出荷出来ません。
	public static String getNyukoIsNotAvailableOrExported(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("nyuko.is.not.available.or.exported", args, locale);
}

	//正確な姓を入力してください。
	public static String getSPECIAL_CHARACTERS_LASTNAME( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("SPECIAL_CHARACTERS_LASTNAME", args, locale);
}

	//いずれか一つ製造所を選択してください。
	public static String getErrorSelectSokoCd( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.select.soko.cd", args, locale);
}

	//新しいパスワードでスペースを利用する事が出来ません。
	public static String getMSG06_011( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_011", args, locale);
}

	//新しいパスワードはメールアドレスに送信されたパスワードと同じにならないようにしてください。
	public static String getMSG06_012( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_012", args, locale);
}

	//旧パスワードと同じパスワードは利用出来ません。
	public static String getMSG06_010( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_010", args, locale);
}

	//この社員番号は既に登録されています。
	public static String getMSG12_003( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG12_003", args, locale);
}

	//訂正区分{0}は正しくありません。
	public static String getErrorNotFoundUploadOrder(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("error.not.found.upload.order", args, locale);
}

	//正確な名を入力してください。
	public static String getSPECIAL_CHARACTERS_FIRSTNAME( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("SPECIAL_CHARACTERS_FIRSTNAME", args, locale);
}

	//メールアドレスの形式が正しくありません。再確認してください。
	public static String getMSG07_002( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG07_002", args, locale);
}

	//ネットワークエラー、もう一度やり直してください。
	public static String getErrorUnexpected( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.unexpected", args, locale);
}

	//正確なURLを入力してください。
	public static String getMSG04_014( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG04_014", args, locale);
}

	//社員番号が存在していません。
	public static String getStfNoNotExists( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("stfNo.not.exists", args, locale);
}

	//The item {0} has been imported successfully.
	public static String getItemImportSuccessfully(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("item.import.successfully", args, locale);
}

	//出荷物を全て読み込んでください。
	public static String getErrorSelectAllItems( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.select.all.items", args, locale);
}

	//パスワードは８桁以上２０桁以下で指定して下さい。
	public static String getMSG06_008( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_008", args, locale);
}

	//パスワードが変更されました。
	public static String getMSG06_009( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_009", args, locale);
}

	//社員番号を入力してください。
	public static String getUserCdRequired( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("userCd.required", args, locale);
}

	//社員番号は存在していません。
	public static String getMSG01_005( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG01_005", args, locale);
}

	//旧パスワードが正しくありません。再入力してください。
	public static String getMSG06_006( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_006", args, locale);
}

	//パスワードは正しくありません。
	public static String getMSG01_006( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG01_006", args, locale);
}

	//パスワードの確認が正しくありません。再入力してください。
	public static String getMSG06_007( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_007", args, locale);
}

	//新しいパスワードの確認を入力してください。
	public static String getMSG06_004( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_004", args, locale);
}

	//旧パスワードと同じパスワードは利用出来ません。
	public static String getMSG06_005( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_005", args, locale);
}

	//この製造番号 {0} は存在しません。
	public static String getItemNotExist(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("item.not.exist", args, locale);
}

	//この送状番号は既に出荷されました。
	public static String getHeaderIsExported( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("header.is.exported", args, locale);
}

	//新しいパスワードを入力してください。
	public static String getMSG06_002( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_002", args, locale);
}

	//送状番号は存在していません。
	public static String getHeaderIsNotExists( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("header.is.not.exists", args, locale);
}

	//旧パスワードを入力してください。
	public static String getMSG06_003( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_003", args, locale);
}

	//この製造番号 {0} はすでにインポートされています。
	public static String getItemExportedBefore(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("item.exported.before", args, locale);
}

	//パスワードが変更されます。宜しいですか？
	public static String getMSG06_001( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_001", args, locale);
}

	//アップロードが完了しました。
	public static String getMSG05_001( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG05_001", args, locale);
}

	//ファイルを選択してください
	public static String getErrorSelectFileToUpload( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.select.file.to.upload", args, locale);
}

	//この製造番号 {0} はすでにインポートされています。
	public static String getItemImportedBefore(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("item.imported.before", args, locale);
}

	//スーパー管理者としてユーザーがサポートしません。
	public static String getErrorSuperAdminNotSupported( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.super.admin.not.supported", args, locale);
}

	//アップロードに失敗しました。入力ファイルを再確認してください。
	public static String getErrorUnexpectedUploadError( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.unexpected.upload.error", args, locale);
}

	//この製造番号 {0} は重複している。
	public static String getNyukoIsDuplicated(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("nyuko.is.duplicated", args, locale);
}

	//正確な電話番号を入力してください。
	public static String getMSG13_006( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG13_006", args, locale);
}

	//特別な桁があります。
	public static String getSPECIAL_CHARACTERS( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("SPECIAL_CHARACTERS", args, locale);
}

	//この製造番号 {0} は送状番号とあっていません。
	public static String getBodyIsNotBelongToHeaderOrDuplicated(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("body.is.not.belong.to.header.or.duplicated", args, locale);
}

	//そのメールアドレスを持つユーザ―が既に存在します。再確認してください。
	public static String getMSG13_002( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG13_002", args, locale);
}

	//社員番号は既に登録されていました。
	public static String getUserCdExists( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("userCd.exists", args, locale);
}

}