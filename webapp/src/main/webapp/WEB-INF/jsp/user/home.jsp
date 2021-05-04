<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Wegual | Home</title>
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Font Awesome -->
<link rel="stylesheet"
	href="<c:url value="/plugins/fontawesome-free/css/all.min.css" />">
<!-- Ionicons -->
<!-- Theme style -->
<link rel="stylesheet" href="<c:url value="/css/adminlte.css" />">
<!-- Google Font: Source Sans Pro -->
<link
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


<style>
.center {
	display: flex;
	justify-content: center;
	align-items: center;
}

.pac-container {
	z-index: 10000 !important;
}

.follow-button {
	font-weight: 700;
	border-radius: 25px;
	min-width: 80px;
}

.pac-container {
	z-index: 10000 !important;
}

.fa-twitter {
	background: #55ACEE;
	color: white;
	padding: 10px;
	font-size: 30px;
	width: 50px;
	text-align: center;
	text-decoration: none;
	border-radius: 50%;
}

.autocomplete {
  background: #fff;
  position: relative;
  width: 20%;
  margin-left: 1%;
}

.autocomplete input {
  padding: 10px 10px;
  font-weight: 300;
  width: 100%;
  font-size: 14px;
  color: #666;
}

.autocomplete .close {
  position: absolute;
  font-size: 200%;
  top: 5%;
  left: calc(100% - 10%);
  align: left;
  color: #aaa;
  cursor: pointer;
  display: none;
}
.autocomplete .close.visible {
  display: block;
}
.dialog {
  width: 100%;
  display: none;
  min-height: 150%;
  max-height: 700%;
  overflow: scroll;
  border-top: 1px solid #f4f4f4;
  position: absolute;
  background: #FDFEFE;
}
.dialog.open {
  display: block;
}
.userSuggest div {
  cursor: pointer;
  transition: all 0.2s;
  background: #FDFEFE;
  font-family: 'helvetica neue';
}
.userSuggest div:hover {
  background: #F2F3F4;
}
</style>
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2uDzEktFvqAcppBjj52NPnIBpsX47euc&libraries=places"></script>
</head>
<body class="sidebar-mini sidebar-collapse"
	onload="checkDetails(${homePageData.user.filledDetails})">
	<div class="wrapper">
		<!-- Navbar -->
		<nav
			class="main-header navbar navbar-expand navbar-white navbar-light">

			<!-- SEARCH FORM -->
<!--  <<<<<<< Updated upstream  -->
			<!--<form class="form-inline ml-4">-->
				<div class="autocomplete">
					<input id="searchBar"
						type="text" placeholder="Search" aria-label="Search">
						<span class="close">&times;</span>
						<div class="dialog">
							<div id="userSuggest" class="container d-none d-lg-block card-footer-compact card-comments "></div>
							<div id="benSuggest" class="container d-none d-lg-block card-footer-compact card-comments "></div>
							<div id="giveupSuggest" class="container d-none d-lg-block card-footer-compact card-comments "></div>
						</div>
				</div>
			<!--</form>-->
			<br>
			<!-- Right navbar links -->
			<ul class="navbar-nav ml-auto">
				<a href="#" class="btn btn-primary" onclick="renderdropdown()"
					data-toggle="modal" data-target="#pledgeBox">Pledge</a>
				<a href="#" id="user-details" class="btn btn-primary"
					data-toggle="modal" data-target="#detailsModal"
					style="display: none;"></a>
				<!-- Messages Dropdown Menu -->
				<!-- Notifications Dropdown Menu -->
				<li class="nav-item dropdown"><a class="nav-link"
					data-toggle="dropdown" href="#"> <i class="far fa-bell"></i> <span
						class="badge badge-warning navbar-badge">15</span>
				</a>
					<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
						<span class="dropdown-item dropdown-header">15
							Notifications</span>
						<div class="dropdown-divider"></div>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item"> <i
							class="fas fa-users mr-2"></i> 8 follow requests <span
							class="float-right text-muted text-sm">12 hours</span>
						</a>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item"> <i class="fas fa-file mr-2"></i>
							3 new beneficiaries <span class="float-right text-muted text-sm">2
								days</span>
						</a> <a href="#" class="dropdown-item"> <i
							class="fas fa-file mr-2"></i> 6 new giveups <span
							class="float-right text-muted text-sm">2 days</span>
						</a> <a href="#" class="dropdown-item"> <i
							class="fas fa-file mr-2"></i> 1 confirmed committ <span
							class="float-right text-muted text-sm">10 mins</span>
						</a>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item dropdown-footer">See All
							Notifications</a>
					</div></li>
				<li class="nav-item"><a class="nav-link" data-toggle="dropdown"
					href="#"> <ion-icon class="far" name="exit-outline"></ion-icon>
				</a></li>
			</ul>
		</nav>
		<!-- /.navbar -->

<!-- Button trigger modal -->
		<!-- New Modal for comments -->


		<!-- Modal -->
		<div class="modal fade" id="commentBox">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h5 class="modal-title">Enter Your Comment</h5>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					
						<!-- Modal body -->
						<div class="modal-body">

							<div class="form-group">
								<label for="exampleFormControlTextarea1">Comment</label>
								<textarea class="form-control" name="description"
									placeholder="Type your comment here" id = "commentTextBox" rows="3"></textarea>
							</div>
							
							<div>
								<input type = "hidden" name="userId"
									id="commentTextBoxUserId" value="${homePageData.user.id}" />
							</div>
							
							<div>
								<input type = "hidden" name="feedId" id = "commentTextBoxFeedId" value=""/>
							</div>

						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<button  class="btn btn-success" data-dismiss="modal" onclick = "postComment()">Post</button>
						</div>
				</div>
			</div>
		</div>
		<!-- New Modal for comments -->

		<div class="modal fade" id="pledgeBox">
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
								<label for="exampleInputEmail1">Beneficiary</label> <select
									name="beneficiaryId" class="custom-select"
									id="beneficiarySelect">
								</select>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Giveup</label> <select
									name="giveupId" class="custom-select" id="giveupSelect">
								</select>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Currency</label> <select
									id="pledgeCurrency" name="currency" class="custom-select"
									id="currencySelect">
									<option value="INR">INR</option>
									<option value="USD">USD</option>
								</select>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Amount</label> <input
									id="pledgeAmount" name="amount" type="number"
									class="form-control" placeholder="Amount" />
							</div>

							<div class="form-group">
								<label for="exampleFormControlTextarea1">Description</label>
								<textarea class="form-control" name="description"
									placeholder="What inspired you to take this pledge?" rows="3"></textarea>
							</div>

							<div>
								<input type="hidden" name="userId"
									value="${homePageData.user.id}" />
							</div>
							<div>
								<input id="pledgeBaseCurrency" type="hidden" name="baseCurrency"
									value="${homePageData.user.baseCurrency}" />
							</div>
						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<button type="submit"  class="btn btn-success" value="submit">Submit</button>
<!--   ======= -->
	<!--  		<form class="form-inline ml-3">
				<div class="input-group input-group-sm">
					<input class="form-control form-control-navbar" type="search"
						placeholder="Search" aria-label="Search">
					<div class="input-group-append">
						<button class="btn btn-navbar" type="submit">
							<i class="fas fa-search"></i>
						</button>
					</div>
				</div>
			</form>
	-->

			
		
<!--     >>>>>>> Stashed changes -->
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
					<form action="/home/user/submit/details" method="post"
						id="userDetailForm">
						<!-- Modal body -->
						<div class="modal-body">
							<div class="form-group">
								<label for="locality">City</label> <input class="form-control"
									id="locality" name="city" type="text"
									placeholder="Enter your city">
							</div>
							<div class="form-group">
								<label for="administrative_area_level_1">State</label> <input
									class="form-control" name="state"
									id="administrative_area_level_1" type="text"
									placeholder="Enter your state">
							</div>
							<div class="form-group">
								<label for="country">Country</label> <input class="form-control"
									name="country" id="country" type="text"
									placeholder="Enter your country">
							</div>
							<div class="form-group">
								<label for="baseCurrencySelect">Base Currency</label> <select
									name="baseCurrency" class="custom-select"
									id="baseCurrencySelect">
									<option value="INR">INR</option>
									<option value="USD">USD</option>
								</select>
							</div>
							<div>
								<input type="hidden" name="userId"
									value="${homePageData.user.id}" />
							</div>
						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<button type="submit" class="btn btn-success" value="submit">Submit</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<!-- Main Sidebar Container -->
		<aside class="main-sidebar sidebar-dark-primary elevation-4">
			<!-- Brand Logo -->
			<a href="#" class="brand-link"> <img
				src="<c:url value="/img/AdminLTELogo.png" />" alt="AdminLTE Logo"
				class="brand-image img-circle elevation-3" style="opacity: .8">
				<span class="brand-text font-weight-light">Wegual</span>
			</a>

			<!-- Sidebar -->
			<div class="sidebar">
				<!-- Sidebar user (optional) -->
				<div class="user-panel mt-3 pb-3 mb-3 d-flex">
					<div class="image">
						<img id="image-profile-sidebar"
							src="<c:url value="${homePageData.user.pictureLink}" />"
							class="img-circle elevation-2" alt="User Image">
					</div>
					<div class="info">
						<a href="<c:url value="/home/profile" />" class="d-block">${homePageData.user.firstName}
							${homePageData.user.lastName}</a>
					</div>
				</div>

				<!-- Sidebar Menu -->
				<nav class="mt-2">
					<ul class="nav nav-pills nav-sidebar flex-column"
						data-widget="treeview" role="menu" data-accordion="false">
						<li class="nav-item"><a href="<c:url value="/home" />"
							class="nav-link"> <i class="nav-icon fas fa-home"></i>
								<p>Home</p>
						</a></li>
						<li class="nav-item"><a
							href="<c:url value="/home/beneficiaries" />" class="nav-link">
								<i class="nav-icon fas fa-hand-holding-usd"></i>
								<p>Beneficiaries</p>
						</a></li>
						<li class="nav-item"><a
							href="<c:url value="/home/giveups" />" class="nav-link"> <i
								class="nav-icon fas fa-praying-hands"></i>
								<p>Giveups</p>
						</a></li>
						<li class="nav-item"><a
							href="<c:url value="/home/pledges" />" class="nav-link"> <i
								class="nav-icon fas fa-hand-holding-heart"></i>
								<p>Pledges</p>
						</a></li>
						<li class="nav-item"><a
							href="<c:url value="/home/followings" />" class="nav-link"> <i
								class="nav-icon fas fa-user-friends"></i>
								<p>People</p>
						</a></li>
						<li onClick="checkTwitterLink()" class="nav-item"><a
							class="fa fa-twitter"></a></li>

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

					<div class="col-md-3">
						<div
							class="d-none d-lg-block card card-footer-compact card-comments ">
							<div class="card-header-compact">
								<h5>
									<b>Trends For You</b>
								</h5>
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
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-4">
								<div class="info-box">
									<span class="info-box-icon bg-info"><i
										class="fas fa-hand-holding-heart"></i></span>
									<div class="info-box-content">
										<span class="info-box-text ">Pledges</span> <span
											class="info-box-number">${homePageData.counts.pledge}</span>
									</div>
									<!-- /.info-box-content -->
								</div>
								<!-- /.info-box -->
							</div>
							<!-- /.col -->
							<div class="col-md-4">
								<div class="info-box">
									<span class="info-box-icon bg-success"><i
										class="fas fa-hand-holding-usd"></i></span>

									<div class="info-box-content">
										<span class="info-box-text">Beneficiaries</span> <span
											class="info-box-number">${homePageData.counts.beneficiary}</span>
									</div>
									<!-- /.info-box-content -->
								</div>
								<!-- /.info-box -->
							</div>
							<!-- /.col -->
							<div class="col-md-4">
								<div class="info-box">
									<span class="info-box-icon bg-gray"><i
										class="fas fa-praying-hands"></i></span>

									<div class="info-box-content">
										<span class="info-box-text">Give Ups</span> <span
											class="info-box-number">${homePageData.counts.giveup}</span>
									</div>
									<!-- /.info-box-content -->
								</div>
								<!-- /.info-box -->
							</div>
							<!-- /.col -->
						</div>


						<c:forEach var="feed" items="${feeds}">
							<div class="col-md-12 card" style="padding: 1%;">
								<div class="card-comment">
									<!-- User image -->
									<div onclick="viewDetailedPost('${feed.feedId}')">
										<div class="container-fluid">
											<img class="img-circle img-sm" src="${feed.pictureLink}"
												alt="User Image"> <span class="username"
												style="margin-left: 1%;"> <b>${feed.summary}</b>
											</span> <span class="text-muted float-right">${feed.timeAgo}</span>
										</div>
										<hr />
										<div class="row" style="margin-left: 1%;">
											<span class="row" style="margin-left: 1%;">
												${feed.detail} </span>
										</div>
									</div>
									<div class="row feed-items" id="${feed.feedId}"
										style="margin: 1%;">
										<c:choose>
											<c:when test="${feed.liked}">
												<button onclick="postLike('${feed.feedId}')" type="button"
													id="${feed.feedId}_like" class="btn btn-light col-md-4">
													<i class="fas fa-heart	" style="color: red">
														(${feed.detailActions.likes})</i>
												</button>
											</c:when>
											<c:otherwise>
												<button onclick="postLike('${feed.feedId}')" type="button"
													id="${feed.feedId}_like" class="btn btn-light col-md-4">
													<i class="far fa-heart	" style="color: black"></i>
												</button>
											</c:otherwise>
										</c:choose>
									<!-- Comment Button -->
										<a data-id="${feed.feedId}" href="#" class="open-comment-box btn btn-light col-md-4 far fa-comment"
											data-toggle="modal" data-target="#commentBox"></a>
											
										<button type="button" class="btn btn-light col-md-4">
											<i class="fa fa-share"></i>
										</button>
									</div>
									<!--  
									<div>
										<textarea class="form-control" id="${feed.feedId}_commentBox"
											name="comment" placeholder="Write a comment" rows="1"></textarea>
										<button
											onclick="postComment('${feed.feedId}', '${homePageData.user.id}')"
											class="btn btn-success">Post</button>
									</div>
									-->
									<div id="displayComments"></div>
								</div>
							</div>
						</c:forEach>






						<!-- /.col -->


						<!-- /.col -->
					</div>
					<div class="col-md-3">


						<div
							class="d-none d-lg-block card card-footer-compact card-comments ">
							<div class="card-header-compact">
								<h5>
									<b>Who to Follow</b>
								</h5>
							</div>
							<!-- /.card-header -->
							<div class="card-comment">
								<!-- User image -->
								<img class="img-circle img-sm"
									src="<c:url value="/img/user3-128x128.jpg" />" alt="User Image">

								<div class="comment-text">
									<span class="username"> Maria Gonzales
										<button type="button"
											class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
									</span>
									<!-- /.username -->

								</div>
								<span class="text-muted"><i class="fas fa-user-friends"></i>&nbsp;7
									Followers</span> <span class="text-muted"><i
									class="fas fa-hand-holding-usd"></i>&nbsp;$1350 Pledged</span>
								<!-- /.comment-text -->
							</div>
							<!-- /.card-body -->
							<div class="card-comment">
								<!-- User image -->
								<img class="img-circle img-sm"
									src="<c:url value="/img/user5-128x128.jpg" />" alt="User Image">

								<div class="comment-text">
									<span class="username"> Serena Gomez
										<button type="button"
											class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
									</span>
									<!-- /.username -->
								</div>
								<span class="text-muted"><i class="fas fa-user-friends"></i>&nbsp;17
									Followers</span> <span class="text-muted"><i
									class="fas fa-hand-holding-usd"></i>&nbsp;$135 Pledged</span>
								<!-- /.comment-text -->
							</div>
							<div class="card-comment">
								<!-- User image -->
								<img class="img-circle img-sm"
									src="<c:url value="/img/user6-128x128.jpg" />" alt="User Image">

								<div class="comment-text">
									<span class="username"> Joaqin Phoenix
										<button type="button"
											class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
									</span>
									<!-- /.username -->
								</div>
								<span class="text-muted"><i class="fas fa-user-friends"></i>&nbsp;21
									Followers</span> <span class="text-muted"><i
									class="fas fa-hand-holding-usd"></i>&nbsp;$50 Pledged</span>
								<!-- /.comment-text -->
							</div>
							<div class="card-comment center card-body-compact">
								<p class="text-primary">Show more</p>
							</div>
						</div>

					</div>


					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
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
		<script
			src="<c:url value="/plugins/bootstrap/js/bootstrap.bundle.min.js" />"></script>
		<!-- AdminLTE App -->
		<script src="<c:url value="/js/adminlte.min.js" />"></script>
		<!-- AdminLTE for demo purposes -->
		<script src="<c:url value="/js/demo.js" />"></script>
		<script src="https://code.jquery.com/jquery-3.5.1.js"
			integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
			crossorigin="anonymous"></script>
		<script type="text/javascript">

$(document).on("click", ".open-comment-box", function () {
		     var feedId = $(this).data('id');
		     $(".modal-body #commentTextBoxFeedId").val( feedId );
		    $('#commentBox').modal('show');
		});
var observerConfig = {
		root: null,
		rootMargin: '0px',
		threshold: 1.0
		};

const userPosts = document.querySelectorAll('.feed-items');

function callback(entries) {

	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
	   console.log(entries[0].target.id); // entries will 
	   feedId = entries[0].target.id;
	   
	   $.ajax({
       	url:'/home/user/post/view/'+feedId,
       	type: "POST",
       	cache: false,
       	contentType: false,
       	data : "{}",
       	processsData: false,
       	beforeSend: function(xhr){
           	xhr.setRequestHeader(header, token);
           	}
       })
       .done(function(e){
           console.log("Post Viewed by current user");
           });
	}

var observer = new IntersectionObserver(callback, observerConfig);

userPosts.forEach( post => {
	   observer.observe(post); 
	});

function getUserServiceURL(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var url = "";
	$.ajax({
        url: '/users/url',
        type: 'GET',
        cache: false,
        contentType: false,
        processData: false,
        async: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function (data, status, xhr){
            console.log(data);
            url = data;
            return data;
        }
	});
	return url;
}

function viewDetailedPost(postid){
	console.log(postid);
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    window.location.href = "http://localhost:9000/user/post/show/"+postid;
    /*$.ajax({
    	url:'/user/post/show/'+postid,
    	type: "POST",
    	cache: false,
    	contentType: false,
    	data : "{}",
    	processsData: false,
    	beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
        	},
   
    success : function(data, status, xhr){
        	console.log(data);
			window.location = data;
        }
    })
    .done(function(e){
        console.log("Detailed post will be showen");
        });*/
}


function showComment(postid){
	var userUrl = getUserServiceURL();
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:'/user/post/comment/'+postid,
        type: "GET",
        cache: false,
        contentType: false,
        processData: false,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
            },
           success : function (data, status, xhr){
			var coms ="";
	        data.map((com) => {
		        	console.log(com);
	    	        coms = coms+
	    	        "<div class='container-fluid'>"+
                		"<img class='img-circle img-sm' src='"+userUrl+com.commenter.picture_link+"'>"+
                		"<span class='username' style='margin-left: 1%;'>"+
                    		"<b>"+com.commenter.name+"</b>"+
                		"</span>"+
            		"</div>"+
            		"<hr/>"+
            		"<div class='row' style='margin-left: 1%;'>"+
                		"<span class='row' style='margin-left: 1%;'>"
                   			+com.comment+
                		"</span>"+
            		"</div>"
	    	        })
	    	        document.getElementById("feedComments").innerHTML= coms;
	        		jQuery.noConflict(); 
	        		$('#commentsModal').modal('show');
               }
       });
    
}

function postLike(feedId){
	var doc = feedId+"_like";
	var temp = document.getElementById(doc).innerHTML;
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
	if(temp.includes("fas")){
		console.log("post was already liked now triggering unlike operation");
		temp = temp.replace("fas", "far").replace("red", "black");
		document.getElementById(doc).innerHTML = temp;
		$.ajax({
        	url:'/home/user/post/unlike/'+feedId,
        	type: "POST",
        	cache: false,
        	contentType: false,
        	data : "{}",
        	processsData: false,
        	beforeSend: function(xhr){
            	xhr.setRequestHeader(header, token);
            	}
        })
        .done(function(e){
            console.log("Post Unliked");
            });
	}
	else{
		console.log("post was not liked now triggering like operation");
		temp = temp.replace("far", "fas").replace("black", "red");
		document.getElementById(doc).innerHTML = temp;
		$.ajax({
        	url:'/home/user/post/like/'+feedId,
        	type: "POST",
        	cache: false,
        	contentType: false,
        	data : "{}",
        	processsData: false,
        	beforeSend: function(xhr){
            	xhr.setRequestHeader(header, token);
            	}
        })
        .done(function(e){
            console.log("Post Liked");
         	});
	}
}
/*function postComment(feedId, userId){
	var comment = document.getElementById(feedId+"_commentBox").value;
	if(comment.trim() == ""){
		alert("No Text in Comment Box");
		return;
	}
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var formData = new FormData();
    formData.append("userId" , userId);
    formData.append("feedId" , feedId);
    formData.append("comment", comment);
    $.ajax({
        url:'home/comment/submit',
        type: "POST",
        cache: false,
        contentType: false,
        processData: false,
        data : formData,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
            },
           success : function (data, status, xhr){
			console.log("Comment posted successfully");
       }});
    
}
*/

function postComment(){
	var comment = document.getElementById("commentTextBox").value;
	var userId =  document.getElementById("commentTextBoxUserId").value;
	var feedId = document.getElementById("commentTextBoxFeedId").value;
	if(comment.trim() == ""){
		alert("No Text in Comment Box");
		return;
	}
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var formData = new FormData();
    formData.append("userId" , userId);
    formData.append("feedId" , feedId);
    formData.append("comment", comment);
    $.ajax({
        url:'/home/comment/submit',
        type: "POST",
        cache: false,
        contentType: false,
        processData: false,
        data : formData,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
            },
           success : function (data, status, xhr){
			console.log("Comment posted successfully");
       }});
    
}

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

	function checkTwitterLink(){
		console.log("Twitter icon clicked");
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
	        url:'/home/checktwitterauth',
	        type: "GET",
	        cache: false,
	        contentType: false,
	        processData: false,
	        beforeSend: function(xhr){
	            xhr.setRequestHeader(header, token);
	            },
	           success : function (data, status, xhr){
		           console.log('---------> ' + data);
		           if(data===0){
		        	   //window.location.href = "https://www.google.com";
		        	   $.ajax({
			       	        url:'/auth/twitter/url',
			       	        type: "GET",
			       	        cache: false,
			       	        contentType: false,
			       	        processData: false,
			       	        beforeSend: function(xhr){
			       	            xhr.setRequestHeader(header, token);
			       	            },
			       	           success : function (data, status, xhr){
			       		           console.log(data);
			       		           window.location.href = data;
			       	        }
			               });
			       }
		           else{
		        	   alert('You have already linked your twitter account');
			       }
	        }
        });
        return;
	}

	function getUserServiceURL(){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		var url = "";
		$.ajax({
            url: '/users/url',
            type: 'GET',
            cache: false,
            contentType: false,
            processData: false,
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success : function (data, status, xhr){
                console.log(data);
                url = data;
                return data;
            }
		});
		return url;
	}

	function getBeneficiaryServiceURL(){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		var url = "";
		$.ajax({
            url: '/beneficiary/url',
            type: 'GET',
            cache: false,
            contentType: false,
            processData: false,
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success : function (data, status, xhr){
                console.log(data);
                url = data;
                return data;
            }
		});
		return url;
	}

	function getGiveupServiceURL(){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		var url = "";
		$.ajax({
            url: '/giveup/url',
            type: 'GET',
            cache: false,
            contentType: false,
            processData: false,
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success : function (data, status, xhr){
                console.log(data);
                url = data;
                return data;
            }
		});
		return url;
	}

	function beneficiaryHome(benId){
		window.location = '/beneficiary/profile/' + benId;
	}
	function findBenByName() {
	    var benUrl = getBeneficiaryServiceURL();
    	var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
        var search = document.getElementById('searchBar').value;
		console.log("Search Term = " + search);
        if (search != "") {

            $.ajax({
                url: '/beneficiary/suggest/' + search,
                type: 'GET',
                cache: false,
                contentType: false,
                processData: false,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success : function (data, status, xhr){
	                if(data.length>0){
	                    console.log(data);
	                    $("#benSuggest").empty();
		                $("#benSuggest").append("<div class='card-header-compact'> <h5> <b> Beneficiaries </b> </h5> </div>");
	                    data.map((item => {
	                    	$("#benSuggest").append("<div class='card-comment' style={margin-top: 5%;}> <img class='img-circle img-sm' src='"+ benUrl + item["picture_link"] +"'> <div class='comment-text'> <span id='"+ item["id"]+"'class='username'>" + item["name"] + "</span> </div</div>");
	                    	
		                }))
			                
			        }
	                else{
	                	$("#benSuggest").empty();
		                }
                }
            });
        }
        else{
        	$("#benSuggest").empty();
		    }
    }

	function findGiveupByName() {
	    var giveupUrl = getGiveupServiceURL();
    	var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
        var search = document.getElementById('searchBar').value;
		console.log("Search Term = " + search);
        if (search != "") {

            $.ajax({
                url: '/giveup/suggest/' + search,
                type: 'GET',
                cache: false,
                contentType: false,
                processData: false,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success : function (data, status, xhr){
	                if(data.length>0){
	                    console.log(data);
	                    $("#giveupSuggest").empty();
		                $("#giveupSuggest").append("<div class='card-header-compact'> <h5> <b> Giveup </b> </h5> </div>");
	                    data.map((item => {
	                    	$("#giveupSuggest").append("<div class='card-comment' style={margin-top: 5%;}> <img class='img-circle img-sm' src='"+ giveupUrl + item["picture_link"] +"'> <div class='comment-text'> <span id='"+ item["id"]+"'class='username'>" + item["name"] + "</span> </div</div>");
	                    	
		                }))
			        }
	                else{
	                	$("#giveupSuggest").empty();
		            }
                }
            });
        }
        else{
        	$("#giveupSuggest").empty();
		}
    }

	function findUserByName() {
	    var userUrl = getUserServiceURL();
    	var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
        var search = document.getElementById('searchBar').value;
		console.log("Search Term = " + search);
        if (search != "") {

            $.ajax({
                url: '/users/suggest/' + search,
                type: 'GET',
                cache: false,
                contentType: false,
                processData: false,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success : function (data, status, xhr){
	                console.log(data.length);
	                if(data.length>0){
		                $("#userSuggest").empty();
		                $("#userSuggest").append("<div class='card-header-compact'> <h5> <b> Users </b> </h5> </div>");
	                	console.log(data);
	                    data.map((item => {
	                    	$("#userSuggest").append("<div class='card-comment'> <img class='img-circle img-sm' src='"+ userUrl + item["picture_link"] +"'> <div class='comment-text'> <span class='username'>" + item["name"] + "</span> </div</div>");
	                    	
		                }))
		            }
	                else{
	                	$("#userSuggest").empty();
	                }

                }
            });
        }
        else{
        	$("#userSuggest").empty();
		    }
    }

	$(document).ready(function () {

		var searchBox = document.getElementById('searchBar');
		searchBox.addEventListener("blur", function () {
			$('.dialog').removeClass('open');
			$('.autocomplete .close').removeClass('visible');
			document.getElementById('searchBar').value = "";
			$("#userSuggest").empty();
			$("#benSuggest").empty();
			$("#giveupSuggest").empty();
		});

		$('.autocomplete input').on('input', function() {
	        $('.dialog').addClass('open');
	        $('.autocomplete .close').addClass('visible');
	        findUserByName();
			findBenByName();
			findGiveupByName();
			if( $.trim($("#userSuggest").html())=='' && $.trim($("#benSuggest").html())=='' && $.trim($("#giveupSuggest").html())==''){
				
			}
	    });
		


	});

</script>
</body>
</html>
