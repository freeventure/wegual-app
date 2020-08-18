<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="_csrf" content="${_csrf.token}"/>
  	<meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>Wegual | Giveup</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="<c:url value="/plugins/fontawesome-free/css/all.min.css" />" >
    <!-- Ionicons -->
    <!-- Theme style -->
    <link rel="stylesheet" href="<c:url value="/css/adminlte.css" />" >
    <!-- Google Font: Source Sans Pro -->
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">

    <style>
        .center {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .follow-button {
            font-weight: 700;
            border-radius: 25px;
            min-width: 80px;
        }
    </style>
</head>

<body class="sidebar-mini sidebar-collapse">
    <div class="wrapper">
        <!-- Navbar -->
        <nav class="main-header navbar navbar-expand navbar-white navbar-light">

            <!-- SEARCH FORM -->
            <form class="form-inline ml-3">
                <div class="input-group input-group-sm">
                    <input class="form-control form-control-navbar" type="search" placeholder="Search"
                        aria-label="Search">
                    <div class="input-group-append">
                        <button class="btn btn-navbar" type="submit">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>

            <!-- Right navbar links -->
            <ul class="navbar-nav ml-auto">
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

        <!-- Main Sidebar Container -->
        <aside class="main-sidebar sidebar-dark-primary elevation-4">
            <!-- Brand Logo -->
            <a href="#" class="brand-link">
                <img src="/img/AdminLTELogo.png"
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
                        <img src="/img/user1-128x128.jpg" class="img-circle elevation-2"
                        alt="User Image">
                    </div>
                    <div class="info">
                        <a href="#" class="d-block">Wegual UserOne</a>
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
                    <div class="col-md-3">
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
                    </div>
                    <div class="col-md-6">
			            <div class="card">
			              <div class="card-header p-2">
			                <ul class="nav nav-pills">
			                  <li class="nav-item"><a class="nav-link active" href="#liked" data-toggle="tab">Liked</a></li>
			                  <li class="nav-item"><a class="nav-link" href="#followed" data-toggle="tab">Followed</a></li>
							  <li class="nav-item"><a class="nav-link" href="#pledged" data-toggle="tab">Pledged For</a></li>
			                </ul>
			              </div><!-- /.card-header -->
			              <div class="card-body">
			                <div class="tab-content">
			                  <!-- /.tab-pane -->
			                  <div class="tab-pane" id="pledged">
		                        <div class="row" style="padding: 1%;">
									<h1>Giveups used for Pledges</h1>
										<div class="col-md-12">
											<c:forEach var="giveup" items="${pledgedgiveups}">
		                                    	<div class="row elevation-1" style="padding: 1%;">
													<div class="col-md-4">
														<img id="followeePic" src="/img/user4-128x128.jpg"
		                                                	class="img-circle elevation-2" alt="User Image">
													</div>
														<div class="col-md-6">
		                                            	<a>
		                                                	<h3>${giveup.name}</h3>
		                                            	</a>
													</div>
													<!-- <div class="col-md-2">
														<button type="button" onclick="unlike(${giveup.id})" class=" float-right follow-button btn btn-outline-primary btn-xs">Unlike</button>
													</div> -->
												</div>
		                                		</c:forEach>
										</div>
								</div>
                    		</div>
                    		<div class="tab-pane active" id="liked">
		                        <div class="row" style="padding: 1%;">
		                                <h1>Giveups liked by User</h1>
			                            <div class="col-md-12">
			                                <c:forEach var="giveup" items="${likedGiveups}">
			                                    <div class="row elevation-1" style="padding: 1%;">
			                                        <div class="col-md-4">
			                                            <img id="followeePic" src="/img/user4-128x128.jpg"
			                                                class="img-circle elevation-2" alt="User Image">
			                                        </div>
			                                        <div class="col-md-6">
			                                            <a>
			                                                <h3>${giveup.name}</h3>
			                                            </a>
			                                        </div>
			                                        <div class="col-md-2">
			                                            <button type="button" onclick="unlike(${giveup.id})" class=" float-right follow-button btn btn-outline-primary btn-xs">Unlike</button>
			                                        </div>
			                                    </div>
			                                </c:forEach>
			                            </div>
		                        </div>
                    		</div>
                    		<div class="tab-pane" id="followed">
		                        <div class="row" style="padding: 1%;">
		                                <h1>Giveups followed by User</h1>
			                            <div class="col-md-12">
			                                <c:forEach var="giveup" items="${followedGiveups}">
			                                    <div class="row elevation-1" style="padding: 1%;">
			                                        <div class="col-md-4">
			                                            <img id="followeePic" src="/img/user4-128x128.jpg"
			                                                class="img-circle elevation-2" alt="User Image">
			                                        </div>
			                                        <div class="col-md-6">
			                                            <a>
			                                                <h3>${giveup.name}</h3>
			                                            </a>
			                                        </div>
			                                        <div class="col-md-2">
			                                            <button type="button" onclick="unfollow(${giveup.id})" class=" float-right follow-button btn btn-outline-primary btn-xs">Unfollow</button>
			                                        </div>
			                                    </div>
			                                </c:forEach>
			                            </div>
		                        </div>
                    		</div>
                   		</div>
                	</div>
                    		</div>
                    		</div>
                    <!-- /.col -->

                    <div class="col-md-3">
                        <div class="d-none d-lg-block card card-footer-compact card-comments ">
                            <div class="card-header-compact">
                                <h5><b>Giveups You May Like</b></h5>
                            </div>
                            <!-- /.card-header -->
                            <c:forEach var="giveup" items="${suggestedGiveups}">
	                            <div class="card-comment">
	                                <!-- User image -->
	                                <img class="img-circle img-sm" src="/img/user3-128x128.jpg" alt="User
	                                Image">
	
	                                <div class="comment-text">
	                                    <span class="username">
	                                        ${giveup.name}
	                                        <button type="button" onclick="like(${giveup.id})" class=" float-right follow-button btn btn-outline-primary btn-xs">Like</button>
	                                    </span><!-- /.username -->
	
	                                </div>
	                                <!-- /.comment-text -->
	                            </div>
	                            <!-- /.card-body -->
	                        </c:forEach>
                            <div class="card-comment center card-body-compact">
                                <p class="text-primary">Show more</p>
                            </div>
                        </div>
                        <div class="d-none d-lg-block card card-footer-compact card-comments ">
                            <div class="card-header-compact">
                                <h5><b>Giveups You May Follow</b></h5>
                            </div>
                            <!-- /.card-header -->
                            <c:forEach var="giveup" items="${suggestedGiveupsforFollow}">
	                            <div class="card-comment">
	                                <!-- User image -->
	                                <img class="img-circle img-sm" src="/img/user3-128x128.jpg" alt="User
	                                Image">
	
	                                <div class="comment-text">
	                                    <span class="username">
	                                        ${giveup.name}
	                                        <button type="button" onclick="follow(${giveup.id})" class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
	                                    </span><!-- /.username -->
	
	                                </div>
	                                <!-- /.comment-text -->
	                            </div>
	                            <!-- /.card-body -->
	                        </c:forEach>
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
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.js"
		integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
		crossorigin="anonymous"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="<c:url value="/js/demo.js" />"></script>
    <script type="text/javascript">
    	function like(id){
    		var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			console.log(token);
			console.log(header);
			var test = "test";
        	$.ajax({
            	url:'/home/giveup/like/'+id,
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
                console.log("GiveUp Liked");
                setInterval('location.reload()', 2000);
                });
        }

    	function unlike(id){
        	var token = $("meta[name='_csrf']").attr("content");
        	var header = $("meta[name='_csrf_header']").attr("content");
        	$.ajax({
            	url:'/home/giveup/unlike/'+id,
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
                console.log("GiveUp UnLiked");
                setInterval('location.reload()', 2000);
                });
        }

    	function follow(id){
        	var token = $("meta[name='_csrf']").attr("content");
        	var header = $("meta[name='_csrf_header']").attr("content");
        	$.ajax({
            	url:'/home/giveup/follow/'+id,
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
                console.log("GiveUp followed");
                setInterval('location.reload()', 2000);
                });
        }

    	function unfollow(id){
        	var token = $("meta[name='_csrf']").attr("content");
        	var header = $("meta[name='_csrf_header']").attr("content");
        	$.ajax({
            	url:'/home/giveup/unfollow/'+id,
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
                console.log("GiveUp unfollowed");
                setInterval('location.reload()', 2000);
                });
        }
    </script>
</body>

</html>