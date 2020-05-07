<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Wegual | Pledge</title>
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
                        <div class="row" style="padding: 1%;">
                                <h1>Pledges Taken By User</h1>
                            <div class="col-md-12">
                                <c:forEach var="pledge" items="${pledges}">
                                    <div class="row elevation-1" style="padding: 1%;">
                                        <div class="col-md-4">
                                            <img id="followeePic" src="/img/user4-128x128.jpg"
                                                class="img-circle elevation-2" alt="User Image">
                                        </div>
                                        <div class="col-md-6">
                                            <a href="/beneficiary/profile/${followee.id}">
                                                <h3>${pledge.beneficiary.name}</h3>
                                            </a>
                                            <h4>${pledge.currency} ${pledge.amount}</h4>
                                            <h4>Status : Pending</h4>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <!-- /.col -->

                    <div class="col-md-3">
                        <div class="d-none d-lg-block card card-footer-compact card-comments ">
                            <div class="card-header-compact">
                                <h5><b>Who to Follow</b></h5>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-comment">
                                <!-- User image -->
                                <img class="img-circle img-sm" src="/img/user3-128x128.jpg" alt="User
                                Image">

                                <div class="comment-text">
                                    <span class="username">
                                        Maria Gonzales
                                        <button type="button"
                                            class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
                                    </span><!-- /.username -->

                                </div>
                                <span class="text-muted"><i class="fas fa-user-friends"></i>&nbsp;7 Followers</span>
                                <span class="text-muted"><i class="fas fa-hand-holding-usd"></i>&nbsp;$1350
                                    Pledged</span>
                                <!-- /.comment-text -->
                            </div>
                            <!-- /.card-body -->
                            <div class="card-comment">
                                <!-- User image -->
                                <img class="img-circle img-sm" src="/img/user5-128x128.jpg" alt="User
                                Image">

                                <div class="comment-text">
                                    <span class="username">
                                        Serena Gomez
                                        <button type="button"
                                            class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
                                    </span><!-- /.username -->
                                </div>
                                <span class="text-muted"><i class="fas fa-user-friends"></i>&nbsp;17 Followers</span>
                                <span class="text-muted"><i class="fas fa-hand-holding-usd"></i>&nbsp;$135
                                    Pledged</span>
                                <!-- /.comment-text -->
                            </div>
                            <div class="card-comment">
                                <!-- User image -->
                                <img class="img-circle img-sm" src="/img/user6-128x128.jpg" alt="User
                                Image">

                                <div class="comment-text">
                                    <span class="username">
                                        Joaqin Phoenix
                                        <button type="button"
                                            class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
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
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="./js/demo.js"></script>
</body>

</html>