<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>WEGUAL</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="assets/img/favicon.png" rel="icon">
  <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i|Poppins:300,400,500,700" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="<c:url value="/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
  <link href="<c:url value="/vendor/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">
  <link href="<c:url value="/vendor/boxicons/css/boxicons.min.css" />" rel="stylesheet">
  <link href="<c:url value="/vendor/owl.carousel/assets/owl.carousel.min.css" />" rel="stylesheet">
  <link href="<c:url value="/vendor/venobox/venobox.css" />" rel="stylesheet">
  <link href="<c:url value="/vendor/aos/aos.css" />" rel="stylesheet">

  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
  <!-- Template Main CSS File -->
  <link href="<c:url value="/css/style.css" />" rel="stylesheet">

  <!-- =======================================================
  * Template Name: Regna - v2.2.1
  * Template URL: https://bootstrapmade.com/regna-bootstrap-onepage-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
</head>

<body>

  <!-- ======= Header ======= -->
  <header id="header" class="header-transparent">
    <div class="container">

      <div id="logo" class="pull-left">
        <a href="index.html"><img src="assets/img/weguallogo.png" alt=""></a>
        <!-- Uncomment below if you prefer to use a text logo -->
        <!--<h1><a href="#hero">Regna</a></h1>-->
      </div>

      <nav id="nav-menu-container">
        <ul class="nav-menu">
          <li class="menu-active"><a href="index.html">Home</a></li>
          <li><a href="#about">About Us</a></li>
          <li><a href="#services">Assets</a></li>
          <li><a href="<c:url value="/home" />">Login</a></li>
        </ul>
      </nav><!-- #nav-menu-container -->
    </div>
  </header><!-- End Header -->

  <!-- ======= Hero Section ======= -->
  <section id="hero">
    <div class="hero-container" data-aos="zoom-in" data-aos-delay="100">
      <h1>We Give Up a Little</h1>
      <h2>Social Platform for Influencing Giving</h2>
      <a href="#about" class="btn-get-started">Get Started</a>
    </div>
  </section><!-- End Hero Section -->

  <main id="main">

    <!-- ======= About Section ======= -->
    <section id="about">
      <div class="container" data-aos="fade-up">
        <div class="row about-container">

          <div class="col-lg-6 content order-lg-1 order-2">
            <h2 class="title">What's WEGUAL?</h2>
            <p>
              Wegual is a social platform for encouraging and facilitating charitable crowdfunding through behavioral change.
            </p>
            <p>
              Each one of us at some point in our life has done some sort of unnecessary expenditure which we know could have been avoided. Why not let's identify all such occasions where we do so and take a pledge to cross-cut those expenses and donate the same to the organizations who have been working hard for the betterment of humanity all around the globe.
            </p>


          </div>

          <div class="col-lg-6 background order-lg-2 order-1" data-aos="fade-left" data-aos-delay="100"></div>
        </div>

      </div>
    </section><!-- End About Section -->

    <!-- ======= Facts Section ======= -->
    <section id="facts">
      <div class="container" data-aos="fade-up">
        <div class="section-header">
          <h3 class="section-title">Wegual Stat's</h3>
          <p class="section-description"></p>
        </div>
        <div class="row counters">

          <div class="col-lg-3 col-6 text-center">
            <span data-toggle="counter-up">${user}</span>
            <p>Users</p>
          </div>

          <div class="col-lg-3 col-6 text-center">
            <span data-toggle="counter-up">${beneficiary}</span>
            <p>Beneficiaries</p>
          </div>

          <div class="col-lg-3 col-6 text-center">
            <span data-toggle="counter-up">${pledge}</span>
            <p>Pledges</p>
          </div>

          <div class="col-lg-3 col-6 text-center">
            <span data-toggle="counter-up">${giveup}</span>
            <p>Giveup's</p>
          </div>

        </div>

      </div>
    </section><!-- End Facts Section -->

    <!-- ======= Services Section ======= -->
    <section id="services">
      <div class="container" data-aos="fade-up">
        <div class="section-header">
          <h3 class="section-title">Our Assets</h3>
          <p class="section-description"></p>
        </div>
        <div class="row">

          <div class="col-lg-4 col-md-6" data-aos="zoom-in">
            <div class="box">
              <div class="icon"><a href=""><i class="fas fa-hand-holding-usd"></i></a></div>
              <h4 class="title"><a href="">Beneficiary</a></h4>
              <p class="description">It is any organization or a community that has been dedicatedly working to make a social impact to justice the existence of humanity.</p>
            </div>
          </div>
          <div class="col-lg-4 col-md-6" data-aos="zoom-in">
            <div class="box">
              <div class="icon"><a href=""><i class="fas fa-praying-hands"></i></a></div>
              <h4 class="title"><a href="">Giveup</a></h4>
              <p class="description">Crowdfunding might not sound feasible at times for many but cross-cutting unnecessary expenses at certain occasions such as Birthday Parties, New Year Party and many more might be the point for generating funds.</p>
            </div>
          </div>
          <div class="col-lg-4 col-md-6" data-aos="zoom-in">
            <div class="box">
              <div class="icon"><a href=""><i class="fas fa-hand-holding-heart"></i></a></div>
              <h4 class="title"><a href="">Pledge</a></h4>
              <p class="description">Once you have identified a giveup that you can follow to gather funds you can now pledge a certain amount you wish to donate to a beneficiary under a particular giveup. Hurray! you have become a part of social good.</p>
            </div>
          </div>

        </div>

      </div>
    </section><!-- End Services Section -->





  </main><!-- End #main -->

  <!-- ======= Footer ======= -->
  <footer id="footer">
    <div class="footer-top">
      <div class="container">

      </div>
    </div>

    <div class="container">
      <div class="copyright">
        &copy; Copyright <strong>WEGUAL</strong>. All Rights Reserved
      </div>
      
    </div>
  </footer><!-- End Footer -->

  <a href="#" class="back-to-top"><i class="fa fa-chevron-up"></i></a>


  <!-- Vendor JS Files -->
  <script src="<c:url value="/vendor/jquery/jquery.min.js" />"></script>
  <script src="<c:url value="/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
  <script src="<c:url value="/vendor/jquery.easing/jquery.easing.min.js" />"></script>
  <script src="<c:url value="/vendor/php-email-form/validate.js" />"></script>
  <script src="<c:url value="/vendor/counterup/counterup.min.js" />"></script>
  <script src="<c:url value="/vendor/waypoints/jquery.waypoints.min.js" />"></script>
  <script src="<c:url value="/vendor/isotope-layout/isotope.pkgd.min.js" />"></script>
  <script src="<c:url value="/vendor/superfish/superfish.min.js" />"></script>
  <script src="<c:url value="/vendor/hoverIntent/hoverIntent.js" />"></script>
  <script src="<c:url value="/vendor/owl.carousel/owl.carousel.min.js" />"></script>
  <script src="<c:url value="/vendor/venobox/venobox.min.js" />"></script>
  <script src="<c:url value="/vendor/aos/aos.js" />"></script>

  <!-- Template Main JS File -->
  <script src="<c:url value="/js/main.js" />"></script>

</body>

</html>