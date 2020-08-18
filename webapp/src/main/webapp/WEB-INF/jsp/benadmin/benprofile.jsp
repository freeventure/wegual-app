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
 <div class = "row">
  <div class = "col-md-3">
   <div class="card card-primary card-outline">
    <div class="card-body box-profile">
     <h3 class="profile-username text-center">${beneficiaryProfileData.beneficiary.name}</h3>
     <p class="text-muted text-center">${beneficiaryProfileData.beneficiary.beneficiaryType}</p>
     <ul class="list-group list-group-unbordered mb-3">
       <li class="list-group-item">
         <b>Followers</b> <a class="float-right">${beneficiaryProfileData.beneficiarySnapshot.userCount}</a>
       </li>
       <li class="list-group-item">
         <b>Pledges</b> <a class="float-right">${beneficiaryProfileData.beneficiarySnapshot.pledgesCount}</a>
       </li>
       <li class="list-group-item">
         <b>Give Ups</b> <a class="float-right">${beneficiaryProfileData.beneficiarySnapshot.giveUpCount}</a>
       </li>
       <li class="list-group-item">
         <b>Amount Pledged</b>
           <ul class="list-group">
             <c:forEach var="currency" items="${beneficiaryProfileData.beneficiarySnapshot.amountByCurrency.keySet()}">
             <li class="list-group-item">
             	${currency} : ${beneficiaryProfileData.beneficiarySnapshot.amountByCurrency.get(currency)} 
             </li>
             </c:forEach>
           </ul>
          <a class="float-right"></a>
       </li>
	 </ul>
    </div>
   </div>          
  </div>
  <div class = "col-md-9">
    <div class="container-fluid">
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
                  </div>
                  <!-- /.tab-pane -->
                </div>
                <!-- /.tab-content -->
              </div><!-- /.card-body -->
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