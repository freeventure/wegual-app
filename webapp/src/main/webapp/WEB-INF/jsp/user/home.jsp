<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="_csrf" content="${_csrf.token}"/>
  <meta name="_csrf_header" content="${_csrf.headerName}"/>
  <title>Wegual | Home</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Font Awesome -->
  <link rel="stylesheet" href="<c:url value="/plugins/fontawesome-free/css/all.min.css" />">
  <!-- Ionicons -->
  <!-- Theme style -->
  <link rel="stylesheet" href="<c:url value="/css/adminlte.css" />">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
  
  <style>
	.center {
	  display: flex;
	  justify-content: center;
	  align-items: center;
	}
	.follow-button {font-weight: 700; border-radius: 25px; min-width:80px;}
	.pac-container {
        z-index: 10000 !important;
    }
</style>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2uDzEktFvqAcppBjj52NPnIBpsX47euc&libraries=places"></script>
</head>
<body class="sidebar-mini sidebar-collapse" onload="checkDetails(${homePageData.user.filledDetails})">
<div class="wrapper">
  <!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light">

    <!-- SEARCH FORM -->
    <form class="form-inline ml-3">
      <div class="input-group input-group-sm">
        <input class="form-control form-control-navbar" type="search" placeholder="Search" aria-label="Search">
        <div class="input-group-append">
          <button class="btn btn-navbar" type="submit">
            <i class="fas fa-search"></i>
          </button>
        </div>
      </div>
    </form>
	
    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
    <a href="#" class="btn btn-primary" onclick="renderdropdown()" data-toggle="modal" data-target="#myModal">Pledge</a>
    <a href="#" id="user-details" class="btn btn-primary" data-toggle="modal" data-target="#detailsModal" style="display: none;"></a>
      <!-- Messages Dropdown Menu -->
      <!-- Notifications Dropdown Menu -->
      <li class="nav-item dropdown">
        <a class="nav-link" data-toggle="dropdown" href="#">
          <i class="far fa-bell"></i>
          <span class="badge badge-warning navbar-badge">15</span>
        </a>
        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
          <span class="dropdown-item dropdown-header">15 Notifications</span>
          <div class="dropdown-divider"></div>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
            <i class="fas fa-users mr-2"></i> 8 follow requests
            <span class="float-right text-muted text-sm">12 hours</span>
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
            <i class="fas fa-file mr-2"></i> 3 new beneficiaries
            <span class="float-right text-muted text-sm">2 days</span>
          </a>
          <a href="#" class="dropdown-item">
            <i class="fas fa-file mr-2"></i> 6 new giveups
            <span class="float-right text-muted text-sm">2 days</span>
          </a>
          <a href="#" class="dropdown-item">
            <i class="fas fa-file mr-2"></i> 1 confirmed committ
            <span class="float-right text-muted text-sm">10 mins</span>
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item dropdown-footer">See All Notifications</a>
        </div>
      </li>
	  <li class="nav-item">
        <a class="nav-link" data-toggle="dropdown" href="#">
          <ion-icon class="far" name="exit-outline"></ion-icon>
        </a>
	  </li>
    </ul>
  </nav>
  <!-- /.navbar -->
  
   <div class="modal fade" id="myModal">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
              <h5 class="modal-title">Enter Details</h5>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
			<form action="/home/pledge/submit" method="post" id="pledgeForm">
            <!-- Modal body -->
            <div class="modal-body">
            	
              	<div class="form-group">
    				<label for="exampleInputEmail1">Beneficiary</label>
    				<select name = "beneficiaryId" class="custom-select" id="beneficiarySelect">
  					</select>
  				</div>
  				<div class="form-group">
    				<label for="exampleInputEmail1">Giveup</label>
    				<select name="giveupId" class="custom-select" id="giveupSelect">
   					</select>
  				</div>
  				<div class="form-group">
    				<label for="exampleInputEmail1">Currency</label>
    				<select id="pledgeCurrency" name="currency" class="custom-select" id="currencySelect">
    					<option  value="INR">INR</option>
    					<option  value="USD">USD</option>
  					</select>
  				</div>
  				<div class="form-group">
    				<label for="exampleInputEmail1">Amount</label>
    				<input id="pledgeAmount" name="amount" type="number" class="form-control"  placeholder="Amount" />
  				</div>
  				<div>
  				 	<input type="hidden" name="userId" value="${homePageData.user.id}" />
  				</div>
  				<div>
  				 	<input id="pledgeBaseCurrency" type="hidden" name="baseCurrency" value="${homePageData.user.baseCurrency}" />
  				</div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
              <button type="submit"  class="btn btn-success" value="submit">Submit</button>
            </div>
			</form>
          </div>
        </div>
      </div>
      
      <div class="modal fade" id="detailsModal">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
              <h5 class="modal-title">Enter Details</h5>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
			<form action="/home/user/submit/details" method="post" id="userDetailForm">
            <!-- Modal body -->
            <div class="modal-body">
              	<div class="form-group">
    				<label for="locality">City</label>
    				<input class="form-control" id="locality" name="city" type="text" placeholder="Enter your city">
  				</div>
  				<div class="form-group">
    				<label for="administrative_area_level_1">State</label>
    				<input class="form-control" name="state" id="administrative_area_level_1" type="text" placeholder="Enter your state">
  				</div>
  				<div class="form-group">
    				<label for="country">Country</label>
    				<input class="form-control" name="country" id="country" type="text" placeholder="Enter your country">
  				</div>
  				<div class="form-group">
    				<label for="baseCurrencySelect">Base Currency</label>
    				<select name="baseCurrency" class="custom-select" id="baseCurrencySelect">
    					<option  value="INR">INR</option>
    					<option  value="USD">USD</option>
  					</select>
  				</div>
  				<div>
  				 	<input type="hidden" name="userId" value="${homePageData.user.id}" />
  				</div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
              <button type="submit"  class="btn btn-success" value="submit">Submit</button>
            </div>
			</form>
          </div>
        </div>
      </div>

  <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="#" class="brand-link">
      <img src="<c:url value="/img/AdminLTELogo.png" />"
           alt="AdminLTE Logo"
           class="brand-image img-circle elevation-3"
           style="opacity: .8">
      <span class="brand-text font-weight-light">Wegual</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar user (optional) -->
      <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        <div class="image">
          <img id="image-profile-sidebar" src="<c:url value="${homePageData.user.pictureLink}" />" class="img-circle elevation-2" alt="User Image">
        </div>
        <div class="info">
          <a href="<c:url value="/home/profile" />" class="d-block">${homePageData.user.firstName} ${homePageData.user.lastName}</a>
        </div>
      </div>

      <!-- Sidebar Menu -->
            <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <li class="nav-item">
            <a href="<c:url value="/home" />" class="nav-link">
			  <i class="nav-icon fas fa-home"></i>
              <p>
                Home
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="<c:url value="/home/beneficiaries" />" class="nav-link">
			  <i class="nav-icon fas fa-hand-holding-usd"></i>
              <p>
                Beneficiaries
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="<c:url value="/home/giveups" />" class="nav-link">
			  <i class="nav-icon fas fa-praying-hands"></i>
              <p>
                Giveups
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="<c:url value="/home/pledges" />" class="nav-link">
			  <i class="nav-icon fas fa-hand-holding-heart"></i>
              <p>
                Pledges
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="<c:url value="/home/followings" />" class="nav-link">
			  <i class="nav-icon fas fa-user-friends"></i>
              <p>
                People
              </p>
            </a>
          </li>
		</ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
      <div class="container-fluid">
        <div class="row">
				<div class="col-md-6">
					<div class="row">
					  <div class="col-md-4">
						<div class="info-box">
						  <span class="info-box-icon bg-info"><i class="fas fa-hand-holding-heart"></i></span>
						  <div class="info-box-content">
							<span class="info-box-text ">Pledges</span>
							<span class="info-box-number">${homePageData.counts.pledge}</span>
						  </div>
						  <!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					  </div>
					  <!-- /.col -->
					  <div class="col-md-4">
						<div class="info-box">
						  <span class="info-box-icon bg-success"><i class="fas fa-hand-holding-usd"></i></span>

						  <div class="info-box-content">
							<span class="info-box-text">Beneficiaries</span>
							<span class="info-box-number">${homePageData.counts.beneficiary}</span>
						  </div>
						  <!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					  </div>
					  <!-- /.col -->
					  <div class="col-md-4">
						<div class="info-box">
						  <span class="info-box-icon bg-gray"><i class="fas fa-praying-hands"></i></span>

						  <div class="info-box-content">
							<span class="info-box-text">Give Ups</span>
							<span class="info-box-number">${homePageData.counts.giveup}</span>
						  </div>
						  <!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					  </div>
					  <!-- /.col -->
					</div>
					<div class="col-md-12" style="padding: 1%;">
						<div class="card-comment elevation-1" >
	                         <!-- User image -->
	                         <div class="container-fluid">
	                             <img class="img-circle img-sm" src="./img/user4-128x128.jpg" alt="User Image">
	                             <span class="username" style="margin-left: 1%;">
	                                 <b>Feed Test</b>
	                             </span>
	                             <span class="text-muted float-right">
	                                 2 hours ago
	                             </span>
	                         </div>
	                         <hr/>
	                         <div class="row" style="margin-left: 1%;">
	                             <span class="row" style="margin-left: 1%;">
	                                 hfgajhfajhakfwhg<br />hgajhfgh<br /> sjdhahdahj<br /> sjdhahdahj<br />
	                                 sjdhahdahj<br /> sjdhahdahj
	                             </span>
	                         </div>
	                         <div class="row" style="margin : 1%;">
	                             <button type="button" class="btn btn-light col-md-4"><i
	                                     class="fa fa-thumbs-up"></i></button>
	                             <button type="button" class="btn btn-light col-md-4"><i
	                                     class="fa fa-comment"></i></button>
	                             <button type="button" class="btn btn-light col-md-4"><i
	                                     class="fa fa-share"></i></button>
	                         </div>
	                         <!-- </div> -->
	                     </div>
                     </div>
				<div class="card card-footer card-comments">
					<div class="card-comment">
					  <!-- User image -->
					  <img class="img-circle img-sm" src="<c:url value="/img/user3-128x128.jpg" />" alt="User Image">

					  <div class="comment-text">
						<span class="username">
						  Maria Gonzales
						  <span class="text-muted float-right">8:03 PM Today</span>
						</span><!-- /.username -->
						<span class="text-muted">It is a long established fact that a reader will be distracted
						by the readable content of a page when looking at its layout.</span>
					  </div>
					  <!-- /.comment-text -->
					</div>
					<div class="card-comment">
					  <!-- User image -->
					  <img class="img-circle img-sm" src="<c:url value="/img/user3-128x128.jpg" />" alt="User Image">

					  <div class="comment-text">
						<span class="username">
						  Maria Gonzales
						  <span class="text-muted float-right">8:03 PM Today</span>
						</span><!-- /.username -->
						<span class="text-muted">It is a long established fact that a reader will be distracted
						by the readable content of a page when looking at its layout.</span>
					  </div>
					  <!-- /.comment-text -->
					</div>
					<div class="card-comment">
					  <!-- User image -->
					  <img class="img-circle img-sm" src="<c:url value="/img/user3-128x128.jpg" />" alt="User Image">

					  <div class="comment-text">
						<span class="username">
						  Maria Gonzales
						  <span class="text-muted float-right">8:03 PM Today</span>
						</span><!-- /.username -->
						<span class="text-muted">It is a long established fact that a reader will be distracted
						by the readable content of a page when looking at its layout.</span>
					  </div>
					  <!-- /.comment-text -->
					</div>					
				</div>
				</div>
          <!-- /.col -->
		  
          <div class="col-md-4">
					<div class="d-none d-lg-block card card-footer-compact card-comments ">
					  <div class="card-header-compact">
						<h5><b>Trends For You</b></h5>
					  </div>
					  <!-- /.card-header -->
					  <div class="card-comment card-body-compact">
						<p class="text-muted">Beneficiaries - Trending</p>
						<p class="username-light">OSAAT USA</p>
						<p class="text-muted">24 Followers, $1200 Pledged</p>
					  </div>
					  <!-- /.card-body -->
					  <div class="card-comment card-body-compact">
						<p class="text-muted">Users - Trending</p>
						<p class="username-light">Wegual UserTwo</p>
						<p class="text-muted">24 Followers, 6 pledges</p>
					  </div>
					  <div class="card-comment card-body-compact">
						<p class="text-muted">Locations - Trending</p>
						<p class="username-light">California, USA</p>
						<p class="text-muted">24 Pledges, 6 Beneficiaries</p>
					  </div>
					  <div class="card-comment card-body-compact">
						<p class="text-muted">Give Ups - Trending</p>
						<p class="username-light">Birthday party gifts give up</p>
						<p class="text-muted">224 Followers, 89 Beneficiaries</p>
					  </div>
					  <div class="card-comment center card-body-compact">
						<p class="text-primary">Show more</p>
					  </div>
					</div>
				
					<div class="d-none d-lg-block card card-footer-compact card-comments ">
					  <div class="card-header-compact">
						<h5><b>Who to Follow</b></h5>
					  </div>
					  <!-- /.card-header -->
						  <div class="card-comment">
						  <!-- User image -->
						  <img class="img-circle img-sm" src="<c:url value="/img/user3-128x128.jpg" />" alt="User Image">

						  <div class="comment-text">
							<span class="username">
							  Maria Gonzales 
							  <button type="button" class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
							</span><!-- /.username -->
							
						  </div>
							<span class="text-muted"><i class="fas fa-user-friends"></i>&nbsp;7 Followers</span>
							<span class="text-muted"><i class="fas fa-hand-holding-usd"></i>&nbsp;$1350 Pledged</span>
						  <!-- /.comment-text -->
						  </div>
					  <!-- /.card-body -->
						  <div class="card-comment">
						  <!-- User image -->
						  <img class="img-circle img-sm" src="<c:url value="/img/user5-128x128.jpg" />" alt="User Image">

						  <div class="comment-text">
							<span class="username">
							  Serena Gomez 
								<button type="button" class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
							</span><!-- /.username -->
						  </div>
							<span class="text-muted"><i class="fas fa-user-friends"></i>&nbsp;17 Followers</span>
							<span class="text-muted"><i class="fas fa-hand-holding-usd"></i>&nbsp;$135 Pledged</span>
						  <!-- /.comment-text -->
						  </div>
						  <div class="card-comment">
						  <!-- User image -->
						  <img class="img-circle img-sm" src="<c:url value="/img/user6-128x128.jpg" />" alt="User Image">

						  <div class="comment-text">
							<span class="username">
							  Joaqin Phoenix
								<button type="button" class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>							  
							</span><!-- /.username -->
						  </div>
							<span class="text-muted"><i class="fas fa-user-friends"></i>&nbsp;21 Followers</span>
							<span class="text-muted"><i class="fas fa-hand-holding-usd"></i>&nbsp;$50 Pledged</span>
						  <!-- /.comment-text -->
						  </div>
						  <div class="card-comment center card-body-compact">
							<p class="text-primary">Show more</p>
						  </div>
				    </div>
				
		  </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- ionic -->
<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>
<!-- jQuery -->
<script src="<c:url value="/plugins/jquery/jquery.min.js" />"></script>
<!-- Bootstrap 4 -->
<script src="<c:url value="/plugins/bootstrap/js/bootstrap.bundle.min.js" />"></script>
<!-- AdminLTE App -->
<script src="<c:url value="/js/adminlte.min.js" />"></script>
<!-- AdminLTE for demo purposes -->
<script src="<c:url value="/js/demo.js" />"></script>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script>
function renderdropdown(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:'/beneficiary/all',
        type: "GET",
        cache: false,
        contentType: false,
        processData: false,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
            },
           success : function (data, status, xhr){
			var bens ="";
	        data.map((ben) => {
	    	        bens = bens+"<option  value="+ben.beneficiary_id+">"+ben.beneficiary_name+"</option>";
	    	        })
	    	        document.getElementById("beneficiarySelect").innerHTML= bens;
               }
       });
    $.ajax({
        url:'/giveup/all',
        type: "GET",
        cache: false,
        contentType: false,
        processData: false,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
            },
           success : function (data, status, xhr){
			var giveups ="";
	        data.map((giveup) => {
	    	        giveups = giveups+"<option  value="+giveup.giveup_id+">"+giveup.giveup_name+"</option>";
	    	        })
	    	        document.getElementById("giveupSelect").innerHTML= giveups;
           }
       });
   };
   $("#pledgeForm").submit(async function(event){
		event.preventDefault();
		var currency = document.getElementById('pledgeCurrency').value;
		var base = document.getElementById('pledgeBaseCurrency').value;
		var amount = parseInt(document.getElementById('pledgeAmount').value);
		console.log(currency);
		console.log(base);
		const response = await fetch('http://data.fixer.io/api/latest?access_key=a41c97c5b33917164033b4b0fdd4c517');
		const myJson = await response.json();
		var rates = myJson["rates"];
		var baseCurrencyValue = rates[base];
		var currencyValue = rates[currency];
		console.log(baseCurrencyValue);
		console.log(currencyValue);
		console.log(amount);
		var baseAmount = ((baseCurrencyValue/currencyValue)*amount);
		console.log(myJson);	
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	    var post_url = $(this).attr("action");
	    var request_method = $(this).attr("method");
	    var form_data = $(this).serialize();
	    form_data += "&baseAmount=" + encodeURIComponent(baseAmount);
	    console.log(form_data);
	    debugger;
	    $.ajax({
				url: post_url,
				type: request_method,
				data: form_data,
				cache: false,
				beforeSend: function(xhr){
					xhr.setRequestHeader(header, token);
					}
	        })
	        .done(function(e){
				console.log("pledge saved");
				setInterval('location.reload()', 3000);
	        });
	});

   $("#userDetailForm").submit(function(event){
		event.preventDefault();
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	    var post_url = $(this).attr("action");
	    var request_method = $(this).attr("method");
	    var form_data = $(this).serialize();
	    $.ajax({
				url: post_url,
				type: request_method,
				data: form_data,
				cache: false,
				beforeSend: function(xhr){
					xhr.setRequestHeader(header, token);
					}
	        })
	        .done(function(e){
				console.log("user details saved");
				setInterval('location.reload()', 3000);
	        });
	});

	function checkDetails(detailsFilled){
		console.log("inside details check", detailsFilled);
		if(detailsFilled){
			return;
		}
		else{
			document.getElementById("user-details").click();
			var componentForm = {
			  locality: 'long_name',
			  administrative_area_level_1: 'long_name',
			  country: 'long_name'
			};
			var input = document.getElementById('locality');
			console.log(input);
	        var options = {
	            types: ['(cities)']
	        };
	        var autocomplete = new google.maps.places.Autocomplete(input, options);
	        //autocomplete.setFields(['address_component']);
	        google.maps.event.addListener(autocomplete, 'place_changed', function () {
	            var place = autocomplete.getPlace();
	            console.log(place);
	            for (var component in componentForm) {
                	document.getElementById(component).value = '';
                	document.getElementById(component).disabled = false;
               }
	            for (var i = 0; i < place.address_components.length; i++) {
	                var addressType = place.address_components[i].types[0];
	                if (componentForm[addressType]) {
	                  var val = place.address_components[i][componentForm[addressType]];
	                  document.getElementById(addressType).value = val;
	                }
	              }
	            // console.log(place["address_components"][0]["long_name"]);
	            // console.log(place["address_components"][1]["long_name"]);
	            // console.log(place["address_components"][2]["long_name"]);
	            // console.log(place["address_components"][3]["long_name"]);
	            //console.log(place);
	        });
		}
	}
</script>

</body>
</html>
