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
</style>
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2uDzEktFvqAcppBjj52NPnIBpsX47euc&libraries=places"></script>
</head>
<body class="sidebar-mini sidebar-collapse"
	onload="showComment('${feed.feedId}')">
	<div class="wrapper">
		<!-- Navbar -->
		<nav
			class="main-header navbar navbar-expand navbar-white navbar-light">

			<!-- SEARCH FORM -->
			

			<!-- Right navbar links -->

		</nav>
		<!-- /.navbar -->

		<!-- Button trigger modal -->
		<!-- New Modal for comments -->


		
		<!-- New Modal for comments -->
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
						
					</div>
					<div class="col-md-6">


							
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
									
								</div>
							</div>
							
								<div id="displayComments"></div>
								

						<!-- /.col -->


						<!-- /.col -->
					</div>
					<div class="col-md-3">

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
<script type = "text/javascript">
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
	    	        '<div class="col-md-12 card" style="padding: 1%;">'+
					'<div class="card-comment">'+
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
            		"</div>"+
            		"</div>"+
            		"</div>"
	    	        })
	    	        document.getElementById("displayComments").innerHTML= coms;
	        		//jQuery.noConflict(); 
	        		//$('#commentsModal').modal('show');
               }
       });
    
}
/*
function showCommentonPage(feed){
	console.log(feed);
	var model = @Html.Raw(Json.Encode(ModelAndView.feeds));
	console.log(model);
}
*/
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


</script>
</body>
</html>
