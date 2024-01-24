  // Trigger ajax event on receiver address after 4th character typed.
var districtList = [];

  function getAddress(){
	$('#recieverPostalCode').on('keyup', function() {
		$("#errorPostal").hide();
    if ($(this).val().length == 6) {

      $.ajax({
    	  type: "post",
        url:"/parcel/getAddressByPostalcode?ajax=true",
        data:{
          postalCode: $("#recieverPostalCode").val()
        },
        success:function(result){
        	if(result!=""){
	          $("#recieverZone").val(result.zone);
	          $("#recieverDivision").val(result.division);
	          $("#recieverDistrict").val(result.district);
	          $("#recieverThana").val(result.thana);
	          $("#recieverSubOffice").val(result.subOffice);
        }
        	else{
        		 $("#errorPostal").text("Postal code does not exist");
                 $("#postalCode").css('background', 'rgb(255, 204, 203)');
                 $("#errorPostal").show();
                 $("#recieverZone").val('');
                 $("#recieverDivision").val('');
                 $("#recieverDistrict").val('');
                 $("#recieverThana").val('');
                 $("#recieverSubOffice").val('');
                 $("#recieverPostalCode").val('');
        	}
        }
      });
    }
  });

   if ($('#recieverPostalCode').val().length == 6){

 		      $.ajax({
 		    	 type: "post",
		        url:"/parcel/getAddressByPostalcode?ajax=true",
		        data:{
		          postalCode: $("#recieverPostalCode").val()
		        },
		        success:function(result){
		        	$("#recieverZone").val(result.zone);
		          $("#recieverDivision").val(result.division);
		           $("#recieverDistrict").val(result.district);
		          $("#recieverThana").val(result.thana);
		          $("#recieverSubOffice").val(result.subOffice);
		        }
		      });
		    }



  // End of block

  // Trigger ajax event on sender address after 4th character typed.
  $('#senderPostalCode').on('keyup', function() {
    if ($(this).val().length == 6) {
      $.ajax({
    	  type: "post",
        url:"/parcel/getAddressByPostalcode?ajax=true",
        data:{
          postalCode: $("#senderPostalCode").val()
        },
        success:function(result){
          $("#senderZone").val(result.zone);
          $("#senderDivision").val(result.division);
          $("#senderDistrict").val(result.district);
          $("#senderThana").val(result.thana);
          $("#senderSubOffice").val(result.subOffice);

        }
      });
    }
  });
  // End of block

  /**** Dropdown functionality on District, Division, Suboffice, thana ****/
  //Populate Recivers all fields based on subOffice
  $('#recieverSubOffice').on('change', function() {
    $.ajax({
    	 type: "post",
      url:"/parcel/getAddressBySubOffice?ajax=true",
      data:{
        subOffice: $("#recieverSubOffice").val()
      },
      success:function(result){
        if($("#recieverSubOffice").val().length > 3){
          $("#recieverPostalCode").val(result.postalCode);
        }
      }
    });
  });

  //Populate Recivers Division option List Based On Zone
  $('#recieverZone').on('change', function() {
    $.ajax({
    	 type: "post",
      url:"/parcel/getDistinctDivisionByZone?ajax=true",
      data:{
        zone: $("#recieverZone").val()
      },
      success:function(result){
        $("#divisionList").empty();
        $.each(result,function(key, data){
          var divisionList = '<option>'+data+'</option>';

          $("#divisionList").append(divisionList);
        });
        $("#recieverDivision").val('');
        $("#recieverDistrict").val('');
        $("#recieverThana").val('');
        $("#recieverSubOffice").val('');
      }
    });
  });

  //Populate Recivers District Option List Based On Division
  $('#recieverDivision').on('change', function() {

    $.ajax({
    	 type: "post",
      url:"/parcel/getDistinctDistrictByDivision?ajax=true",
      data:{
        division: $("#recieverDivision").val()
      },
      success:function(result){
        $("#districtList").empty();
        $.each(result,function(key, data){
          districtList = '<option>'+data+'</option>';

          $("#districtList").append(districtList);
        });
        $("#recieverDistrict").val('');
        $("#recieverThana").val('');
        $("#recieverSubOffice").val('');
      }
    });
    $.ajax({
    	 type: "post",
    	url:"/parcel/getAddressByDivision?ajax=true",
        data:{
        	division: $("#recieverDivision").val()
        },
        success : function(result){
        	$("#recieverZone").val(result[1].zone);
        	$("#recieverDistrict").val('');
        	$("#recieverThana").val('');
        	$("#recieverSubOffice").val('');
        }
    });

  });

  //Populate Recivers Thana Option List Based On District
    $('#recieverDistrict').on('change', function() {
    $.ajax({
    	 type: "post",
      url:"/parcel/getDistinctThanaByDistrict?ajax=true",
      data:{
        district: $("#recieverDistrict").val()
      },
      success:function(result){
        $("#thanaList").empty();
        $.each(result,function(key, data){
          var thanaList = '<option>'+data+'</option>';
          $("#thanaList").append(thanaList);
        });
        $("#recieverThana").val('');
        $("#recieverSubOffice").val('');
      }
    });
    $.ajax({
    	 type: "post",
    	url:"/parcel/getAddressByDistrict?ajax=true",
        data:{
        	district: $("#recieverDistrict").val()
        },
        success : function(result){
        	$("#recieverDivision").val(result[1].division);
            $("#recieverZone").val(result[1].zone);
            district: $("#recieverThana").val('');

        }
    });
  });


  // Trigger ajax event on sender address after 3th character typed.
  // Populate Recivers Thana list after 3 characters

  $('#recieverThana').on('keyup', function() {
    if ($(this).val().length == 3) {
      $.ajax({
    	  type: "post",
        url:"/parcel/getAddressByThana?ajax=true",
        data:{
          thana: $("#recieverThana").val()
        },
        success:function(result){
        	$.each(result,function(key, data){
        		$("#thanaList").empty();
                var thanaList = '<option>'+data.thana+'</option>';
                $("#thanaList").append(thanaList);
              });
        	$('#recieverThana').change(function(){
        		var thana = $("#recieverThana").val();
        		$.each(result,function(key, data){
        			if(data.thana == thana ){
        				$("#recieverDivision").val(data.division);
        				$("#recieverDistrict").val(data.district);
        				$("#recieverZone").val(data.zone);
        				$("#recieverSubOffice").val('');
        			}
        		})
        	});
        }
      });
    }
  });

	//Trigger ajax event on sender address after 3th character typed.
  // Populate Recivers subOffice list after 3 characters
  $('#recieverSubOffice').on('keyup', function() {
	    if ($(this).val().length == 3) {
	      $.ajax({
	    	  type: "post",
	        url:"/parcel/getAddressBysubOfficeStartsWith?ajax=true",
	        data:{
	          subOffice: $("#recieverSubOffice").val()
	        },
	        success:function(result){
	        	$.each(result,function(key, data){
	        		$("#recieverSubOffice").empty();
	                var thanaList = '<option>'+data.subOffice+'</option>';
	                $("#subOfficeList").append(thanaList);
	              });
	        	$('#recieverSubOffice').change(function(){
	        		var subOffice = $("#recieverSubOffice").val();
	        		$.each(result,function(key, data){
	        			if(data.subOffice == subOffice ){
	        				$("#recieverDivision").val(data.division);
	        				$("#recieverDistrict").val(data.district);
	        				$("#recieverZone").val(data.zone);
	        				$("#recieverThana").val(data.thana);
	        			}
	        		})
	        	});
	        }
	      });
	    }
	  });


  //Populate SubOffice Option List Based On SubOffice
  $('#recieverThana').on('change', function() {
    $.ajax({
    	 type: "post",
      url:"/parcel/getDistinctSubOfficeByThana?ajax=true",
      data:{
        thana: $("#recieverThana").val()
      },
        success:function(result){
        $("#subOfficeList").empty();
        $.each(result,function(key, data){
          var subOfficeList = '<option>'+data+'</option>';
          $("#subOfficeList").append(subOfficeList);
        });
      }
    });
  });

  //Populate Sender all fields based on subOffice
  $('#senderSubOffice').on('change', function() {
    $.ajax({
    	 type: "post",
      url:"/parcel/getAddressBySubOffice?ajax=true",
      data:{
        subOffice: $("#senderSubOffice").val()
      },
      success:function(result){
        if($("#senderSubOffice").val().length > 3){
          $("#senderPostalCode").val(result.postalCode);
        }
      }
    });
  });

  //Populate Sender Division option List Based On Zone
  $('#senderZone').on('change', function() {
    $.ajax({
    	 type: "post",
      url:"/parcel/getDistinctDivisionByZone?ajax=true",
      data:{
        zone: $("#senderZone").val()
      },
      success:function(result){
        $("#senderDivisionList").empty();
        $.each(result,function(key, data){
          var divisionList = '<option>'+data+'</option>';
          $("#senderDivisionList").append(divisionList);
        });
        $("#senderDivision").val(result.division);
        $("#senderDistrict").val(result.district);
        $("#senderThana").val(result.thana);
        $("#senderSubOffice").val(result.subOffice);
      }
    });

  });

  //Populate Sender District Option List Based On Division
  $('#senderDivision').on('change', function() {
    $.ajax({
    	 type: "post",
      url:"/parcel/getDistinctDistrictByDivision?ajax=true",
      data:{
        division: $("#senderDivision").val()
      },
      success:function(result){
        $("#senderDistrictList").empty();
        $.each(result,function(key, data){
          var districtList = '<option>'+data+'</option>';
          $("#senderDistrictList").append(districtList);
        });
        $("#senderDistrict").val(result.district);
        $("#senderThana").val(result.thana);
        $("#senderSubOffice").val(result.subOffice);
      }
    });
    $.ajax({
    	 type: "post",
    	url:"/parcel/getAddressByDivision?ajax=true",
        data:{
        	division: $("#senderDivision").val()
        },
        success : function(result){
        	$("#senderZone").val(result[1].zone);
        	$("#senderDistrict").val('');
        	$("#senderThana").val('');
        	$("#senderSubOffice").val('');
          }
    });
  });

  //Populate Sender Thana Option List Based On District
  $('#senderDistrict').on('change', function() {
    $.ajax({
    	 type: "post",
      url:"/parcel/getDistinctThanaByDistrict?ajax=true",
      data:{
        district: $("#senderDistrict").val()
      },
      success:function(result){
        $("#senderThanaList").empty();
        $.each(result,function(key, data){
          var thanaList = '<option>'+data+'</option>';
          $("#senderThanaList").append(thanaList);
        });
        $("#senderThana").val(result.thana);
        $("#senderSubOffice").val(result.subOffice);
      }
    });
    $.ajax({
    	 type: "post",
    	url:"/parcel/getAddressByDistrict?ajax=true",
        data:{
        	district: $("#senderDistrict").val()
        },
        success : function(result){
           	$("#senderDivision").val(result[1].division);
            $("#senderZone").val(result[1].zone);


        }
    });
  });

  //Populate Senders SubOffice Option List Based On SubOffice
  $('#senderThana').on('change', function() {
    $.ajax({
    	 type: "post",
      url:"/parcel/getDistinctSubOfficeByThana?ajax=true",
      data:{
        thana: $("#senderThana").val()
      },
      success:function(result){
        $("#sendersubOfficeList").empty();
        $.each(result,function(key, data){
          var subOfficeList = '<option>'+data+'</option>';
          $("#sendersubOfficeList").append(subOfficeList);
        });
        $("#senderSubOffice").val(result.subOffice);
      }
    });
  });

  //Populate Senders Zone List on document Ready
  $.ajax({
	  type: "post",
    url:"/parcel/getDistinctZone?ajax=true",
    success:function(result){
      $.each(result,function(key, data){
        var zoneList = '<option>'+data+'</option>';
        $("#senderZoneList").append(zoneList);
        $("#zoneList").append(zoneList);
      });
    }
  });

//Populate Senders Division List on document Ready
  $.ajax({
	  type: "post",
	    url:"/parcel/getDistinctDivision?ajax=true",
	    success:function(result){
	      $.each(result,function(key, data){
	        var divisionList = '<option>'+data+'</option>';
	        $("#senderDivisionList").append(divisionList);
	        $("#divisionList").append(divisionList);
	      });
	    }
	  });

//Populate Senders Division List on document Ready
  $.ajax({
	  type: "post",
	    url:"/parcel/getDistinctDistrict?ajax=true",
	    success:function(result){
	      $.each(result,function(key, data){
	        var districtList = '<option>'+data+'</option>';
	        $("#senderDistrictList").append(districtList);
	        $("#districtList").append(districtList);
	      });
	    }
	  });

  // Trigger ajax event on sender address after 3th character typed.
  // Populate sender's Thana list after 3 characters
  $('#senderThana').on('keyup', function() {
    if ($(this).val().length == 3) {
      $.ajax({
    	  type: "post",
        url:"/parcel/getAddressByThana?ajax=true",
        data:{
          thana: $("#senderThana").val()
        },
        success:function(result){
        	$.each(result,function(key, data){
        		$("#senderThanaList").empty();
                var thanaList = '<option>'+data.thana+'</option>';
                $("#senderThanaList").append(thanaList);
              });
        	$('#senderThana').change(function(){
        		var thana = $("#senderThana").val();
        		$.each(result,function(key, data){

        			if(data.thana == thana ){
        				$("#senderDivision").val(data.division);
        				$("#senderDistrict").val(data.district);
        				$("#senderZone").val(data.zone);
        				$("#sendetsubOffice").val('');
         			}
        		})
        	});
        }
      });
    }
  });

	//Trigger ajax event on sender address after 3th character typed.
  // Populate sender's subOffice list after 3 characters
  $('#senderSubOffice').on('keyup', function() {
	    if ($(this).val().length == 3) {
	      $.ajax({
	    	  type: "post",
	        url:"/parcel/getAddressBysubOfficeStartsWith?ajax=true",
	        data:{
	          subOffice: $("#senderSubOffice").val()
	        },
	        success:function(result){
	        	$.each(result,function(key, data){
	        		$("#senderSubOffice").empty();
	                var thanaList = '<option>'+data.subOffice+'</option>';
	                $("#sendersubOfficeList").append(thanaList);
	              });
	        	$('#senderSubOffice').change(function(){
	        		var subOffice = $("#senderSubOffice").val();
	        		$.each(result,function(key, data){
	        			if(data.subOffice == subOffice ){
	        				$("#senderDivision").val(data.division);
	        				$("#senderDistrict").val(data.district);
	        				$("#senderZone").val(data.zone);
	        				$("#senderThana").val(data.thana);
	        			}
	        		})
	        	});
	        }
	      });
	    }
      });
    }
  // End of block