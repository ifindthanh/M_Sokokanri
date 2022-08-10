	function edit(element) {
    	var currentElement = $(element);
    	var action = currentElement.closest('tr').find(".action");
    	currentElement.closest('tr').find(".origin").hide();
    	action.show();
    	currentElement.closest('tr').find(".lblDesc").hide();
    	currentElement.closest('tr').find(".description").show();
    	currentElement.closest('tr').find(".lblCost").hide();
    	currentElement.closest('tr').find(".txtCost").show();
    	currentElement.closest('tr').find(".lblQuantity").hide();
    	currentElement.closest('tr').find(".txtQuantity").show();
    	currentElement.closest('tr').find(".lblTotal").hide();
    	currentElement.closest('tr').find(".txtTotal").show();
    	
    	currentElement.closest('tr').find(".lblRealCost").hide();
    	currentElement.closest('tr').find(".txtRealCost").show();
    	currentElement.closest('tr').find(".lblRealQuantity").hide();
    	currentElement.closest('tr').find(".txtRealQuantity").show();
    	currentElement.closest('tr').find(".lblRealPrice").hide();
    	currentElement.closest('tr').find(".txtRealPrice").show();
    	currentElement.closest('tr').find(".lblComputeCost").hide();
    	currentElement.closest('tr').find(".txtComputeCost").show();
    	currentElement.closest('tr').find(".lblComputePrice").hide();
    	currentElement.closest('tr').find(".txtComputePrice").show();
    	currentElement.closest('tr').find(".lblBuyingCode").hide();
    	currentElement.closest('tr').find(".txtBuyingCode").show();
    }
    
	function save(element) {
		var aConfirm = window.confirm("Lưu đơn hàng?");
		if (!aConfirm) {
			cancel(element);
			return;
		}
		var currentElement = $(element);
    	var action = currentElement.closest('tr').find(".action");
    	
    	
    	var param = {
    		"id": currentElement.attr("item"),
			"category": {
				"id": currentElement.attr("category")
			},
			"tree": {
				"id":  currentElement.closest('tr').find(".txtName").val()
			},
			"provider": {
				"id":  currentElement.closest('tr').find(".txtBrand").val()
			},
    		"description": currentElement.closest('tr').find(".description").val(),
    		"price": currentElement.closest('tr').find(".txtCost").val(),
    		"quantity": parseInt(currentElement.closest('tr').find(".txtQuantity").val())
    	};
    	$('#ajax-overlay').show();
    	$.ajax({
			type : "POST",
			url : "luu-item",
			data : JSON.stringify(param),
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			success : function(response) {
				$('#ajax-overlay').hide();
				if (response.status == 0) {
					alert(response.message);
					action.hide();
					displayBack(currentElement);
					revertPrice();
					return;
				}
				currentElement.closest('tr').find(".origin").show();
				currentElement.closest('tr').find(".lblName").html(param.name);
		    	currentElement.closest('tr').find(".lblBrand").html(param.brand);
		    	currentElement.closest('tr').find(".lblLink").html(param.link);
		    	currentElement.closest('tr').find(".lblDesc").html(param.description);
		    	currentElement.closest('tr').find(".lblCost").html(param.cost);
		    	currentElement.closest('tr').find(".lblQuantity").html(param.quantity);
		    	currentElement.closest('tr').find(".lblRealCost").html(param.realCost);
		    	currentElement.closest('tr').find(".lblRealQuantity").html(param.realQuantity);
		    	currentElement.closest('tr').find(".lblRealPrice").html(currentElement.closest('tr').find(".txtRealPrice").val());
		    	currentElement.closest('tr').find(".lblComputeCost").html(param.computeCost);
		    	currentElement.closest('tr').find(".lblComputePrice").html(currentElement.closest('tr').find(".txtComputePrice").val());
		    	currentElement.closest('tr').find(".lblTotal").html(currentElement.closest('tr').find(".txtTotal").val());
		    	currentElement.closest('tr').find(".lblBuyingCode").html(currentElement.closest('tr').find(".txtBuyingCode").val());
				action.hide();
				displayBack(currentElement);
				revertPrice();
			},
			error : function() {
				$('#ajax-overlay').hide();
				currentElement.closest('tr').find(".origin").show();
				action.hide();
				displayBack(currentElement);
				revertPrice();
			}

		});
    	
    	
    }
    
	function cancel(element) {
		var currentElement = $(element);
    	var action = currentElement.closest('tr').find(".action");
    	currentElement.closest('tr').find(".origin").show();
    	action.hide();
    	displayBack(currentElement);
    	revertPrice();
	}
	
	function computeMoney(element) {
    	var currentElement = $(element);
    	var txtTotal = currentElement.closest('tr').find(".txtTotal");
    	var txtCost = currentElement.closest('tr').find(".txtCost");
    	var txtQuantity = currentElement.closest('tr').find(".txtQuantity");
    	var rate = 1;
    	if (txtCost.val() && txtCost.val() != "" 
    			&& txtQuantity.val() && txtQuantity.val() != "" 
    				&& rate && rate != "") {
    		txtTotal.val((parseInt(txtQuantity.val())* parseFloat(txtCost.val()) * parseFloat(rate)).toFixed(0));
    		totalPrice();
    	} else {
    		txtTotal.val();
    	}
    	
    }
	
	function totalPrice(){
		var sum = 0;
		$(".txtTotal").each(function(){
			if ($(this).val() != "")
				sum += parseFloat($(this).val());
		});
		$("#total_price").html(sum.toFixed(0));
		
		var realSum = 0;
		$(".txtRealPrice").each(function(){
			if ($(this).val() != "")
				realSum += parseFloat($(this).val());
		});
		$("#real_price").html(realSum.toFixed(0));
		
		var computeSum = 0;
		$(".txtComputePrice").each(function(){
			if ($(this).val() != "")
				computeSum += parseFloat($(this).val());
		});
		$("#compute_price").html(computeSum.toFixed(0));
	}
	
	function revertPrice(){
		var sum = 0;
		$(".lblTotal").each(function(){
			if($(this).html() != "")
				sum += parseFloat($(this).html());
		});
		$("#total_price").html(sum.toFixed(0));
		
		var realSum = 0;
		$(".lblRealPrice").each(function(){
			console.log($(this).html());
			if($(this).html() != "")
				realSum += parseFloat($(this).html());
		});
		$("#real_price").html(realSum.toFixed(0));
		
		var computeSum = 0;
		$(".lblComputePrice").each(function(){
			console.log($(this).html());
			if($(this).html() != "")
				computeSum += parseFloat($(this).html());
		});
		$("#compute_price").html(computeSum.toFixed(0));
	}
	
	function displayBack(currentElement){
		currentElement.closest('tr').find(".lblName").show();
    	currentElement.closest('tr').find(".txtName").hide();
    	currentElement.closest('tr').find(".lblBrand").show();
    	currentElement.closest('tr').find(".txtBrand").hide();
    	currentElement.closest('tr').find(".lblLink").show();
    	currentElement.closest('tr').find(".txtLink").hide();
    	currentElement.closest('tr').find(".lblDesc").show();
    	currentElement.closest('tr').find(".description").hide();
    	currentElement.closest('tr').find(".lblCost").show();
    	currentElement.closest('tr').find(".txtCost").hide();
    	currentElement.closest('tr').find(".lblQuantity").show();
    	currentElement.closest('tr').find(".txtQuantity").hide();
    	currentElement.closest('tr').find(".lblTotal").show();
    	currentElement.closest('tr').find(".txtTotal").hide();
    	
    	
    	currentElement.closest('tr').find(".lblRealCost").show();
    	currentElement.closest('tr').find(".txtRealCost").hide();
    	currentElement.closest('tr').find(".lblRealQuantity").show();
    	currentElement.closest('tr').find(".txtRealQuantity").hide();
    	currentElement.closest('tr').find(".lblRealPrice").show();
    	currentElement.closest('tr').find(".txtRealPrice").hide();
    	
    	currentElement.closest('tr').find(".lblComputeCost").show();
    	currentElement.closest('tr').find(".txtComputeCost").hide();
    	currentElement.closest('tr').find(".lblComputePrice").show();
    	currentElement.closest('tr').find(".txtComputePrice").hide();
    	
    	currentElement.closest('tr').find(".lblBuyingCode").show();
    	currentElement.closest('tr').find(".txtBuyingCode").hide();
	}
	
	function selectItem(id, element, baseUrl) {
    	var chkbox = $(element);
    	$('#ajax-overlay').show();
    	if (chkbox.is(':checked')) {
    		$.ajax({
    			type : "GET",
    			url : baseUrl+"/chon-don-hang?id=" + id,
    			success : function(result) {
    				$('#ajax-overlay').hide();
    			},
    			error : function() {
    				$('#ajax-overlay').hide();
    			}
    		});
    	} else {
    		$('#ajax-overlay').show();
    		$.ajax({
    			type : "GET",
    			url : baseUrl+"/bo-chon-don-hang?id=" + id,
    			success : function(result) {
    				$('#ajax-overlay').hide();
    			},
    			error : function() {
    				$('#ajax-overlay').hide();
    			}
    		});
    	}
    	checkSelectAll();
    	$("#selectedRecords").html("(Đã chọn " + $('.order_id:checkbox:checked').length+")");
    }
    
	function selectAllItems(element, baseUrl) {
		var chkbox = $(element);
		var ids = "";
		$(".order_id").each(function (){
			$(this).prop('checked', chkbox.is(':checked'));
			ids += $(this).attr("order_id")+",";
		})
    	if (chkbox.is(':checked')) {
    		$('#ajax-overlay').show();
    		$.ajax({
    			type : "GET",
    			url : baseUrl + "/chon-tat-ca?ids="+ids,
    			success : function(result) {
    				$('#ajax-overlay').hide();
    			},
    			error : function() {
    				$('#ajax-overlay').hide();
    			}
    		});
    	} else {
    		$('#ajax-overlay').show();
    		$.ajax({
    			type : "GET",
    			url : baseUrl + "/bo-chon-tat-ca?ids="+ids,
    			success : function(result) {
    				$('#ajax-overlay').hide();
    			},
    			error : function() {
    				$('#ajax-overlay').hide();
    				
    			}
    		});
    	}
		$("#selectedRecords").html("(Đã chọn " + $('.order_id:checkbox:checked').length+")");
    }
	
	function computeRealMoney(element) {
    	var currentElement = $(element);
    	var txtTotal = currentElement.closest('tr').find(".txtRealPrice");
    	var txtCost = currentElement.closest('tr').find(".txtRealCost");
    	var txtQuantity = currentElement.closest('tr').find(".txtRealQuantity");
    	var rate = 1;
    	if (txtCost.val() && txtCost.val() != "" 
    			&& txtQuantity.val() && txtQuantity.val() != ""
    				&& rate && rate != "") {
    		txtTotal.val((parseInt(txtQuantity.val())* parseFloat(txtCost.val()) * parseFloat(rate)).toFixed(0));
    		totalPrice();
    	} else {
    		txtTotal.val("");
    	}
    }
    
    function computeMoneyFromRealQuantity(element) {
    	var currentElement = $(element);
    	var txtTotal = currentElement.closest('tr').find(".txtRealPrice");
    	var txtCost = currentElement.closest('tr').find(".txtRealCost");
    	var txtComputeCost = currentElement.closest('tr').find(".txtComputeCost");
    	var txtComputePrice = currentElement.closest('tr').find(".txtComputePrice");
    	var rate = 1;
    	if (txtCost.val() && txtCost.val() != "" 
    			&& currentElement.val() && currentElement.val() != ""
    				&& rate && rate != "") {
    		txtTotal.val((parseInt(currentElement.val())* parseFloat(txtCost.val())* parseFloat(rate)).toFixed(0));
    		totalPrice();
    	} else {
    		txtTotal.val("");
    	}
    	
    	if (txtComputeCost.val() && txtComputeCost.val() != "" 
			&& currentElement.val() && currentElement.val() != ""
				&& rate && rate != "") {
    		txtComputePrice.val((parseInt(currentElement.val())* parseFloat(txtComputeCost.val())* parseFloat(rate)).toFixed(0));
    		totalPrice();
		} else {
			txtComputePrice.val("");
		}
    }
	
    function computeMoneyFromRealCost(element) {
    	var currentElement = $(element);
    	var txtRealQuantity = currentElement.closest('tr').find(".txtRealQuantity");
    	var txtComputePrice = currentElement.closest('tr').find(".txtComputePrice");
    	var rate = 1;
    	if (txtRealQuantity.val() && txtRealQuantity.val() != "" 
			&& currentElement.val() && currentElement.val() != ""
				&& rate && rate != "") {
    		txtComputePrice.val((parseFloat(currentElement.val())* parseInt(txtRealQuantity.val())* parseFloat(rate)).toFixed(0));
    		totalPrice();
		} else {
			txtComputePrice.val("");
		}
    }
    
    function cancelOrders(url) {
    	if ($('.order_id:checkbox:checked').length == 0) {
    		alert("Vui lòng chọn đơn hàng.");
    		return;
    	}
    	var check = confirm("Bạn có chắc muốn hủy đơn hàng này?");
    	if (check) {
    		window.location.href = url;
    	}
    }
    
    function deleteOrders(url) {
    	if ($('.order_id:checkbox:checked').length == 0) {
    		alert("Vui lòng chọn đơn hàng.");
    		return;
    	}
    	var check = confirm("Bạn có chắc muốn xóa đơn hàng này?");
    	if (check) {
    		window.location.href = url;
    	}
        
    }
    

    function viewOrder(id) {
    	$('#ajax-overlay').show();
		$.ajax({
			type : "GET",
			url : "xem-don-hang/"+ id,
			success : function(result) {
				$('#ajax-overlay').hide();
				$("#orderDetailModal").modal('show');
				$('#order_detail').html(result);
				$('#modal_orderId').val(id);
			},
			error : function() {
				$('#ajax-overlay').hide();
			}
		});
		
    }
    
    function checkSelectAll(){
		var check = true;
		$(".order_id").each(function (){
			if (!$(this).is(':checked')) {
				check = false;
				return;
			}
		});
		if (check) {
			$("#selectAll").prop('checked', true);
		} else {
			$("#selectAll").prop('checked', false);
		}
	}
    
    
    function viewOrderHistory(){
    	window.location.href = "../donhang/xem-lich-su/" + $("#modal_orderId").val();
    }