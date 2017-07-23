package vn.com.nsmv.i18n;

import java.util.Locale;
public class SokokanriMessage {
	private static final ExposedResourceBundleMessageSource messageSource = new ExposedResourceBundleMessageSource();

	//正確な名を入力してください。
	public static String getSPECIAL_CHARACTERS_FIRSTNAME( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("SPECIAL_CHARACTERS_FIRSTNAME", args, locale);
}

	//この送状番号は既に出荷されました。
	public static String getHeaderIsExported( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("header.is.exported", args, locale);
}

	//Không thể chuyển trạng thái đơn hàng đã chọn.
	public static String getMessageErrorCannotUpdateStatus( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.cannot.update.status", args, locale);
}

	//Bạn không được phép thao tác với đơn hàng này.
	public static String getMessageErrorNoPermissionInOrder( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.no.permission.in.order", args, locale);
}

	//Đã xuất hóa đơn
	public static String getLabelBillExported( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.bill.exported", args, locale);
}

	//アップロードが完了しました。
	public static String getMSG05_001( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG05_001", args, locale);
}

	//社員番号が存在していません。
	public static String getStfNoNotExists( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("stfNo.not.exists", args, locale);
}

	//Chi tiết hóa đơn: {0}
	public static String getLabelBillDetail(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("label.bill.detail", args, locale);
}

	//Không thể ghi chú đơn hàng đã chọn.
	public static String getMessageErrorCannotNoteSelectedOrder( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.cannot.note.selected.order", args, locale);
}

	//Tên khách hàng: {0}
	public static String getLabelNameOfCustomer(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("label.name.of.customer", args, locale);
}

	//File đã chọn không chứa thông tin của đơn hàng nào.
	public static String getMessageErrorNoOrderInFile( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.no.order.in.file", args, locale);
}

	//この製造番号 {0} は送状番号とあっていません。
	public static String getBodyIsNotBelongToHeaderOrDuplicated(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("body.is.not.belong.to.header.or.duplicated", args, locale);
}

	//The items had been exported successfully.
	public static String getItemExportSuccessfully( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("item.export.successfully", args, locale);
}

	//アップロードに失敗しました。入力ファイルを再確認してください。
	public static String getErrorUnexpectedUploadError( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.unexpected.upload.error", args, locale);
}

	//Đã mua
	public static String getLabelBought( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.bought", args, locale);
}

	//File vừa chọn không phải là excel file.
	public static String getMessageErrorInvalidExcel( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.excel", args, locale);
}

	//出荷物を全て読み込んでください。
	public static String getErrorSelectAllItems( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.select.all.items", args, locale);
}

	//Đường link của không hợp lệ.
	public static String getMessageErrorInvalidLink( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.link", args, locale);
}

	//特別な桁があります。
	public static String getSPECIAL_CHARACTERS( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("SPECIAL_CHARACTERS", args, locale);
}

	//Số lượng thực mua của đơn {0} không được để trống
	public static String getMessageErrorQuantityCannotBeEmptyWithParam(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("message.error.quantity.cannot.be.empty.with.param", args, locale);
}

	//この製造番号 {0} は出荷出来ません。
	public static String getNyukoIsNotAvailableOrExported(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("nyuko.is.not.available.or.exported", args, locale);
}

	//この製造番号 {0} はすでにインポートされています。
	public static String getItemExportedBefore(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("item.exported.before", args, locale);
}

	//Đơn giá tính tiền mua của đơn {0} không được để trống
	public static String getMessageErrorComputeCostCannotBeEmpty(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("message.error.compute.cost.cannot.be.empty", args, locale);
}

	//Hóa đơn không tồn tại.
	public static String getMessageErrorBillNotExist( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.bill.not.exist", args, locale);
}

	//Địa chỉ không được để trống.
	public static String getMessageErrorAddressCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.address.cannot.be.empty", args, locale);
}

	//Nhà phân phối không hợp lệ.
	public static String getMessageErrorInvalidBrand( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.brand", args, locale);
}

	//ネットワークエラー、もう一度やり直してください。
	public static String getErrorUnexpected( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.unexpected", args, locale);
}

	//Tên sản phẩm không hợp lệ.
	public static String getMessageErrorInvalidName( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.name", args, locale);
}

	//Đã chuyển về VN
	public static String getLabelTransferredToVn( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.transferred.to.vn", args, locale);
}

	//社員番号を入力してください。
	public static String getUserCdRequired( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("userCd.required", args, locale);
}

	//Địa chỉ email không được để trống.
	public static String getMessageErrorEmailCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.email.cannot.be.empty", args, locale);
}

	//Tất cả
	public static String getLabelAll( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.all", args, locale);
}

	//Đơn giá mua của đơn {0} không được để trống
	public static String getMessageErrorCostCannotBeEmptyWithParam(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("message.error.cost.cannot.be.empty.with.param", args, locale);
}

	//正確なURLを入力してください。
	public static String getMSG04_014( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG04_014", args, locale);
}

	//Đã duyệt
	public static String getLabelApproved( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.approved", args, locale);
}

	//Vui lòng điền ít nhất 1 sản phẩm
	public static String getMessageErrorProvideAtLeastOneItem( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.provide.at.least.one.item", args, locale);
}

	//Đã lưu kho
	public static String getLabelStored( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.stored", args, locale);
}

	//Tổng tiền
	public static String getLabelTotalPrice( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.total.price", args, locale);
}

	//Số lượng không được để trống.
	public static String getMessageErrorQuantityCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.quantity.cannot.be.empty", args, locale);
}

	//Ghi chú không hợp lệ.
	public static String getMessageErrorInvalidNote( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.note", args, locale);
}

	//Đường link không được để trống.
	public static String getMessageErrorLinkCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.link.cannot.be.empty", args, locale);
}

	//正確な姓を入力してください。
	public static String getSPECIAL_CHARACTERS_LASTNAME( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("SPECIAL_CHARACTERS_LASTNAME", args, locale);
}

	//Không thể duyệt đơn hàng đã chọn.
	public static String getMessageErrorCannotApproveSelectedOrder( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.cannot.approve.selected.order", args, locale);
}

	//Số lượng phải lớn hơn 0.
	public static String getMessageErrorQuantityMustGtZero( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.quantity.must.gt.zero", args, locale);
}

	//Đã chuyển về tại nước ngoài
	public static String getLabelTransferred( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.transferred", args, locale);
}

	//ファイルを選択してください
	public static String getErrorSelectFileToUpload( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.select.file.to.upload", args, locale);
}

	//Đơn giá không hợp lệ.
	public static String getMessageErrorInvalidCost( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.cost", args, locale);
}

	//この製造番号 {0} は重複している。
	public static String getNyukoIsDuplicated(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("nyuko.is.duplicated", args, locale);
}

	//そのメールアドレスを持つユーザ―が既に存在します。再確認してください。
	public static String getMSG13_002( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG13_002", args, locale);
}

	//メールアドレスの形式が正しくありません。再確認してください。
	public static String getMSG07_002( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG07_002", args, locale);
}

	//Chờ duyệt
	public static String getLabelWaitingToApproval( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.waiting.to.approval", args, locale);
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

	//この社員番号は既に登録されています。
	public static String getMSG12_003( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG12_003", args, locale);
}

	//新しいパスワードを入力してください。
	public static String getMSG06_002( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_002", args, locale);
}

	//Đơn giá phải lớn hơn 0.
	public static String getMessageErrorCostMustGtZero( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.cost.must.gt.zero", args, locale);
}

	//パスワードが変更されます。宜しいですか？
	public static String getMSG06_001( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_001", args, locale);
}

	//この製造番号 {0} はすでにインポートされています。
	public static String getItemImportedBefore(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("item.imported.before", args, locale);
}

	//Mã mua hàng của đơn {0} không được để trống
	public static String getMessageErrorBuydingCodeCannotBeEmpty(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("message.error.buyding.code.cannot.be.empty", args, locale);
}

	//パスワードの確認が正しくありません。再入力してください。
	public static String getMSG06_007( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_007", args, locale);
}

	//正確な電話番号を入力してください。
	public static String getMSG13_006( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG13_006", args, locale);
}

	//旧パスワードが正しくありません。再入力してください。
	public static String getMSG06_006( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_006", args, locale);
}

	//Đơn giá không được để trống.
	public static String getMessageErrorCostCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.cost.cannot.be.empty", args, locale);
}

	//旧パスワードと同じパスワードは利用出来ません。
	public static String getMSG06_005( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_005", args, locale);
}

	//Đã hoàn thành
	public static String getLabelFinished( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.finished", args, locale);
}

	//Đang giao hàng
	public static String getLabelIsShipping( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.is.shipping", args, locale);
}

	//新しいパスワードの確認を入力してください。
	public static String getMSG06_004( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_004", args, locale);
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

	//スーパー管理者としてユーザーがサポートしません。
	public static String getErrorSuperAdminNotSupported( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.super.admin.not.supported", args, locale);
}

	//Đơn hàng đã được duyệt, không thể xóa đơn hàng.
	public static String getMessageErrorOrderIsApprovedCannotDelete( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.order.is.approved.cannot.delete", args, locale);
}

	//Đã ghi chú
	public static String getLabelNoted( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.noted", args, locale);
}

	//Tên mặt hàng không được để trống.
	public static String getMessageErrorNameCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.name.cannot.be.empty", args, locale);
}

	//この製造番号 {0} はすでにインポートされています。
	public static String getItemAlreadyExist(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("item.already.exist", args, locale);
}

	//Số điện thoại không được để trống
	public static String getMessageErrorPhoneCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.phone.cannot.be.empty", args, locale);
}

	//パスワードは正しくありません。
	public static String getMSG01_006( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG01_006", args, locale);
}

	//社員番号は既に登録されていました。
	public static String getUserCdExists( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("userCd.exists", args, locale);
}

	//社員番号は存在していません。
	public static String getMSG01_005( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG01_005", args, locale);
}

	//Đơn hàng không tồn tại.
	public static String getMessageErrorOrderNotExist( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.order.not.exist", args, locale);
}

	//この製造番号 {0} は存在しません。
	public static String getItemNotExist(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("item.not.exist", args, locale);
}

	//旧パスワードと同じパスワードは利用出来ません。
	public static String getMSG06_010( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_010", args, locale);
}

	//Họ và tên không được để trống.
	public static String getMessageErrorFullNameCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.full.name.cannot.be.empty", args, locale);
}

	//いずれか一つ製造所を選択してください。
	public static String getErrorSelectSokoCd( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("error.select.soko.cd", args, locale);
}

	//新しいパスワードはメールアドレスに送信されたパスワードと同じにならないようにしてください。
	public static String getMSG06_012( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_012", args, locale);
}

	//The item {0} has been imported successfully.
	public static String getItemImportSuccessfully(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("item.import.successfully", args, locale);
}

	//新しいパスワードでスペースを利用する事が出来ません。
	public static String getMSG06_011( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("MSG06_011", args, locale);
}

	//Số lượng phải là số nguyên.
	public static String getMessageErrorInvalieQuantity( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalie.quantity", args, locale);
}

	//訂正区分{0}は正しくありません。
	public static String getErrorNotFoundUploadOrder(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("error.not.found.upload.order", args, locale);
}

}