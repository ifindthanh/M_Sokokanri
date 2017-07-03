	function edit(element) {
    	var currentElement = $(element);
    	var action = currentElement.closest('tr').find(".action");
    	currentElement.closest('tr').find(".origin").hide();
    	action.show();
    	currentElement.closest('tr').find(".lblName").hide();
    	currentElement.closest('tr').find(".txtName").show();
    	currentElement.closest('tr').find(".lblBrand").hide();
    	currentElement.closest('tr').find(".txtBrand").show();
    	currentElement.closest('tr').find(".lblLink").hide();
    	currentElement.closest('tr').find(".txtLink").show();
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
    		"name": currentElement.closest('tr').find(".txtName").val(),
    		"brand": currentElement.closest('tr').find(".txtBrand").val(),
    		"link": currentElement.closest('tr').find(".txtLink").val(),
    		"description": currentElement.closest('tr').find(".description").val(),
    		"cost": currentElement.closest('tr').find(".txtCost").val(),
    		"quantity": parseInt(currentElement.closest('tr').find(".txtQuantity").val()),
    		"realCost": currentElement.closest('tr').find(".txtRealCost").val(),
    		"realQuantity": parseInt(currentElement.closest('tr').find(".txtRealQuantity").val()),
    		"computeCost": currentElement.closest('tr').find(".txtComputeCost").val(),
    		"buyingCode": currentElement.closest('tr').find(".txtBuyingCode").val()
    	};
    	$.ajax({
			type : "POST",
			url : "luu-item",
			data : JSON.stringify(param),
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			success : function(response) {
				if (response.status == 0) {
					alert(response.message);
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
				
			},
			error : function() {
				currentElement.closest('tr').find(".origin").show();
				action.hide();
				displayBack(currentElement);
			}

		});
    	
    	
    }
    
	function cancel(element) {
		var currentElement = $(element);
    	var action = currentElement.closest('tr').find(".action");
    	currentElement.closest('tr').find(".origin").show();
    	action.hide();
    	displayBack(currentElement);
	}
	
	function computeMoney(element) {
    	var currentElement = $(element);
    	var txtTotal = currentElement.closest('tr').find(".txtTotal");
    	var txtCost = currentElement.closest('tr').find(".txtCost");
    	var txtQuantity = currentElement.closest('tr').find(".txtQuantity");
    	if (txtCost.val() && txtCost.val() != "" 
    			&& txtQuantity.val() && txtQuantity.val() != "") {
    		txtTotal.val((parseInt(txtQuantity.val())* parseFloat(txtCost.val())).toFixed(4));
    	} else {
    		txtTotal.val("");
    	}
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
    	if (chkbox.is(':checked')) {
    		$.ajax({
    			type : "GET",
    			url : baseUrl+"/chon-don-hang?id=" + id,
    			success : function(result) {
    			},
    			error : function() {
    			}
    		});
    	} else {
    		$.ajax({
    			type : "GET",
    			url : baseUrl+"/bo-chon-don-hang?id=" + id,
    			success : function(result) {
    			},
    			error : function() {
    			}
    		});
    	}
    }
    
	function selectAllItems(element, baseUrl) {
		var chkbox = $(element);
		var ids = "";
		$(".order_id").each(function (){
			$(this).prop('checked', chkbox.is(':checked'));
			ids += $(this).attr("order_id")+",";
		})
    	if (chkbox.is(':checked')) {
    		$.ajax({
    			type : "GET",
    			url : baseUrl + "/chon-tat-ca?ids="+ids,
    			success : function(result) {
    				
    			},
    			error : function() {
    			}
    		});
    	} else {
    		$.ajax({
    			type : "GET",
    			url : baseUrl + "/bo-chon-tat-ca?ids="+ids,
    			success : function(result) {
    				
    			},
    			error : function() {
    				
    			}
    		});
    	}
    }