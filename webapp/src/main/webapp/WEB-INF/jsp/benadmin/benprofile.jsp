<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

 <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>Beneficiary Profile Page</title>

    <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Font Awesome -->
  <link rel="stylesheet" href="<c:url value="/plugins/fontawesome-free/css/all.min.css" />">
  
  <!-- Theme style -->
  <link rel="stylesheet" href="<c:url value="/css/adminlte.css" />">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
  <style>
  	.feed {
    	margin-left: 25%;
	}
  </style>
 </head>

<body style="background-color: mintcream">
    <div class="container-fluid">
        <div class="col-md-6 feed">
            <div class="row" style="margin-top: -5%; margin-bottom: 1%">
                <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="/img/photo1.png" class="d-block w-100" style="height: inherit">
                        </div>
                        <div class="carousel-item">
                            <img src="/img/photo2.png" class="d-block w-100" style="height: inherit">
                        </div>
                        <div class="carousel-item">
                            <img src="/img/photo4.jpg" class="d-block w-100" style="height: inherit">
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
           <div class="row">
      			<h1>${beneficiaryProfileData.beneficiary.name}</h1>
           </div>
           </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="info-box">
                        <span class="info-box-icon bg-info"><i class="fas fa-hand-holding-usd"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text ">Pledges</span>
                            <span class="info-box-number">${beneficiaryProfileData.beneficiarySnapshot.pledgesCount}</span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
                <!-- /.col -->
                <div class="col-md-4">
                    <div class="info-box">
                        <span class="info-box-icon bg-success"><i class="fas fa-user-friends"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">Followers</span>
                            <span class="info-box-number">${beneficiaryProfileData.beneficiarySnapshot.userCount}</span>
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
                            <span class="info-box-number">${beneficiaryProfileData.beneficiarySnapshot.giveUpCount}</span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
                <!-- /.col -->

            </div>
            <div class="card card-footer card-comments">
                <div class="card-comment">
                    <!-- User image -->
                    <img class="img-circle img-sm" src="/img/user3-128x128.jpg" alt="User Image">

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
                    <img class="img-circle img-sm" src="/img/user3-128x128.jpg" alt="User Image">

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
                    <img class="img-circle img-sm" src="/img/user3-128x128.jpg" alt="User Image">

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
                    <img class="img-circle img-sm" src="/img/user3-128x128.jpg" alt="User Image">

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
                    <img class="img-circle img-sm" src="/img/user3-128x128.jpg" alt="User Image">

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
                    <img class="img-circle img-sm" src="/img/user3-128x128.jpg" alt="User Image">

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
            <div class="d-none d-lg-block card card-footer-compact card-comments">
                <div class="card-header-compact">
                    <h5><b>Benficiary You May Like</b></h5>
                </div>
                <!-- /.card-header -->
                <div class="card-comment">
                    <!-- User image -->
                    <img class="img-circle img-sm" src="/img/user3-128x128.jpg" alt="User Image">

                    <div class="comment-text">
                                    <span class="username">
                                      Maria Gonzales
                                      <button type="button"
                                              class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
                                    </span><!-- /.username -->

                    </div>
                    <span class="text-muted"><i class="fas fa-user-friends"></i>&nbsp;7 Followers</span>
                    <span class="text-muted"><i class="fas fa-hand-holding-usd"></i>&nbsp;$1350 Pledged</span>
                    <!-- /.comment-text -->
                </div>
                <!-- /.card-body -->
                <div class="card-comment">
                    <!-- User image -->
                    <img class="img-circle img-sm" src="/img/user5-128x128.jpg" alt="User Image">

                    <div class="comment-text">
                                    <span class="username">
                                      Serena Gomez
                                        <button type="button"
                                                class=" float-right follow-button btn btn-outline-primary btn-xs">Follow</button>
                                    </span><!-- /.username -->
                    </div>
                    <span class="text-muted"><i class="fas fa-user-friends"></i>&nbsp;17 Followers</span>
                    <span class="text-muted"><i class="fas fa-hand-holding-usd"></i>&nbsp;$135 Pledged</span>
                    <!-- /.comment-text -->
                </div>
                <div class="card-comment">
                    <!-- User image -->
                    <img class="img-circle img-sm" src="/img/user6-128x128.jpg" alt="User Image">

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

    </div>
    </div>
    </div>

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


    <script type="text/javascript">
        $(document).ready(function () {
            $('#sidebarCollapse').on('click', function () {
                $('#sidebar').toggleClass('active');
            });
        });
    </script>
</body>

</html>