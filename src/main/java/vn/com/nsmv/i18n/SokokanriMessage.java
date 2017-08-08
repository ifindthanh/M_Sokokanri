package vn.com.nsmv.i18n;

import java.util.Locale;
public class SokokanriMessage {
	private static final ExposedResourceBundleMessageSource messageSource = new ExposedResourceBundleMessageSource();

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

	//Email không tồn tại trong hệ thống.
	public static String getMessageErrorEmailNotExists( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.email.not.exists", args, locale);
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

	//Đường link của không hợp lệ.
	public static String getMessageErrorInvalidLink( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.link", args, locale);
}

	//Đổi mật khẩu thành công.
	public static String getMessageInforChangePasswordSuccessfully( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.infor.change.password.successfully", args, locale);
}

	//Số lượng thực mua của đơn {0} không được để trống
	public static String getMessageErrorQuantityCannotBeEmptyWithParam(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("message.error.quantity.cannot.be.empty.with.param", args, locale);
}

	//Mật khẩu xác nhận không khớp.
	public static String getMessageErrorInvalidConfirmPassword( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.confirm.password", args, locale);
}

	//Tổng cộng
	public static String getLabelTotalRecords( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.total.records", args, locale);
}

	//Mật khẩu hiện tại không đúng.
	public static String getMessageErrorInvalidPassword( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.password", args, locale);
}

	//Hiển thị
	public static String getLabelNumberOfDisplay( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.number.of.display", args, locale);
}

	//User không tồn tại trong hệ thống.
	public static String getMessageErrorUserNotExists( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.user.not.exists", args, locale);
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

	//Vui lòng điền ít nhất 1 sản phẩm
	public static String getMessageErrorProvideAtLeastOneItem( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.provide.at.least.one.item", args, locale);
}

	//Email này đã được đăng ký trong hệ thống. Vui lòng chọn địa chỉ email khác. 
	public static String getMessageErrorEmailExisted( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.email.existed", args, locale);
}

	//Đã duyệt
	public static String getLabelApproved( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.approved", args, locale);
}

	//Vui lòng chọn ít nhật một role.  
	public static String getMessageErrorUserRoleEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.user.role.empty", args, locale);
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

	//Không thể duyệt đơn hàng đã chọn.
	public static String getMessageErrorCannotApproveSelectedOrder( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.cannot.approve.selected.order", args, locale);
}

	//Đơn giá không hợp lệ.
	public static String getMessageErrorInvalidCost( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.cost", args, locale);
}

	//Đăng ký tài khoản thành công.
	public static String getMessageInforSignInSuccessfully( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.infor.sign.in.successfully", args, locale);
}

	//Chờ duyệt
	public static String getLabelWaitingToApproval( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.waiting.to.approval", args, locale);
}

	//Số điện thoại không đúng.
	public static String getMessageErrorInvalidPhone( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.phone", args, locale);
}

	//Đơn giá phải lớn hơn 0.
	public static String getMessageErrorCostMustGtZero( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.cost.must.gt.zero", args, locale);
}

	//Hiển thị tối đa
	public static String getLabelMaxOfDisplay( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.max.of.display", args, locale);
}

	//Mã mua hàng của đơn {0} không được để trống
	public static String getMessageErrorBuydingCodeCannotBeEmpty(Object arg0,  Locale locale){

		Object[] args = {arg0};
		return SokokanriMessage.messageSource.getMessage("message.error.buyding.code.cannot.be.empty", args, locale);
}

	//Đơn giá không được để trống.
	public static String getMessageErrorCostCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.cost.cannot.be.empty", args, locale);
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

	//Đơn hàng đã được duyệt, không thể xóa đơn hàng.
	public static String getMessageErrorOrderIsApprovedCannotDelete( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.order.is.approved.cannot.delete", args, locale);
}

	//Tên mặt hàng không được để trống.
	public static String getMessageErrorNameCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.name.cannot.be.empty", args, locale);
}

	//Đã ghi chú
	public static String getLabelNoted( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("label.noted", args, locale);
}

	//Định dạng email không đúng.
	public static String getMessageErrorInvalidEmail( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.email", args, locale);
}

	//Số điện thoại không được để trống
	public static String getMessageErrorPhoneCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.phone.cannot.be.empty", args, locale);
}

	//Invalid request.
	public static String getMessageErrorInvalidRequest( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.request", args, locale);
}

	//Mật khẩu phải bao gồm tối thiếu 6 ký tự.
	public static String getMessageErrorInvalidPasswordLength( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalid.password.length", args, locale);
}

	//Đơn hàng không tồn tại.
	public static String getMessageErrorOrderNotExist( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.order.not.exist", args, locale);
}

	//Họ và tên không được để trống.
	public static String getMessageErrorFullNameCannotBeEmpty( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.full.name.cannot.be.empty", args, locale);
}

	//Số lượng phải là số nguyên.
	public static String getMessageErrorInvalieQuantity( Locale locale){

		Object[] args = {};
		return SokokanriMessage.messageSource.getMessage("message.error.invalie.quantity", args, locale);
}

}