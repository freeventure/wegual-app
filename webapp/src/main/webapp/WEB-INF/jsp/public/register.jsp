<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Register for Wegual Account</title>
<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
        crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
<style type="text/css">
	body {
		background: #dfe7e9;
		font-family: 'Roboto', sans-serif;
	}
	
	.error-message {
		font-size: 12px;
		font-color: red;
	}
    .form-control {
		font-size: 12px;
		transition: all 0.4s;
		box-shadow: none;
	}
	.form-control:focus {
        border-color: #5cb85c;
    }
    .form-control, .btn {
        border-radius: 50px;
		outline: none !important;
    }
	.signup-form {
		width: 400px;
    	margin: 0 auto;
		padding: 20px 0;
	}
    .signup-form form {
		border-radius: 5px;
    	margin-bottom: 10px;
        background: #fff;
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        padding: 20px;
    }
	.signup-form a {
		color: #5cb85c;
	}    
	.signup-form h2 {
		text-align: center;
		font-size: 24px;
		margin: 10px 0 15px;
	}
	.signup-form .hint-text {
		color: #999;
		font-size: 12px;
		text-align: center;
		margin-bottom: 10px;
	}
	.signup-form .form-group {
		margin-bottom: 10px;
	}
    .signup-form .btn {        
        font-size: 14px;
		line-height: 24px;
        font-weight: bold;
		text-align: center;
    }
	.signup-btn {
		text-align: center;
		border-color: #5cb85c;
		margin-top: 10px;
		transition: all 0.4s;
	}
	.signup-btn:hover {
		opacity: 0.8;
	}
    .or-seperator {
        margin: 30px 0 0;
        text-align: center;
        border-top: 1px solid #e0e0e0;
    }
    .or-seperator b {
        padding: 0 10px;
		width: 40px;
		height: 40px;
		font-size: 16px;
		text-align: center;
		line-height: 40px;
		background: #fff;
		display: inline-block;
        border: 1px solid #e0e0e0;
		border-radius: 50%;
        position: relative;
        top: -22px;
        z-index: 1;
    }
    .social-btn .btn {
		color: #fff;
        margin: 5px 0 0 5px;
		font-size: 12px;
		border-radius: 50px;
		font-weight: normal;
		border: none;
		transition: all 0.4s;
    }	
	.social-btn .btn:first-child {
		margin-left: 0;
	}
	.social-btn .btn:hover {
		opacity: 0.8;
	}
	.social-btn .btn-primary {
		background: #507cc0;
	}
	.social-btn .btn-info {
		background: #64ccf1;
	}
	.social-btn .btn-danger {
		background: #df4930;
	}
	.social-btn .btn i {
		float: left;
		margin: 3px 10px;
		font-size: 12px;
	}
</style>
</head>
<body>
<div class="signup-form">
    <form:form action="/public/signup" method="post" modelAttribute="registerAccount">
		<h2 class="my-1">Create an Account</h2>
		<p class="hint-text">Sign up with your social media account or email address</p>
		<div class="social-btn text-center">
			<a href="#" class="btn btn-primary btn-lg"><i class="fa fa-facebook"></i> Facebook</a>
			<a href="#" class="btn btn-info btn-lg"><i class="fa fa-twitter"></i> Twitter</a>
			<a href="#" class="btn btn-danger btn-lg"><i class="fa fa-google"></i> Google</a>
		</div>
		<div class="or-seperator"><b>or</b></div>
        <div class="form-group">
        	<form:input type="text" class="form-control input-lg" path="firstName" placeholder="First Name" required="required" />
        </div>
        <div class="form-group">
        	<form:input type="text" class="form-control input-lg" path="lastName" placeholder="Last Name" required="required" />
        </div>
        <div class="form-group">
        	<form:input type="text" class="form-control input-lg" path="username" placeholder="Username" required="required" />
        </div>
		<div class="form-group">
        	<form:input type="email" class="form-control input-lg" path="email" placeholder="Email Address" required="required" />
        	<form:errors path="email" cssClass="error-message" />
        </div>
		<div class="form-group">
            <form:input type="password" class="form-control input-lg" path="password" placeholder="Password" required="required" />
            <form:errors path="password" cssClass="error-message" />
        </div>
		<div class="form-group">
            <form:input type="password" class="form-control input-lg" path="confirmPassword" placeholder="Confirm Password" required="required" />
            <form:errors path="confirmPassword" cssClass="error-message" />
        </div>  
        <div class="form-group my-3">
            <button type="submit" class="btn btn-outline-primary btn-lg btn-block signup-btn">Sign Up</button>
        </div>
        <div class="text-center my-0">Already have an account? <a href="/home">Login here</a></div>
    </form:form>
    
</div>
</body>
</html>