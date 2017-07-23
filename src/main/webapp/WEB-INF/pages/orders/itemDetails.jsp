<%@page contentType="text/html" pageEncoding="UTF-8"%>
	<div id="page_content">
		<form action="tat-ca" method="POST">
			<div class="col-sm-12">
				<table id="tableList" class="listBusCard table">
					<thead>
						<tr>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr class="headings" role="row">
							<td class="myLabel">Mã đơn hàng</td>
							<td>${item.formattedId}</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Tên khách hàng</td>
							<td>${item.user.fullname}</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Trạng thái</td>
							<td><order:status status="${item.status }" /></td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Tên sản phẩm</td>
							<td>
									${item.name }
							</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Nhà phân phối</td>
							<td>
									${item.brand }
							</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Link</td>
							<td>
									${item.link }
							</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Mô tả thêm</td>
							<td>
									${item.description }
							</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Đơn giá</td>
							<td>
									${item.cost }
							</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Số lượng</td>
							<td>
									${item.quantity }
							</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Tổng tiền</td>
							<td>${item.getTotal() }</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Đơn giá mua</td>
							<td>
									${item.computeCost }
							</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Số lượng mua</td>
							<td>
									${item.realQuantity }
							</td>
						</tr>
						<tr class="headings" role="row">
							<td class="myLabel">Tổng tiền thực tế</td>
							<td>${item.getComputePrice() }</td>
						</tr>
					</tbody>
				</table>

			</div>
		</form>
	</div>
