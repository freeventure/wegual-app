<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Wegual | User Profile</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Font Awesome -->
  <link rel="stylesheet" href="<c:url value="/plugins/fontawesome-free/css/all.min.css" />">
  <!-- Ionicons -->
  <!-- Theme style -->
  <link rel="stylesheet" href="<c:url value="/css/adminlte.min.css" />">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
</head>
<body class="sidebar-mini sidebar-collapse">
<div class="wrapper">
  <!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      
      <li class="nav-item d-none d-sm-inline-block">
        <a href="../../index3.html" class="nav-link">Home</a>
      </li>
    </ul>

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
        <a class="nav-link" href="<c:url value="/logout" />">
          <ion-icon class="far" name="exit-outline"></ion-icon>
        </a>
	  </li>
    </ul>
  </nav>
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="../../index3.html" class="brand-link">
    
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
          <img src="<c:url value="/img/user2-160x160.jpg" />" class="img-circle elevation-2" alt="User Image">
        </div>
        <div class="info">
          <a href="#" class="d-block">${userProfileData.user.firstName} ${userProfileData.user.lastName}</a>
        </div>
      </div>

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <li class="nav-item">
            <a href="#" class="nav-link">
			  <ion-icon class="nav-icon fas" name="home-outline" size="large"></ion-icon>
              <p>
                Home
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
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>Profile</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">User Profile</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-3">

            <!-- Profile Image -->
            <div class="card card-primary card-outline">
              <div class="card-body box-profile">
                <div class="text-center">
                  <img class="profile-user-img img-fluid img-circle"
                       src="<c:url value="/img/user2-160x160.jpg" />"
                       alt="User profile picture">
                </div>

                <h3 class="profile-username text-center">${userProfileData.user.firstName} ${userProfileData.user.lastName}</h3>

                <p class="text-muted text-center">Software Engineer</p>

                <ul class="list-group list-group-unbordered mb-3">
                  <li class="list-group-item">
                    <b>Followers</b> <a class="float-right">${userProfileData.counts.followers}</a>
                  </li>
                  <li class="list-group-item">
                    <b>Following</b> <a class="float-right">${userProfileData.counts.following}</a>
                  </li>
                  <li class="list-group-item">
                    <b>Beneficiaries</b> <a class="float-right">${userProfileData.counts.beneficiaries}</a>
                  </li>
                  <li class="list-group-item">
                    <b>Give ups</b> <a class="float-right">${userProfileData.counts.giveups}</a>
                  </li>
				</ul>
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->

            <!-- About Me Box -->
            <div class="card card-primary">
              <div class="card-header">
                <h3 class="card-title">About Me</h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
                <strong><i class="fas fa-book mr-1"></i> Education</strong>

                <p class="text-muted">
                  B.S. in Computer Science from the University of Tennessee at Knoxville
                </p>

                <hr>

                <strong><i class="fas fa-map-marker-alt mr-1"></i> Location</strong>

                <p class="text-muted">Malibu, California</p>
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col -->
          <div class="col-md-9">
            <div class="card">
              <div class="card-header p-2">
                <ul class="nav nav-pills">
                  <li class="nav-item"><a class="nav-link active" href="#settings" data-toggle="tab">Settings</a></li>
				  <li class="nav-item"><a class="nav-link" href="#timeline" data-toggle="tab">Timeline</a></li>
                </ul>
              </div><!-- /.card-header -->
              <div class="card-body">
                <div class="tab-content">
                  <!-- /.tab-pane -->
                  <div class="tab-pane" id="timeline">
                    <!-- The timeline -->
					<div class="timeline timeline-inverse">
                    <c:forEach var="entry" items="${timeline}">
  					  <!-- Key represents date box -->	
                      <div class="time-label">
                        <span class="bg-light">
                          <c:out value="${entry.key}"/>
                        </span>
                      </div>
                      <c:forEach var="timelineElement" items="${entry.value}">
						<div>
	                        <i class="fas ${timelineElement.iconName} ${timelineElement.iconColor}"></i>
	
	                        <div class="timeline-item">
	                          <span class="time"><i class="far fa-clock"></i>${timelineElement.timeAgo}</span>
	                          <h3 class="timeline-header">${timelineElement.summary}</h3>
							  <c:if test="${timelineElement.hasDetail}">
		                          <div class="timeline-body">
		                            ${timelineElement.detail}
		                          </div>
		                          <div class="timeline-footer">
		                          	<c:if test="${timelineElement.showView}">
		                            	<a href="#" class="btn btn-primary btn-sm">View</a>
		                            </c:if>
		                            <c:if test="${timelineElement.showShare}">
		                            	<a href="#" class="btn btn-danger btn-sm">Share</a>
		                            </c:if>
		                          </div>
	                          </c:if>
	                        </div>
                      </div>                      
                      </c:forEach>
					</c:forEach>
                      <div>
						<a class="far bg-gray" href="#">
                        <i class="far fa-clock"></i>
						</a>
                      </div>
                    </div>
                  </div>
                  <!-- /.tab-pane -->

                  <div class="active tab-pane" id="settings">
                    <form class="form-horizontal">
                      <div class="form-group row">
                        <label for="inputName" class="col-sm-2 col-form-label">First Name</label>
                        <div class="col-sm-10">
                          <input type="email" class="form-control" id="inputName" value="${userProfileData.user.firstName}" placeholder="Name">
                        </div>
                      </div>
                      <div class="form-group row">
                        <label for="inputName2" class="col-sm-2 col-form-label">Last Name</label>
                        <div class="col-sm-10">
                          <input type="text" class="form-control" id="inputName2" value=" ${userProfileData.user.lastName}" placeholder="Name">
                        </div>
                      </div>
                      <div class="form-group row">
                        <label for="inputTitle" class="col-sm-2 col-form-label">Title</label>
                        <div class="col-sm-10">
                          <input type="text" class="form-control" id="inputTitle" placeholder="Name">
                        </div>
                      </div>
                      <div class="form-group row">
                        <label for="inputEmail" class="col-sm-2 col-form-label">Email</label>
                        <div class="col-sm-10">
                          <input type="email" class="form-control" id="inputEmail" value=" ${userProfileData.user.email}" placeholder="Email">
                        </div>
                      </div>
                      <div class="form-group row">
                        <label for="inputAboutMe" class="col-sm-2 col-form-label">Description</label>
                        <div class="col-sm-10">
                          <textarea class="form-control" id="inputAboutMe" placeholder="About Me"></textarea>
                        </div>
                      </div>
                      <div class="form-group row">
                        <div class="offset-sm-2 col-sm-10">
                          <button type="submit" class="btn btn-danger">Save</button>
                        </div>
                      </div>
                    </form>
                  </div>
                  <!-- /.tab-pane -->
                </div>
                <!-- /.tab-content -->
              </div><!-- /.card-body -->
            </div>
            <!-- /.nav-tabs-custom -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <div class="float-right d-none d-sm-block">
      <b>Version</b> 3.0.2
    </div>
    <strong>Copyright &copy; 2014-2019 <a href="http://adminlte.io">AdminLTE.io</a>.</strong> All rights
    reserved.
  </footer>

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
</body>
</html>
